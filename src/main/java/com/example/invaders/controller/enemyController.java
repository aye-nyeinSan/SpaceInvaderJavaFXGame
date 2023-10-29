package com.example.invaders.controller;



import com.example.invaders.SpaceInvaderApp;

import com.example.invaders.model.Boss;
import com.example.invaders.model.Bullet;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Sprite;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.invaders.SpaceInvaderApp.bossAnimationTimer;
import static com.example.invaders.controller.playerController.player;
import static com.example.invaders.view.GamePlatform.boss;


public class enemyController {
  static Logger logger = LogManager.getLogger(enemyController.class);


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

    public static void moveEnemiesDown(List<Enemy> enemies, double newY) {
        Duration animationDuration = Duration.seconds(1.0); // Adjust the duration as needed

        ParallelTransition parallelTransition = new ParallelTransition();

        for (Enemy enemy : enemies) {
            TranslateTransition transition = new TranslateTransition(animationDuration, enemy);
            transition.setToY(newY);
            parallelTransition.getChildren().add(transition);
        }

        parallelTransition.play();
    }



    public static void moveBoss() {
        int paneWidth = 600;
        double speed = 5.0;

        double currentX = boss.getTranslateX();
        logger.debug("Boss location :{}",currentX);
        double newLocationX = currentX + speed;
        logger.debug("Boss is located to :{}",newLocationX);
        boss.setTranslateX(newLocationX);
        if(boss.getTranslateX() >= paneWidth - boss.getFitWidth()){
            boss.setTranslateX(0);
        }
        if (boss.dead) {
            logger.info("Boss is dead!");
            bossAnimationTimer.stop();
        }
    }




}
