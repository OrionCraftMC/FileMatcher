package io.github.orioncraftmc.tools.filematcher

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.types.path

class MatchCommand : CliktCommand("Matches two different files/directories and outputs a match file that maps one side to the other", name = "FileMatcher") {
    val path1 by argument(help = "The path for the first file/directory to compare with").path(mustExist = true)
    val path2 by argument(help = "The path for the second file/directory to compare with").path(mustExist = true)

    val outputMatchPath by argument(help = "The output file with the file matches").path(canBeDir = false)

    val basePath1 by argument(help = "The root/base path for the first file/directory").default("/")
    val basePath2 by argument(help = "The root/base path for the second file/directory").default("/")

    override fun run() {
        FileMatcher(path1, path2, outputMatchPath, basePath1, basePath2).doMatch()
    }

}

fun main(args: Array<String>) {
    MatchCommand().main(args)
}