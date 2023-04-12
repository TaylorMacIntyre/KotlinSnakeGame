package com.example.snakegame

class SnakeNode(row: Int, column: Int) {
    // We want to keep track of previous column so that it can be used when we are adding a new part to the snake
    var row: Int = 0;
    var column: Int = 0;
    var previousRow: Int = 0;
    var previousColumn: Int = 0;

    init {
        this.row = row;
        this.column = column;
        this.previousRow = row;
        this.previousColumn = column;
    }
}


class SnakeBody {
    var body: ArrayList<SnakeNode> = ArrayList<SnakeNode>()
    var head: SnakeNode = SnakeNode(0,120)

    init {

        this.body = ArrayList<SnakeNode>();
        body.add( SnakeNode(0, 0))
        body.add( SnakeNode(0, 20))
        body.add( SnakeNode(0, 40))
        body.add( SnakeNode(0, 60))
        body.add( SnakeNode(0, 80))
        body.add( SnakeNode(0, 100))
        body.add(head);

    }

    fun addNode() {
        var newRow: Int
        var newColumn: Int

        newRow = body.get(body.size - 1).previousRow;
        newColumn = body.get(body.size - 1).previousColumn;

        var newNode = SnakeNode(newRow, newColumn)

        body.add(newNode)
    }

    fun addNode(x: Int, y: Int) {

        val newColumn = x;
        val newRow = y;

        var newNode = SnakeNode(newRow, newColumn)

        body.add(newNode);

    }

    fun removeNode() {
        body.removeAt(0)
    }
}