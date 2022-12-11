package Day08

import readInput
import kotlin.math.max

fun main() {
    fun buildMatrix(treeMatrix: Matrix, input: List<String>) {
        for ((i, line) in input.withIndex()) {
            for ((j, v) in line.withIndex()) {
                treeMatrix[i, j] = v.digitToInt()
            }
        }
    }

    fun part1(input: List<String>): Int {
        val rowCount = input.size
        val colCount = input.first().length
        val outerCount = colCount * 2 + (rowCount - 2) * 2

        val treeMatrix = Matrix(rowCount, colCount)
        buildMatrix(treeMatrix, input)
        val visibleMatrix = Matrix(rowCount, colCount) // check pas le outer

        for (i in 1 until rowCount - 1) {
            // looking from the left
            var highestValueOnRowFromLeft = treeMatrix[i, 0]
            treeMatrix.forEachInRow(i, startFromBeginning = true) { j, value ->
                if (value > highestValueOnRowFromLeft) {
                    visibleMatrix[i, j] = 1
                }

                if (value > highestValueOnRowFromLeft) {
                    highestValueOnRowFromLeft = value
                }
            }

            // looking from the right
            var highestValueOnRowFromRight = treeMatrix[i, colCount - 1]
            treeMatrix.forEachInRow(i, startFromBeginning = false) { j, value ->
                if (value > highestValueOnRowFromRight) {
                    visibleMatrix[i, j] = 1
                }

                if (value > highestValueOnRowFromRight) {
                    highestValueOnRowFromRight = value
                }
            }
        }

        for (j in 1 until colCount - 1) {
            // looking from the top
            var highestValueOnColumnFromTop = treeMatrix[0, j]
            treeMatrix.forEachInColumn(j, startFromBeginning = true) { i, value ->
                if (value > highestValueOnColumnFromTop) {
                    visibleMatrix[i, j] = 1
                }

                if (value > highestValueOnColumnFromTop) {
                    highestValueOnColumnFromTop = value
                }
            }

            // looking from the bottom
            var highestValueOnRowFromRight = treeMatrix[rowCount - 1, j]
            treeMatrix.forEachInColumn(j, startFromBeginning = false) { i, value ->
                if (value > highestValueOnRowFromRight) {
                    visibleMatrix[i, j] = 1
                }

                if (value > highestValueOnRowFromRight) {
                    highestValueOnRowFromRight = value
                }
            }
        }

        return outerCount + visibleMatrix.count(1)
    }

    fun part2(input: List<String>): Int {
        val rowCount = input.size
        val colCount = input.first().length

        val treeMatrix = Matrix(rowCount, colCount)
        buildMatrix(treeMatrix, input)
        val scenicScore = Matrix(rowCount, colCount)

        for (i in 0 until rowCount) {
            // looking from the left
            val viewedFromLeft = mutableListOf<Int>()
            viewedFromLeft.add(treeMatrix[i, 0])
            treeMatrix.forEachInRow(i, startFromBeginning = true) { j, value ->
                if (j == 0) {
                    scenicScore[i, j] = 0
                    return@forEachInRow
                }

                var treeSeen = 0
                for (tree in viewedFromLeft.reversed()) {
                    if (tree < value) {
                        treeSeen += 1
                    } else {
                        treeSeen += 1
                        break
                    }
                }
                scenicScore[i, j] = treeSeen
                viewedFromLeft.add(value)
            }

            // looking from the right
            val viewedFromRight = mutableListOf<Int>()
            viewedFromRight.add(treeMatrix[i, colCount - 1])
            treeMatrix.forEachInRow(i, startFromBeginning = false) { j, value ->
                if (j == colCount - 1) {
                    scenicScore[i, j] = 0
                    return@forEachInRow
                }

                var treeSeen = 0
                for (tree in viewedFromRight.reversed()) {
                    if (tree < value) {
                        treeSeen += 1
                    } else {
                        treeSeen += 1
                        break
                    }
                }
                scenicScore[i, j] *= treeSeen
                viewedFromRight.add(value)
            }
        }

        for (j in 1 until colCount - 1) {
            // looking from the top
            val viewedFromTop = mutableListOf<Int>()
            viewedFromTop.add(treeMatrix[0, j])
            treeMatrix.forEachInColumn(j, startFromBeginning = true) { i, value ->
                if (i == 0) {
                    scenicScore[i, j] = 0
                    return@forEachInColumn
                }

                var treeSeen = 0
                for (tree in viewedFromTop.reversed()) {
                    if (tree < value) {
                        treeSeen += 1
                    } else {
                        treeSeen += 1
                        break
                    }
                }
                scenicScore[i, j] *= treeSeen
                viewedFromTop.add(value)
            }

            // looking from the bottom
            val viewedFromBottom = mutableListOf<Int>()
            viewedFromBottom.add(treeMatrix[rowCount - 1, j])
            treeMatrix.forEachInColumn(j, startFromBeginning = false) { i, value ->
                if (i == rowCount - 1) {
                    scenicScore[i, j] = 0
                    return@forEachInColumn
                }

                var treeSeen = 0
                for (tree in viewedFromBottom.reversed()) {
                    if (tree < value) {
                        treeSeen += 1
                    } else {
                        treeSeen += 1
                        break
                    }
                }
                scenicScore[i, j] *= treeSeen
                viewedFromBottom.add(value)
            }
        }

        return scenicScore.max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08/Day08_test")
//    check(part1(testInput) == 21) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 8) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day08/Day08")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}

class Matrix(val rows: Int, val cols: Int) {
    // stored in row-major
    private val data = MutableList(rows * cols) { 0 }

    // define a operator [] to access individual elements of the matrix
    operator fun get(row: Int, col: Int): Int {
        return data[row * cols + col]
    }

    operator fun set(row: Int, col: Int, value: Int) {
        data[row * cols + col] = value
    }

    fun count(value: Int): Int {
        return data.count { it == value }
    }

    // dont check outer edge
    fun forEachInColumn(
        col: Int,
        startFromBeginning: Boolean = true,
        action: (Int, Int) -> Unit
    ) {
        val range = 0 until rows
        for (i in if (startFromBeginning) range else range.reversed()) {
            action(i, this[i, col])
        }
    }

    // dont check outer edge
    fun forEachInRow(
        row: Int,
        startFromBeginning: Boolean = true,
        action: (Int, Int) -> Unit
    ) {
        val range = 0 until cols
        for (j in if (startFromBeginning) range else range.reversed()) {
            action(j, this[row, j])
        }
    }

    fun max(): Int {
        return data.max()
    }
}
