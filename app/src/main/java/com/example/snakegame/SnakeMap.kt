package com.example.snakegame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/*** This class is about game map.
 * It contains functions related to grid,
 * fruits and collision detection ***/

class SnakeMap(val rows: Int, val cols: Int) {
    private var blockWidth = 0f
    private var blockHeight = 0f
    private var paintLine: Paint? = null
    var callBack: onOverCallback? = null
    val map = ArrayList<ArrayList<MapBlock>>()
    var food: MapBlock? = null

    init {
        for (i in 0 until rows) {
            val row = ArrayList<MapBlock>()
            for (j in 0 until cols) {
                row.add(MapBlock.EMPTY)
            }
            map.add(row)
        }

        initPaint()
    }

    /*** init the canvas' functions and draw the map ***/
    private fun initPaint() {
        paintLine = Paint()
        paintLine?.style = Paint.Style.FILL
        paintLine?.color = Color.parseColor("#CCFFFF")
        paintLine?.isAntiAlias = true
        paintLine?.isDither = true
    }


    fun onDraw(canvas: Canvas) {
        drawBlock(canvas)
    }

    private fun drawBlock(canvas: Canvas) {

        blockWidth = (20 / cols).toFloat()
        blockHeight = blockWidth

        drawLine(canvas, cols, rows)
    }

    private fun drawLine(canvas: Canvas, cols: Int, rows: Int) {
        for (i in 0 until cols + 1) {
            canvas.drawLine(0f, i * blockHeight, 20.toFloat(), i * blockHeight, paintLine!!)
        }
        
        for (i in 0 until cols) {
            canvas.drawLine(i * blockWidth, 0f, i * blockWidth, blockWidth * cols, paintLine!!)
        }

        callBack?.onOver(true)
    }
    
    /*** callback function ***/
    interface onOverCallback {
        fun onOver(isOk: Boolean)
    }

    
    /*** This function is used to randomly generate fruits on the map ***/
    fun generateFood() {
        //TODO: Fruits may be generated at the location of the snake
        val emptyTiles = ArrayList<Pair<Int, Int>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (map[i][j] == MapBlock.EMPTY) {
                    emptyTiles.add(Pair(i, j))
                }
            }
        }
        if (emptyTiles.isNotEmpty()) {
            val (i, j) = emptyTiles.random()
            map[i][j] = MapBlock.FOOD
            food = MapBlock.FOOD
        }
    }
    /*** This function is used to detect if a wall or snake has been collided with ***/
    fun checkCollision(position: Pair<Int, Int>): Boolean {
        val (i, j) = position
        return (i < 0 || i >= rows || j < 0 || j >= cols || map[i][j] == MapBlock.SNAKE)
    }
    /*** This function is used to detect if the food has been collided with ***/
    fun checkEaten(position: Pair<Int, Int>): Boolean {
        val (i, j) = position
        return map[i][j] == MapBlock.FOOD
    }
    /*** This function is used to set the type of grid ***/
    fun setTile(position: Pair<Int, Int>, tile: MapBlock) {
        val (i, j) = position
        map[i][j] = tile
    }
}
