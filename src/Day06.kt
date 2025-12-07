fun main() {
    fun part1(input: List<String>): Long {
        val data = input.map { it.split(' ').filterNot(String::isEmpty) }
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

    fun part2(input: List<String>): Long {
        val data = with(input) {
            val maxLength = maxOf(String::length)
            map { it.padEnd(maxLength) }
        }
        val operatorRow = input.last()
        val indicies = operatorRow.mapIndexedNotNull { i, c -> if (!c.isWhitespace()) i else null }
        val numberRows = data.dropLast(1)
        val numberData = indicies.zipWithNext { a, b ->
            numberRows.map { it.slice(b - 2 downTo a) }
        } + listOf(numberRows.map { it.substring(indicies.last()) })
        val operators = operatorRow.split(' ').filterNot(String::isEmpty)
        return numberData.zip(operators).asReversed().sumOf { (strings, op) ->
            val numbers = strings.first().indices.map { i ->
                strings.mapNotNull { it[i].takeUnless(Char::isWhitespace) }.joinToString("").toLong()
            }
            val value = when (op) {
                "*" -> numbers.reduce { acc, n -> n * acc }
                "+" -> numbers.sum()
                else -> error("invalid operator '$op'")
            }
            value
        }
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
