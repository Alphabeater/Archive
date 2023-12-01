import java.io.File
import java.util.Scanner

val octopusMatrix: ArrayList<ArrayList<Int>> = arrayListOf() //energy matrix of the octupi.
val octopusMatrixFlash: ArrayList<ArrayList<Boolean>> = arrayListOf() //keep track of which octopi flashed.

fun sincronizationOfFlashes(n: Int): Boolean {
    var count = 0

    for (l in octopusMatrix) {
        count += l.count { it == 0 }
    }
    return count == n*n
}

fun addtoNeighbors(i: Int, j: Int, n: Int) { //recursive function that gets valid neighbors and add energy to them.
    var directions = mutableListOf(true, true, true, true, true, true, true, true) //N NE E SE S SW W NW

    octopusMatrixFlash[i][j] = true //this particular octopus already flashed, so it should not flash again.
    if (i == 0) { //N
        directions[7] = false
        directions[0] = false
        directions[1] = false
    }
    if (i == n - 1) { //S
        directions[3] = false
        directions[4] = false
        directions[5] = false
    }
    if (j == 0) { //W
        directions[5] = false
        directions[6] = false
        directions[7] = false
    }
    if (j == n - 1) { //E
        directions[1] = false
        directions[2] = false
        directions[3] = false
    }
    for ((k, d) in directions.withIndex()) //N NE E SE S SW W NW
        if (d) when (k) { //all possibilities (if direction, then repeat recursive function for this neighbor.
            0 -> { //N
                helper(i - 1, j, n)
            }
            1 -> { //NE
                helper(i - 1, j + 1, n)
            }
            2 -> { //E
                helper(i, j + 1, n)
            }
            3 -> { //SE
                helper(i + 1, j + 1, n)
            }
            4 -> { //S
                helper(i + 1, j, n)
            }
            5 -> { //SW
                helper(i + 1, j - 1, n)
            }
            6 -> { //W
                helper(i, j - 1, n)
            }
            7 -> { //NW
                helper(i - 1, j - 1, n)
            }
        }
}

fun helper(i: Int, j: Int, n: Int) { //function to avoid boilerplate.
    octopusMatrix[i][j]++
    if (!octopusMatrixFlash[i][j] && octopusMatrix[i][j] > 9) addtoNeighbors(i, j, n) //if it's not already flashed but has more than 9 energy.
}

fun zeroAndPrintMatrix(n: Int) {
    for (i in 0 until n) {
        for (j in 0 until n) {
            if (octopusMatrix[i][j] > 9) octopusMatrix[i][j] = 0
//            if (octopusMatrix[i][j] != 0) print("\u001b[0;37m${octopusMatrix[i][j]}\u001B[0m  ") //optional
//            else print("\u001B[0;29m${octopusMatrix[i][j]}\u001B[0m  ") //optional
        }
//        println() //optional
    }
//    println() //optional
}

fun falseMatrix(n: Int) {
    for (i in 0 until n)
        for (j in 0 until n)
            octopusMatrixFlash[i][j] = false
}

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()!!

        octopusMatrix.add(line.map { it.digitToInt() } as ArrayList<Int>)
    }

    val n = octopusMatrix.size //assuming matrix is NxN.

    for (i in 0 until n) {
        octopusMatrixFlash.add(arrayListOf(false, false, false, false, false, false, false, false, false, false))
    }

//    zeroAndPrintMatrix(n) //optional

    var step = 0 //keeps track of the step number.

    while (!sincronizationOfFlashes(n)) { //breaks the loop if all of the octopi flash at the same time.
        step++
        for (i in 0 until n)
            for (j in 0 until n) {
                octopusMatrix[i][j]++
                if (octopusMatrix[i][j] > 9) {
                    if (!octopusMatrixFlash[i][j]) addtoNeighbors(i, j, n)
                }
            }

        zeroAndPrintMatrix(n) //change values > 9 to 0 (just to print it better).
        falseMatrix(n) //need to give false values on all flash matrix to use in the next iteration.
    }

    println(step)
}
