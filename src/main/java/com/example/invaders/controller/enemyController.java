package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;
import com.example.invaders.model.Bullet;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Sprite;

import java.util.List;

import static com.example.invaders.SpaceInvaderApp.root;
import static com.example.invaders.controller.SpriteController.sprites;
import static com.example.invaders.controller.playerController.player;
import static com.example.invaders.model.Bullet.shoot;


public class enemyController {
    private List<Enemy> enemies;
    private double t = 0;
    public enemyController(List<Enemy> enemies) {
        this.enemies = enemies;

    }

    public void moveEnemies(List<Enemy> enemies) {
        int paneWidth = 600;
        double enemySpeed = 0.8;
        int direction = 1;
        int distanceThreshold = 5; // Minimum distance between enemies


        for (int i = 0; i < enemies.size(); i++) {
            Enemy currentEnemy = enemies.get(i);
            double newX = currentEnemy.getTranslateX() + enemySpeed * direction;
            if ( newX + currentEnemy.getWidth() >= paneWidth -20) {
                direction = -1;
                newX=10;
            }
            currentEnemy.setTranslateX(newX);

            for (int j = 0; j < i; j++) {
                Enemy otherEnemy = enemies.get(j);
                double distance = newX - (otherEnemy.getTranslateX() + otherEnemy.getWidth());

                if (distance < distanceThreshold) {
                    newX = otherEnemy.getTranslateX() + otherEnemy.getWidth() + distanceThreshold;
                 }
            }
              if(newX < 0 ){
                direction = 1;
                newX = currentEnemy.getTranslateX() + enemySpeed * direction;
                currentEnemy.setTranslateX(newX+5);


            }
        }
    }







    public void update() {
        t += 0.016;
        sprites().forEach(s -> {
            switch (s.type) {
                case "enemybullet":
                    s.moveDown();
                    if (s.getBoundsInParent().intersects(playerController.getPlayer().getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
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
        SpaceInvaderApp.root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });
        if (t > 2) {
            t = 0;
        }
    }

    public void checkCollisions() {
    }

    public void shoot(Sprite enemy) {
       Bullet.shoot(enemy);
    }
}
