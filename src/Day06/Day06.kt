package Day06

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val signal = input[0]
        val queue = Queue<Char>()

        for ((i, c) in signal.withIndex()) {
            if (queue.size() == 4) {
                return i
            }

            if (queue.contains(c)) {
                while (queue.dequeue() != c) {
                }
            }

            queue.enqueue(c)
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val signal = input[0]
        val queue = Queue<Char>()

        for ((i, c) in signal.withIndex()) {
            if (queue.size() == 14) {
                return i
            }

            if (queue.contains(c)) {
                while (queue.dequeue() != c) {
                }
            }

            queue.enqueue(c)
        }

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06/Day06_test")
    check(part1(testInput) == 10) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 29) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day06/Day06")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}

class Queue<T> {
    private val list = mutableListOf<T>()

    fun enqueue(element: T) {
        list.add(element)
    }

    fun dequeue(): T? {
        if (list.isEmpty()) {
            return null
        }
        return list.removeAt(0)
    }

    fun size(): Int {
        return list.size
    }

    fun contains(element: T): Boolean {
        return list.contains(element)
    }

    fun indexOf(element: T): Int {
        return list.indexOf(element)
    }
}
