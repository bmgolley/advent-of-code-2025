import kotlin.math.pow
import kotlin.math.sqrt

fun Int.pow(n: Int) = toDouble().pow(n)
fun <E> List<E>.subList(fromIndex: Int) = subList(fromIndex, size)

data class Point3D(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: Point3D) = sqrt(
        (x - other.x).pow(2)
                + (y - other.y).pow(2)
                + (z - other.z).pow(2)
    )
}

fun findDistances(input: List<String>): Pair<Map<Double, Pair<Point3D, Point3D>>, List<Point3D>> {
    val junctions = input.map {
        val (x, y, z) = it.split(',').map(String::toInt)
        Point3D(x, y, z)
    }
    val distances = sortedMapOf<Double, Pair<Point3D, Point3D>>()
    for ((i, junction) in junctions.dropLast(1).withIndex()) {
        junctions.subList(i + 1).associateTo(distances) { junction.distanceTo(it) to (junction to it) }
    }
    return distances to junctions
}

fun addToConnections(connections: MutableMap<Point3D, Int>, count: Int, junc1: Point3D, junc2: Point3D): Int {
    var count = count
    val con1 = connections[junc1]
    if (con1 != null) {
        val con2 = connections[junc2]
        if (con2 == null) {
            connections[junc2] = con1
        } else if (con1 != con2) {
            for (k in connections.keys) {
                connections.replace(k, con1, con2)
            }
        }
    } else {
        connections[junc1] = connections.getOrPut(junc2) { count++ }
    }
    return count
}

fun main() {
    fun part1(input: List<String>, maxConnections: Int = 0): Int {
        val (distances) = findDistances(input)
        val maxConnections = maxConnections.takeIf { it > 0 } ?: distances.size
        val connections = mutableMapOf<Point3D, Int>()
        var count = 0
        for ((junc1, junc2) in distances.values.take(maxConnections)) {
            count = addToConnections(connections, count, junc1, junc2)
        }
        return connections.keys.groupingBy(connections::get)
            .eachCount()
            .values
            .sortedDescending()
            .take(3)
            .reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        val (distances, junctions) = findDistances(input)
        val numJunctions = junctions.size
        val connections = mutableMapOf<Point3D, Int>()
        var count = 0
        for ((junc1, junc2) in distances.values) {
            count = addToConnections(connections, count, junc1, junc2)
            if (connections.size == numJunctions && connections.values.toSet().size == 1) {
                return junc1.x * junc2.x
            }
        }
        return -1
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput, 10) == 40)
    check(part2(testInput) == 25272)

    val input = readInput("Day08")
    part1(input, 1000).println() // 50568
    part2(input).println() // 36045012
}
