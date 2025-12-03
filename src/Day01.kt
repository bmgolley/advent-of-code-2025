import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        val start = 50
        val positions = input.runningFold(start) { acc, string ->
            (string.replace('L', '-').replace('R', '+').toInt() + acc) % 100
        }
        return positions.count { it == 0 }
    }

    fun part2(input: List<String>): Int {
        val start = 50
        var count = 0
        var position = start
        for (rotationString in input) {
            val prevPosition = position
            val rotation = rotationString.replace('L', '-').replace('R', '+').toInt()
            // count number of times rotated more than 100
            count += (rotation / 100).absoluteValue
            // add net change
            position += rotation % 100
            // and convert to actual position
            position %= 100
            if (position < 0) {
                position += 100
            }
            // if rotation was negative but new position > previous position, or vice versa, then dial crossed 100
            if (prevPosition != 0
                && (position == 0 || prevPosition != position && (position - prevPosition).sign != rotation.sign)
            ) {
                count++
            }
        }
        return count
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
