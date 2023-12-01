import java.io.File
import java.util.Scanner

const val days = 256
const val max_days = 8

var fishCount = MutableList<Long>(max_days + 1) { 0 }

fun List<Long>.rotateLeft(n: Int) = (drop(n) + take(n)) as MutableList<Long>

fun addDay() { //rotate left by 1 and set the fish for the 8th day + sum the fish for the 6th day
    var aux = 0L

    if (fishCount[0] > 0) {
        aux = fishCount[0] //just an auxiliary var
    }
    fishCount = fishCount.rotateLeft(1)
    fishCount[8] + aux
    fishCount[6] += aux
}

fun main() {
    val file = File("src/input.txt")
    val fishList = Scanner(file).nextLine().split(',').map{ it.toInt() }
    fishList.forEach { fishCount[it]++ } //populate count for each day

    for (i in 0 until days) {
//        fishCount.forEach { print("$it ") } //for test purposes
//        println() //for test purposes
        addDay()
    }

    println(fishCount.sum())
}
