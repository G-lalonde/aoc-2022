import java.util.*

fun main() {
    fun part1(input: List<String>): Int {

        var mostCalories = Double.MIN_VALUE

        var calories = 0.0

        for (line: String in input) {
            if (line.isEmpty()) {
                if (calories > mostCalories) {
                    mostCalories = calories
                }
                calories = 0.0
            } else {
                calories += line.toDouble()
            }
        }

        return mostCalories.toInt()
    }

    fun part2(input: List<String>): Int {
        var mostCalories = mutableListOf<Double>()

        var calories = 0.0
        for (line: String in input) {
            if (line.isEmpty()) {
                mostCalories.add(calories)
                mostCalories.sortByDescending { it }
                mostCalories = mostCalories.take(3).toMutableList()

                calories = 0.0
            } else {
                calories += line.toDouble()
            }
        }

        mostCalories.add(calories)
        mostCalories.sortByDescending { it }
        mostCalories = mostCalories.take(3).toMutableList()

        return mostCalories.sum().toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}
