object Ranges {
    var cols: Int = 0
        private set
    var rows: Int = 0
        private set
    var size: Int = 0
        private set
    lateinit var sizeCoordinate: Coordinate
        private set
    lateinit var allCoordinates: List<Coordinate>
        private set
    val randomCoordinate: Coordinate
        get() = Coordinate((0..cols).random(), (0..rows).random())

    fun setSize(cols: Int, rows: Int) {
        this.cols = cols
        this.rows = rows
        size = cols * rows
        sizeCoordinate = Coordinate(cols, rows)
        val allCoordinates = mutableListOf<Coordinate>()
        for (x in 0 until cols)
            for (y in 0 until rows)
                allCoordinates.add(Coordinate(x, y))
        this.allCoordinates = allCoordinates
    }

    fun inRange(coordinate: Coordinate) = coordinate.x * coordinate.y < size

    fun getCoordinatesAround(coordinate: Coordinate): List<Coordinate> {
        val list = mutableListOf<Coordinate>()
        for (x in coordinate.x - 1 .. coordinate.x + 1)
            for (y in coordinate.y - 1 .. coordinate.y + 1) {
                val around = Coordinate(x, y)
                if (inRange(around))
                    if (around != coordinate)
                        list.add(around)
            }
        return list
    }
}
data class Coordinate(val x: Int, val y: Int) {
    val xd: Double by lazy { x.toDouble() }
    val yd: Double by lazy { y.toDouble() }
}

internal class Matrix(val defaultBox: Box) {
    private val matrix: Array<Box> = Array(Ranges.size) {defaultBox}

    fun getBox(coordinate: Coordinate) =
            if (Ranges.inRange(coordinate))
                matrix[coordinate.x * Ranges.rows + coordinate.y]
            else null

    fun setBox(coordinate: Coordinate, box: Box) {
        if (Ranges.inRange(coordinate))
            matrix[coordinate.x * Ranges.rows + coordinate.y] = box
    }
}

