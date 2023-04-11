package com.example.snakegame

class SnakeNode(row: Int, column: Int) {
    var row: Int = 0;
    var column: Int = 0;
    var previousRow: Int = 0;
    var previousColumn: Int = 0;

    init {
        this.row = row;
        this.column = column;
        this.previousRow = row - 1;
        this.previousColumn = column - 1;
    }
}


class SnakeBody {
    var body: ArrayList<SnakeNode> = ArrayList<SnakeNode>()
    var direction: String = "right"
    var head: SnakeNode = SnakeNode(0,0)

    init {
        this.body = ArrayList<SnakeNode>();
        body.add(SnakeNode(0,0));
        head = body.get(0);
        this.direction = "right";

        addNode()
        addNode()
        addNode()
        addNode()
        addNode()

        addNode()
        addNode()
        addNode()

    }

    fun move() {

        for (i in (body.size-1) downTo 1) {
            body.get(i).previousColumn = body.get(i).column
            body.get(i).previousRow = body.get(i).row
            body.get(i).column = body.get(i-1).column;
            body.get(i).row = body.get(i-1).row;
        }

        if (this.direction.equals("left")) {
            head.previousColumn = head.column
            head.previousRow = head.row
            head.column = head.column - 1;

        }
        else if (direction.equals("right")) {
            head.previousColumn = head.column
            head.previousRow = head.row
            head.column = head.column + 1;
        }

        else if (direction.equals("down")) {
            head.previousColumn = head.column
            head.previousRow = head.row
            head.row = head.row + 1;
        }

        else {
            head.previousColumn = head.column
            head.previousRow = head.row
            head.row = head.row - 1;
        }
    }

    fun addNode() {
        var newRow: Int
        var newColumn: Int

        newRow = body.get(body.size - 1).previousRow;
        newColumn = body.get(body.size - 1).previousColumn;

        var newNode = SnakeNode(newRow, newColumn)

        body.add(newNode)
    }
}