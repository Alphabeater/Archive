import java.io.File
import java.util.Scanner
import kotlin.math.pow

/*  dddd            x0x0x0x0
   e    a          x6      x1
   e    a          x6      x1
    ffff      ->    x2x2x2x2
   g    b          x5      x3
   g    b          x5      x3
    cccc            x4x4x4x4
*/

var stv = mutableMapOf<String, Int>() //string to value
var sitl = mutableMapOf<Int, Char>() //subindex to letter: subindex from the x in the pic -> corresponding letter

fun cleanFun(input: List<String>): List<String> {
    val output = mutableListOf<String>()

    for (item in input) {
        output.add(item.toMutableList().sorted().joinToString(""))
    }

    return if (input.size > 4) {
        output.sortedBy{ it.length }
    } else output
}

fun addValues(input: List<String>) {
    //frequency of letters: x6=4, x7=6, x4=9
    val letters = IntArray(7)

    for (i in 'a'..'g') {
        for (word in input) {
            if (i in word) letters[i - 'a']++
        }
    }
    for (i in letters.indices) {
        when (letters[i]) {
            4 -> sitl[5] = 'a' + i
            6 -> sitl[6] = 'a' + i
            9 -> sitl[3] = 'a' + i
        }
    }
    for (item in input) {
        when (item.length) {
            2 -> stv[item] = 1
            3 -> stv[item] = 7
            4 -> stv[item] = 4
            7 -> stv[item] = 8
        }
    }

    sitl[1] = input[0].notInLetterMap()
    sitl[0] = input[1].notInLetterMap()
    sitl[2] = input[2].notInLetterMap()
    sitl[4] = input[3].notInLetterMap()

    stv[changeIndexToString("013456")] = 0
    stv[changeIndexToString("01245")] = 2
    stv[changeIndexToString("01234")] = 3
    stv[changeIndexToString("02346")] = 5
    stv[changeIndexToString("023456")] = 6
    stv[changeIndexToString("012346")] = 9
}

fun String.notInLetterMap(): Char {
    for (char in this) {
        if (!sitl.containsValue(char)) return char
    }
    return 'z' //never reached
}

fun changeIndexToString(numbers: String): String {
    var word = ""

    for (number in numbers) {
        word += sitl[number.digitToInt()]
    }

    return word.toMutableList().sorted().joinToString("")
}

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)
    var sum = 0

    while (scanner.hasNext()) {
        val (input, output) = scanner.nextLine().split(" | ")
        val inputClean = cleanFun(input.split(' '))
        val outputClean = cleanFun(output.split(' '))
        var partialSum = 0

        addValues(inputClean)

        for ((e, item) in outputClean.reversed().withIndex()) {
            partialSum += (10.0.pow(e) * stv[item]!!).toInt()
        }

        sum += partialSum
        stv.clear()
        sitl.clear()
    }

    println(sum)
}