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
    var foodLocation: Pair<Int, Int> = Pair(0,0)
    var snake: SnakeBody

    init {
        for (i in 0 until rows) {
            val row = ArrayList<MapBlock>()
            for (j in 0 until cols) {
                row.add(MapBlock.EMPTY)
            }
            map.add(row)
        }

        snake = SnakeBody()

        // This will add the snake's body to the grid
        for (i in 0 until snake.body.size) {
            map[snake.body[i].row][snake.body[i].column] = MapBlock.SNAKE
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
    fun generateFood(): Pair<Int, Int> {
        //TODO: Fruits may be generated at the location of the snake
        val emptyTiles = ArrayList<Pair<Int, Int>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if(containsSnake(i, j)) {
                    map[i][j] = MapBlock.SNAKE //check with Taicheng that this doesn't interfere with his logic
                } else if (map[i][j] == MapBlock.EMPTY) {
                    emptyTiles.add(Pair(i, j))
                }
            }
        }
        if (emptyTiles.isNotEmpty()) {
            val (i, j) = emptyTiles.random()
            map[i][j] = MapBlock.FOOD
            food = MapBlock.FOOD

            return Pair(i, j)
        }

        return Pair(0, 0)
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
    /*** This function is to check if mapTile contains snake body ***/
    fun containsSnake(row: Int, col: Int): Boolean {
        for(i in 0 until snake.body.size){
            //check that there isn't a snake node at the position
            if(snake.body[i].row == row && snake.body[i].column == col){
                //there is a snake body part at this position
                return true
            }
        }
        return false
    }
}
