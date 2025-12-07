fun main() {
    fun part1(input: List<String>): Int {
        val i = input.indexOfFirst(String::isEmpty)
        val ranges = input.take(i).map { it.split('-').let { (l, r) -> l.toLong()..r.toLong() } }
        val ids = input.drop(i + 1).map(String::toLong)
        return ids.count { id -> ranges.any { id in it } }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    // check(part2(testInput) == 1)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
