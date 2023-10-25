package utils

fun runBlockAndBenchmark(block: () -> String): String {

    val startTime = System.currentTimeMillis()

    val result = block.invoke()

    val endTime = System.currentTimeMillis()

    val executionTime = endTime - startTime

    return "$result \n execution time: $executionTime milis"

}