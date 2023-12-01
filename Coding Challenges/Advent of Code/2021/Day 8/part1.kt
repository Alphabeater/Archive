import java.io.File
import java.util.Scanner

fun main() {
    val file = File("src/input.txt")

    val scanner = Scanner(file)
    var count = 0

    while (scanner.hasNext()) {
        val (input, output) = scanner.nextLine().split(" | ")
        val inputClean = cleanFun(input.split(' '))
        val outputClean = cleanFun(output.split(' '))

        for (item in outputClean) {
            if (inputClean.contains(item)) count++
        }
    }

    println(count)
}

fun cleanFun(input: List<String>): List<String> {
    val output = mutableListOf<String>()

    for (item in input) {
        val size = item.length
        if (size == 2 || size == 3 || size == 4 || size == 7) output.add(item.toMutableList().sorted().joinToString(""))
    }
    return output
}
