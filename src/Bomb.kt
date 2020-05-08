internal class Bomb(val totalBombs: Int) {
    private val bombMap: Matrix

    init {
        bombMap = Matrix(Box.ZERO)
        placeBomb()
    }

    private fun placeBomb() {
        val bombs = Array(totalBombs){ Ranges.randomCoordinate }
        bombs.forEach {coordinate ->
            bombMap.setBox(coordinate, Box.BOMB)
        }
        bombs.forEach {coordinate ->
            Ranges.getCoordinatesAround(coordinate).forEach {numCoordinate ->
                bombMap.setBox(numCoordinate, Box.NUM1)
            }
        }
    }

    fun getBomb(coordinate: Coordinate) = bombMap.getBox(coordinate)
}