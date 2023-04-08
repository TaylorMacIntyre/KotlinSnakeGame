package com.example.snakegame

import android.content.Context
import android.content.SharedPreferences


/*** Score Class
 *   Author: Aditya Sharma (B00827775)
 *   This class keeps track of the current score and high score of the game ***/

class Score(context: Context) {
    var score = 0
    var highScore = 0
    private val ref: Context = context
    private lateinit var pref: SharedPreferences

    /*** This adds a point to the score and checks if the current score has exceeded the high score.
     *   If it has, it replaces the high score ***/
    fun addScore() {
        score += 1;
        if(score > highScore){
            replaceHighScore(score)
        }
    }
    /*** update the high score value ***/
    private fun replaceHighScore(score: Int) {
        highScore = score;
        saveSession(highScore)
    }
    private fun saveSession(score: Int) {
        val pref = ref.getSharedPreferences("mysharedpref", Context.MODE_PRIVATE)
        val editor=pref.edit()
        editor.putInt("highScore",score)
        editor.putString("name","User")
        editor.commit()
    }
    fun returnSession(): SharedPreferences {
        return pref;
    }
    /*** return the current score value ***/
    fun getGameScore(): Int {
        return score;
    }
    /*** return the highest score ***/
    fun getGameHighScore(): Int {
        return highScore;
    }

}
