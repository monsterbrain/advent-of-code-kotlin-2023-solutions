enum class CardType(val point: Int) {
    HighCard(1),
    OnePair(2),
    TwoPair(3),
    ThreeOfKind(4),
    FullHouse(5),
    FourOfKind(6),
    FiveOfKind(7)
}

fun main() {


    fun getCardTypeOf(charCountMap: MutableMap<Char, Int>): CardType {
        return when {
            charCountMap.keys.count() == 5 -> CardType.HighCard // all different
            charCountMap.keys.count() == 4 -> CardType.OnePair
            charCountMap.keys.count() == 3 -> {
                if (charCountMap.values.sorted().equals(listOf(1, 2, 2))) {
                    CardType.TwoPair
                } else {
                    CardType.ThreeOfKind
                }
            }

            charCountMap.keys.count() == 2 -> {
                if (charCountMap.values.sorted().equals(listOf(1, 4))) {
                    CardType.FourOfKind
                } else {
                    CardType.FullHouse
                }
            }
            charCountMap.keys.count() == 1 -> CardType.FiveOfKind

            else -> CardType.HighCard
        }
    }

    data class Bid (
        val hand: String,
        val cardType: CardType,
        val bidAmount: Int
    )

    fun part1(input: List<String>): Int {
        val bidList = mutableListOf<Bid>()
        input.forEach { line ->
            val (hand, bid) = line.split(" ")
            println("hand=$hand, bid-$bid")
            val charCountMap = mutableMapOf<Char, Int>()
            hand.forEach {
                if (charCountMap.containsKey(it)) {
                    charCountMap[it] = (charCountMap[it]?:0)+1
                } else {
                    charCountMap[it] = 1
                }
            }

            val cardType = getCardTypeOf(charCountMap)
            cardType.println()

            val bidHand = Bid(hand, cardType, bid.toInt())
            bidList.add(bidHand)
            println(charCountMap)
        }

        val bidComparator = object : Comparator<Bid> {
            override fun compare(bid1: Bid, bid2: Bid): Int {
                return if (bid1.cardType.point == bid2.cardType.point) {
                    val charVals = mapOf(
                        'T' to 10,
                        'J' to 11,
                        'Q' to 12,
                        'K' to 13,
                        'A' to 14
                    )

                    // check for each value
                    for (i in bid1.hand.indices) {
                        val c1Value = if (bid1.hand[i].isDigit()) bid1.hand[i].digitToInt() else charVals[bid1.hand[i]]
                        val c2Value = if (bid2.hand[i].isDigit()) bid2.hand[i].digitToInt() else charVals[bid2.hand[i]]

                        if (c1Value == c2Value) continue

                        return c1Value!! - c2Value!!
                    }
                    return 0
                } else {
                    bid1.cardType.point - bid2.cardType.point
                }
            }
        }
        bidList.sortWith(bidComparator)


        return bidList.withIndex().sumOf {
            (it.index+1) * it.value.bidAmount
        }
    }

    fun getMaxValueChar(hand: String): Char {
        if(hand.isEmpty()) return 'J' // no change
        val charCountMap = mutableMapOf<Char, Int>()
        hand.forEach {
            if (charCountMap.containsKey(it)) {
                charCountMap[it] = (charCountMap[it]?:0)+1
            } else {
                charCountMap[it] = 1
            }
        }

        return charCountMap.filter { it.value == charCountMap.values.max() }.keys.first()
    }

    fun part2(input: List<String>): Int {
        val bidList = mutableListOf<Bid>()
        input.forEach { line ->
            val (hand, bid) = line.split(" ")
            println("hand=$hand, bid-$bid")
            val woJoker = hand.replace("J","", true)
            val maxValueChar = getMaxValueChar(woJoker)
            println("maxValueChar=$maxValueChar")
            val newHand = hand.replace('J',maxValueChar, true)
            println("newHand=$newHand")
            val charCountMap = mutableMapOf<Char, Int>()
            newHand.forEach {
                if (charCountMap.containsKey(it)) {
                    charCountMap[it] = (charCountMap[it]?:0)+1
                } else {
                    charCountMap[it] = 1
                }
            }

            val cardType = getCardTypeOf(charCountMap)
            cardType.println()

            val bidHand = Bid(hand, cardType, bid.toInt())
            bidList.add(bidHand)
            println(charCountMap)
        }

        val bidComparator = object : Comparator<Bid> {
            override fun compare(bid1: Bid, bid2: Bid): Int {
                return if (bid1.cardType.point == bid2.cardType.point) {
                    val charVals = mapOf(
                        'T' to 10,
                        'J' to 1, // JOKER
                        'Q' to 12,
                        'K' to 13,
                        'A' to 14
                    )

                    // check for each value
                    for (i in bid1.hand.indices) {
                        val c1Value = if (bid1.hand[i].isDigit()) bid1.hand[i].digitToInt() else charVals[bid1.hand[i]]
                        val c2Value = if (bid2.hand[i].isDigit()) bid2.hand[i].digitToInt() else charVals[bid2.hand[i]]

                        if (c1Value == c2Value) continue

                        return c1Value!! - c2Value!!
                    }
                    return 0
                } else {
                    bid1.cardType.point - bid2.cardType.point
                }
            }
        }
        bidList.sortWith(bidComparator)

        return bidList.withIndex().sumOf {
            (it.index+1) * it.value.bidAmount
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput).also { it.println() } == 6440)

    val testInput2 = readInput("Day07_test")
    check(part2(testInput2) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
