package io.github.orioncraftmc.tools.filematcher.algos

object MatcherAlgorithmManager {
    val algorithms = listOf(
        NameMatcherAlgorithm,
        Sha256MatcherAlgorithm
    )
}