fun main() {
    fun part1(input: List<String>, addProblemDampener: Boolean): Int {
        var result = 0
        val reportToErrors = mutableMapOf<Int, MutableList<Int>>()

        val parsedInput: List<List<Int>> = input.map { line ->
            line.split("\\s+".toRegex()).map { it.toInt() }
        }
        for ((i, report) in parsedInput.withIndex()) {
            var errorsFound = false
            val isIncreasing = report.last() > report.first()
            var prevNum: Int = if (isIncreasing) report.first() - 1 else report.first() + 1
            for ((j, num) in report.withIndex()) {
                if (isNotSafe(num, prevNum, isIncreasing)) {
                    errorsFound = true
                    var errors = reportToErrors.getOrDefault(i, mutableListOf())
                    errors.addAll(listOf(j - 1, j))
                    errors = errors.distinct().toMutableList()
                    reportToErrors.put(i, errors)
                }
                prevNum = num
            }
            if (!errorsFound) result++
        }
        if (addProblemDampener) result += part2_tolerateSingleError(parsedInput, reportToErrors)
        return result
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput, false) == 2)
    val input = readInput("Day02")
    part1(input, false).println()
    check(part1(testInput, true) == 4)
    part1(input, true).println()
}

fun part2_tolerateSingleError(reports: List<List<Int>>, reportToErrors: MutableMap<Int, MutableList<Int>>): Int {
    var result = 0

    for ((reportIndex, errors) in reportToErrors) {
        val report = reports[reportIndex].toMutableList()

        for (errorIndex in errors) {
            if (errorIndex < report.size) {
                val removedValue = report.removeAt(errorIndex)
                if (checkReport(report)) {
                    result++
                    break
                } else {
                    report.add(errorIndex, removedValue)
                }
            }
        }
    }
    return result;
}

private fun checkReport(report: List<Int>): Boolean {
    val isIncreasing = report.last() > report.first()
    var prevNum: Int = if (isIncreasing) report.first() - 1 else report.first() + 1
    for (num in report) {
        if (isNotSafe(num, prevNum, isIncreasing)) {
            return false
        }
        prevNum = num
    }
    return true
}

private fun isNotSafe(currentNum: Int, prevNum: Int, isIncreasing: Boolean): Boolean {
    return if (isIncreasing) {
        currentNum <= prevNum || (currentNum - prevNum !in 1..3)
    } else {
        currentNum >= prevNum || (prevNum - currentNum !in 1..3)
    }
}
