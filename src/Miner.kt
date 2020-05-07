import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Miner: Application() {
    private val cols = 15
    private val rows = 1
    private val imageSize = 50.0

    override fun start(primaryStage: Stage) {
        // Иницилизируем стадию
        primaryStage.initStage(initParent())
    }

    private fun initParent(): Parent {
        val pane = Pane()
        pane.setMinSize(cols * imageSize, rows * imageSize)
        return pane
    }

    private fun Stage.initStage(parent: Parent) {
        // Создаем сцену
        val scene = Scene(parent)
        // Добавляем сцену в этап, настраиваем и запускаем
        this.scene = scene
        title = "Минер"
        isResizable = false
        show()
    }
}

fun main(args: Array<String>) {
    Application.launch(Miner::class.java, *args)
}