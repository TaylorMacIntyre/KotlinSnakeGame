import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import com.example.snakegame.MainActivity

import com.example.snakegame.SnakeBody
import com.example.snakegame.SnakeMap
import kotlin.math.abs

class SnakeThread(
    private val surfaceHolder: SurfaceHolder,
    private val snakeBody: SnakeBody,
    private val snakeMap: SnakeMap,
    private val mainActivity: MainActivity
) : Thread() {

    var running = false
    var isPaused = true
    var direction = "right";
    val steps: Int = 20

    var widthBounds =  surfaceHolder.surfaceFrame.width()
    var heightBounds = surfaceHolder.surfaceFrame.height()

    var food = snakeMap.generateFood()

    private var paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 15.0F
    }

    fun moveSnake(canvas: Canvas) {

        var head = snakeMap.snake.head

        if(direction === "down") {
            head.row += steps
        } else if (direction === "up") {
            head.row -= steps
        } else if(direction === "right") {
            head.column += steps
        } else if (direction === "left") {
            head.column -= steps
        }

        snakeMap.snake.addNode(head.column, head.row)

        /** Check if intersecting with bounds **/
        if(head.column > widthBounds || head.column < 0 || head.row > heightBounds || head.row < 0) {
            mainActivity.runOnUiThread {
                mainActivity.gameOver()
            }
        }

        /** Check if intersecting with food **/
        if(!(abs(head.row - food.second) < 30) || !(abs(head.column - food.first) < 30)) {
            snakeMap.snake.removeNode()
        } else {
            food = snakeMap.generateFood()
            mainActivity.runOnUiThread {
                mainActivity.updateScore()
            }
        }

        for (i in (snakeMap.snake.body.size-1) downTo 1) {
            val c = snakeMap.snake.body.get(i).column.toFloat()
            val r = snakeMap.snake.body.get(i).row.toFloat()
            canvas.drawRect(c, r,c+steps, r+steps , paint)
        }

    }

    fun drawFood(canvas: Canvas) {

        val x = food.first.toFloat()
        val y = food.second.toFloat()

        canvas.drawRect(x, y, x+steps, y+steps, paint)
    }

    override fun run() {
        while (running) {
            if(!isPaused) {

                val canvas = surfaceHolder.lockCanvas()

                sleep(50)
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                if (canvas != null) {
                    moveSnake(canvas)
                    drawFood(canvas)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}
