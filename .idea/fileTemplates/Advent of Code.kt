#set( $Code = "bar" )
fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day${Day}_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)

    val input = readInput("Day$Day")
    part1(input).println()
    part2(input).println()
}
