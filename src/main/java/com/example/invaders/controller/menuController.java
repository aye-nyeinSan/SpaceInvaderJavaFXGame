package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;
import com.example.invaders.model.Player;
import com.example.invaders.view.GamePlatform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.invaders.SpaceInvaderApp.*;
import static com.example.invaders.controller.playerController.player;

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
            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow(),player,isSoundOff);
        } else {
            // Start the new game without background music
            SpaceInvaderApp.playbackgroundSoundOff(); // Turn off background music
            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow(),player, isSoundOff);
        }
    }

    @FXML
    public void onHelp(ActionEvent event){
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(SpriteController.class.getResource("/Help.fxml"));
            Scene mainMenuScene = new Scene(fxmlLoader.load(), 600, 502);
            Stage helpStage=new Stage();
            helpStage.setTitle("Player");
            helpStage.setScene(mainMenuScene);
            helpStage.setResizable(false);
            helpStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onQuit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


@FXML
    public void onHome(ActionEvent event) throws Exception {
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.closeMainGameStage();
        spaceInvaderApp.start((Stage) ((Node) event.getSource()).getScene().getWindow());

    }
    @FXML
    public void addPlayer(MouseEvent event) throws Exception {
        GamePlatform platform = new GamePlatform();
        ImageView clickedImageView = (ImageView) event.getSource();
        Image selectedImage = clickedImageView.getImage();
        Player player = platform.addingPlayer(root,selectedImage);
         System.out.println("Image selected");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.startGame(stage, player,true);

    }

@FXML
    public void onQuitGameConsole(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SpaceInvaderApp app = new SpaceInvaderApp();
        stage.close();
        app.stopGame(SpaceInvaderApp.getStage());
    }

    public void onSoundOff(ActionEvent event){
        if(this.soundBtn.isSelected())
        {   soundBtn.setText("Music On \uD83D\uDD0A ");
            SpaceInvaderApp.playbackgroundSoundOff();}
        else {
            soundBtn.setText("Music Off \uD83D\uDD07");
            SpaceInvaderApp.playbackgroundSoundOn();}

    }
@FXML
    public void onStartOver(ActionEvent event) {
        SpriteController.closeGameOverScreen();
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.closeMainGameStage();
        SpriteController.RestartGame((Stage) ((Node) event.getSource()).getScene().getWindow(), true);

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