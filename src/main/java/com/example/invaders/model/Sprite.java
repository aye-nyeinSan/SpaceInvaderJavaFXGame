package com.example.invaders.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public  class Sprite extends ImageView {
    public boolean dead = false;
    public final String type;

    public Sprite(int x, int y, Image image,String type){
        super(image);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void moveLeft(){
        setTranslateX(getTranslateX() - 5);
    }

    public void moveRight(){
        setTranslateX(getTranslateX() + 5);
    }

    public void moveUp(){
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown(){
        setTranslateY(getTranslateY() + 5);
    }

    public boolean isDead(){
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getType() {
        return type;
    }
}

