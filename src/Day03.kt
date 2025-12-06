fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val numbers = it.chunked(1).map(String::toInt)
            val max = numbers.dropLast(1).max()
            val iMax = numbers.indexOf(max)
            val max2 = numbers.drop(iMax + 1).max()
            "$max$max2".toInt()
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf {
            val numbers = it.chunked(1).map(String::toInt)
            val found = mutableListOf<Int>()
            val end = numbers.size
            var start = 0
            for (i in 11 downTo 0) {
                with(numbers.subList(start, end - i)) {
                    val n = max()
                    start += indexOf(n) + 1
                    found += n
                }
            }
            found.joinToString("").toLong()
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
