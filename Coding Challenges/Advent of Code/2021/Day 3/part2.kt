import java.io.File
import kotlin.math.pow

fun getNewList(list: List<String>, position: Int, criteria: Char, length: Int): List<String> {
    if (list.size == 1) return list
    val criteriaBit = getBit(list, position, criteria) //get the new criteria bit which will determine the new list for the next step of the recursion.
    val shorterList = mutableListOf<String>()
    list.forEach { //if the position in each number matches the criteria bit, append the number to the new list.
        if (it[position] == criteriaBit) shorterList += it
    }
    return getNewList(shorterList, position + 1, criteria, length) //next step of the recursion with the new list and the next position.
}

fun getBit(list: List<String>, position: Int, criteria: Char): Char {
    var count = 0
    list.forEach { //counts how many 1's in the number.
        if (it[position] == '1') {
            count++
        }
    }
    val other = list.size - count //a variable that determines the remaining values (0's).
    return if (criteria == '1') { //most
        if (count >= other) '1'
        else '0'
    } else { //least
        if (count < other) '1'
        else '0'
    }
}

fun convertToDecimal(exp: Int): Int {
    return 2.0.pow(exp).toInt()
}

fun main() {
    val file = File("src/input.txt")
    val list = file.readLines().toList()
    val length = list[0].length
    val oxygenGeneratorRatingSequence = getNewList(list, 0, '1', length)
    val CO2ScrubberRatingSequence = getNewList(list, 0, '0', length)
    var oxygenGeneratorRating = 0
    var CO2ScrubberRating = 0

    for (i in 0 until length) { //converts each rating to their corresponding decimal value.
        if (oxygenGeneratorRatingSequence.first()[i] == '1') {
            oxygenGeneratorRating += convertToDecimal(length - i - 1)
        }
        if (CO2ScrubberRatingSequence.first()[i] == '1') {
            CO2ScrubberRating += convertToDecimal(length - i - 1)
        }
    }
    print(oxygenGeneratorRating * CO2ScrubberRating)
}