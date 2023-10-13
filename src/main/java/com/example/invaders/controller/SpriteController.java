package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;

import com.example.invaders.model.Bullet;
import com.example.invaders.model.Sprite;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static com.example.invaders.SpaceInvaderApp.root;
import static com.example.invaders.SpaceInvaderApp.sprites;
import static com.example.invaders.controller.playerController.*;
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
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        System.out.println("Before Dead Health :" + player.getHealth());
                        player.dead = true;
                        s.dead = true;
                        player.setDead(true);
                        s.setDead(true);

                        if (player.getCurrentChance() < 3) {
                            root.getChildren().removeIf(n -> {
                                Sprite one = (Sprite) n;
                                return one.dead;
                            });

                            //javafx animation delay ,PAUSE
                            PauseTransition delay = new PauseTransition(Duration.seconds(1));
                            delay.setOnFinished(event -> {
                                respawn();
                                System.out.println("After dead, Health: " + player.getHealth());
                                System.out.println("After dead , Current life: " + player.getCurrentChance());
                            });
                            delay.play();
                        }
                           if(player.getCurrentChance()== 3 && player.getHealth()== 33) {
                               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                               alert.setHeaderText(null);
                               alert.setContentText("You are dead!");
                               alert.show();
                           }

                    }
                    break;
                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            enemy.setDead(true);
                            int pointEarned=5;
                            player.increaseScore(pointEarned);
                            System.out.println("Score:"+ player.getScore());


                        }
                    });
                    break;
                case "playerspecialBullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            int pointEarned=10;
                            player.increaseScore(pointEarned);
                            System.out.println("Score:"+ player.getScore());
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


