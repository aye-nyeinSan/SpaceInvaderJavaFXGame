package com.example.invaders.controller;

import com.example.invaders.model.Sprite;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

import com.example.invaders.SpaceInvaderApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.invaders.SpaceInvaderApp.root;
import static com.example.invaders.SpaceInvaderApp.sprites;
import static com.example.invaders.controller.playerController.*;
import static com.example.invaders.model.Bullet.shoot;

public class SpriteController {
    static double t = 0;
  public  static boolean isGameOver = false;
    static Logger logger = LogManager.getLogger(SpriteController.class);




    public static void showSprites(){
        List<Sprite > sprites = sprites();
        for (int i = 0; i < sprites.size(); i++) {

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
                        logger.error("Enemy bullet shot to player");
                        logger.debug("Before Dead Health :" + player.getHealth());
                        Thread explosionSoundThread = new Thread(()->{
                            SpaceInvaderApp.playEffectSound( new Media(SpaceInvaderApp.class.getResource("/sounds/explosion.wav").toExternalForm()));

                        });
                        explosionSoundThread.start();


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
                                logger.debug("After dead, Health: " + player.getHealth());
                                logger.debug("After dead , Current life: " + player.getCurrentChance());
                            });
                            delay.play();
                        }

                           if(player.getCurrentChance()== 3 && player.getHealth()<= 33) {
                               Thread gameOverSoundThread = new Thread(()->{
                                   SpaceInvaderApp.playEffectSound( new Media(SpaceInvaderApp.class.getResource("/sounds/gameover.m4a").toExternalForm()));

                               });
                               isGameOver = true;
                               gameOverSoundThread.start();
                               logger.warn("player has no life!");
                               logger.error("player is dead!");

                             showGameOverScreen();

                           }
                           if(player.getCurrentChance()>=4){
                               logger.error("player has already dead!");
                           }
                       showExplosion(player);


                    }
                    break;
                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e->e.type.equals("enemy")).forEach(enemy->{
                        if(s.getBoundsInParent().intersects(enemy.getBoundsInParent())){

                            enemy.dead = true;
                            s.dead = true;
                            enemy.setDead(true);
                            int pointEarned=5;
                            player.increaseScore(pointEarned);
                            System.out.println("Score:"+ player.getScore());

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
                            int pointEarned=10;
                            player.increaseScore(pointEarned);
                            System.out.println("Score:"+ player.getScore());
                        }
                    });
                    break;
                case "enemy":
                    if(t>2){
                        if(Math.random()<0.3){
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

        if(t>2){
            t = 0;
        }
    }
    private static void showGameOverScreen(){
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(SpriteController.class.getResource("/game-over.fxml"));
            Scene mainMenuScene = new Scene(fxmlLoader.load(), 250, 250);
            Stage gameOverStage=new Stage();
            gameOverStage.setTitle("Game Over");
            gameOverStage.setScene(mainMenuScene);
            gameOverStage.show();
            Text scoreVar=(Text) mainMenuScene.lookup("#scoreVar");
            scoreVar.setText(String.valueOf(player.getScore()));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void showExplosion(Sprite target) {
        Image explosion_img=new Image(SpaceInvaderApp.class.getResourceAsStream("assets/explo1.png"));
        Sprite explosion=new Sprite(0,0,explosion_img,"explosion");
        explosion.setTranslateX(target.getTranslateX()-50);
        explosion.setTranslateY(target.getTranslateY()-50);
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