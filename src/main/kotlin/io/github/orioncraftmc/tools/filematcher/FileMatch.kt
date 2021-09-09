package io.github.orioncraftmc.tools.filematcher

import java.nio.file.Path

data class FileMatch(val path1: Path, val path2: Path, val description: String)