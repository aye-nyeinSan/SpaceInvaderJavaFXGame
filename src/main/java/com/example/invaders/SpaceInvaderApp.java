package com.example.invaders;

import com.example.invaders.controller.SpriteController;
import com.example.invaders.controller.enemyController;
import com.example.invaders.controller.playerController;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Player;
import com.example.invaders.model.Sprite;
import com.example.invaders.view.GamePlatform;
import javafx.animation.AnimationTimer;

import javafx.application.Application;

import javafx.application.Platform;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.stage.Stage;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.example.invaders.controller.SpriteController.isGameOver;


public class SpaceInvaderApp extends Application {

    public static Pane root = new Pane();
    GamePlatform platform;

    Logger logger = LogManager.getLogger(SpaceInvaderApp.class);
    private static MediaPlayer backgroundMediaPlayer;
    public static Stage stage;
    private static boolean isPaused;
    private static AnimationTimer animationTimer;

    public static List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
    }

    public static void RemoveEnemies() {
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : root.getChildren()) {
            if (node instanceof Enemy) {
                nodesToRemove.add(node);
            }
        }
        root.getChildren().removeAll(nodesToRemove);
    }


    @Override
    public void start(Stage window) throws Exception {
        stage = window;
        setMainMenuScreen(stage);
        stage.show();
    }

public void closeMainGameStage(){
    if(stage!= null){
        stage.close();
    }
}
    public void startGame(Stage window) {
        stage = window;
        platform= new GamePlatform();
        Region background = platform.addingBackgroundImage();
        Player player = platform.addingPlayer(root);
//        // Check if there are existing enemies, and if so, remove them
//        List<Enemy> existingEnemies = root.getChildren().stream()
//                .filter(node -> node instanceof Enemy)
//                .map(node -> (Enemy) node)
//                .toList();
//
//        if (!existingEnemies.isEmpty()) {
//            root.getChildren().removeAll(existingEnemies);
//        }

        List<Enemy> enemies = platform.addingEnemies(root);

      

        Thread backgroundSoundThread = new Thread(() -> {
            playbackgroundSound(new Media(SpaceInvaderApp.class.getResource("/sounds/aggressivebackground.mp3").toExternalForm()));
        });
        backgroundSoundThread.start();

        playerController playerController = new playerController(player);
        enemyController enemyController = new enemyController(enemies);

        Pane overlay = new Pane();
        VBox scoreBoard = new VBox();
        scoreBoard.setAlignment(Pos.CENTER);
        scoreBoard.setStyle("-fx-background-color: lightblue;");
        Label scoreLabel = new Label("Score: " + player.getScore());
        scoreBoard.getChildren().add(scoreLabel);

        overlay.getChildren().add(scoreBoard);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, root, overlay);


        Scene scene = new Scene(stackPane);
        scene.getRoot().requestFocus();
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    logger.debug("User is clicking LEFT key ");
                    playerController.moveLeft();
                    break;
                case RIGHT:
                    logger.debug("User is clicking Right key ");
                    playerController.moveRight();
                    break;
                case SPACE:
                    logger.debug("User is clicking Space key ");
                    playerController.shoot();
                    new Thread(() -> {
                        playEffectSound(new Media(SpaceInvaderApp.class.getResource("/sounds/shoot.wav").toExternalForm()));

                    }).start();
                    break;
                case C:
                    logger.debug("User is clicking C key ");
                    playerController.shootSpecial();
                    new Thread(() -> {
                        playEffectSound(new Media(SpaceInvaderApp.class.getResource("/sounds/shoot.wav").toExternalForm()));

                    }).start();
                    break;


            }
        });
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                SpriteController.update();
                enemyController.moveEnemies(enemies);
                playerController.checkCollision();
                scoreLabel.setText("Score: " + player.getScore());

                ExecutorService executor = Executors.newFixedThreadPool(4);

                executor.execute(() -> {
                    Platform.runLater(() -> {
                        scoreLabel.setText("Score: " + player.getScore());
                        if (isGameOver) {
                            backgroundSoundThread.interrupt();
                            backgroundMediaPlayer.stop();
                            overlay.getChildren().removeAll();
                        }
                    });
                });
                executor.execute(() -> {
                    Platform.runLater(() -> {
                        addLives(overlay, player.getChances());
                        addPauseButton(overlay);
                    });
                });
            }
        };


        logger.debug("Animation is working!");
        try {
            animationTimer.start();
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
            logger.info("App started successfully.");
        } catch (Exception e) {
            logger.error("App can not start!");

        }

    }

    public static void startAnimation() {

        animationTimer.start();
    }
    public static void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
    public static List<Enemy> addEnemies(){
        List<Enemy> enemies = new ArrayList<>();
        Image enemyImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/alien.png"));

        //5 enemies by 3 lines
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 5; i++) {
                enemies.add(new Enemy(90 + i * 100, 150 + j * 80, enemyImage, "enemy"));
                root.getChildren().addAll(enemies.get(enemies.size() - 1));
            }

        }
        return enemies;
    }



    private void addPauseButton(Pane overlay) {
        Image pauseIcon = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/PauseIcon.png"));
        Button pause = new Button("", new ImageView(pauseIcon));
        pause.setStyle(" -fx-border-width: 0;");
        pause.setStyle("-fx-background-radius: 60%");
        pause.setTranslateX(540);
        pause.setTranslateY(7);
        overlay.getChildren().add(pause);
    }



    private void addLives(Pane overlay,int playerChances) {
        Image playerLife = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/shield.png"));
        double iconSpacing = 10.0;
        overlay.getChildren().removeIf(node -> node instanceof ImageView);
        for (int i = 0; i < playerChances; i++) {
            ImageView lifeIcon = new ImageView(playerLife);
            lifeIcon.setLayoutX(i * (lifeIcon.getBoundsInLocal().getWidth() + iconSpacing));
            lifeIcon.setLayoutY(20);
            overlay.getChildren().add(lifeIcon);
        }
    }

    public static Pane getRoot() {
        return root;

    }

    public static void playEffectSound(Media media) {
        MediaPlayer media_player = new MediaPlayer(media);
        media_player.play();

    }

    public static void playbackgroundSound(Media media) {
        backgroundMediaPlayer = new MediaPlayer(media);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();

    }

    public static void playbackgroundSoundOff() {

        backgroundMediaPlayer.stop();

    }

    public static void playbackgroundSoundOn() {

        backgroundMediaPlayer.play();

    }

    public static Stage getStage() {
        return stage;
    }

    public void setMainMenuScreen(Stage window) throws Exception {
        stage=window;
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceInvaderApp.class.getResource("/hello-view.fxml"));
        Scene mainMenuScene = new Scene(fxmlLoader.load(), 600, 500);
        playbackgroundSound(new Media(SpaceInvaderApp.class.getResource("/sounds/aggressivebackground.mp3").toExternalForm()));
        getStage().setTitle("Space Invaders");
        getStage().setScene(mainMenuScene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}