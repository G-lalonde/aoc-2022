package Day10

import readInput

fun main() {
    data class Instruction(val cycle: Int, val increment: Int)

    fun execute(instruction: String): Instruction {
        if (instruction == "noop") {
            return Instruction(1, 0)
        }

        val (_, increment) = instruction.split(" ")
        return Instruction(2, increment.toInt())
    }

    fun part1(input: List<String>): Int {
        var X = 1
        var cycle = 0
        val cycleCheck = intArrayOf(20, 60, 100, 140, 180, 220)
        val signalStrength = mutableListOf<Int>()

        for (instruction in input) {
            val (cycleIncrement, increment) = execute(instruction)
            for (i in 0 until cycleIncrement) {
                cycle += 1

                if (cycle in cycleCheck) {
                    signalStrength.add(cycle * X)
                }
            }
            X += increment
        }

        return signalStrength.sum()
    }

    fun part2(input: List<String>): Int {
        var X = 1
        var cycle = 0

        val sprite = mutableListOf<Char>()

        for (instruction in input) {
            val (cycleIncrement, increment) = execute(instruction)
            for (i in 0 until cycleIncrement) {


                val CRT = listOf(X - 1, X, X + 1)
                sprite.add(if (cycle % 40 in CRT) '|' else ' ')

                cycle += 1
            }
            X += increment
        }

        for (i in intArrayOf(40, 80, 120, 160, 200, 240)) {
            println(sprite.subList(i - 40, i).joinToString(""))
        }

        return 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10/Day10_test")
    check(part1(testInput) == 13140) { "Got instead : ${part1(testInput)}" }
//    check(part2(testInput) == 2) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day10/Day10")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : \n${part2(input)}")
}
