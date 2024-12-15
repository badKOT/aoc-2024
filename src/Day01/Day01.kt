import kotlin.math.*;

fun main() {
    fun part1(input: List<String>): Int {
        // calculate distance between lists
        val (list1, list2) = parseInput(input)

        list1.sort()
        list2.sort()

        var result = 0
        for (i in list1.indices) {
            result += abs(list1[i] - list2[i])
        }
        return result
    }

    fun part2(input: List<String>): Int {
        // calculate similarity score
        val (list1, list2) = parseInput(input)

        var result = 0
        for (num in list1) {
            result += num * list2.count { it == num }
        }
        return result
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    val input = readInput("Day01")
    part1(input).println()

    check(part2(testInput) == 31)
    part2(input).println()
}

private fun parseInput(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    for (item in input) {
        val row = item.split("\\s+".toRegex())
        list1.add(row[0].toInt())
        list2.add(row[1].toInt())
    }

    return Pair(list1, list2)
}
