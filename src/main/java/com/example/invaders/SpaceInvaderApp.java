package com.example.invaders;


import com.example.invaders.controller.SpriteController;
import com.example.invaders.controller.enemyController;
import com.example.invaders.controller.playerController;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Player;
import com.example.invaders.model.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SpaceInvaderApp extends Application {
  public static Pane root = new Pane();
    public static List<Sprite> sprites() {

        return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
    }
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
            Pane overlay = new Pane();
            VBox scoreBoard = new VBox();
            scoreBoard.setAlignment(Pos.CENTER);

            Label scoreLabel = new Label("Score: " + player.getScore());
            scoreBoard.getChildren().add(scoreLabel);

            overlay.getChildren().add(scoreBoard);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(root, overlay);


            Scene scene = new Scene(stackPane);
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
                    case S:
                        playerController.shootSpecial();
                }
            });



            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                   SpriteController.update();

                    enemyController.moveEnemies(enemies);
                    playerController.checkCollision();
                    scoreLabel.setText("Score: " + player.getScore());


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
