package com.example.invaders.model;

import com.example.invaders.SpaceInvaderApp;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;

import static com.example.invaders.controller.playerController.PLAYER_SHOOT_COOLDOWN;
import static com.example.invaders.controller.playerController.lastPlayerShotTime;

public class Bullet extends Sprite{
    //enemy bullet images
    static Image enemyBullet=new Image(SpaceInvaderApp.class.getResourceAsStream("assets/fireball.png"));

    //player bullet images
    static Image normalBullet=new Image(SpaceInvaderApp.class.getResourceAsStream("assets/bullet.png"));
    static Image specialBullet=new Image(SpaceInvaderApp.class.getResourceAsStream("assets/bomb.png"));






    public Bullet(int x, int y, Image image, String type) {
        super(x, y, image, type);

    }

    public static void shoot(Sprite who) {
        long currentTime = System.nanoTime();
        int xOffset = 20;
        int yOffset = 0; // Adjust this value for the Y offset if needed

        if (who.type.equals("enemy")) {

            Bullet bullet = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, enemyBullet,who.type + "bullet");
            SpaceInvaderApp.getRoot().getChildren().addAll(bullet);
        } else if (who.type.equals("player") && currentTime - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN) {
            Bullet bullet1 = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, normalBullet, who.type + "bullet");

            SpaceInvaderApp.getRoot().getChildren().addAll(bullet1);
            lastPlayerShotTime = currentTime;
        }
    }

    public static void shootSpecial(Sprite who) {
        long currentTime = System.nanoTime();
        int xOffset = 20;
        int yOffset = 0;

        if (who.type.equals("player") && currentTime - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN) {

            Bullet bullet2 = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, specialBullet, who.type + "specialBullet");

            SpaceInvaderApp.getRoot().getChildren().addAll(bullet2);
            lastPlayerShotTime = currentTime;
        }
    }


}
