import java.io.File
import java.util.Scanner
import kotlin.system.exitProcess

const val DIM = 5

class BingoBoard {
    private val size = DIM
    val board = Array(size) { IntArray(size) } //this represents a single bingo board[x][x].
    val found = Array(size) { Array(size){ false } } //this represents what numbers have been guessed so far.
}

fun getLottoNumbers(scanner: Scanner): List<Int> { //reads and stores the lotto numbers.
    return scanner.nextLine().split(",").map{ it.toInt() }
}

fun getBingoBoards(scanner: Scanner): MutableList<BingoBoard> {
    val boards: MutableList<BingoBoard> = mutableListOf()
    var boardCount = -1
    var count = 0

    while (scanner.hasNext()) {
        var line = scanner.nextLine()
        if (line.isBlank()) { //this happens when a new line is found in the input.
            boards += BingoBoard()
            boardCount++
            count = 0
            continue
        }
        if (line.first() == ' ') line = line.drop(1) //prevent an error if the first character is a spacebar.
        val values = line.split(" +".toRegex()).map{ it.toInt() }
        for (i in 0 until DIM) {
            boards[boardCount].board[count][i] = values[i] //the values of each bingo board are stored.
        }
        count++
    }
    return boards
}

fun drawLottoNumber(boards: MutableList<BingoBoard>, number: Int): MutableList<BingoBoard> {
    boards.forEach {
        for (j in 0 until DIM)
            for (k in 0 until DIM)
                if (it.board[j][k] == number) it.found[j][k] = true //set the found number on each board.
    }
    return boards
}

fun checkWinner(boards: MutableList<BingoBoard>): BingoBoard? { //returns the position of the winner board.
    var countHorizontal = 0
    var countVertical = 0
    boards.forEach { bingoBoard ->
        for (j in 0 until DIM) {
            for (k in 0 until DIM) {
                if (bingoBoard.found[j][k]) countHorizontal++
                if (bingoBoard.found[k][j]) countVertical++
            }
            if (countHorizontal == 5 || countVertical == 5) return bingoBoard
            countHorizontal = 0
            countVertical = 0
        }
    }
    return null
}

fun getWinner(bingoBoard: BingoBoard, number: Int): Int {
    var sum = 0
    for (i in 0 until DIM) {
        for (j in 0 until DIM) {
            if (!bingoBoard.found[i][j]) sum += bingoBoard.board[i][j]
        }
    }
    return sum * number
}

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)
    val numbers = getLottoNumbers(scanner)
    var boards = getBingoBoards(scanner)

    for (number in numbers) {
        boards = drawLottoNumber(boards, number)
        val board = checkWinner(boards)
        if (board != null) {
            println(getWinner(board, number))
            exitProcess(1)
        }
    }
}
