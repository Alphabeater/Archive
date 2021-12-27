import java.io.File
import java.util.Scanner

const val days = 80

fun addDay(fishList: MutableList<Int>): MutableList<Int> {
    for (i in 0 until fishList.size) {
        if (fishList[i] == 0) {
            fishList[i] = 6
            fishList += 8
        } else fishList[i]--
    }
    return fishList
}

fun main() {
    val file = File("src/input.txt")
    var fishList = Scanner(file).nextLine().split(',').map{ it.toInt() }.toMutableList()

    for (i in 0 until days) {
        fishList = addDay(fishList)
    }
    println(fishList.count())
}
