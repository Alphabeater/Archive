import java.io.File
import java.util.Scanner

var sum = 0
val list = listOf('(', '[', '{', '<')

fun analyzeLine(l: String) { //removes legal pairs of matching characters, if found a corrupt character, breaks the main loop and adds the value to the sum.
    //If no other legal pairs remain and the line still has characters means that this is an incomplete line, which breaks the main loop and continues to the next line.
    var line = l

    outerLoop@ while(true) {
        innerLoop@ for (i in line.indices) {
            if (i < line.length - 1)
                when (line[i]) {
                    '(' -> {
                        if (line[i + 1] == ')') {
                            line = line.removeRange(i..i + 1)
                            break@innerLoop
                        } else {
                            if (line[i + 1] !in list) {
                                illegalValue(line[i + 1])
                                break@outerLoop
                            }
                        }
                    }
                    '[' -> {
                        if (line[i + 1] == ']') {
                            line = line.removeRange(i..i + 1)
                            break@innerLoop
                        } else {
                            if (line[i + 1] !in list) {
                                illegalValue(line[i + 1])
                                break@outerLoop
                            }
                        }
                    }
                    '{' -> {
                        if (line[i + 1] == '}') {
                            line = line.removeRange(i..i + 1)
                            break@innerLoop
                        } else {
                            if (line[i + 1] !in list) {
                                illegalValue(line[i + 1])
                                break@outerLoop
                            }
                        }
                    }
                    '<' -> {
                        if (line[i + 1] == '>') {
                            line = line.removeRange(i..i + 1)
                            break@innerLoop
                        } else {
                            if (line[i + 1] !in list) {
                                illegalValue(line[i + 1])
                                break@outerLoop
                            }
                        }
                    }
                    else -> break@innerLoop
                }
            else {
                break@outerLoop
            }
        }
    }
}

fun illegalValue(char: Char) { //this works always because the first "if" in the analizeLine function makes the "when" in this function never happen (for that particular character).
    when (char) {
        ')' -> sum += 3
        ']' -> sum += 57
        '}' -> sum += 1197
        '>' -> sum += 25137
    }
}

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()!!
        analyzeLine(line)
    }

    println(sum)
}
