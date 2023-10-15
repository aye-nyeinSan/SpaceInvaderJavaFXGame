package com.example.invaders.controller;

import com.example.invaders.model.Bullet;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Sprite;

import java.util.List;
import java.util.stream.Collectors;
import static com.example.invaders.controller.playerController.player;

public class enemyController {
    private List<Enemy> enemies;

    public enemyController(List<Enemy> enemies) {
        this.enemies = enemies;

    }

    //Enemy Moving
//    public void moveEnemies(List<Enemy> enemies) {
//        int paneWidth = 600;
//        double enemySpeed = 0.8;
//        int direction = 1;
//        int distanceThreshold = 5; // Minimum distance between enemies
//
//
//        for (int i = 0; i < enemies.size(); i++) {
//            Enemy currentEnemy = enemies.get(i);
//            double newX = currentEnemy.getTranslateX() + enemySpeed * direction;
//            if ( newX + currentEnemy.getWidth() >= paneWidth -20) {
//                direction = -1;
//                newX = 0;
//            }
//            currentEnemy.setTranslateX(newX);
//
//            for (int j = 0; j < i; j++) {
//                Enemy otherEnemy = enemies.get(j);
//                double distance = newX - (otherEnemy.getTranslateX() + otherEnemy.getWidth());
//
//                if (distance < distanceThreshold) {
//                    newX = otherEnemy.getTranslateX() + otherEnemy.getWidth() + distanceThreshold;
//                }
//            }
//            if(newX < 0 ){
//                direction = 1;
//                newX = currentEnemy.getTranslateX() + enemySpeed * direction;
//                currentEnemy.setTranslateX(newX+10);
//
//
//            }
//        }
//    }

    public void shoot(Sprite enemy) {
        Bullet.shoot(enemy);
    }

    public void checkCollisions() {
    }

}
