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
    private boolean soundOff;

//    @FXML
//    public void onNewGame(ActionEvent event){
//            SpaceInvaderApp spaceInvaderApp=new SpaceInvaderApp();
//        boolean isSoundOff = this.soundBtn.isSelected();
//        System.out.println(isSoundOff);
//        if (!isSoundOff) {
//            // Start the new game with background music
//            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow(),player);
//        } else {
//            // Start the new game without background music
//            SpaceInvaderApp.playbackgroundSoundOff(); // Turn off background music
//            spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow(),player);
//        }
//    }
@FXML
    public void onQuit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void onQuitGameConsole(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SpaceInvaderApp app = new SpaceInvaderApp();
        stage.close();
        app.stopGame(SpaceInvaderApp.getStage());
    }
    @FXML
    public Boolean onSoundOff(){
       soundOff= this.soundBtn.isSelected();
        if(soundOff)
        {   soundBtn.setText("Music On \uD83D\uDD0A ");
            SpaceInvaderApp.playbackgroundSoundOff();
            return true;
        }
        else {
            soundBtn.setText("Music Off \uD83D\uDD07");
            SpaceInvaderApp.playbackgroundSoundOn();
            return false;
        }

    }

@FXML
    public void onStartOver(ActionEvent event) throws Exception {
    menuController menuController = new menuController();
    boolean isSoundOff = menuController.soundOff;
        SpriteController.closeGameOverScreen();
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.closeMainGameStage();
        spaceInvaderApp.startGame((Stage) ((Node) event.getSource()).getScene().getWindow(),player, isSoundOff);

    }

    @FXML
    public void onHome(ActionEvent event) throws Exception {
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.closeMainGameStage();
        spaceInvaderApp.start((Stage) ((Node) event.getSource()).getScene().getWindow());

    }
    @FXML
    public void onChoosePlayer(ActionEvent event){
        try{
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
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
    @FXML
    public void addPlayer(MouseEvent event) throws Exception {
         menuController menuController = new menuController();
        boolean isSoundOff = menuController.soundOff;
        GamePlatform platform = new GamePlatform();
        ImageView clickedImageView = (ImageView) event.getSource();
        Image selectedImage = clickedImageView.getImage();
        Player player = platform.addingPlayer(root,selectedImage);
         System.out.println("Image selected");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SpaceInvaderApp spaceInvaderApp = new SpaceInvaderApp();
        spaceInvaderApp.startGame(stage, player,  isSoundOff);

    }


}