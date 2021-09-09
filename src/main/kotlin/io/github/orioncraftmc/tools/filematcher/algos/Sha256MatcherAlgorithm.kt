package io.github.orioncraftmc.tools.filematcher.algos

import java.nio.file.FileSystem
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.readBytes

object Sha256MatcherAlgorithm : MatcherAlgorithm {
    val shaAlgorithm: MessageDigest = MessageDigest.getInstance("SHA-256")

    override fun describeFile(fs: FileSystem, path: Path, root: Path): String {
        return shaAlgorithm.digest(path.readBytes()).joinToString("") {
            "%02x".format(it)
        }
    }
}