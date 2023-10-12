package com.example.invaders.model;

import javafx.scene.paint.Color;

public class Player extends Sprite {
    private int health;
    private int score;

    public Player(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, "player", color);
        health = 100;
        score = 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }
}
