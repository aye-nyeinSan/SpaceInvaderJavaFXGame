package com.example.invaders.controller;



import com.example.invaders.SpaceInvaderApp;

import com.example.invaders.model.Bullet;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Sprite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import static com.example.invaders.controller.playerController.player;


public class enemyController {
    private List<Enemy> enemies;
    Logger logger = LogManager.getLogger(enemyController.class);

    public enemyController(List<Enemy> enemies) {
        this.enemies = enemies;

    }

    public static void moveEnemies(List<Enemy> enemies) {
        int paneWidth = 600;
        double enemySpeed = 0.8;
        int direction = 1;
        int distanceThreshold = 5; // Minimum distance between enemies


        for (int i = 0; i < enemies.size(); i++) {
            Enemy currentEnemy = enemies.get(i);
            double newX = currentEnemy.getTranslateX() + enemySpeed * direction;
            if ( newX + currentEnemy.getLayoutX() >= paneWidth -20) {
                direction = -1;
                newX = 0;
            }
            currentEnemy.setTranslateX(newX);

            for (int j = 0; j < i; j++) {
                Enemy otherEnemy = enemies.get(j);
                double distance = newX - (otherEnemy.getTranslateX() + otherEnemy.getLayoutX());

                if (distance < distanceThreshold) {
                    newX = otherEnemy.getTranslateX() + otherEnemy.getLayoutX() + distanceThreshold;

                 }
            }
            if(newX < 0 ){
                direction = 1;
                newX = currentEnemy.getTranslateX() + enemySpeed * direction;
                currentEnemy.setTranslateX(newX+10);


            }
        }
    }

    public boolean isOutOfBounds(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getTranslateX() > 0 && enemy.getTranslateX() < 600 - enemy.getLayoutX())
                return false;

        }
        return true;
    }





    public void shoot(Sprite enemy) {
       Bullet.shoot(enemy);

    }

    public void checkCollisions() {

    }



}
