package sorting
import java.util.*

val s = Scanner(System.`in`)

fun main(args: Array<String>) {
    if (args.isEmpty()) parseText("word")
    else if (args.contains("-sortIntegers")) {
        sortIntegers()
    } else if (args[0] == "-dataType") {
        when (args[1]) {
            "long" -> parseText("long")
            "line" -> parseText("line")
            "word" -> parseText("word")
            else -> throw Exception("Invalid argument.")
        }
    } else throw Exception("Incorrect ose of program.")
}

fun sortIntegers() {
    val numbers = mutableListOf<Int>()
    do {
        numbers += s.nextInt()
    } while(s.hasNext())
    val sortedNumbers = numbers.sorted()
    println("Total numbers: ${numbers.size}.\n" +
            "Sorted data: ${sortedNumbers.joinToString(" ")}")
}

fun parseText(option: String) {
    when (option) {
        "long" -> {
            val numbers = mutableListOf<Int>()
            do {
                numbers += s.nextInt()
            } while(s.hasNext())
            val maxNumber = numbers.max()
            val maxNumberCount = numbers.count { it == maxNumber }
            println("Total numbers: ${numbers.size}.\n" +
                    "The greatest number: $maxNumber ($maxNumberCount time(s), ${100 * maxNumberCount / numbers.size}%).")
        }
        "line" -> {
            val lines = mutableListOf<String>()
            do {
                lines += s.nextLine()
            } while(s.hasNext())
            val longestLine = lines.maxBy { it.length }
            val longestLineCount = lines.count { it == longestLine}
            println("Total lines: ${lines.size}.\n" +
                    "The longest line: \n$longestLine\n" +
                    "($longestLineCount time(s), ${100 * longestLineCount / lines.size}%).")
        }
        "word" -> {
            val words = mutableListOf<String>()
            do {
                words += s.next()
            } while(s.hasNext())
            val longestWord = words.maxBy { it.length }
            val longestWordCount = words.count { it == longestWord}
            println("Total words: ${words.size}.\n" +
                    "The longest word: $longestWord ($longestWordCount time(s), ${100 * longestWordCount / words.size}%).")
        }
    }
}