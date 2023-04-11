package com.example.snakegame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback, SnakeMap.onOverCallback {
    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var snakeBody: SnakeBody
    private lateinit var snakeMap: SnakeMap
    private var snakeThread: SnakeThread? = null
    private var running = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        surfaceView = findViewById(R.id.surface_view)

        surfaceView.holder.addCallback(this)

        snakeBody = SnakeBody()
        snakeMap = SnakeMap(10, 10)
        snakeMap.callBack = this

    }
    
//    private fun startGameLoop() {
//        val handler = Handler(Looper.getMainLooper())
//        val runnable = object : Runnable {
//            override fun run() {
//                if (running) {
//                    handler.postDelayed(this, 200)
//                }
//            }
//        }
//        handler.postDelayed(runnable, 200)
//    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        snakeThread = SnakeThread(holder, snakeBody, snakeMap)
        snakeThread?.running = true
        snakeThread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        snakeThread?.running = false
        while (retry) {
            try {
                snakeThread?.join()
                retry = false
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun onOver(isOk: Boolean) {
        if (!isOk) {
        }
    }
}

class SnakeThread(private val surfaceHolder: SurfaceHolder, private val snakeBody: SnakeBody, private val snakeMap: SnakeMap) : Thread() {
    var running = false

    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 20.0F
    }

    fun drawSnake(canvas: Canvas, x: Int, y: Int, color: Int) {
       canvas.drawRect(x.toFloat(), y.toFloat(), (x+50).toFloat(), (y+50).toFloat(), paint)
    }

    fun moveSnake(canvas: Canvas) {

        snakeBody.move()

        for (i in (snakeBody.body.size-1) downTo 1) {
            drawSnake(canvas, snakeBody.head.previousColumn - 10, snakeBody.head.previousRow, Color.BLACK)
        }

        drawSnake(canvas, snakeBody.head.column, snakeBody.head.row, Color.WHITE)
    }


    override fun run() {
        while (running) {

            val canvas = surfaceHolder.lockCanvas()

            if (canvas != null) {

                moveSnake(canvas)

                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}
