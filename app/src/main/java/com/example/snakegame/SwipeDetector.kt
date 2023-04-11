package com.example.snakegame
import android.view.MotionEvent

class SwipeDetector {
    companion object {
        const val TOUCH_MIN_DISTANCE = 100
    }

    private var xDown = 0f
    private var yDown = 0f
    private var xUp = 0f
    private var yUp = 0f

    fun onTouchEvent(event: MotionEvent, listener: SwipeListener) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                xDown = event.x
                yDown = event.y
            }
            MotionEvent.ACTION_UP -> {
                xUp = event.x
                yUp = event.y

                val deltaX = xUp - xDown
                val deltaY = yUp - yDown

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > TOUCH_MIN_DISTANCE) {
                        if (deltaX > 0) {
                            listener.onSwipeRight()
                        } else {
                            listener.onSwipeLeft()
                        }
                    }
                } else {
                    if (Math.abs(deltaY) > TOUCH_MIN_DISTANCE) {
                        if (deltaY > 0) {
                            listener.onSwipeDown()
                        } else {
                            listener.onSwipeUp()
                        }
                    }
                }
            }
        }
    }

    interface SwipeListener {
        fun onSwipeRight()
        fun onSwipeLeft()
        fun onSwipeUp()
        fun onSwipeDown()
    }
}
