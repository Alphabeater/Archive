import java.io.File

fun main() {
    val file = File("src/input.txt")
    val array = file.readLines().toList()
    var aim = 0
    var horizontalPosition = 0
    var depth = 0
    array.forEach{
        val tuple = it.split(" ")
        val command = tuple[0]
        val number = tuple[1].toInt()
        when(command) {
            "forward" -> {
                horizontalPosition += number
                depth += aim * number
            }
            "down" -> aim += number
            "up" -> aim -= number
        }
    }
    print(horizontalPosition * depth)
}