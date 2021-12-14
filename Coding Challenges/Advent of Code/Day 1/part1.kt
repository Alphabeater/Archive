import java.io.File

fun main() {
    val file = File("src/input.txt")
    val array = file.readLines().toList()
    var count = 0
    for (i in 1..array.lastIndex) { //if the previous number is bigger, depth increases.
        if (array[i].toInt() > array[i - 1].toInt()) count++
    }
    print(count)
}