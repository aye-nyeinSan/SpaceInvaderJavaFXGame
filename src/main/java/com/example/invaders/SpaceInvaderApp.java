package com.example.invaders;

import com.example.invaders.controller.ScoreController;
import com.example.invaders.controller.SpriteController;
import com.example.invaders.controller.enemyController;
import com.example.invaders.controller.playerController;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Player;
import com.example.invaders.model.Score;
import com.example.invaders.model.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class SpaceInvaderApp extends Application {
  public static Pane root = new Pane();
        @Override
        public void start(Stage stage) throws Exception {

            root.setPrefSize(600,700);


            Player player = new Player(300, 600, 40, 40, Color.BLUE);
            List<Enemy> enemies = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy(90 + i * 100, 150, 30, 30, Color.RED));
               root.getChildren().add(enemies.get(i));
            }

            root.getChildren().add(player);
            playerController playerController = new playerController(player);
            enemyController enemyController = new enemyController(enemies);



            Scene scene = new Scene(root);
            scene.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case LEFT:
                        playerController.moveLeft();
                        break;
                    case RIGHT:
                        playerController.moveRight();
                        break;
                    case SPACE:
                        playerController.shoot();
                        break;
                }
            });

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    playerController.update();
                    enemyController.update();
                    enemyController.moveEnemies(enemies);
                    playerController.checkCollision();
                    enemyController.checkCollisions();

                }
            };

            timer.start();


            // Set up the game scene and input handling

            stage.setScene(scene);
            stage.show();
        }

    public static  Pane getRoot() {
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
