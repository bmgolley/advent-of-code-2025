operator fun String.get(range: IntRange) = substring(range)

fun main() {
    fun part1(input: List<String>): Int {
        val beams = mutableListOf(listOf(input.first().indexOf("S")))
        var count = 0
        for (rowInitial in input.drop(1)) {
            var row = rowInitial
            for (i in beams.last()) {
                row = when (val c = row[i]) {
                    '.' -> row.replaceRange(i..i, "|")
                    '|' -> row
                    '^' -> {
                        if (row[i - 1] != '|' || row[i + 1] != '|') {
                            count++
                        }
                        row.replaceRange(i - 1..i + 1, "|^|")
                    }

                    else -> error("invalid character '$c'")
                }
            }
            beams.add(row.mapIndexedNotNull { index, ch -> index.takeIf { ch == '|' } })
        }
        return count
    }

    fun part2(input: List<String>): Long {
        val startRow = input.first()
        val pathCount = startRow.map { if (it == 'S') 1L else 0 }.toMutableList()
        for (row in input.drop(1)) {
            for ((i, char) in row.withIndex()) {
                if (char == '^') {
                    val n = pathCount[i].also { pathCount[i] = 0 }
                    pathCount[i - 1] += n
                    pathCount[i + 1] += n
                }
            }
        }
        return pathCount.sum()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 40L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
