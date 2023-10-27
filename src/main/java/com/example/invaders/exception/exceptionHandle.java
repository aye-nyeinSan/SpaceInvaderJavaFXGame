package com.example.invaders.exception;

import com.example.invaders.model.Player;
import com.example.invaders.model.Sprite;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;



public class exceptionHandle  {



    public void showTalkingDialog(String s,Player player, Pane root) {
        // Create a Text node for the dialog
        Text dialogText = new Text(s);
        dialogText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        dialogText.setFill(Color.BLACK);

        // Create a Rectangle node as the background for the dialog
        Rectangle dialogBackground = new Rectangle(200, 50, Color.WHITE);
        dialogBackground.setOpacity(0.7);

        // Create a StackPane to contain both the dialog background and text
        StackPane dialogPane = new StackPane();
        dialogPane.getChildren().addAll(dialogBackground, dialogText);

        // Position the dialog beside the player

        dialogPane.setTranslateX(player.getTranslateX() + player.getBoundsInParent().getWidth() + 10);
        dialogPane.setTranslateY(player.getTranslateY() - 20);

        // Add the dialogPane to the game scene (root)
        root.getChildren().add(dialogPane);

        // You may want to use a timeline to remove the dialog after a certain time
        Duration dialogDuration = Duration.seconds(3);
        PauseTransition dialogRemoval = new PauseTransition(dialogDuration);
        dialogRemoval.setOnFinished(event -> {
            root.getChildren().remove(dialogPane);
        });
        dialogRemoval.play();
    }
}
