fun main() {
    fun part1(input: List<String>) = input.flatMap {
        it.split(',')
    }.flatMap { s ->
        val (first, last) = s.split('-').map(String::toLong)
        (first..last).filter { lng ->
            lng.toString().let { it.length % 2 == 0 && it.chunked(it.length / 2).toHashSet().size == 1 }
        }
    }.sum()


    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
