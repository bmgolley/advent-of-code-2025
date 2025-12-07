import kotlin.math.max
import kotlin.math.min

fun <T : ClosedRange<*>> T.overlaps(value: T): Boolean =
    contains(value.start) || contains(value.endInclusive) || start in value || endInclusive in value

fun Iterable<LongRange>.combine(vararg ranges: LongRange): LongRange {
    val first = min(minOf(LongRange::first), ranges.minOf(LongRange::first))
    val last = max(maxOf(LongRange::last), ranges.maxOf(LongRange::last))
    return first..last
}

val LongRange.size: Long
    get() = endInclusive - start + 1

fun main() {
    fun part1(input: List<String>): Int {
        val i = input.indexOfFirst(String::isEmpty)
        val ranges = input.take(i).map {
            it.split('-').map(String::toLong).let { (l, r) -> l..r }
        }
        val ids = input.drop(i + 1).map(String::toLong)
        return ids.count { id -> ranges.any { id in it } }
    }

    fun part2(input: List<String>): Long {
        val ranges = input.take(input.indexOfFirst(String::isEmpty)).map {
            it.split('-').map(String::toLong).let { (l, r) -> l..r }
        }
        val combinedRanges = mutableSetOf<LongRange>()
        for (range in ranges) {
            val found = combinedRanges.filter(range::overlaps).toSet()
            if (found.isNotEmpty()) {
                combinedRanges.removeAll(found)
                combinedRanges.add(found.combine(range))
            } else {
                combinedRanges.add(range)
            }
        }
        return combinedRanges.sumOf(LongRange::size)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
