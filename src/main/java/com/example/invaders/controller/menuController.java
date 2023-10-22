package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

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
    public void onStartOver(ActionEvent event){
    }

    @FXML
    public void onHome(ActionEvent event){

    }



}