package io.github.orioncraftmc.tools.filematcher.algos

import java.nio.file.FileSystem
import java.nio.file.Path
import kotlin.io.path.relativeTo

object NameMatcherAlgorithm : MatcherAlgorithm {
    override fun describeFile(fs: FileSystem, path: Path, root: Path): String {
        return path.relativeTo(path.parent.parent ?: root).toString()
    }
}