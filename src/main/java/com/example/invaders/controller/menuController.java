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
        boolean isSoundOff = this.soundBtn.isSelected();
        System.out.println(isSoundOff);
        if (!isSoundOff) {
            // Start the new game with background music
            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow(),isSoundOff);
        } else {
            // Start the new game without background music
            SpaceInvaderApp.playbackgroundSoundOff(); // Turn off background music
            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow(), isSoundOff);
        }
    }

    public void onQuit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }
    public void onSoundOff(ActionEvent event){
        if(this.soundBtn.isSelected())
        {   soundBtn.setText("Music On \uD83D\uDD0A ");
            SpaceInvaderApp.playbackgroundSoundOff();}
        else {
            soundBtn.setText("Music Off \uD83D\uDD07");
            SpaceInvaderApp.playbackgroundSoundOn();}

    }


    public void onStartOver(ActionEvent event) {
        SpriteController.closeGameOverScreen();
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.closeMainGameStage();
        SpriteController.RestartGame((Stage) ((Node) event.getSource()).getScene().getWindow(), true);

    }

    public void onHome(ActionEvent event) throws Exception {
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.closeMainGameStage();
        spaceInvaderApp.start((Stage) ((Node) event.getSource()).getScene().getWindow());

    }
}