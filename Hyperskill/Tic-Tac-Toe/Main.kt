package tictactoe

fun printBoard(board: MutableList<Char>) {
    var str = "---------\n"
    for (i in 0..8 step 3) {
        str+= "| ${board[i]} ${board[i + 1]} ${board[i + 2]} |\n"
    }
    str+= "---------\n"
    str = str.replace("_", " ")
    print(str)
}

fun isGameFinished(board: MutableList<Char>): Boolean {
    val nWinsX = winsGame(board, "X")
    val nWinsO = winsGame(board, "O")
    val nEmpty = board.count { it == '_' }

//    if (validGame(board, nWinsX, nWinsO)) {
        if (nWinsX == nWinsO && nWinsX == 0 && nEmpty == 0) {
            println("Draw")
            return true
        }
        else if (nWinsX == nWinsO + 1) {
            println("X wins")
            return true
        }
        else if (nWinsX + 1 == nWinsO) {
            println("O wins")
            return true
        }
//        else if ( nEmpty > 0) println("Game not finished")
//    } else println("Impossible")
    return false
}

fun winsGame(board: MutableList<Char>, s: String): Int {
    var wins = 0
    val inARow = s.repeat(3)
    for (i in 0..8 step 3) {
        if ("${board[i]}${board[i + 1]}${board[i + 2]}" == inARow) wins++
    }
    for (i in 0..2) {
        if ("${board[i]}${board[i + 3]}${board[i + 6]}" == inARow) wins++
    }
    if ("${board[0]}${board[4]}${board[8]}" == inARow) wins++
    if ("${board[2]}${board[4]}${board[6]}" == inARow) wins++
    return wins
}

//fun validGame(board: MutableList<Char>, wX: Int, wO: Int): Boolean {
//    val nX = board.count { it == 'X' }
//    val nO = board.count { it == 'O' }
//    return (nX == nO || nX + 1 == nO || nX - 1 == nO)
//        && ((wX <= 1 && wO == 0) || (wO <= 1 && wX == 0))
//}

fun main() {
    var turn = 'X'
    val board = mutableListOf('_', '_', '_', '_', '_', '_', '_', '_', '_')
    printBoard(board)
    while (true) {
        print("Enter the coordinates: ")
        try {
            val (a, b) = readLine()!!.split(' ').map { it.toInt() }
            if (a !in 1..3 || b !in 1..3) {
                println("Coordinates should be from 1 to 3!")
                continue
            } else {
                if (board[(a - 1) * 3 + b - 1] != '_') {
                    println("This cell is occupied! Choose another one!")
                    continue
                } else {
                    board[(a - 1) * 3 + b - 1] = turn
                }
            }
        } catch(e: Exception) {
            println("You should enter numbers!")
            continue
        }
        turn = if (turn == 'X') 'O' else 'X'
        printBoard(board)
        if(isGameFinished(board)) break
    }
}
