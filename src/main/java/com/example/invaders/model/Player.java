package com.example.invaders.model;


import javafx.scene.image.Image;

public class Player extends Sprite{
    public Player(int x, int y, Image image, String type) {
        super(x, y, image, type);
    }
    private int health;
    int score;
    int  chances;
    int currentChance;



    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }

    public int getCurrentChance() {
        return currentChance;
    }

    public void setCurrentChance(int currentChance) {
        this.currentChance = currentChance;
    }


//     public Player(int x, int y, int w, int h, Color color) {
//         super(x, y, w, h, "player", BLUE);
//         health = 100;
//         chances=3;
//         currentChance=1;
//        this.color = BLUE;
//        score = 0;
//     }


    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore(int points) {

        score+= points;

    }
}
