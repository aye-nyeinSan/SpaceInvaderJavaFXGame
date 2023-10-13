package com.example.invaders.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Sprite extends Rectangle {
    public boolean dead = false;
    public final String type;

    public Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    void moveLeft(){
        setTranslateX(getTranslateX() - 5);
    }

    void moveRight(){
        setTranslateX(getTranslateX() + 5);
    }

    public void moveUp(){
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown(){
        setTranslateY(getTranslateY() + 5);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getType() {
        return type;
    }
}


