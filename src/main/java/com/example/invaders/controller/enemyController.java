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


    public void moveEnemies(List<Enemy> enemies) {
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
                    newX = otherEnemy.getTranslateX() + otherEnemy.getLayoutX()+ distanceThreshold;
                 }
            }
              if(newX < 0 ){
                direction = 1;
                newX = currentEnemy.getTranslateX() + enemySpeed * direction;
                currentEnemy.setTranslateX(newX+10);

                    logger.debug("Enemy ship is moving!");
            }
        }
    }

//    public void moveEnemies(List<Enemy> enemies) {
//        int paneWidth = 600;
//        double enemySpeed = 1;
//        int distanceThreshold = 5; // Minimum distance between enemies
//        boolean wallCollision = false;
//
//        // Determine the direction based on wall collision
//        int direction = wallCollision ? -1 : 1;

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

//            for (int j = 0; j < i; j++) {
//
//
//                if (newX + currentEnemy.getWidth() >= paneWidth - 20) {
//                    wallCollision = true;
//                    direction = -1;
//
//                    // If the last enemy collides with the wall, stop its movement
//                    if (i == enemies.size() - 1) {
//                        newX = paneWidth - currentEnemy.getWidth() - 20;
//
//                        // Move all other enemies before the last one to the left
//                        for (int k= 0; k <= i; k++) {
//                            Enemy enemyBeforeLast = enemies.get(j);
//                            enemyBeforeLast.setTranslateX(enemyBeforeLast.getTranslateX() - enemySpeed * 2);
//                        }
//                    } else {
//                        // Move all other enemies to the left
//                        for (Enemy enemy : enemies) {
//                            enemy.setTranslateX(enemy.getTranslateX() - enemySpeed * 2);
//                        }
//                    }
//                }
//
//                // Check if the enemy goes out of the screen on the left
//                if (newX < 0) {
//                    wallCollision = true;
//                    direction = 1;
//                    newX = 10;
//
//                    // If the first enemy collides with the wall, stop its movement
//                    if (i == 0) {
//                        newX = 10;
//
//                        // Move all other enemies after the first one to the right
//                        for (int m= i + 1; m < enemies.size(); m++) {
//                            Enemy enemyAfterFirst = enemies.get(m);
//                            enemyAfterFirst.setTranslateX(enemyAfterFirst.getTranslateX() + enemySpeed );
//                        }
//                    } else {
//                        // Move all other enemies to the right
//                        for (Enemy enemy : enemies) {
//                            enemy.setTranslateX(enemy.getTranslateX() + enemySpeed * 2);
//                        }
//                    }
//                }
//
//                // Set the new X position for the current enemy
//                currentEnemy.setTranslateX(newX);

//            }
//        }
//    }



    public void shoot(Sprite enemy) {
       Bullet.shoot(enemy);

    }

    public void checkCollisions() {
    }



}
