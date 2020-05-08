internal class Flag {
    var bombCount: Int = 0
    var countOfClosedBoxes: Int = Ranges.size
        private set
    private val flagMap = Matrix(Box.CLOSED)

    fun getFlag (coordinate: Coordinate) = flagMap.getBox(coordinate)
    fun setOpenedToBox(coordinate: Coordinate) {
        flagMap.setBox(coordinate, Box.OPENED)
        countOfClosedBoxes--
    }

    fun toggleFlagedToBox(coordinate: Coordinate) {
        when(flagMap.getBox(coordinate)) {
            Box.FLAGED -> {
                flagMap.setBox(coordinate, Box.INFORM)
                bombCount++
            }
            Box.CLOSED -> {
                flagMap.setBox(coordinate, Box.FLAGED)
                bombCount--
            }
            Box.INFORM -> flagMap.setBox(coordinate, Box.CLOSED)
        }
    }

    fun setBombedToBox(coordinate: Coordinate) {
        flagMap.setBox(coordinate, Box.BOMBED)
    }

    fun setNoBombedToBox(coordinate: Coordinate) {
        flagMap.setBox(coordinate, Box.NOBOMB)
    }
}