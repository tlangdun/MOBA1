class Board(private val rows: Int, private val cols: Int) {
    private val arr = Array(rows) { IntArray(cols) }

    fun setBoard() {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                this.arr[i][j] = (0..1).random() //0 dead // 1 alive
            }
        }
    }

    fun getArray(): Array<IntArray> {
        return arr;
    }

    fun printArray() {
        for (row in arr) {
            println(row.contentToString())
        }
    }
}