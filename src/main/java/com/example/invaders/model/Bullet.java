package com.example.invaders.model;

import com.example.invaders.SpaceInvaderApp;
import javafx.scene.image.Image;

import static com.example.invaders.controller.playerController.PLAYER_SHOOT_COOLDOWN;
import static com.example.invaders.controller.playerController.lastPlayerShotTime;

public class Bullet extends Sprite{
    //bullet images
    static Image bulletImage=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\bomb.png");

    public Bullet(int x, int y, Image image, String type) {
        super(x, y, image, type);
    }

    public static void shoot(Sprite who) {
        long currentTime = System.nanoTime();
        int xOffset = 20;
        int yOffset = 0; // Adjust this value for the Y offset if needed

        if (who.type.equals("enemy")) {
            Bullet bullet = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, bulletImage,who.type + "bullet");
            SpaceInvaderApp.getRoot().getChildren().addAll(bullet);
        } else if (who.type.equals("player") && currentTime - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN) {
            Bullet bullet1 = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, bulletImage, who.type + "bullet");
            SpaceInvaderApp.getRoot().getChildren().addAll(bullet1);
            lastPlayerShotTime = currentTime;
        }
    }

    public static void shootSpecial(Sprite who) {
        long currentTime = System.nanoTime();
        int xOffset = 20;
        int yOffset = 0; // Adjust this value for the Y offset if needed

        if (who.type.equals("player") && currentTime - lastPlayerShotTime >= PLAYER_SHOOT_COOLDOWN) {
            Bullet bullet2 = new Bullet((int) who.getTranslateX() + xOffset, (int) who.getTranslateY() + yOffset, bulletImage, who.type + "specialBullet");
            SpaceInvaderApp.getRoot().getChildren().addAll(bullet2);
            lastPlayerShotTime = currentTime;
        }
    }


}
