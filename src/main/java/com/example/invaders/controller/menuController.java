package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.example.invaders.controller.playerController.player;

public class menuController {

    @FXML
    public void onNewGame(ActionEvent event){
            SpaceInvaderApp spaceInvaderApp=new SpaceInvaderApp();
            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    public void onQuit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }

    @FXML
    public void onChoosePlayer(ActionEvent event){
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(SpriteController.class.getResource("/choose-player.fxml"));
            Scene mainMenuScene = new Scene(fxmlLoader.load(), 600, 400);
            Stage choosePlayerStage=new Stage();
            choosePlayerStage.setTitle("Player");
            choosePlayerStage.setScene(mainMenuScene);
            choosePlayerStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



}