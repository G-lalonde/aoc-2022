package Day04

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var fullyContainedCount = 0

        for (line in input) {
            val (start1, end1) = line.split(",")[0].split("-").map { it.toInt() }
            val (start2, end2) = line.split(",")[1].split("-").map { it.toInt() }

            if (start1 <= start2 && end1 >= end2) {
                fullyContainedCount++
            } else if (start2 <= start1 && end2 >= end1) {
                fullyContainedCount++
            }
        }

        return fullyContainedCount
    }

    fun part2(input: List<String>): Int {
        var fullyContainedCount = 0

        for (line in input) {
            val (start1, end1) = line.split(",")[0].split("-").map { it.toInt() }
            val (start2, end2) = line.split(",")[1].split("-").map { it.toInt() }

            if (!(start1 < start2 && end1 < start2 || start1 > end2 && end1 > end2)) {
                fullyContainedCount++
            }
        }

        return fullyContainedCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04/Day04_test")
    check(part1(testInput) == 2) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 4) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day04/Day04")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}
