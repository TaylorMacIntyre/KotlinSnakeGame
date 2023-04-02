package com.example.snakegame

/*** This class is about game map.
 * It contains functions related to grid,
 * fruits and collision detection ***/

class SnakeMap(val rows: Int, val cols: Int) {
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
    }
    /*** This function is used to randomly generate fruits on the map ***/
    fun generateFood() {
        //TODO: Fruits may be generated at the location of the snake
        val emptyTiles = ArrayList<Pair<Int, Int>>()
        //return an Arraylist<Pair<int,int>> of coordinates around snake's head
        val coordinatesNearHead = coordinatesNearSnakeHead()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val pair = Pair(i, j)
                if (containsSnake(i, j)) {
                    map[i][j] = MapBlock.SNAKE //check with Taicheng that this doesn't interfere with his logic
                } else if(coordinatesNearHead.indexOf(pair) != -1) {
                    //coordinate is too close to snake's head, don't use it to spawn fruit
                } else if (map[i][j] == MapBlock.EMPTY) {
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
    /*** This function is to check if mapTile contains snake body ***/
    fun containsSnake(row: Int, col: Int): Boolean {
        for(i in 0 until SnakeBody().body.size){
            //check that there isn't a snake node at the position
            if(SnakeBody().body[i].row == row && SnakeBody().body[i].column == col){
                //there is a snake body part at this position
                return true
            }
        }
        return false
    }

    /*** This function creates an arrayList of tiles that are around the snakes head, will be used
     * to ensure fruit doesn't spawn close to snakes head, 3x4 with the snake's head in the short side
     * DISCLAIMER: it's ugly because the area around the snakes head isn't square, so it has to be
     * calculated differently for each cardinal direction
     */
    fun coordinatesNearSnakeHead(): ArrayList<Pair<Int, Int>>{
        val coordinatesNearSnakeHead = ArrayList<Pair<Int, Int>>()
        val headRow = SnakeBody().body[0].row
        val headCol = SnakeBody().body[0].column

        if(SnakeBody().direction.equals("left")){
            for(i in (headRow-1) until headRow+2 ){
                for(j in (headCol-3) until headCol+1){
                    if(i>=0 && j>=0 && i<rows && j<cols) {
                        //check value is on game board, if it is, add it to arraylist
                        val pair = Pair<Int, Int>(i, j)
                        coordinatesNearSnakeHead.add(pair)
                    }
                }
            }
        } else if (SnakeBody().direction.equals("right")) {
            for(i in (headRow-1) until headRow+2){
                for(j in headCol until headCol+4){
                    if(i>=0 && j>=0 && i<rows && j<cols) {
                        //check value is on game board, if it is, add it to arraylist
                        val pair = Pair<Int, Int>(i, j)
                        coordinatesNearSnakeHead.add(pair)
                    }
                }
            }

        } else if (SnakeBody().direction.equals("down")) {
            for(i in (headRow-3) until headRow+1){
                for(j in (headCol-1) until (headCol+2)){
                    if(i>=0 && j>=0 && i<rows && j<cols) {
                        //check value is on game board, if it is, add it to arraylist
                        val pair = Pair<Int, Int>(i, j)
                        coordinatesNearSnakeHead.add(pair)
                    }
                }
            }
        } else /*direction == up*/{
            for(i in headRow until (headRow+4)){
                for(j in (headCol-1) until (headCol+2)){
                    if(i>=0 && j>=0 && i<rows && j<cols) {
                        //check value is on game board, if it is, add it to arraylist
                        val pair = Pair<Int, Int>(i, j)
                        coordinatesNearSnakeHead.add(pair)
                    }
                }
            }
        }
        return coordinatesNearSnakeHead
    }
    
}
