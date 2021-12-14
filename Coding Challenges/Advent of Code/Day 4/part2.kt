import java.io.File
import java.util.Scanner
import kotlin.system.exitProcess

const val DIM = 5

class BingoBoard {
    private val size = DIM
    var winner = false
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
        if (!it.winner)
            for (j in 0 until DIM)
                for (k in 0 until DIM)
                    if (it.board[j][k] == number) it.found[j][k] = true //set the found number on each board (if it isn't a binning board yet).
    }
    return boards
}

fun printBoardsAdvanced(boards: MutableList<BingoBoard>) {
    for (i in 0 until 50) {//chosen to fit screen
        for (j in 0 until 50) {//chosen to fit screen
            val bingoBoard = boards[i / DIM * 10 + j / DIM]
            val board = bingoBoard.board[i % DIM][j % DIM]
            val found = bingoBoard.found[i % DIM][j % DIM]

            print(if (found) {
                if (bingoBoard.winner) {
                    if (board in 0..9) "\u001b[0;32m ${board}\u001B[0m  " //32: green
                    else "\u001b[0;32m ${board}\u001B[0m "
                } else {
                    if (board in 0..9) "\u001b[0;29m ${board}\u001B[0m  " //29: white
                    else "\u001b[0;29m ${board}\u001B[0m "
                }
            } else {
                if (board in 0..9) "\u001b[0;37m ${board}\u001B[0m  " //37: gray
                else "\u001b[0;37m ${board}\u001B[0m "
            })
            if (j != 0 && (j + 1) % DIM == 0) print("   ")
        }
        println()
        if (i != 0 && (i + 1) % DIM == 0) println()
    }
}

fun checkWinner(boards: MutableList<BingoBoard>): Int { //returns the position of the winner board.
    var boardNumber = -1
    var countHorizontal = 0
    var countVertical = 0
    boards.forEachIndexed { i, bingoBoard ->
        if (!bingoBoard.winner) {
            for (j in 0 until DIM) {
                for (k in 0 until DIM) {
                    if (bingoBoard.found[j][k]) countHorizontal++
                    if (bingoBoard.found[k][j]) countVertical++
                }
                if (countHorizontal == DIM || countVertical == DIM) {
                    bingoBoard.winner = true
                    boardNumber = i
                }
                countHorizontal = 0
                countVertical = 0
            }
        }
    }
    return boardNumber
}

fun countWinners(boards: MutableList<BingoBoard>): Boolean {
    var count = 0
    for (i in 0 until boards.size) {
        if (boards[i].winner) count++
    }
    return count == boards.size
}

fun getWinner(boards: MutableList<BingoBoard>, boardNumber: Int, number: Int): Int {
    var sum = 0
    for (i in 0 until DIM) {
        for (j in 0 until DIM) {
            if (!boards[boardNumber].found[i][j]) sum += boards[boardNumber].board[i][j]
        }
    }
    return sum * number
}

fun main() {
    val file = File("src/input.txt")
    val scanner = Scanner(file)
    val numbers = getLottoNumbers(scanner)
    var boards = getBingoBoards(scanner)

//    println("Lotto numbers: $numbers") //optional
    for (number in numbers) {
//        println("number drawn: $number") //optional
        boards = drawLottoNumber(boards, number)
//        printBoardsAdvanced(boards) //optional
//        println("-----------------------------------------------------------------------------------------------------------------------------------------\n") //optional
        val boardNumber = checkWinner(boards)
        if (countWinners(boards)) {
            println(getWinner(boards, boardNumber, number))
            exitProcess(1)
        }
//        println("next line...") //optional
//        readLine()!! //optional
    }
}


