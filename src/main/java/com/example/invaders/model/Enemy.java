package com.example.invaders.model;


import javafx.scene.image.Image;

public class Enemy extends Sprite{
    private int health;
    public Enemy(int x, int y, Image image, String type) {
        super(x, y, image, type);
    }

    public int getHealth(){
        return health;
    }
}

