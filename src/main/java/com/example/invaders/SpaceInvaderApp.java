package com.example.invaders;

import com.example.invaders.controller.SpriteController;
import com.example.invaders.controller.enemyController;
import com.example.invaders.controller.playerController;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Player;
import com.example.invaders.model.Sprite;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceInvaderApp extends Application {
    public static Pane root = new Pane();

    public static List<Sprite> sprites(){
        return root.getChildren().stream().map(n->(Sprite)n).collect(Collectors.toList());
    }


    private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        setMainMenuScreen();
        stage.show();
    }

    private void setMainMenuScreen()throws Exception {
//        StackPane mainMenuRoot=new StackPane();
//        Scene mainMenuScene = new Scene(mainMenuRoot, 600, 500);
//
//        // Create a button to start the game
//        Button startGameButton = new Button("Start Game");
//        startGameButton.setOnAction(e -> startGame());
//
//        mainMenuRoot.getChildren().add(startGameButton);
//
//        stage.setScene(mainMenuScene);

        FXMLLoader fxmlLoader=new FXMLLoader(SpaceInvaderApp.class.getResource("/hello-view.fxml"));
        Scene mainMenuScene = new Scene(fxmlLoader.load(), 600, 700);
        this.stage.setTitle("Space Invaders");
        this.stage.setScene(mainMenuScene);
    }

    public void startGame() {
        root.setPrefSize(600,700);
        Image playerImage=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\player.png");
        Player player = new Player(300, 600,playerImage, "player");

        Image enemyImage=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\alien.png");
        List<Enemy> enemies = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy(90 + i * 100, 150, enemyImage, "enemy"));
            Sprite s1 = new Sprite(90 + i * 100, 150, enemyImage, "enemy");
            Sprite s2 = new Sprite(90 + i * 100, 200, enemyImage, "enemy");
            Sprite s3 = new Sprite(90 + i * 100, 250, enemyImage, "enemy");
            root.getChildren().addAll(enemies.get(i),s1,s2,s3);

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

        Scene gameScene=new Scene(stackPane);
        gameScene.setOnKeyPressed(e->{
            switch (e.getCode()){
                case LEFT:
                    playerController.moveLeft();
                    break;
                case RIGHT:
                    playerController.moveRight();
                    break;
                case SPACE:
                    playerController.shoot();
                    break;
                case C:
                    playerController.shootSpecial();

            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                SpriteController.update();

// enemyController.moveEnemies(enemies);
                playerController.checkCollision();
                scoreLabel.setText("Score: "+player.getScore());
            }
        };

        timer.start();


// Set up the game scene and input handling

        stage.setScene(gameScene);

    }


    public static Pane getRoot(){
        return root;

    }

    public static void main(String[] args) {
        launch(args);
    }
}