import java.io.File
import java.util.Scanner

val list = listOf('(', '[', '{', '<')
val lineScores = mutableListOf<Long>()
val scoreDict = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4) //scores for each character.

fun analyzeLine(l: String) { //if it's a corrupted line, skip. If it's an incomplete line, it's evaluated by getScore().
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
                            if (line[i + 1] !in list) break@outerLoop
                        }
                    }
                    '[' -> {
                        if (line[i + 1] == ']') {
                            line = line.removeRange(i..i + 1)
                            break@innerLoop
                        } else {
                            if (line[i + 1] !in list) break@outerLoop
                        }
                    }
                    '{' -> {
                        if (line[i + 1] == '}') {
                            line = line.removeRange(i..i + 1)
                            break@innerLoop
                        } else {
                            if (line[i + 1] !in list) break@outerLoop
                        }
                    }
                    '<' -> {
                        if (line[i + 1] == '>') {
                            line = line.removeRange(i..i + 1)
                            break@innerLoop
                        } else {
                            if (line[i + 1] !in list) break@outerLoop
                        }
                    }
                    else -> break@innerLoop
                }
            else {
                getScore(line)
                break@outerLoop
            }
        }
    }
}

fun getScore(line: String) { //reverse the incomplete line and loop it, using the formula provided.
    var score = 0L

    for (char in line.reversed()) {
        score *= 5
        score += scoreDict[char]!!
    }
    lineScores.add(score)
}

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()!!
        analyzeLine(line)
    }

    println(lineScores.sorted()[lineScores.size/2]) //sort the values and display the one in the middle.
}

