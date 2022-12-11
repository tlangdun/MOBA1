class Game {
    private val rows = 8;
    private val cols = 8;
    init {
        val oldBoard = Board(this.rows, this.cols)
        oldBoard.setBoard()
        oldBoard.printArray()

        val newBoard = Board(this.rows, this.cols)
        updateCell(oldBoard.getArray(), newBoard.getArray())
        println()
        newBoard.printArray()
    }

    private fun updateCell(board : Array<IntArray>, newBoard : Array<IntArray>) {
        for (i in 0 until this.rows) {
            for (j in 0 until this.cols) {
                val aliveCells = countLivingNeighbors(board, i, j)

                if (board[i][j] == 1 && (aliveCells == 2 || aliveCells == 3)) {
                    newBoard[i][j] = 1
                } else if (board[i][j] == 0 && aliveCells == 3) {
                    newBoard[i][j] = 1
                } else {
                    newBoard[i][j] = 0
                }
            }
        }
    }

    private fun countLivingNeighbors(board: Array<IntArray>, row: Int, col: Int) : Int {
        var count = 0;
        for (i in -1..1) {
            val currentRow = row + i
            if (currentRow < 0 || currentRow >= this.rows) continue

            for (j in -1..1) {
                val currentCol = col + j

                if (currentCol < 0 || currentCol >= this.cols) continue
                if (board[currentRow][currentCol] == 1 && !(currentCol == col && currentRow == row) ) count++;
            }
        }
        return count
    }

    private fun main(board: Board) {
        board.printArray()
    }
}

fun main() {
    Game()
}