package com.example.snakegame

import SnakeThread
import android.graphics.*
import android.graphics.Path.Direction
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback, SnakeMap.onOverCallback {

    private lateinit var surfaceView: SurfaceView
    private lateinit var snakeBody: SnakeBody
    private lateinit var snakeMap: SnakeMap

    private lateinit var startButton: TextView
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var upButton: Button
    private lateinit var downButton: Button

    private lateinit var scoreText: TextView

    private var score: Int = 0

    private var snakeThread: SnakeThread? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initializeViews()

        surfaceView = findViewById(R.id.surface_view)
        surfaceView.holder.addCallback(this)
        surfaceView.holder.setFormat(PixelFormat.TRANSPARENT);


        startButton.setOnClickListener() {
            snakeThread?.isPaused = !snakeThread?.isPaused!!
            if(snakeThread?.isPaused === true) {
                startButton.text = "Start"
            } else {
                startButton.text = "Pause"
            }
        }

        // When changing direction, we check to see if the direction currently being travelled is the opposite of the new direction, if it is, we do not change direction to avoid snake moving on itself
        leftButton.setOnClickListener() {
            if (snakeThread?.direction !== "right") {
                snakeThread?.direction = "left"
            }
        }

        rightButton.setOnClickListener() {
            if (snakeThread?.direction !== "left") {
                snakeThread?.direction = "right"
            }
        }

        upButton.setOnClickListener() {
            if (snakeThread?.direction !== "down") {
                snakeThread?.direction = "up"
            }
        }

        downButton.setOnClickListener() {
            if (snakeThread?.direction !== "up") {
                snakeThread?.direction = "down"
            }
        }

    }

    override fun surfaceCreated(holder: SurfaceHolder) {

        Log.e("size:", surfaceView.width.toString())
        Log.e("size:", surfaceView.height.toString())

        snakeBody = SnakeBody()
        snakeMap = SnakeMap(surfaceView.width, surfaceView.height)
        snakeMap.callBack = this

        snakeThread = SnakeThread(holder, snakeBody, snakeMap, this)
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
                throw e
            }
        }
    }

    override fun onOver(isOk: Boolean) {
        snakeThread?.interrupt()
        if (!isOk) {

        }
    }

    private fun initializeViews() {
        startButton = findViewById<TextView>(R.id.snake_start)
        leftButton = findViewById<Button>(R.id.snake_left)
        rightButton = findViewById<Button>(R.id.snake_right)
        upButton = findViewById<Button>(R.id.snake_up)
        downButton = findViewById<Button>(R.id.snake_down)

        scoreText = findViewById<TextView>(R.id.currentScore)
    }

    fun updateScore(){
        score++
        scoreText.text = "Score: " + score.toString()
    }

    fun gameOver(){

        snakeThread?.isPaused = true

        val inflater = LayoutInflater.from(this)
        val gameOverlay = inflater.inflate(R.layout.game_overlay, null)

        val restartButton = gameOverlay.findViewById<Button>(R.id.restart_button)

        val relativeLayout = findViewById<RelativeLayout>(R.id.relative_overlay)
        relativeLayout.addView(gameOverlay)

        restartButton.setOnClickListener() {
            recreate()
        }

    }

}