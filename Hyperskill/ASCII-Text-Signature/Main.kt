package signature
import java.io.File
import java.util.*
import kotlin.math.abs
import kotlin.math.max

fun getInput(): Pair<String, String> {
    val scanner = Scanner(System.`in`)
    print("Enter name and surname: ")
    val name = scanner.nextLine()
    print("Enter person's status: ")
    val status = scanner.nextLine()
    return Pair(name, status)
}

fun getLetters(scanner: Scanner, depth: Int): MutableMap<Char, List<String>> {
    val letters = mutableMapOf<Char, List<String>>()

    while(scanner.hasNextLine()){
        val line = scanner.nextLine()
        val lineInfo = line.split(' ')
        if (lineInfo[0].isNotEmpty() && lineInfo[1].isNotEmpty()) {
            if (lineInfo[0].first().isLetter() && lineInfo[1].first().isDigit()) {
                letters[lineInfo[0].first()] = List(depth) { scanner.nextLine() }
            }
        }
    }

    return letters
}

fun fillLines(name: String, lines: MutableList<String>, letters: Map<Char, List<String>>, depth: Int): MutableList<String> {
    for (value in name) {
        for ((letter, string) in letters) {
            if (value == letter) {
                for (i in 0 until depth) {
                    lines[i] += string[i]
                }
            }
        }
        if (value == ' ') {
            if (depth == 10) {
                for (i in 0 until depth) {
                    lines[i] += " ".repeat(10)
                }
            } else {
                for (i in 0 until depth) {
                    lines[i] += " ".repeat(5)
                }
            }
        }
    }

    return lines
}

fun printMessage(nameLines: MutableList<String>, statusLines: MutableList<String>, depthName: Int, depthStatus: Int) {
    val textLength = max(nameLines[0].length, statusLines[0].length)
    val spaceToFill = abs(nameLines[0].length / 2.0 - statusLines[0].length / 2.0).toInt()

    print("8".repeat(textLength + 8)); println()
    if (nameLines[0].length >= statusLines[0].length) {
        for (i in 0 until depthName){
            print("88  "); print(nameLines[i]); print("  88"); println()
        }
        for (i in 0 until depthStatus){
            printIf(statusLines[i], spaceToFill, textLength)
        }
    } else {
        for (i in 0 until depthName){
            printIf(nameLines[i], spaceToFill, textLength)
        }
        for (i in 0 until depthStatus){
            print("88  "); print(statusLines[i]); print("  88"); println()
        }
    }
    print("8".repeat(textLength + 8)); println()
}

fun printIf(s: String, n: Int, t: Int) {
    print("88  "); print(" ".repeat(n)); print(s)
    if ((t - s.length) % 2 == 0) print(" ".repeat(n))
    else print(" ".repeat(n + 1)); print("  88"); println()
}

fun main() {
    val (name, status) = getInput()

    val fileRoman = File("D:/roman.txt").readText(Charsets.US_ASCII)
    val fileMedium = File("D:/medium.txt").readText(Charsets.US_ASCII)

    val scannerRoman = Scanner(fileRoman)
    val scannerMedium = Scanner(fileMedium)

    val depthRoman = scannerRoman.nextLine().split(' ').map { it.toInt() }.first()
    val depthMedium = scannerMedium.nextLine().split(' ').map { it.toInt() }.first()

    val romanLetters = getLetters(scannerRoman, depthRoman)
    val mediumLetters = getLetters(scannerMedium, depthMedium)

    var nameLines = mutableListOf("", "", "", "", "", "", "", "", "", "")
    nameLines = fillLines(name, nameLines, romanLetters, depthRoman)

    var statusLines = mutableListOf("", "", "")
    statusLines = fillLines(status, statusLines, mediumLetters, depthMedium)

    printMessage(nameLines, statusLines, depthRoman, depthMedium)
}