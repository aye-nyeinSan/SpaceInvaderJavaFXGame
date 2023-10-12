package com.example.invaders.model;

import com.example.invaders.SpaceInvaderApp;
import javafx.scene.paint.Color;

import static com.example.invaders.controller.playerController.PLAYER_SHOOT_COOLDOWN;
import static com.example.invaders.controller.playerController.lastPlayerShotTime;

public class Bullet extends Sprite {
    int point;

    public Bullet(int x, int y, int w, int h, String type, Color color) {
        super(x, y, w, h, type, color);
    }

    public static void shoot(Sprite who) {
        long currentTime = System.nanoTime();
        int xOffset = 20;
        int yOffset = 0; // Adjust this value for the Y offset if needed

        if (who.type.equals("enemy")) {
            Bullet bullet = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, 5, 20,who.type + "bullet", Color.BLACK);
            SpaceInvaderApp.getRoot().getChildren().addAll(bullet);
        } else if (who.type.equals("player") && currentTime - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN) {
            Bullet bullet1 = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, 5, 20, who.type + "bullet", Color.BLACK);
            SpaceInvaderApp.getRoot().getChildren().addAll(bullet1);
            lastPlayerShotTime = currentTime;
        }
    }

    public static void shootSpecial(Sprite who) {
        long currentTime = System.nanoTime();
        int xOffset = 20;
        int yOffset = 0; // Adjust this value for the Y offset if needed

        if (who.type.equals("player") && currentTime - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN) {

            Bullet bullet2 = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, 5, 5, who.type + "specialBullet", Color.RED);
            SpaceInvaderApp.getRoot().getChildren().addAll(bullet2);
            lastPlayerShotTime = currentTime;
        }
    }


}

