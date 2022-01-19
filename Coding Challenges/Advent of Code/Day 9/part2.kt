import java.io.File
import java.util.Scanner

var row = 0
var col = 0
var size = 0
val sizeList = mutableListOf<Int>() //list that stores all basin sizes.

fun setMapDimensions(file: File) {
    val scanner = Scanner(file)
    var bool = false

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()
        if (!bool) {
            col = line.length
            bool = true
        }
        row++
    }
}

fun getBasinSize(map: Array<Array<Pair<Int, Boolean>>>, i: Int, j: Int, level: Int) {
    //checks if indexes are inside the matrix, if it's part of the inner basin(0-8) and if it's not visited already.
    if (i >= 0 && i <= row - 1 && j >= 0 && j <= col - 1 && !map[i][j].second && map[i][j].first != 9) {
        map[i][j] = map[i][j].copy(second = true)
        size++ //when visited, size increases by one.
        getBasinSize(map, i - 1, j, level + 1) //go to closest 4 neighbors.
        getBasinSize(map, i + 1, j, level + 1)
        getBasinSize(map, i, j - 1, level + 1)
        getBasinSize(map, i, j + 1, level + 1)
        if (level == 0) sizeList.add(size) //only gets size of the basin when on root.
    }
}

fun main() {
    val file = File("src/input.txt")
    setMapDimensions(file)
    val map = Array(row) { Array(col) { Pair(0, false) } } //[value/id, isItVisited?]
    val scanner = Scanner(file)

    var i = 0
    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()

        for ((j, digit) in line.withIndex()) {
            map[i][j] = map[i][j].copy(first = digit.digitToInt())
        }
        i++
    }

    for (i in 0 until row) {
        for (j in 0 until col) {
            size = 0
            getBasinSize(map, i, j, 0)
        }
    }

    println(sizeList.sortedDescending().take(3).reduce { acc, i -> acc * i  })
}
