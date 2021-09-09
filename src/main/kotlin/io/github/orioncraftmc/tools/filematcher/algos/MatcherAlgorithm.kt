package io.github.orioncraftmc.tools.filematcher.algos

import java.nio.file.FileSystem
import java.nio.file.Path

interface MatcherAlgorithm {

    fun describeFile(fs: FileSystem, path: Path, root: Path): String
}