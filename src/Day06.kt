fun main() {
    fun part1(input: List<String>): Int {
        val maxTimes = input[0].substringAfter(":")
            .trim()
            .split("""\s+""".toRegex())
            .map { it.toInt() }
        maxTimes.println()

        val maxDistances = input[1].substringAfter(":")
            .trim()
            .split("""\s+""".toRegex())
            .map { it.toInt() }
        maxDistances.println()

        val winConditionList = mutableListOf<Int>()

        for (i in maxTimes.indices) {
            val maxTime = maxTimes[i]
            val maxDistance = maxDistances[i]

            winConditionList.add(0)

            for (holdTime in 1..maxTime) {
                println("hold for $holdTime, move for ${maxTime-holdTime} with speed $holdTime")
                val distanceCovered = holdTime * (maxTime-holdTime)// speed *remaining time
                println("distanceCovered = $distanceCovered")

                if (distanceCovered > maxDistance) {
                    winConditionList[i]++
                }
            }
        }

        winConditionList.println()

        return winConditionList.reduce { acc, i -> acc*i }
    }

    fun part2(input: List<String>): Int {
        val maxTime = input[0].substringAfter(":")
            .trim().replace("""\s+""".toRegex(),"").toLong()
        println("maxTime=$maxTime")

        val maxDistance = input[1].substringAfter(":")
            .trim().replace("""\s+""".toRegex(),"").toLong()

        println("maxDistance=$maxDistance")

        var winConditions = 0
        for (holdTime in 1..maxTime) {
            println("hold for $holdTime, move for ${maxTime-holdTime} with speed $holdTime")
            val distanceCovered = holdTime * (maxTime-holdTime)// speed *remaining time
            println("distanceCovered = $distanceCovered")

            if (distanceCovered > maxDistance) {
                winConditions++
            }
        }

        return winConditions
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    val testInput2 = readInput("Day06_test")
    check(part2(testInput2) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
