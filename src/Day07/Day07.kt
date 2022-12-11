package Day07

import readInput

fun main() {
    fun buildRoot(root: NTree<String>, input: List<String>) {
        var cwd = root
        var previousCommand = ""

        for (line in input) {
            val split = line.split(" ")
            if (split[0] == "$") {
                when (split[1]) {
                    "cd" -> {
                        previousCommand = "cd"
                        if (split[2] == "/") {
                            continue
                        } else if (split[2] == "..") {
                            cwd = cwd.parent!!
                        } else {
                            var child = cwd.find(split[2])
                            if (child == null) {
                                child = NTree(split[2], isDir = true)
                                cwd.appendChild(child)
                            }

                            cwd = child
                        }
                    }
                    "ls" -> {
                        previousCommand = "ls"
                    }
                }
            } else {
                if (previousCommand == "ls") {
                    if (split[0] == "dir") {
                        if (cwd.find(split[1]) == null) {
                            cwd.appendChild(NTree(split[1], isDir = true))
                        }
                    } else {
                        val size = Integer.parseInt(split[0])
                        if (cwd.find(split[1]) == null) {
                            cwd.appendChild(NTree(split[1], isDir = false, size = size))
                        }
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val atMostSize = 100000

        val root = NTree("/", isDir = true)
        buildRoot(root, input)

        val sizes = arrayListOf<Int>()
        root.traverseDepthFirst {
            if (it.size() <= atMostSize) {
                if (it.isDir) {
                    sizes.add(it.size())
                }
            }
        }

        return sizes.sum()
    }

    fun part2(input: List<String>): Int {
        val totalDiskSpace = 70000000
        val spaceForUpdate = 30000000

        val root = NTree("/", isDir = true)
        buildRoot(root, input)

        val unusedSpace = totalDiskSpace - root.size()
        val spaceNeeded = spaceForUpdate - unusedSpace

        val sizes = arrayListOf<Int>()
        root.traverseDepthFirst {
            if (it.size() >= spaceNeeded) {
                if (it.isDir) {
                    sizes.add(it.size())
                }
            }
        }

        return sizes.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07/Day07_test")
    check(part1(testInput) == 95437) { "Got instead : ${part1(testInput)}" }
    check(part2(testInput) == 24933642) { "Got instead : ${part2(testInput)}" }

    val input = readInput("Day07/Day07")
    println("Answer for part 1 : ${part1(input)}")
    println("Answer for part 2 : ${part2(input)}")
}

class NTree<T>(name: T, var isDir: Boolean, var size: Int = 0) {
    var name: T? = name
    var parent: NTree<T>? = null
    var children: MutableList<NTree<T>> = mutableListOf()

    fun appendChild(child: NTree<T>) {
        child.parent = this
        this.children.add(child)
    }

    fun find(name: T): NTree<T>? {
        for (child in children) {
            if (child.name == name) {
                return child
            }
        }
        return null
    }

    fun traverseDepthFirst(visit: (NTree<T>) -> Unit) {
        visit(this)
        for (child in children) {
            child.traverseDepthFirst(visit)
        }
    }

    fun size(): Int {
        var size = 0
        traverseDepthFirst {
            size += it.size
        }
        return size
    }
}
