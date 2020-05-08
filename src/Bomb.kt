import kotlin.math.min

internal class Bomb(totalBombs: Int) {
    private val bombMap: Matrix = Matrix(Box.ZERO)
    val totalBombs = min(totalBombs, Ranges.size / 4)

    init {
        placeBomb()
    }

    private fun placeBomb() {
        repeat(totalBombs) {
            var coordinate: Coordinate
            while (true) {
                coordinate = Ranges.randomCoordinate
                if (bombMap.getBox(coordinate) != Box.BOMB) break
            }
            bombMap.setBox(coordinate, Box.BOMB)
            incNumbersAroundBomb(coordinate)
        }


    }

    fun getBomb(coordinate: Coordinate) = bombMap.getBox(coordinate)

    private fun incNumbersAroundBomb(coordinate: Coordinate) {
        Ranges.getCoordinatesAround(coordinate).forEach {numCoordinate ->
            if (Box.BOMB != bombMap.getBox(numCoordinate))
                bombMap.setBox(numCoordinate, bombMap.getBox(numCoordinate)!!.nextNumberBox)
        }
    }
}