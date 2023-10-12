package com.example.invaders.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Sprite {
    private int health;

    public Enemy(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, "enemy", color);
        health = 50; // Example health value
    }

    public void move() {
        // Enemy movement logic
    }

    public void handleCollision() {
        // Handle collisions with player or player's bullets
    }

    public int getHealth() {
        return health;
    }


}