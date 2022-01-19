import java.io.File
import java.util.Scanner

fun setMapDimensions(file: File): Pair<Int, Int> {
    val scanner = Scanner(file)
    var row = 0
    var col = 0
    var bool = false

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()
        if (!bool) {
            col = line.length
            bool = true
        }
        row++
    }

    return Pair(row, col)
}

fun main() {
    val file = File("src/input.txt")
    val (row, col) = setMapDimensions(file)
    val map = Array(row) { Array(col) { Pair(0, true) } } //[value, isLowPoint?]
    val scanner = Scanner(file)

    var i = 0
    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()

        for ((j, digit) in line.withIndex()) {
            map[i][j] = map[i][j].copy(first = digit.digitToInt())
        }
        i++
    }

    for (i in 0 until row) { //iterate through every matrix element and compare it to the closest 4 neighbors (up, down, left, right)
        for (j in 0 until col) {
            if (i > 0) if (map[i][j].first >= map[i - 1][j].first) map[i][j] = map[i][j].copy(second = false)
            if (i < row - 1) if (map[i][j].first >= map[i + 1][j].first) map[i][j] = map[i][j].copy(second = false)
            if (j > 0) if (map[i][j].first >= map[i][j - 1].first) map[i][j] = map[i][j].copy(second = false)
            if (j < col - 1) if (map[i][j].first >= map[i][j + 1].first) map[i][j] = map[i][j].copy(second = false)
        }
    }

    var sum = 0

    for (i in 0 until row) {
        for (j in 0 until col) {
            if (map[i][j].second) sum += map[i][j].first + 1
        }
    }

    println(sum)
}
