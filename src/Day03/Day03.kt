fun main() {

    fun part1(input: List<String>): Int {
        var result = 0
        for (row in input) {
            val mulsFound = Regex("mul\\(\\d{1,3},\\d{1,3}\\)").findAll(row).map { it.value }
            result += mulsFound.sumOf {
                val nums = it.substring(4, it.length - 1).split(",")
                nums[0].toInt() * nums[1].toInt()
            }
        }
        return result
    }

    fun part2_perplexity(input: List<String>): Int {
        val instructionPattern = Regex("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)")
        var enabled = true
        var total = 0

        input.forEach { line ->
            instructionPattern.findAll(line).forEach { match ->
                when (match.value) {
                    "do()" -> enabled = true
                    "don't()" -> enabled = false
                    else -> {
                        if (enabled) {
                            val (num1, num2) = match.destructured
                            total += num1.toInt() * num2.toInt()
                        }
                    }
                }
            }
        }

        return total
    }

    var testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    val input = readInput("Day03")
    part1(input).println()

    testInput = readInput("Day03_test2")
    check(part2_perplexity(testInput) == 48)
    part2_perplexity(input).println()

    testInput = listOf("mul(1,2)don't()mul(2,3)")
    check(part2_perplexity(testInput) == 2)
    testInput = listOf("mul(1,2)do()mul(2,3)")
    check(part2_perplexity(testInput) == 8)
    testInput = listOf("don't()do()don't()")
    check(part2_perplexity(testInput) == 0)
}

private fun mul(input: String): Int {
    val numPair = input.substring(4, input.length - 1).split(",").map { it.toInt() }
    return numPair[0] * numPair[1]
}
