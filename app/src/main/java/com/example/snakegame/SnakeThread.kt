import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.widget.Button
import com.example.snakegame.R
import com.example.snakegame.SnakeBody
import com.example.snakegame.SnakeMap
import kotlinx.coroutines.delay

class SnakeThread(private val surfaceHolder: SurfaceHolder, private val snakeBody: SnakeBody, private val snakeMap: SnakeMap) : Thread() {
    var running = false
    var isPaused = true
    var direction = "right";

    private var paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 20.0F
    }

    fun drawSnake(canvas: Canvas, x: Int, y: Int, snakeColor: Int, direction: String) {

        paint = Paint().apply {
            color = snakeColor
            style = Paint.Style.STROKE
            strokeWidth = 20.0F
        }

        val rotationAngle = when (direction) {
            "up" -> 270F
            "down" -> 90F
            "left" -> 180F
            "right" -> 0F
            else -> 0F
        }

        canvas.save()
        canvas.rotate(rotationAngle, x.toFloat(), y.toFloat())
        canvas.drawRect((x + 5).toFloat(), (y + 5).toFloat(), (x + 15).toFloat(), (y + 15).toFloat(), paint)
        canvas.restore()
    }



    fun moveSnake(canvas: Canvas) {

        snakeBody.move()

        for (i in (snakeBody.body.size-1) downTo 1) {
            drawSnake(canvas, snakeBody.body.get(i).column + (i*20), snakeBody.body.get(i).row, Color.WHITE, direction)
        }
        Log.e("x,y" ,snakeBody.head.column.toString())
        drawSnake(canvas, snakeBody.head.column, snakeBody.head.row, Color.WHITE, direction)
    }

    fun drawFood(canvas: Canvas) {
        canvas.drawRect(200.toFloat(), 305.toFloat(), 210.toFloat(), 310.toFloat(), paint)
    }


    override fun run() {
        while (running) {
            if(!isPaused) {

                val canvas = surfaceHolder.lockCanvas()

                sleep(20)
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
