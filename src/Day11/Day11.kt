package Day11

import readInput
import java.math.BigInteger
import java.time.LocalDateTime

fun main() {
    fun extractInstructions(input: String): Instruction {
        val regex =
            """Monkey (\d+):\s+Starting items: (\d+(?:, \d+)*)\s+Operation: new = old ([+,-,\\,\*]) (old|\d+)\s+Test: divisible by (\d+)\s+If true: throw to monkey (\d+)\s+If false: throw to monkey (\d+)""".toRegex()
        val groups = regex.find(input)?.groupValues!!

        val startingItems = Queue<BigInteger>()
        groups[2]
            .split(", ")
            .toList()
            .map { it.toBigInteger() }
            .forEach { startingItems.enqueue(it) }

        return Instruction(
            monkey = groups[1].toInt(),
            items = startingItems,
            operation = groups[3].first(),
            operationValue = { value -> if (groups[4] == "old") value else groups[4].toBigInteger() },
            divisibleBy = groups[5].toInt(),
            throwToIf = { bool -> if (bool) groups[6].toInt() else groups[7].toInt() }
        )
    }

    fun operate(firstValue: BigInteger, operator: Char, secondValue: BigInteger): BigInteger {
        return when (operator) {
            '*' -> firstValue * secondValue
            '/' -> firstValue / secondValue
            '+' -> firstValue + secondValue
            '-' -> firstValue - secondValue
            else -> BigInteger("0")
        }
    }

    fun isDivisible(dividend: BigInteger, divisor: BigInteger): Boolean {
        return (dividend % divisor) == BigInteger("0")
    }

    fun part1(input: List<String>): Int {
        val filteredList = input
            .filter { it.isNotEmpty() }

        val monkeys = mutableListOf<Instruction>()
        for (i in filteredList.indices step 6) {
            monkeys.add(
                extractInstructions(
                    filteredList
                        .subList(i, i + 6)
                        .joinToString("")
                )
            )
        }

        val itemInspected = MutableList(monkeys.size) { 0 }

        for (round in 1..20) {
            for (instruction in monkeys) {
                while (!instruction.items.isEmpty()) {
                    val item = instruction.items.dequeue()!!
                    val worryLevel =
                        operate(
                            item,
                            instruction.operation,
                            instruction.operationValue(item)
                        ) / BigInteger("3")
                    val throwTo =
                        instruction.throwToIf(
                            isDivisible(
                                worryLevel,
                                instruction.divisibleBy.toBigInteger()
                            )
                        )
                    val monkeyThrownTo = monkeys.first { it.monkey == throwTo }
                    monkeyThrownTo.items.enqueue(worryLevel)
                    itemInspected[instruction.monkey] += 1
                }
            }
        }

        val (one, two) = itemInspected.sortedDescending().toList().take(2)
        return one * two
    }

    fun part2(input: List<String>): Long {
        val filteredList = input
            .filter { it.isNotEmpty() }

        val monkeys = mutableListOf<Instruction>()
        for (i in filteredList.indices step 6) {
            monkeys.add(
                extractInstructions(
                    filteredList
                        .subList(i, i + 6)
                        .joinToString("")
                )
            )
        }

        val itemInspected = MutableList(monkeys.size) { 0 }

        for (round in 1..10000) {
            for (instruction in monkeys) {
                while (!instruction.items.isEmpty()) {
                    val item = instruction.items.dequeue()!!
                    val worryLevel =
                        operate(item, instruction.operation, instruction.operationValue(item))
                    val throwTo =
                        instruction.throwToIf(
                            isDivisible(
                                worryLevel,
                                instruction.divisibleBy.toBigInteger()
                            )
                        )
                    val monkeyThrownTo = monkeys.first { it.monkey == throwTo }
                    monkeyThrownTo.items.enqueue(worryLevel)
                    itemInspected[instruction.monkey] += 1
                }
            }

            if (round in arrayOf(1, 20, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 10000)) {
                print("[${LocalDateTime.now()}] Round ")
                println(round)
//                for (monkey in monkeys.indices) {
//                    println("Monkey $monkey inspected items ${itemInspected[monkey]} times.")
//                }
            }
        }

        val (one, two) = itemInspected.sortedDescending().toList().take(2)
        return one.toLong() * two.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11/Day11_test")
//    check(part1(testInput) == 10605) { "Got instead : ${part1(testInput)}" }
//    check(part2(testInput) == 2713310158) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day11/Day11")
//    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}

class Instruction(
    val monkey: Int,
    val items: Queue<BigInteger>,
    val operation: Char,
    val operationValue: (BigInteger) -> BigInteger,
    val divisibleBy: Int,
    val throwToIf: (Boolean) -> Int,
)

class Queue<T> {
    private val list = mutableListOf<T>()

    fun enqueue(item: T) = list.add(item)

    fun dequeue(): T? {
        return if (list.isNotEmpty()) list.removeAt(0) else null
    }

    fun peek(): T? = list.firstOrNull()

    fun isEmpty(): Boolean = list.isEmpty()

    fun size(): Int = list.size

    fun print() = println(this.list)
}

