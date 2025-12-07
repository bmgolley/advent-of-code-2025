fun main() {
    fun part1(input: List<String>): Long {
        val data = input.map { it.split(' ').filter(String::isNotEmpty) }
        val n = data.map(List<*>::lastIndex).toSet().single()
        return (0..n).sumOf { i ->
            val problem = data.map { it[i] }
            val numbers = problem.dropLast(1).map(String::toLong)
            when (val op = problem.last()) {
                "*" -> numbers.reduce { acc, n -> n * acc }
                "+" -> numbers.sum()
                else -> error("invalid operator '$op'")
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    // check(part2(testInput) == 1)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
