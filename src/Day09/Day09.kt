package Day09

import readInput

fun main() {

    fun move(position: Pair<Int, Int>, to: String): Pair<Int, Int> {
        return when (to) {
            "R" -> position + Pair(1, 0)
            "L" -> position + Pair(-1, 0)
            "U" -> position + Pair(0, 1)
            "D" -> position + Pair(0, -1)
            else -> position
        }
    }

    fun areTouching(first: Pair<Int, Int>, second: Pair<Int, Int>): Boolean {
        return !(first.first > second.first + 1
                || first.first < second.first - 1
                || first.second > second.second + 1
                || first.second < second.second - 1)
    }

    fun part1(input: List<String>): Int {
        var headPosition = Pair(0, 0)
        var tailPosition = Pair(0, 0)

        val positionsOfTail = mutableSetOf<Pair<Int, Int>>()
        positionsOfTail.add(tailPosition)

        for (line in input) {
            val moves = line.split(" ")
            val direction = moves[0]
            val distance = moves[1].toInt()
            for (i in 1..distance) {
                headPosition = move(headPosition, direction)
                if (!areTouching(headPosition, tailPosition)) {
                    // move tail
                    val difference = headPosition - tailPosition
                    tailPosition += difference
                    positionsOfTail.add(tailPosition)
                }
            }

        }

        return positionsOfTail.size
    }

    fun part2(input: List<String>): Int {
        val positions = MutableList(10) { Pair(0, 0) }

        val positionsOfTail = mutableSetOf<Pair<Int, Int>>()
        positionsOfTail.add(positions.last())

        for (line in input) {
            val moves = line.split(" ")
            val direction = moves[0]
            val distance = moves[1].toInt()
            for (i in 1..distance) {
                positions[0] = move(positions.first(), direction)

                for (j in 1 until positions.size) {
                    if (!areTouching(positions[j], positions[j - 1])) {
                        // move tail
                        val difference = positions[j - 1] - positions[j]
                        positions[j] += difference
                    }
                    positionsOfTail.add(positions.last())
                }
            }
        }

        return positionsOfTail.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09/Day09_test")
//    check(part1(testInput) == 13) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 36) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day09/Day09")
//    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    // Define a function that adds two pairs together
    return Pair(this.first + other.first, this.second + other.second)
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    // Define a function that adds two pairs together
    var x = this.first - other.first
    if (x > 1) {
        x = 1
    }
    if (x < -1) {
        x = -1
    }

    var y = this.second - other.second
    if (y > 1) {
        y = 1
    }
    if (y < -1) {
        y = -1
    }

    return Pair(x, y)
}
