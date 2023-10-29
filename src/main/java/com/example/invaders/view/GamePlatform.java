package com.example.invaders.view;

import com.example.invaders.SpaceInvaderApp;
import com.example.invaders.controller.enemyController;
import com.example.invaders.model.Boss;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Player;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GamePlatform {
    ArrayList<Enemy> enemies  = new ArrayList<>();
    public static Boss boss;

    public Region addingBackgroundImage() {
        // Add the background to the root pane
        Region background = new Region();
        Image img = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/Sunset.jpg"));
        BackgroundImage backgroundImg = new BackgroundImage(new ImagePattern(img).getImage(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, true, true));
        background.setBackground(new Background(backgroundImg));
        background.setPrefSize(600, 650);  // Set the size to match your scene size
        return background;
    }

    public Player addingPlayer(Pane pane,Image selectedImg) {
        if(selectedImg!=null){
            Player player = new Player(300, 580, selectedImg, "player");
            pane.getChildren().add(player);
            return player;
        }else
        {//Adding player
        Image playerImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/player3.png"));
        Player player = new Player(300, 580, playerImage, "player");
        pane.getChildren().add(player);
            return player;
        }

    }

    public List<Enemy> addingEnemies(Pane pane) {
            Image enemyImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/alien.png"));

            //5 enemies by 3 lines
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 5; i++) {
                    this.enemies.add(new Enemy(90 + i * 100, 150 + j * 80, enemyImage, "enemy"));
                    pane.getChildren().addAll(enemies.get(enemies.size() - 1));
                }
            }

        return this.enemies;
    }
    public static Timeline BossSpawning(Pane pane) {
        Image bossImg = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/boss.png"));
         boss = new Boss(240, (int) -bossImg.getHeight(), bossImg, "Boss"); // Set the initial Y position to be above the visible area
           pane.getChildren().add(boss);
           Duration duration = Duration.seconds(8);
        int endY = 100;
        KeyValue keyValue = new KeyValue(boss.translateYProperty(), endY);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);
        Timeline timeline = new Timeline(keyFrame);

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        return timeline;
    }



    public List<Enemy> getEnemies() {
        return this.enemies;
    }


}
