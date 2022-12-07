package DayXX

import readInput

fun main() {
    fun part1(input: List<String>): Int {

        return 1
    }

    fun part2(input: List<String>): Int {

        return 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("DayXX/DayXX_test")
    check(part1(testInput) == 1) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 2) { "Got instead : ${part2(testInput)}" }

    val input = readInput("DayXX/DayXX")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}
