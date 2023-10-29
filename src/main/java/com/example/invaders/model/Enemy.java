package com.example.invaders.model;
import javafx.scene.image.Image;
public class Enemy extends Sprite{
    private int health;
    // private AnimatedSprite animatedSprite;
    public Enemy(int x, int y, Image image, String type) {
        super(x, y, image, type);
    }

    public Enemy(int x, int y, Image[] frames, String type) {
        super(x, y, frames, type); // Image array constructor for animated enemy
    }

    public int getHealth(){
        return health;
    }
}