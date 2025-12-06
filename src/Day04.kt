import kotlin.math.max
import kotlin.math.min

const val ROLL = '@'
const val ADJACENT_THRESHOLD = 4

fun <E> List<E>.safeWindow(idx: Int) = subList(max(idx - 1, 0), min(idx + 1, lastIndex) + 1)
fun String.safeWindow(idx: Int) = substring(max(idx - 1, 0), min(idx + 1, lastIndex) + 1)

fun main() {
    fun part1(input: List<String>): Int {
        return input.withIndex().sumOf { (i, row) ->
            val safeWindow = input.safeWindow(i)
            row.withIndex().count { (j, cell) ->
                if (cell == ROLL) {
                    safeWindow.sumOf { rowI -> rowI.safeWindow(j).count { it == ROLL } } - 1 < ADJACENT_THRESHOLD
                } else {
                    false
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
