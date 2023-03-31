package com.example.snakegame

/*** Author: Aditya Sharma (B00827775)
 *   This class keeps track of the current score and high score of the game ***/

class Score() {
    var score = 0
    var highScore = 0

    /*** This adds a point to the score and checks if the current score has exceeded the high score.
     *   If it has, it replaces the high score ***/
    fun addScore() {
        score += 1;
        if(score >= highScore){
            highScore = score;
        }
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
