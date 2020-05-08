class Game(cols: Int, rows: Int, bombs:Int) {

    private val bomb: Bomb

    init {
        Ranges.setSize(cols, rows)
        bomb = Bomb(bombs)
    }

    fun getBox(coordinate: Coordinate) = bomb.getBomb(coordinate)
}