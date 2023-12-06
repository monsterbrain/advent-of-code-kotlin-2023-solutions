fun main() {
    fun part1(input: List<String>): Int {
        val twoDigitList = mutableListOf<Int>()
        input.forEach {
            val tens = it.first { it.isDigit() }
            val ones = it.last { it.isDigit() }
            twoDigitList += (tens.digitToInt()*10 + ones.digitToInt())
        }

        return twoDigitList.sum()
    }

    fun replaceWordsToDigits(input: String): String {
        var replaced = input
        val digitWords = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9)
        // replace first and last
        replaced.findAnyOf(digitWords.keys.toList())?.let {
            if (replaced.indexOfFirst { it.isDigit() } > it.first) {
                replaced = replaced.replaceFirst(it.second, digitWords[it.second].toString())
            }
        }
        // replace last
        replaced.findLastAnyOf(digitWords.keys.toList())?.let {
            replaced = replaced.replaceRange(it.first until it.first+it.second.length, digitWords[it.second].toString())
        }
        return replaced
    }

    fun part2(input: List<String>): Int {
        val twoDigitList = mutableListOf<Int>()
        input.forEach { code ->
            val replaced = replaceWordsToDigits(code)
            val tens = replaced.first { it.isDigit() }
            val ones = replaced.last { it.isDigit() }
            twoDigitList += (tens.digitToInt()*10 + ones.digitToInt())
        }
        return twoDigitList.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
