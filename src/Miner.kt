import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.stage.Stage

class Miner: Application() {
    private val cols = 9
    private val rows = 9
    private val bombs = 10
    private val imageSize = 50

    private val game = Game(cols, rows, bombs)


    override fun start(primaryStage: Stage) {
        val canvas = Canvas(imageSize * Ranges.sizeCoordinate.xd, imageSize * Ranges.sizeCoordinate.yd)
        val graphicsContext = canvas.graphicsContext2D
        setImages()
        Ranges.allCoordinates.forEach { coordinate ->
            graphicsContext.drawImage(game.getBox(coordinate)?.image as Image, coordinate.xd * imageSize, coordinate.yd * imageSize)
        }
        val root = HBox()
        root.alignment = Pos.CENTER
        root.children.add(canvas)
        // Создаем сцену
        val scene = Scene(root)
        // Добавляем сцену в этап, настраиваем и запускаем
        primaryStage.run {
            this.scene = scene
            title = "Минер"
            isResizable = false
            icons.add(getImage("icon"))
            show()
        }
    }

    private fun setImages() {
        Box.values().forEach { box ->
            box.image = getImage(box.name.toLowerCase())
        }
    }

    private fun getImage(name: String): Image {
        val imagePath = "img/$name.png"
        return Image(javaClass.getResource(imagePath).toString())
    }
}

fun main(args: Array<String>) {
    Application.launch(Miner::class.java, *args)
}