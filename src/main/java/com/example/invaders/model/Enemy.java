package com.example.invaders.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Sprite {
    private int health;

    public Enemy(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, "enemy", color);
        health = 50; // Example health value
    }


    public void handleCollision(Sprite other) {
        // Check the type of the other sprite and handle collisions accordingly.
        if (other.type.equals("player")) {
            // Handle collision with the player
        } else if (other.type.equals("playerbullet") || other.type.equals("playerspecialBullet")) {
            // Handle collision with player's bullets
            health -= 10; // Example damage value
            if (health <= 0) {
                dead = true; // If health reaches zero, mark the enemy as dead.
            }
        }
    }
    public int getHealth() {
        return health;
    }


}