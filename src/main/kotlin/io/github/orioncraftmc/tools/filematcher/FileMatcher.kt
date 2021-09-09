package io.github.orioncraftmc.tools.filematcher

import io.github.orioncraftmc.tools.filematcher.algos.MatcherAlgorithm
import io.github.orioncraftmc.tools.filematcher.algos.MatcherAlgorithmManager
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.time.LocalDateTime
import kotlin.io.path.writer

class FileMatcher(
    path1: Path,
    path2: Path,
    private val outputMatchPath: Path,
    private val basePath1: String,
    private val basePath2: String
) {

    private val fs1: FileSystem = FileSystems.newFileSystem(path1)
    private val fs2: FileSystem = FileSystems.newFileSystem(path2)

    private val globalMatches = mutableListOf<List<FileMatch>>()

    fun log(msg: Any, level: String = "INFO") {
        val now = LocalDateTime.now()
        println(
            "[${now.hour.toString().padStart(2, '0')}:${
                now.minute.toString().padStart(2, '0')
            }:${now.second.toString().padStart(2, '0')}] $level - FileMatcher: $msg"
        )
    }
    fun warn(msg: Any) {
        log(msg, "WARN")
    }

    fun doMatch() {
        log("Preparing to match")
        MatcherAlgorithmManager.algorithms.forEach { algorithm ->
            log("Matching files with ${algorithm.javaClass.simpleName}")

            val (matches, time) = measureTimeMillisWithResult {
                val fs1DescriptionMap = walkFileSystemAndGetMatches(fs1, algorithm, basePath1)
                val fs2DescriptionMap = walkFileSystemAndGetMatches(fs2, algorithm, basePath2)

                fs2DescriptionMap.mapNotNull { description ->
                    fs1DescriptionMap[description.key]?.let {
                        FileMatch(
                            it,
                            description.value,
                            description.key
                        )
                    }
                }
            }
            log("Successfully matched ${matches.count()} files with ${algorithm.javaClass.simpleName} in ${time}ms")

            globalMatches.add(matches)
        }

        log("Merging all matches")

        val mergedMatches = globalMatches.flatten().distinctBy { it.path2 }.sortedBy { it.path1.fileName }

        log("Successfully merged all matches resulting in ${mergedMatches.count()} matches")

        outputMatchPath.writer(charset("UTF-8")).use {
            val lastIndex = mergedMatches.indices.last
            mergedMatches.forEachIndexed { i, match ->
                it.append("${match.path1}->${match.path2}")
                if (i != lastIndex) it.appendLine()
            }
        }

        log("Successfully output ${mergedMatches.count()} matches to ${outputMatchPath}")
    }

    private fun walkFileSystemAndGetMatches(
        fileSystem: FileSystem,
        algorithm: MatcherAlgorithm,
        basePath: String
    ): MutableMap<String, Path> {
        // maps File Description -> File Path
        val fileDescriptionMap = mutableMapOf<String, Path>()
        val root = fileSystem.getPath("/${basePath.removePrefix("/")}")
        Files.walkFileTree(root, object : SimpleFileVisitor<Path>() {
            override fun visitFile(file: Path, attrs: BasicFileAttributes?): FileVisitResult {
                val fileDescription = algorithm.describeFile(fileSystem, file, root)
                if (fileDescriptionMap[fileDescription] != null) {
                    warn("Found collision for description $fileDescription: ${fileDescriptionMap[fileDescription]} and $file")
                }
                fileDescriptionMap[fileDescription] = file
                return FileVisitResult.CONTINUE
            }
        })

        return fileDescriptionMap
    }

}

inline fun <T> measureTimeMillisWithResult(block: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val result = block()
    return result to System.currentTimeMillis() - start
}
