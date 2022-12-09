package Day05

import readInput
import java.util.regex.Pattern

fun main() {

    fun stackCrates(string: String, piles: List<Stack<Char>>) {
        val regex = """\[(.*?)\]"""
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(string)

        while (matcher.find()) {
            val group = matcher.group(1)
            val index = matcher.start(1)

            piles[(index - 1) / 4].push(group[0])
        }
    }

    fun part1(input: List<String>): String {
        val indexOfPiles = input.indexOfFirst { it.startsWith(" 1") }
        val pileCount = input[indexOfPiles].split(" ").last().toInt()

        val piles = mutableListOf<Stack<Char>>()
        for (i in 0 until pileCount) {
            piles.add(Stack())
        }

        input.subList(0, indexOfPiles).asReversed().forEach {
            stackCrates(it, piles)
        }

        val regex = "\\d+".toRegex()
        input.subList(indexOfPiles + 2, input.size).forEach {
            val (numberOfOperation, from, to) = regex.findAll(it).map { it.value.toInt() }
                .toList()

            for (i in 0 until numberOfOperation) {
                val value = piles[from - 1].pop()
                if (value != null) {
                    piles[to - 1].push(value)
                }
            }
        }

        var result = ""
        for (i in 0 until pileCount) {
            result += piles[i].peek()
        }

        return result
    }

    fun part2(input: List<String>): String {
        val indexOfPiles = input.indexOfFirst { it.startsWith(" 1") }
        val pileCount = input[indexOfPiles].split(" ").last().toInt()

        val piles = mutableListOf<Stack<Char>>()
        for (i in 0 until pileCount) {
            piles.add(Stack())
        }

        input.subList(0, indexOfPiles).asReversed().forEach {
            stackCrates(it, piles)
        }

        val regex = "\\d+".toRegex()
        input.subList(indexOfPiles + 2, input.size).forEach {
            val (numberOfOperation, from, to) = regex.findAll(it).map { it.value.toInt() }
                .toList()

            val tempStack = Stack<Char>()
            for (i in 0 until numberOfOperation) {
                val value = piles[from - 1].pop()
                if (value != null) {
                    tempStack.push(value)
                }
            }
            for (i in 0 until numberOfOperation) {
                val value = tempStack.pop()
                if (value != null) {
                    piles[to - 1].push(value)
                }
            }
        }

        var result = ""
        for (i in 0 until pileCount) {
            result += piles[i].peek()
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05/Day05_test")
    check(part1(testInput) == "CMZ") { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == "MCD") { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day05/Day05")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}

class Stack<T> {
    private val list = mutableListOf<T>()

    fun push(element: T) {
        list.add(element)
    }

    fun pop(): T? {
        return if (list.isEmpty()) null else list.removeAt(list.size - 1)
    }

    fun peek(): T? {
        return list.lastOrNull()
    }
}