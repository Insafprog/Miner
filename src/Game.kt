class Game(cols: Int, rows: Int, bombs:Int) {

    val bombCount: Int
        get() = flag.bombCount
    private val bomb: Bomb
    private val flag: Flag
    internal var state: GameState = GameState.PLAYED
        private set

    init {
        Ranges.setSize(cols, rows)
        flag = Flag()
        bomb = Bomb(bombs)
        flag.bombCount = bomb.totalBombs
    }

    fun getBox(coordinate: Coordinate) =
            if (flag.getFlag(coordinate) == Box.OPENED)
                bomb.getBomb(coordinate)
            else
                flag.getFlag(coordinate)

    fun clickLeftButton(coordinate: Coordinate) {
        if (state == GameState.PLAYED)
            openBox(coordinate)
            checkWinner()
    }

    fun clickRightButton(coordinate: Coordinate) {
        if (state == GameState.PLAYED)
            flag.toggleFlagedToBox(coordinate)
    }

    private fun openBox(coordinate: Coordinate) {
        when(flag.getFlag(coordinate)) {
            Box.CLOSED -> when(bomb.getBomb(coordinate)) {
                Box.ZERO -> openBoxesAround(coordinate)
                Box.BOMB -> openBombs(coordinate)
                else -> flag.setOpenedToBox(coordinate)
            }
            else -> return
        }
    }

    private fun checkWinner() {
        if (state == GameState.PLAYED) {
            if (flag.countOfClosedBoxes == bomb.totalBombs)
                state = GameState.WINNER
        }
    }

    private fun openBombs(coordinate: Coordinate) {
        state = GameState.BOMBED
        Ranges.allCoordinates.forEach { bobs ->
            if (bomb.getBomb(bobs) == Box.BOMB)
                flag.setOpenedToBox(bobs)
            else if (flag.getFlag(bobs) == Box.FLAGED)
                flag.setNoBombedToBox(bobs)
        }
        flag.setBombedToBox(coordinate)
    }

    private fun openBoxesAround(coordinate: Coordinate) {
        flag.setOpenedToBox(coordinate)
        Ranges.getCoordinatesAround(coordinate).forEach { around ->
            openBox(around)
        }
    }
}