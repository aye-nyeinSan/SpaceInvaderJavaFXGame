package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;

import com.example.invaders.model.Bullet;
import com.example.invaders.model.Sprite;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.invaders.SpaceInvaderApp.root;
import static com.example.invaders.SpaceInvaderApp.sprites;
import static com.example.invaders.controller.playerController.player;
import static com.example.invaders.model.Bullet.shoot;

public class SpriteController {
    static double t = 0;


public static void showSprites(){
      List<Sprite > sprites = sprites();
    for (int i = 0; i < sprites.size(); i++) {
        System.out.println(sprites.get(i).type);
    }

}

    public static void update() {
        t += 0.016;

        sprites().forEach(s -> {
              //  showSprites();
            switch (s.type) {
                case "enemybullet":
                    System.out.println(s.getTranslateY());
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
                    break;
                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                        }
                    });
                    break;
                case "playerspecialBullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                        }
                    });
                    break;
                case "enemy":
                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            shoot(s);
                        }
                    }
                    break;

            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });
        if (t > 2) {
            t = 0;
        }
    }
}


