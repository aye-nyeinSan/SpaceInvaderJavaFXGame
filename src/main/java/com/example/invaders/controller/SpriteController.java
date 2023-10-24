package com.example.invaders.controller;

import com.example.invaders.model.Sprite;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;

import com.example.invaders.SpaceInvaderApp;

import com.example.invaders.model.Bullet;
import com.example.invaders.model.Sprite;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static com.example.invaders.SpaceInvaderApp.*;
import static com.example.invaders.controller.playerController.*;
import static com.example.invaders.model.Bullet.shoot;

public class SpriteController {
    static double t = 0;
   static  Stage gameOverStage;
    public static boolean isGameOver = false;
    static Logger logger = LogManager.getLogger(SpriteController.class);


    public static void showSprites() {
        List<Sprite> sprites = sprites();
        for (int i = 0; i < sprites.size(); i++) {
            //System.out.println(sprites.get(i).type);
        }

    }

    public static void update() {
        t += 0.016;

        sprites().forEach(s -> {
            switch (s.type) {
                case "enemybullet":
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        logger.error("Enemy bullet shot to player");
                        logger.debug("Before Dead Health :" + player.getHealth());


                        Thread explosionSoundThread = new Thread(() -> {
                            SpaceInvaderApp.playEffectSound(new Media(SpaceInvaderApp.class.getResource("/sounds/explosion.wav").toExternalForm()));

                        });
                        explosionSoundThread.start();


                        player.dead = true;
                        s.dead = true;
                        player.setDead(true);
                        s.setDead(true);

                        if (player.getCurrentChance() < 4) {
                            root.getChildren().removeIf(n -> {
                                Sprite one = (Sprite) n;
                                return one.dead;
                            });

                            //javafx animation delay ,PAUSE
                            PauseTransition delay = new PauseTransition(Duration.seconds(1));
                            delay.setOnFinished(event -> {
                                respawn();
                                logger.debug("After dead, Health: " + player.getHealth());
                                logger.debug("After dead , Current life: " + player.getCurrentChance());
                            });
                            delay.play();
                        }

                        else if (player.getCurrentChance()==4) {
                            player.setHealth(0);
                            Thread gameOverSoundThread = new Thread(() -> {
                                SpaceInvaderApp.playEffectSound(new Media(SpaceInvaderApp.class.getResource("/sounds/gameover.m4a").toExternalForm()));

                            });
                            isGameOver = true;
                            gameOverSoundThread.start();
                            previousScore.add(player.getScore());
                            logger.info("Previous added as {}", player.getScore());
                            logger.warn("player has no life!");
                            logger.error("player is dead!");

                            //GameOverScreen
                            showGameOverScreen();
                        }
                       else if (player.getCurrentChance() >= 4) {
                            logger.error("player has already dead!");
                        }
                        showExplosion(player);


                    }
                    break;
                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {

                            enemy.dead = true;
                            s.dead = true;
                            enemy.setDead(true);
                            int pointEarned = 5;
                            player.increaseScore(pointEarned);
                            System.out.println("Score:" + player.getScore());

                            showExplosion(enemy);

                        }
                    });
                    break;
                case "playerspecialBullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            int pointEarned = 10;
                            player.increaseScore(pointEarned);
                            System.out.println("Score:" + player.getScore());
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
    public static void RestartGame(Stage window  ){
       if(window != null){
           window.close();
       }

        SpaceInvaderApp spaceInvaderApp=new SpaceInvaderApp();
        spaceInvaderApp.RemoveEnemies();
        spaceInvaderApp.startGame(window);
        isGameOver=false;
        player.setCurrentChance(1);
        player.setHealth(100);
        player.setScore(0);
        player.setChances(3);


    }

    private static void showGameOverScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SpriteController.class.getResource("/game-over.fxml"));
            Scene gameOverMenuScene = new Scene(fxmlLoader.load(), 250, 250);
            gameOverStage = new Stage();
            gameOverStage.initStyle(StageStyle.UTILITY);
            gameOverStage.initModality(Modality.WINDOW_MODAL);
            gameOverStage.setTitle("Game Over");
            gameOverStage.setResizable(false);
            gameOverStage.setScene(gameOverMenuScene);
            gameOverStage.show();
            SpaceInvaderApp.stopAnimation();

            Text scoreVar = (Text) gameOverMenuScene.lookup("#scoreVar");
            scoreVar.setText(String.valueOf(player.getScore()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeGameOverScreen() {
        if (gameOverStage != null) {
            gameOverStage.close();
        }
    }
    private static void showExplosion(Sprite target) {

        Image explosion_img = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/explo1.png"));

        Sprite explosion = new Sprite(0, 0, explosion_img, "explosion");
        explosion.setTranslateX(target.getTranslateX() - 100);
        explosion.setTranslateY(target.getTranslateY() - 100);
        root.getChildren().add(explosion);

        int explosionDuration = 500; // Adjust the duration as needed

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(explosionDuration),
                        event -> root.getChildren().remove(explosion)
                )
        );

        timeline.setCycleCount(1); // Play the animation once
        timeline.play();
    }


}