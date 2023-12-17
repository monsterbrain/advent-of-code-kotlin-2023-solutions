fun main() {
    fun part1(input: List<String>): Int {
        val actual = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        var possibleSum = 0
        input.forEach {
            // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            val gameId = it.substringBefore(":").split(" ").last().toInt()
            var isGamePossible = true
            it.substringAfter(":").trim().split(";").forEach { set ->
                println("checking $set")
                // 3 blue, 4 red
                """(\d+)\s(\w+)""".toRegex().findAll(set).forEach { matchResult ->
                    val (count, color) = matchResult.destructured
                    println("$count, $color")
                    if (count.toInt() > actual[color]!!) {
                        isGamePossible = false
                    }
                }
            }
            println(" game possible = $isGamePossible")
            if (isGamePossible) possibleSum += gameId
        }

        return possibleSum
    }

    fun part2(input: List<String>): Int {
        var sumOfMinPossible = 0
        input.forEach {
            // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            val minCounts = mutableMapOf(
                "red" to 0,
                "blue" to 0,
                "green" to 0
            )
            it.substringAfter(":").trim().split(";").forEach { set ->
                println("checking $set")
                // 3 blue, 4 red
                """(\d+)\s(\w+)""".toRegex().findAll(set).forEach { matchResult ->
                    val (count, color) = matchResult.destructured
                    println("$count, $color")
                    if (count.toInt() > minCounts[color]!!) {
                        minCounts[color] = count.toInt()
                    }
                }
            }
            println(" minCounts  = $minCounts")
            var mult = 1
            minCounts.values.forEach {
                mult = mult * it
            }

            sumOfMinPossible += mult
        }
        return sumOfMinPossible
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val testInput2 = readInput("Day02_test")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
