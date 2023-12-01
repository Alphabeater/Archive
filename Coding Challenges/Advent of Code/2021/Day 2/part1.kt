import java.io.File

fun main() {
    val file = File("src/input.txt")
    val array = file.readLines().toList()
    var horizontalPosition = 0
    var verticalPosition = 0
    array.forEach{
        val tuple = it.split(" ") //separate the command and the number given.
        val command = tuple[0]
        val number = tuple[1].toInt()
        when(command) {
            "forward" -> horizontalPosition += number
            "down" -> verticalPosition += number
            "up" -> verticalPosition -= number
        }
    }
    print(horizontalPosition * verticalPosition)
}