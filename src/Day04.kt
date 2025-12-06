import kotlin.math.max
import kotlin.math.min

const val ROLL = '@'
const val ADJACENT_THRESHOLD = 4

fun <E> List<E>.safeWindow(idx: Int) = subList(max(idx - 1, 0), min(idx + 1, lastIndex) + 1)
fun String.safeWindow(idx: Int) = substring(max(idx - 1, 0), min(idx + 1, lastIndex) + 1)

typealias Table<E> = List<MutableList<E>>

operator fun <E> Table<E>.get(i: Int, j: Int) = this[i][j]
operator fun <E> Table<E>.set(i: Int, j: Int, element: E) = this[i].set(j, element)

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
        val table: Table<Char> = input.map(String::toMutableList)
        var removed = 0
        do {
            val indexes = mutableListOf<Pair<Int, Int>>()
            for ((i, row) in table.withIndex()) {
                val safeWindow = table.safeWindow(i)
                for ((j, cell) in row.withIndex()) {
                    if (cell == ROLL) {
                        val n = safeWindow.sumOf { rowI -> rowI.safeWindow(j).count { it == ROLL } } - 1
                        if (n < ADJACENT_THRESHOLD) {
                            indexes += i to j
                        }
                    }
                }
            }
            val removedThisRound = indexes.size
            for ((i, j) in indexes) {
                assert(table[i, j] == '@')
                table[i, j] = '.'
            }
            removed += removedThisRound
        } while (removedThisRound > 0)
        return removed
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
