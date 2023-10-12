package com.example.invaders;

import com.example.model.Sprite;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class SpaceInvaderApp extends Application {

    private Pane root = new Pane();


    private double t = 0;
    //player images
    private Image playerImage=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\player.png");

    //enemy images
    private Image enemyImage=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\alien.png");
    private Image enemyImage2=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\alien2.png");
    private Image enemyImage3=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\alien3.png");

    //bullet images
    private Image bulletImage=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\bomb.png");

    //explosion images
    private Image explosion_img=new Image("C:\\Users\\DELL\\Desktop\\SpaceInvader\\src\\main\\resources\\com\\example\\assets\\explo1.png");
    private Sprite explosion=new Sprite(0,0,explosion_img,"explosion");


    //creating player
    private Sprite player = new Sprite(300, 600,playerImage, "player");


    private Parent createContent(){
        root.setPrefSize(600,700);
        explosion.visibleProperty().set(false);

        root.getChildren().add(player);
        root.getChildren().add(explosion);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        timer.start();
        
        nextLevel();

        return root;

    }

    private void nextLevel() {
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i*100, 150,enemyImage,"enemy");
            Sprite s2= new Sprite(90 + i*100,200, enemyImage2,"enemy");
            Sprite s3= new Sprite(90 + i*100,250, enemyImage3,"enemy");
            root.getChildren().add(s);
            root.getChildren().add(s2);
            root.getChildren().add(s3);
        }
    }

    private List<Sprite> sprites(){
        return root.getChildren().stream().map(n->(Sprite)n).collect(Collectors.toList());
    }

    private void update(){
        t += 0.016;
        sprites().forEach(s -> {
            switch (s.type){
                case "enemybullet":
                    s.moveDown();
                    if(s.getBoundsInParent().intersects(player.getBoundsInParent())){
                        player.dead = true;
                        s.dead = true;
                        showExplosion(player);
                    }
                    break;
                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e->e.type.equals("enemy")).forEach(enemy->{
                        if(s.getBoundsInParent().intersects(enemy.getBoundsInParent())){
                            enemy.dead = true;
                            s.dead = true;
                            showExplosion(enemy);
                        }
                    });
                    break;
                case "enemy":
                    if(t>2){
                        if(Math.random()<0.3){
                            shoot(s);
                        }

                    }
                    break;

            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });
        if(t>2){
            t = 0;
        }
    }

    private void showExplosion(Sprite target) {
        explosion.setTranslateX(target.getTranslateX()-100);
        explosion.setTranslateY(target.getTranslateY()-100);
        explosion.visibleProperty().set(true);

        int explosionDuration = 500; // Adjust the duration as needed

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(explosionDuration),
                        event -> explosion.visibleProperty().set(false)
                )
        );

        timeline.setCycleCount(1); // Play the animation once
        timeline.play();
    }

    private void shoot(Sprite who){
        Sprite s = new Sprite((int)who.getTranslateX() + 20, (int)who.getTranslateY(), bulletImage,who.type+"bullet");
        root.getChildren().add(s);

    }


    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(e->{
            switch (e.getCode()){
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case SPACE:
                    shoot(player);
                    break;

            }
        });
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        System.out.println();
        launch(args);
    }
}
