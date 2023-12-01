import java.io.File

fun main() {
    val file = File("src/input.txt")
    val array = file.readLines().toList()
    var count = 0
    for (i in 3..array.lastIndex) { // if you compare A + B + C to B + C + D, B and C are irrelevant, so only A and D are compared.
        if (array[i].toInt() > array[i - 3].toInt()) count++
    }
    print(count)
}