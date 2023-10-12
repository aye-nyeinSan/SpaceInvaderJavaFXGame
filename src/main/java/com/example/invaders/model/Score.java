package com.example.invaders.model;

public class Score {
    private int score;

    public Score() {
        score = 0;
    }

    public void increaseScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    // Methods for rendering and displaying the score
}