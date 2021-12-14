import java.io.File
import kotlin.math.pow

fun convertToDecimal(exp: Int): Int {
    return 2.0.pow(exp).toInt()
}

fun main() {
    val file = File("src/input.txt")
    val list = file.readLines().toList()
    val length = list[0].length
    val arrayCount = Array(length){0}
    list.forEach { //counts how many 1's in the numbers and stores it in an array. This new array contains the bit criteria of every position of the numbers.
        for (j in 0 until length) {
            if (it[j] == '1') {
                arrayCount[j]++
            }
        }
    }
    var gammaRate = 0
    var epsilonRate = 0
    for (i in 0 until length) { //calculates the new values of the rates.
        if (arrayCount[i] > list.size / 2) gammaRate += convertToDecimal(length - i - 1)
        else epsilonRate += convertToDecimal(length - i - 1)
    }
    print(gammaRate * epsilonRate)
}