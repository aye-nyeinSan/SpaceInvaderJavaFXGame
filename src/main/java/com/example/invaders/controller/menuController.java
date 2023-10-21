package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;





public class menuController {

    @FXML
    ToggleButton soundBtn;

    @FXML
    public void onNewGame(ActionEvent event){
            SpaceInvaderApp spaceInvaderApp=new SpaceInvaderApp();
            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow());

    }

    public void onQuit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }
    public void onSoundOff(ActionEvent event){
        if(this.soundBtn.isSelected())
        {   soundBtn.setText("Sound On \uD83D\uDD0A ");
            SpaceInvaderApp.playbackgroundSoundOff();}
        else {
            soundBtn.setText("Sound Off \uD83D\uDD07");
            SpaceInvaderApp.playbackgroundSoundOn();}

    }

}