package com.example.invaders.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Sprite {
    private int health;

    public Enemy(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, "enemy", color);

    }




    public int getHealth() {
        return health;
    }


}