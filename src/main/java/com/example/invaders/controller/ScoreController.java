package com.example.invaders.controller;

import com.example.invaders.model.Score;

public class ScoreController {
    private  Score score;

    public ScoreController(Score score) {
        this.score = score;
    }

    public void increaseScore(int points) {
        // Handle increasing the score
    }

    public int getScore() {
        return score.getScore();
    }
}
