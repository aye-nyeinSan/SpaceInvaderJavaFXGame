package com.example.invaders.view;

import com.example.invaders.SpaceInvaderApp;
import com.example.invaders.model.Enemy;
import com.example.invaders.model.Player;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.List;

public class GamePlatform {
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

    public Player addingPlayer(Pane pane) {
        //Adding player
        Image playerImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/player1.png"));
        Player player = new Player(300, 580, playerImage, "player");
        pane.getChildren().add(player);
        return player;
    }

    public List<Enemy> addingEnemies(Pane pane) {
        List<Enemy> enemies = new ArrayList<>();
        Image enemyImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/alien.png"));

        //5 enemies by 3 lines
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 5; i++) {
                enemies.add(new Enemy(90 + i * 100, 150 + j * 80, enemyImage, "enemy"));
                pane.getChildren().addAll(enemies.get(enemies.size() - 1));
            }

        }
        return enemies;
    }



}
