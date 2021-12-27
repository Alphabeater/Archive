import java.io.File
import java.util.Scanner
import kotlin.math.abs

var crabPositions = listOf<Int>()
var minVal = 0
var maxVal = 0
var value = -1 //used to return the last value of the recursive function.

fun getMinFuel(minPos: Int, maxPos: Int): Int {
    val midPos = (maxPos + minPos) / 2

    if (getFuel(minPos + 1) == getFuel(maxPos - 1)) {
        value = minOf(getFuel(minPos), getFuel(maxPos), getFuel(midPos)) //update the global value to return on the
        return value
    }

    val lowerMidPos = (minPos + midPos) / 2
    val upperMidPos = (midPos + maxPos) / 2

    if (getFuel(lowerMidPos) < getFuel(upperMidPos)) { //choose lower half of array
        getMinFuel(minPos, midPos)
    } else { //choose upper half of array
        getMinFuel(midPos, maxPos)
    }

    return value
}

fun getFuel(pos: Int): Int {
    var fuel = 0

    for (crab in crabPositions) {
        fuel += abs(crab - pos)
    }
    return fuel
}

fun main() {
    val file = File("src/input.txt")
    crabPositions = Scanner(file).nextLine().split(',').map{ it.toInt() }
    minVal = crabPositions.minOrNull()!!
    maxVal = crabPositions.maxOrNull()!!

    println(getMinFuel(minVal, maxVal))
}
