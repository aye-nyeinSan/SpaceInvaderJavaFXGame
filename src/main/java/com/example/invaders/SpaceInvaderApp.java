package com.example.invaders;

import com.example.invaders.controller.SpriteController;
import com.example.invaders.controller.enemyController;
import com.example.invaders.controller.playerController;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Player;
import com.example.invaders.model.Sprite;
import javafx.animation.AnimationTimer;

import javafx.application.Application;

import javafx.application.Platform;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.layout.*;

import javafx.stage.Stage;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SpaceInvaderApp extends Application {

  public static Pane root = new Pane();
  Logger logger = LogManager.getLogger(SpaceInvaderApp.class);
    private static MediaPlayer backgroundMediaPlayer;
    private Stage stage;

    public static List<Sprite> sprites() {
        return root.getChildren().stream().map(n->(Sprite)n).collect(Collectors.toList());
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        setMainMenuScreen();
        stage.show();
    }


public void startGame(Stage window){
    // Add the background to the root pane
    Region background = new Region();
    // background.setStyle("-fx-background-color: lightblue;");
    Image img  = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/Sunset.jpg"));
    BackgroundImage backgroundImg = new BackgroundImage(new ImagePattern(img).getImage(),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(100, 100, true, true, true, true));
    background.setBackground(new Background(backgroundImg));
    background.setPrefSize(600, 700);  // Set the size to match your scene size
//player images
    Image playerImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/player.png"));

//enemy images
    Image enemyImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/alien.png"));

//creating player
    Player player = new Player(300, 600, playerImage, "player");

    List<Enemy> enemies = new ArrayList<>();


    //5 enemies by 3 lines
    for(int j=0; j<3; j++){
        for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy(90 + i * 100, 150 + j * 80 , enemyImage, "enemy"));
            root.getChildren().addAll(enemies.get(enemies.size()-1));
        }

    }
    root.getChildren().add(player);

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
    stackPane.getChildren().addAll(background,root, overlay);

    Scene scene=new Scene(stackPane);

    //background game sound track

    scene.setOnKeyPressed(e->{
        switch (e.getCode()){

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
                new Thread(()->{
                    playEffectSound( new Media(SpaceInvaderApp.class.getResource("/sounds/shoot.wav").toExternalForm()));

                }).start();
                break;
            case C:
                logger.debug("User is clicking C key ");
                playerController.shootSpecial();
                new Thread(()->{
                    playEffectSound( new Media(SpaceInvaderApp.class.getResource("/sounds/shoot.wav").toExternalForm()));

                }).start();
                break;


        }
    });

    AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long l) {

            SpriteController.update();

            enemyController.moveEnemies(enemies);
            playerController.checkCollision();

            scoreLabel.setText("Score: " + player.getScore());


            Platform.runLater(() ->{
                scoreLabel.setText("Score: "+player.getScore());
                if(SpriteController.isGameOver){
                    backgroundSoundThread.interrupt();
                    backgroundMediaPlayer.stop();


                }
            });

        }
    };
    logger.debug("Animation is working!");
    try {
        timer.start();
        window.setScene(scene);
        window.show();
        logger.info("App started successfully.");
    } catch (Exception e) {
        logger.error("App can not start!");

    }

}
    public static Pane getRoot(){
        return root;

    }
    public  static void playEffectSound(Media media){
        MediaPlayer media_player = new MediaPlayer(media);
        media_player.play();

    }
    public  static void playbackgroundSound(Media media){
        backgroundMediaPlayer = new MediaPlayer(media);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();

    }

            private void setMainMenuScreen()throws Exception {
                FXMLLoader fxmlLoader=new FXMLLoader(SpaceInvaderApp.class.getResource("/hello-view.fxml"));
                Scene mainMenuScene = new Scene(fxmlLoader.load(), 600, 700);
                this.stage.setTitle("Space Invaders");
                this.stage.setScene(mainMenuScene);
            }


    public static void main(String[] args) {
        launch(args);
    }
}