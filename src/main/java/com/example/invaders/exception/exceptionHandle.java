package com.example.invaders.exception;

import com.example.invaders.SpaceInvaderApp;
import com.example.invaders.model.Player;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class exceptionHandle {
    public void showTalkingDialog(String s, Player player, Pane pane, String direction) {
        Text dialogText = new Text(s);
        dialogText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
        dialogText.setFill(Color.BLACK);

        // Load the appropriate dialog background image based on the direction
        Image dialogBackgroundImage;
        if (direction.equals("right")) {
            dialogBackgroundImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/dialoug.png"));
        } else if (direction.equals("left")) {
            dialogBackgroundImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/leftdialoug.png"));
        } else {
            // Use a default dialog background image or handle other directions as needed
            dialogBackgroundImage = new Image(SpaceInvaderApp.class.getResourceAsStream("assets/rightdialoug.png"));
        }

        ImageView dialogBackgroundImgView = new ImageView(dialogBackgroundImage);

        StackPane dialogPane = new StackPane();
        dialogPane.getChildren().addAll(dialogBackgroundImgView, dialogText);

        double dialogX = 0;
        if (direction.equals("right")) {
            dialogX = player.getTranslateX() - player.getWidth() - 300;
        } else if (direction.equals("left")) {
            dialogX = player.getTranslateX() -player.getWidth() - 180;
        }

        dialogPane.setTranslateX(dialogX);
        dialogPane.setTranslateY(200);

        // Add the dialogPane to the game scene (root)
        pane.getChildren().add(dialogPane);

        PauseTransition dialogRemoval = new PauseTransition();
        dialogRemoval.setOnFinished(event -> {
            pane.getChildren().remove(dialogPane);
        });
        dialogRemoval.play();
    }

}
