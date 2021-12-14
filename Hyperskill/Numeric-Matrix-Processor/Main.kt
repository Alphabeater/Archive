package processor

import java.util.*

val s = Scanner(System.`in`)

fun main() {
    loop@ do {
        print("""
            1. Add matrices
            2. Multiply matrix by a constant
            3. Multiply matrices
            4. Transpose matrix
            5. Calculate a determinant
            6. Inverse matrix
            0. Exit
            Your choice: 
        """.trimIndent())
        val option = s.nextInt()
        try {
            when (option) {
                0 -> break@loop
                1 -> printMatrix(sumMatrix(
                    readMatrix("first "), readMatrix("second ")))
                2 -> printMatrix(multiplyMatrixConst(
                    readMatrix(""), readConst()))
                3 -> printMatrix(multiplyMatrix(
                    readMatrix("first "), readMatrix("second ")
                ))
                4 -> printMatrix(transposeMatrix())
                5 -> println(calculateDeterminant(readMatrix("")))
                6 -> readMatrix("").let { printMatrix(inverseMatrix(
                    it, recursiveDeterminant(it))) }
                else -> throw Exception("Invalid option.")
            }
        } catch (e: Exception) {
            println(e.message)
            continue@loop
        }
    } while (true)
}

fun sumMatrix(a: Array<Array<Double>>, b: Array<Array<Double>>):
        Array<Array<Double>> {
    if (a.size == b.size && a[0].size == b[0].size) {
        val c = Array(a.size) { Array(a[0].size) { 0.0 } }
        for (i in a.indices) {
            for (j in a[0].indices) {
                c[i][j] = a[i][j] + b[i][j]
            }
        }
        return c
    } else throw Exception("The operation cannot be performed.")
}

fun multiplyMatrixConst(m: Array<Array<Double>>, const: Double):
        Array<Array<Double>> {
    val c = Array(m.size) { Array(m[0].size) { 0.0 } }
    for (i in m.indices) {
        for (j in m[0].indices) {
            c[i][j] = m[i][j] * const
        }
    }
    return c
}

fun multiplyMatrix(a: Array<Array<Double>>, b: Array<Array<Double>>):
        Array<Array<Double>> {
    if (a[0].size == b.size) {
        val c = Array(a.size) { Array(b[0].size) { 0.0 } }
        for (i in c.indices) {
            for (j in c[0].indices) {
                var r = 0.0
                for (k in a[0].indices){
                    r += a[i][k] * b[k][j]
                }
                c[i][j] = r
            }
        }
        return c
    } else throw Exception("The operation cannot be performed.")
}

fun transposeMatrix(): Array<Array<Double>> {
    print("""
        1. Main diagonal
        2. Side diagonal
        3. Vertical line
        4. Horizontal line
        Your choice: 
    """.trimIndent())
    val option = s.nextInt()
    val m = readMatrix("")
    return when (option) {
        1 -> tMainDiagonal(m)
        2 -> tSideDiagonal(m)
        3 -> tVerticalLine(m)
        4 -> tHorizontalLine(m)
        else -> throw Exception("Invalid option.")
    }
}

fun tMainDiagonal(m: Array<Array<Double>>): Array<Array<Double>> {
    val r = Array(m[0].size) { Array(m.size) { 0.0 } }
    for (i in m.indices) {
        for (j in m[0].indices) {
            r[j][i] = m[i][j]
        }
    }
    return r
}

fun tSideDiagonal(m: Array<Array<Double>>): Array<Array<Double>> {
    val r = Array(m[0].size) { Array(m.size) { 0.0 } }
    for (i in m.size - 1 downTo 0) {
        for (j in m[0].size - 1 downTo 0) {
            r[i][j] = m[m[0].size - 1 - j][m.size - 1 - i]
        }
    }
    return r
}

fun tVerticalLine(m: Array<Array<Double>>): Array<Array<Double>> {
    val r = Array(m[0].size) { Array(m.size) { 0.0 } }
    for (i in m.indices) {
        for (j in m[0].indices) {
            r[i][j] = m[i][m[0].size - 1 - j]
        }
    }
    return r
}

fun tHorizontalLine(m: Array<Array<Double>>): Array<Array<Double>> {
    val r = Array(m[0].size) { Array(m.size) { 0.0 } }
    for (i in m.indices) {
        for (j in m[0].indices) {
            r[i][j] = m[m.size - 1 - i][j]
        }
    }
    return r
}

fun calculateDeterminant(m: Array<Array<Double>>): Double {
    if (m.size == m[0].size) {
        return recursiveDeterminant(m)
    } else throw Exception("The operation cannot be performed.")
}

fun recursiveDeterminant(m: Array<Array<Double>>): Double {
    return if (m.size == 2) {
        m[0][0] * m[1][1] - m[0][1] * m[1][0]
    } else {
        var res = 0.0
        for (j in m[0].indices) {
            val n = getMinor(m, j)
            val sign = if ( j % 2 == 0) 1.0 else -1.0
            res += sign * m[0][j] * recursiveDeterminant(n)
        }
        res
    }
}

fun getMinor(m: Array<Array<Double>>, x: Int): Array<Array<Double>> {
    val r = Array(m[0].size - 1) { Array(m.size - 1) { 0.0 } }
    var k = 0
    for (i in 1 until m.size) {
        for (j in m[0].indices) {
            if (x != j) {
                r[i - 1][k++] = m[i][j]
            }
        }
        k = 0
    }
    return r
}

fun getAdjointMatrix(m: Array<Array<Double>>): Array<Array<Double>> {
    val r = Array(m[0].size) { Array(m.size) { 0.0 } }
    for (i in m.indices) {
        for (j in m[i].indices) {
            var t =  Array(m[0].size) { Array(m[0].size) { 0.0 } }
            for (k in m.indices) {
                t[k] = m[k].filterIndexed { id, _ -> id != j }.toTypedArray()
            }
            t = t.filterIndexed { id, _ -> id != i }.toTypedArray()
            val sign = if ( (i + j) % 2 == 0) 1.0 else -1.0
            r[i][j] = sign * recursiveDeterminant(t)
        }
    }
    return tMainDiagonal(r)
}

fun inverseMatrix(m: Array<Array<Double>>, det: Double): Array<Array<Double>> {
    if (det != 0.0) {
        return multiplyMatrixConst(getAdjointMatrix(m), 1.0 / det)
    } else throw Exception("This matrix doesn't have an inverse.")
}

fun readMatrix(str: String): Array<Array<Double>> {
    print("Enter size of ${str}matrix: ")
    val (row, col) = Array(2) { s.nextInt() }
    print("Enter ${str}matrix:\n")
    return Array(row) { Array(col) { s.nextDouble() } }
}

fun printMatrix(c: Array<Array<Double>>) {
    println("The result is:")
    for (i in c.indices) {
        for (j in c[0].indices) {
            print("${c[i][j]} ")
        }
        println()
    }
}

fun readConst(): Double{
    print("Enter constant: ")
    return s.nextDouble()
}
