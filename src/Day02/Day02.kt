package Day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {

        return 1
    }

    fun part2(input: List<String>): Int {

        return 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02/Day02_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 2)

    val input = readInput("Day02/Day02")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
    println("Answer for part 2 : ${part2(input)}")
}
