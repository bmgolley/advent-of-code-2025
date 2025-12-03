fun main() {
    fun part1(input: List<String>): Int {
        val start = 50
        val positions = input.runningFold(start) { acc, string ->
            (string.replace('L', '-').replace('R', '+').toInt() + acc) % 100
        }
        return positions.count { it == 0 }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
