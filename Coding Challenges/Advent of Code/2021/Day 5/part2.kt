import java.io.File
import java.util.*
import kotlin.math.abs

const val DIM = 1000
var m = Array(DIM) { IntArray(DIM) { 0 } }

fun rangeMinMax (x: Int, y: Int) = minOf(x, y)..maxOf(x, y)

fun fillMatrix(s: String) {
    val n = s.split(" -> ")
    val (x1, y1) = n[0].split(',').map { it.toInt() }
    val (x2, y2) = n[1].split(',').map { it.toInt() }

    if (x1 == x2) { //vertical
        for (i in rangeMinMax(y1, y2)) {
            m[x1][i]++
        }
    }
    if (y1 == y2) { //horizontal
        for (i in rangeMinMax(x1, x2)) {
            m[i][y1]++
        }
    }
    if (abs(y2 - y1) == abs(x2 - x1)) { //diagonal
        val xPos = x1 < x2 //if x should increase or not
        val yPos = y1 < y2 //same for y
        for (i in 0 .. abs(y2 - y1)) {
            if (xPos) { if (yPos) m[x1 + i][y1 + i]++ else m[x1 + i][y1 - i]++ }
            else { if (yPos) m[x1 - i][y1 + i]++ else m[x1 - i][y1 - i]++ }
        }
    }
}

fun countMatrix(): Int {
    var count = 0

    for (i in 0 until DIM)
        for (j in 0 until DIM)
            if (m[i][j] > 1) count++
    return count
}

/*fun printMatrix() { //test for lidl matrix
    for (i in 0 until 10) {
        for (j in 0 until 10) {
            print(if (m[j][i] == 0) ". "
                else "${m[j][i]} ")
        }
        println()
    }
}*/

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)

    while(scanner.hasNext()) {
        fillMatrix(scanner.nextLine())
    }
    println(countMatrix())
}
