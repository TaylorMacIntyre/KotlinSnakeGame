import com.example.snakegame.SnakeBody
import com.example.snakegame.SnakeMap
import com.example.snakegame.SnakeNode
import org.junit.Test
import org.junit.Assert.*

class SnakeBodyTest {
    @Test
    fun snakeBodySizeTest() {
        val snakeBody = SnakeBody()
        assertEquals(snakeBody.body.size, 7)
    }

    @Test
    fun snakeBodyHeadTest() {
        val snakeBody = SnakeBody()
        val head = snakeBody.head

        assertEquals(head.column, 120)
        assertEquals(head.row, 0)
    }

    @Test
    fun snakeBodyAddTest() {
        val snakeBody = SnakeBody()
        snakeBody.addNode(0, 20)

        val newNode = snakeBody.body.last()

        assertEquals(newNode.row, 0)
        assertEquals(newNode.column, 20)
    }

    @Test
    fun snakeBodyAddSizeTest() {
        val snakeBody = SnakeBody()
        assertEquals(snakeBody.body.size, 7)

        snakeBody.addNode()
        assertEquals(snakeBody.body.size, 8)
    }

    @Test
    fun snakeBodyRemoveTest() {
        val snakeBody = SnakeBody()
        assertEquals(snakeBody.body.size, 7)

        snakeBody.removeNode()
        assertEquals(snakeBody.body.size, 6)
    }
}


class SnakeMapTest {

    @Test
    fun snakeMapFoodTest(){
        val snakeMap = SnakeMap(100, 100)
        val foodCoordinates = snakeMap.generateFood()

        var condition = (foodCoordinates.first <= 100 && foodCoordinates.second <= 100)

        assertEquals(condition, true)
    }
}