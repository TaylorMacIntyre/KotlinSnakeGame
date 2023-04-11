package com.example.snakegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

class MainActivity : AppCompatActivity(), SwipeDetector.SwipeListener {

    private lateinit var swipeDetector: SwipeDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeDetector = SwipeDetector()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        swipeDetector.onTouchEvent(event!!, this)
        return super.onTouchEvent(event)
    }


    override fun onSwipeRight() {}

    override fun onSwipeLeft() {}

    override fun onSwipeUp() {}

    override fun onSwipeDown() {}
}