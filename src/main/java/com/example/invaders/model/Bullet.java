package com.example.invaders.model;

import com.example.invaders.SpaceInvaderApp;
import javafx.scene.paint.Color;

public class Bullet extends Sprite {
    int point;
    public Bullet(int x, int y, int w, int h, String type, Color color) {
        super(x, y, w, h, "bullet", color);
    }

    public static void shoot(Sprite who) {
        Bullet bullet = new Bullet((int)who.getTranslateX() + 20, (int)who.getTranslateY(), 5,20, who.type + "bullet", Color.BLACK);
        SpaceInvaderApp.getRoot().getChildren().add(bullet);
    }
}
