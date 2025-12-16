import java.awt.geom.Path2D
import kotlin.math.absoluteValue
import kotlin.math.min

fun Path2D.moveTo(point: Point, offset: Number = 0.5) =
    moveTo(point.x.toDouble() + offset.toDouble(), point.y.toDouble() + offset.toDouble())

fun Path2D.lineTo(point: Point, offset: Number = 0.5) =
    lineTo(point.x.toDouble() + offset.toDouble(), point.y.toDouble() + offset.toDouble())

fun Path2D.contains(rectangle: Rectangle, offset: Number = 0.5): Boolean {
    val (x, y, w, h) = rectangle
    val offsetDouble = offset.toDouble()
    return contains(
        x.toDouble() + offsetDouble, y.toDouble() + offsetDouble,
        w.toDouble() - 2 * offsetDouble, h.toDouble() - 2 * offsetDouble,
    )
}

data class Point(val x: Int, val y: Int) {
    fun areaBetween(other: Point) = ((x - other.x + 1).toLong() * (y - other.y + 1)).absoluteValue
    fun toRectangle(opposite: Point) = Rectangle(this, opposite)
}

data class Rectangle(val x: Int, val y: Int, val w: Int, val h: Int) {
    val area: Long get() = w.toLong() * h.toLong()

    init {
        require(w >= 0)
        require(h >= 0)
    }

    constructor(point1: Point, point2: Point) : this(
        min(point1.x, point2.x),
        min(point1.y, point2.y),
        (point1.x - point2.x).absoluteValue + 1,
        (point1.y - point2.y).absoluteValue + 1,
    )
}

fun main() {
    fun part1(input: List<String>): Long {
        val points = input.map { val (x, y) = it.split(',').map(String::toInt); Point(x, y) }
        val result = points.subList(0, points.lastIndex).withIndex().maxOf { (i, point) ->
            points.subList(i + 1).maxOf(point::areaBetween)
        }
        return result
    }

    fun part2(input: List<String>): Long {
        val points = input.map {
            val (x, y) = it.split(',').map(String::toInt)
            Point(x, y)
        }

        val path = Path2D.Double().apply {
            moveTo(points.first())
            for (point in points.subList(1, points.size)) {
                lineTo(point)
            }
            closePath()
        }

        val result = points.subList(0, points.lastIndex).asSequence()
            .withIndex()
            .maxOf { (i, point) ->
                points.asSequence()
                    .drop(i + 1)
                    .map(point::toRectangle)
                    .filter(path::contains)
                    .maxOfOrNull(Rectangle::area) ?: 0
            }
        return result
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
