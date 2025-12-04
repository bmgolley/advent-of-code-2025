fun main() {
    fun part1(input: List<String>) = input.single().split(',').flatMap { s ->
        val (first, last) = s.split('-').map(String::toLong)
        (first..last).filter { lng ->
            lng.toString().let { it.length % 2 == 0 && it.chunked(it.length / 2).toHashSet().size == 1 }
        }
    }.sum()


    fun part2(input: List<String>): Long {
        // yes, regex is a cop-out, but I couldn't think of a better way that wouldn't be a mess
        val regex = """^(\d+)\1+$""".toRegex()
        val ids = input.single().split(',').flatMap { s ->
            val (first, last) = s.split('-').map(String::toLong)
            (first..last).filter { lng -> lng.toString() matches regex }
        }
        return ids.sum()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
