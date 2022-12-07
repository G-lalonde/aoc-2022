package Day03

import readInput

fun main() {

    fun score(char: Char): Int {
        if (char.isLowerCase()) {
            return char.code - 'a'.code + 1
        } else {
            return char.code - 'A'.code + 27
        }
    }

    fun part1(input: List<String>): Int {
        var score = 0

        for (line in input) {
            val firstPart = line.slice(0 until line.length / 2)
            val secondPart = line.slice(line.length / 2 until line.length)

            val intersection = firstPart
                .filter { c -> secondPart.contains(c) }
                .take(1)
                .map { c -> score(c) }
                .take(1)

            score += intersection.first()
        }

        return score
    }

    fun part2(input: List<String>): Int {

        var score = 0
        val parts = Array(3) { "" }

        for ((i, line) in input.withIndex()) {
            parts[i % 3] = line

            if (i % 3 == 2) {
                val firstPart = parts[0]
                val secondPart = parts[1]
                val thirdPart = parts[2]

                val intersection = firstPart
                    .filter { c -> secondPart.contains(c) }
                    .filter { c -> thirdPart.contains(c) }
                    .take(1)
                    .map { c -> score(c) }
                    .take(1)

                score += intersection.first()
                parts[0] = ""
                parts[1] = ""
                parts[2] = ""
            }
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03/Day03_test")
    check(part1(testInput) == 157) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 70) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day03/Day03")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}
