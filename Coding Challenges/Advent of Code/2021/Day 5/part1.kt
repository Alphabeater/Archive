import java.io.File
import java.util.*

const val DIM = 1000
var m = Array(DIM) { IntArray(DIM) { 0 } }

fun fillMatrix(s: String) {
    val n = s.split(" -> ")
    val (x1, y1) = n[0].split(',').map { it.toInt() }
    val (x2, y2) = n[1].split(',').map { it.toInt() }

    if (x1 == x2) {
        val minY = minOf(y1, y2)
        val maxY = maxOf(y1, y2)
        for (i in minY..maxY) {
            m[x1][i]++
        }
    } else if (y1 == y2) {
        val minX = minOf(x1, x2)
        val maxX = maxOf(x1, x2)
        for (i in minX..maxX) {
            m[i][y1]++
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

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)

    while(scanner.hasNext()) {
        fillMatrix(scanner.nextLine())
    }

    println(countMatrix())
}


