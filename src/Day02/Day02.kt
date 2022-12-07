package Day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val shapePoints: HashMap<Char, Int> = hashMapOf(
            'A' to 1, // Rock
            'B' to 2, // Paper
            'C' to 3, // Scissors
            'X' to 1, // Rock
            'Y' to 2, // Paper
            'Z' to 3, // Scissors
        )

        fun draw(me: Char, over: Char): Boolean {
            return when {
                me == 'X' && over == 'A' -> true
                me == 'Y' && over == 'B' -> true
                me == 'Z' && over == 'C' -> true
                else -> false
            }
        }

        fun win(me: Char, over: Char): Boolean {
            return when {
                me == over -> false
                me == 'X' && over == 'C' -> true
                me == 'Y' && over == 'A' -> true
                me == 'Z' && over == 'B' -> true
                else -> false
            }
        }

        fun score(other: Char, me: Char): Int {
            return when {
                draw(me, other) -> 3 + shapePoints[me]!! // draw
                win(me, other) -> 6 + shapePoints[me]!! // win
                else -> 0 + shapePoints[me]!! // lose
            }
        }

        var score = 0

        for (line: String in input) {
            if (line.isEmpty()) {
                continue
            }

            val (other, me) = line.split(" ")
            score += score(other[0], me[0])
        }

        return score
    }

    fun part2(input: List<String>): Int {
        val shapePoints: HashMap<Char, Int> = hashMapOf(
            'A' to 1, // Rock
            'B' to 2, // Paper
            'C' to 3, // Scissors
        )

        val resultPoints: HashMap<Char, Int> = hashMapOf(
            'Z' to 6, // win
            'Y' to 3, // draw
            'X' to 0, // lose
        )

        val winAgainst: HashMap<Char, Char> = hashMapOf(
            'A' to 'B', // Rock
            'B' to 'C', // Paper
            'C' to 'A', // Scissors
        )

        val loseAgainst: HashMap<Char, Char> = hashMapOf(
            'A' to 'C', // Rock
            'B' to 'A', // Paper
            'C' to 'B', // Scissors
        )

        fun shapeToChoose(instruction: Char, over: Char): Char {
            return when (instruction) {
                'X' -> loseAgainst[over]!! // lose
                'Y' -> over // draw
                'Z' -> winAgainst[over]!! // win
                else -> 'A'
            }
        }

        fun score(other: Char, instruction: Char): Int {
            val myShape = shapeToChoose(instruction, other)
            val result = resultPoints[instruction]!!
            val shape = shapePoints[myShape]!!
            return result + shape
        }

        var score = 0

        for (line: String in input) {
            if (line.isEmpty()) {
                continue
            }

            val (other, me) = line.split(" ")
            score += score(other[0], me[0])
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02/Day02_test")
    check(part1(testInput) == 15) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 12) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day02/Day02")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}
