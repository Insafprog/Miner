import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.event.EventHandler
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import java.lang.NumberFormatException
import kotlin.math.max
import kotlin.math.min

class Miner: Application() {
    private var cols = 10
    private var rows = 10
    private var bombs = 25
    private val imageSize = 35

    private var game = Game(cols, rows, bombs)


    override fun start(primaryStage: Stage) {
        val canvas = Canvas(imageSize * Ranges.sizeCoordinate.xd, imageSize * Ranges.sizeCoordinate.yd)
        val graphicsContext = canvas.graphicsContext2D
        setImages()
        drawImage(graphicsContext)
        val root = VBox()
        val text = Text("Левая - открыть, Правая - флаг, Средняя - перезапуск")
        text.font = Font.font(14.0)
        text.textAlignment = TextAlignment.LEFT
        val hBox = HBox()
        val bombs = TextField(game.bombCount.toString())
        val cols = TextField(cols.toString())
        val rows = TextField(rows.toString())
        bombs.maxWidth = 50.0
        cols.maxWidth = 50.0
        rows.maxWidth = 50.0
        hBox.children.addAll(Label("Бомбы:"), bombs, Label("Столбцы:"), cols, Label("Строки:"), rows)
        hBox.spacing = 10.0
        root.children.add(hBox)
        root.children.add(canvas)
        root.children.add(text)
        // Создаем сцену
        val scene = Scene(root)
        val eventHandler = EventHandler<MouseEvent>{ event ->
            val x = event.x.toInt() / imageSize
            val y = event.y.toInt() / imageSize
            val coordinate = Coordinate(x, y)
            when(event.button) {
                MouseButton.PRIMARY -> {
                    game.clickLeftButton(coordinate)
                    canvas.graphicsContext2D.clearRect(0.0, 0.0, imageSize * Ranges.sizeCoordinate.xd, imageSize * Ranges.sizeCoordinate.yd)
                    Ranges.allCoordinates.forEach { coord ->
                        graphicsContext.drawImage(game.getBox(coord)?.image as Image, coord.xd * imageSize, coord.yd * imageSize, imageSize.toDouble(), imageSize.toDouble())
                    }
                }
                MouseButton.SECONDARY -> {
                    game.clickRightButton(coordinate)
                    canvas.restart(coordinate)
                }
                MouseButton.MIDDLE -> {
                    canvas.gameRestart()
                }
            }
            text.text = getMessage()
            bombs.text = game.bombCount.toString()
        }
        val window = Stage()
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler)
        bombs.setOnAction {
            try {
                this.bombs = max(bombs.text.toInt(), 10)
            } catch (e: NumberFormatException) {
                return@setOnAction
            } finally {
                bombs.text = this.bombs.toString()
            }
            canvas.gameRestart()
        }
        cols.setOnAction {
            try {
                this.cols = min(max(cols.text.toInt(), 10), 100)
            } catch (e: NumberFormatException) {
                return@setOnAction
            } finally {
                cols.text = this.cols.toString()
            }
            canvas.gameRestart()
            root.minWidth = imageSize * Ranges.sizeCoordinate.xd
            root.maxWidth = imageSize * Ranges.sizeCoordinate.xd
            canvas.width = imageSize * Ranges.sizeCoordinate.xd
            canvas.gameRestart()
            window.sizeToScene()
        }
        rows.setOnAction {
            try {
                this.rows = min(max(rows.text.toInt(), 10), 100)
            } catch (e: NumberFormatException) {
                return@setOnAction
            } finally {
                rows.text = this.rows.toString()
            }
            canvas.gameRestart()
            root.minHeight = imageSize * Ranges.sizeCoordinate.yd
            root.maxHeight = imageSize * Ranges.sizeCoordinate.yd + 50
            canvas.height = imageSize * Ranges.sizeCoordinate.yd
            canvas.gameRestart()
            window.sizeToScene()
        }
        // Добавляем сцену в этап, настраиваем и запускаем
        window.run {
            this.scene = scene
            title = "Минер"
            isResizable = false
            sizeToScene()
            icons.add(getImage("icon"))
            show()
        }
    }

    private fun getMessage(): String {
        return when(game.state) {
            GameState.PLAYED -> when((0..11).random()){
                0 -> "Мимо! Как жаль :-("
                1 -> "Ну ты и везучий!"
                2 -> "Осталось еще чуть-чуть"
                3 -> "Режь красный"
                4 -> "Сапер ошибается лишь один раз"
                5 -> "Бооооом!!! Шутка :-)"
                6 -> "Лошадью ходи!"
                7 -> "Мечта многих девушек - выйти замуж за богатого сапера"
                8 -> "Я думал меня трудно удивить, но..."
                9 -> "На хитрого минера, саперов не напасешься"
                10 -> "Ну, ждемс..."
                11 -> "Че смотришь? Жми давай!"
                else -> ""
            }
            GameState.BOMBED -> "ХА-ХА-ХА!!! ВЫ ПРОИГРАЛИ!!!"
            GameState.WINNER -> "Поздравляем! Вы выигали!!!"
        }
    }

    private fun Canvas.gameRestart() {
        graphicsContext2D.clearRect(0.0, 0.0, imageSize * Ranges.sizeCoordinate.xd, imageSize * Ranges.sizeCoordinate.yd)
        game = Game(cols, rows, bombs)
        drawImage(graphicsContext2D)
    }

    private fun drawImage(graphicsContext: GraphicsContext) {
        Ranges.allCoordinates.forEach { coordinate ->
            graphicsContext.drawImage(game.getBox(coordinate)?.image as Image, coordinate.xd * imageSize, coordinate.yd * imageSize, imageSize.toDouble(), imageSize.toDouble())
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

    private fun Canvas.restart(coordinate: Coordinate, sizeW: Double = imageSize.toDouble(), sizeH: Double = imageSize.toDouble()) {
        graphicsContext2D.clearRect(coordinate.xd * sizeW, coordinate.yd * sizeH, sizeW, sizeH)
        graphicsContext2D.drawImage(game.getBox(coordinate)?.image as Image, coordinate.xd * sizeW, coordinate.yd * sizeH, sizeW, sizeH)
    }
}

fun main(args: Array<String>) {
    Application.launch(Miner::class.java, *args)
}