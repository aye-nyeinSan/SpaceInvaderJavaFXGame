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
import javafx.scene.image.Image;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SpaceInvaderApp extends Application {
  public static Pane root = new Pane();
  Logger logger = LogManager.getLogger(SpaceInvaderApp.class);
    public static List<Sprite> sprites() {
        return root.getChildren().stream().map(n->(Sprite)n).collect(Collectors.toList());
    }

    @Override
    public void start(Stage stage) throws Exception {
        root.setPrefSize(600,700);

// adding exploration effect
// explosion.visibleProperty().set(false);

//player images
        Image playerImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/player.png"));

//enemy images
        Image enemyImage=new Image(SpaceInvaderApp.class.getResourceAsStream("assets/alien.png"));

//creating player
        Player player = new Player(300, 600,playerImage, "player");

        List<Enemy> enemies = new ArrayList<>();


        //5 enemies by 3 lines
       for(int j=0; j<3; j++){
           for (int i = 0; i < 5; i++) {
               enemies.add(new Enemy(90 + i * 100, 150 + j * 80 , enemyImage, "enemy"));
               root.getChildren().addAll(enemies.get(enemies.size()-1));
           }

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

        Scene scene=new Scene(stackPane);
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

                    break;
                case C:
                    logger.debug("User is clicking C key ");
                    playerController.shootSpecial();


            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                SpriteController.update();

                enemyController.moveEnemies(enemies);
                playerController.checkCollision();
                scoreLabel.setText("Score: "+player.getScore());
            }
        };
        logger.debug("Animation is working!");
        try{
            timer.start();
            stage.setScene(scene);
            stage.show();
            logger.info("App started successfully.");
        }
        catch(Exception e){
            logger.error("App can not start!");

        }

        stage.setScene(scene);
        stage.show();

    }

    public static Pane getRoot(){
        return root;

    }


    public static void main(String[] args) {
        launch(args);
    }
}

