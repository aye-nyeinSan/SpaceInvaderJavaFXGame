package com.example.invaders;

import com.example.invaders.controller.SpriteController;
import com.example.invaders.controller.enemyController;
import com.example.invaders.controller.menuController;
import com.example.invaders.controller.playerController;
import com.example.invaders.exception.exceptionHandle;
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

import javafx.stage.Modality;
import javafx.stage.Stage;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.stream.Collectors;

import static com.example.invaders.controller.SpriteController.isGameOver;



public class SpaceInvaderApp extends Application {

     Scene scene;
    public static Pane root = new Pane();
    public static Pane exceptionPane = new Pane();
   public static  GamePlatform platform;
    public static  StackPane stackPane= new StackPane();

    Logger logger = LogManager.getLogger(SpaceInvaderApp.class);

    private static MediaPlayer backgroundMediaPlayer;
    public static Stage stage;
    private static boolean isPaused;
    private static AnimationTimer animationTimer;
    Thread backgroundSoundThread;
    static  boolean isSpawned=false;

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
        stage=window;
        stage.centerOnScreen();
        setMainMenuScreen(stage);
        stage.show();
    }

    public void closeMainGameStage(){
        if(stage!= null){
            stage.close();
        }
    }
    public void stopGame(Stage window){
        stage = window;
        stage.close();
    }

    public void startGame(Stage window,Player player,boolean isSoundOff) {
        playbackgroundSoundOff();
        stage = window;
        platform= new GamePlatform();

        Region background = platform.addingBackgroundImage();
        // Check if there are existing enemies, and if so, remove them
        List<Enemy> existingEnemies = root.getChildren().stream()
                .filter(node -> node instanceof Enemy)
                .map(node -> (Enemy) node)
                .collect(Collectors.toList());

        if (!existingEnemies.isEmpty()) {
            root.getChildren().removeAll(existingEnemies);
        }

        List<Enemy> enemies = platform.addingEnemies(root);

        if(isSoundOff){
            playbackgroundSoundOff();
        }
        else
        { backgroundSoundThread = new Thread(() -> {
            playbackgroundSound(new Media(SpaceInvaderApp.class.getResource("/sounds/aggressivebackground.mp3").toExternalForm()));
        });
        backgroundSoundThread.start();
        }

        playerController playerController = new playerController(player);
        enemyController enemyController = new enemyController(enemies);

        Pane overlay = new Pane();
        VBox scoreBoard = new VBox();
        scoreBoard.setAlignment(Pos.CENTER);
        scoreBoard.setStyle("-fx-background-color: lightblue;");
        Label scoreLabel = new Label("Score: " + player.getScore());
        scoreBoard.getChildren().add(scoreLabel);

        overlay.getChildren().add(scoreBoard);

        exceptionPane = new Pane();

        if(stackPane.getChildren().isEmpty()){
            stackPane.getChildren().addAll(background,root, overlay,exceptionPane);
        }
        else{
            System.out.println("Removed children");
            stackPane.getChildren().removeAll();
        }

        exceptionHandle exception = new exceptionHandle();
         scene = new Scene(stackPane);
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


                default:
                   exception.showTalkingDialog("You clicked \n another key: \n" + e.getCode(), player, exceptionPane, "default");
            }

        });
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ExecutorService executor = Executors.newFixedThreadPool(4);
                SpriteController.update();
                if(!isSpawned && platform.getEnemies().size() <= 10){
                    playbackgroundSound(new Media(SpaceInvaderApp.class.getResource("/sounds/BossComing.mp3").toExternalForm()));
                    GamePlatform.BossSpawning(root);
                    isSpawned = true;
                }
                enemyController.moveEnemies(enemies);
                executor.execute(() -> {
                    Platform.runLater(() -> {
                        playerController.checkCollision();
                    });
                });
//                scoreLabel.setText("Score: " + player.getScore());

                executor.execute(() -> {
                    Platform.runLater(() -> {
                        scoreLabel.setText("Score: " + player.getScore());
                        if (isGameOver) {
                            backgroundMediaPlayer.stop();
                            overlay.getChildren().removeAll();
                        }
                    });
                });
                executor.execute(() -> {
                    Platform.runLater(() -> {
                        addLives(overlay, player.getChances());
                    });
                });

                executor.execute(() -> {
                    Platform.runLater(() -> {
                       workingPauseButton(addPauseButton(overlay));

                    });
                });
                overlay.requestFocus();
            }
        };


        logger.debug("Animation is working!");
        try {
            animationTimer.start();
            stage.setScene(scene);
            stage.centerOnScreen();
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

public void workingPauseButton(Button pause){
    Image playIcon = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/playIcon.png"));
    Stage pauseStage = new Stage();
    pauseStage.initOwner(stage);
    pauseStage.initModality(Modality.APPLICATION_MODAL);
    pauseStage.setTitle("Game Paused");
    pauseStage.setWidth(200);
    pauseStage.setHeight(100);
    VBox pauseMenu = new VBox();
    pauseMenu.setAlignment(Pos.CENTER);
    Label pauseLabel = new Label("Game Paused");
    Button resumeButton = new Button("Resume");
    resumeButton.setGraphic(new ImageView(playIcon));
    pauseMenu.getChildren().addAll(pauseLabel, resumeButton);


    resumeButton.setOnAction(event -> {
        pauseStage.close();
        startAnimation();
    });
    Scene pauseScene = new Scene(pauseMenu);
    pauseStage.setScene(pauseScene);
    pause.setOnAction(event ->
    { logger.debug("Pause button is clicked!");
        pauseStage.show();
        stopAnimation();
    });
}



    private Button addPauseButton(Pane overlay) {
        Image pauseIcon = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/PauseIcon.png"));
        Button pause = new Button();
        pause.setGraphic(new ImageView(pauseIcon));
        pause.setStyle("-fx-background-radius: 40%");
        pause.setTranslateX(540);
        pause.setTranslateY(7);
        overlay.getChildren().add(pause);
        pause.setFocusTraversable(true);
        return pause;
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
    public static StackPane getStackPane() {
        return stackPane;
    }
    public static void setStackPane(StackPane stackPane) {
        SpaceInvaderApp.stackPane = stackPane;
    }
    public void setMainMenuScreen(Stage window)throws Exception {
        stage=window;
        FXMLLoader fxmlLoader=new FXMLLoader(SpaceInvaderApp.class.getResource("/hello-view.fxml"));
        Scene mainMenuScene = new Scene(fxmlLoader.load(), 600, 500);
        playbackgroundSound(new Media(SpaceInvaderApp.class.getResource("/sounds/aggressivebackground.mp3").toExternalForm()));
        getStage().setTitle("Space Invaders");
        getStage().setScene(mainMenuScene);
        getStage().centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}