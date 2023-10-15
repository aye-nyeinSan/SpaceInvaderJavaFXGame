package com.example.invaders.controller;

import com.example.invaders.model.Bullet;
import com.example.invaders.model.Player;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.invaders.SpaceInvaderApp.root;

public class playerController {
    static Player player ;
    int score=0;
    static boolean isMoveLeft= false;
    static boolean isMoveRight= false;
    public static long lastPlayerShotTime = 0;
    public static final long PLAYER_SHOOT_COOLDOWN = 500_000_000; // 0.5 seconds (adjust as needed)


    public playerController(Player player) {
        this.player = player;
    }

    public void moveLeft(){
        isMoveLeft = true;
        isMoveRight = false;
        if(isMoveLeft){
            player.setTranslateX(player.getTranslateX() - 5);
        }

    }
    public void moveRight(){
        isMoveLeft = false;
        isMoveRight = true;
        if(isMoveRight){
            player.setTranslateX(player.getTranslateX() + 5);

        }
    }
    public static Player getPlayer() {
        return player;
    }


    public void checkCollision() {
        if (player.getTranslateX()>= 560)
        {
            // System.out.println("player is collided with wall");
            player.setTranslateX(560);
            moveLeft();
        } else if (player.getTranslateX()<=0) {
            player.setTranslateX(0);
            moveRight();

        }
    }

    public void shoot() {
        Bullet.shoot(player);
    }

    public void shootSpecial() {
        Bullet.shootSpecial(player);
    }

    public static void respawn(){
        player.setTranslateX(300);
        player.setTranslateY(600);
        player.setDead(false);

        if( player.getCurrentChance()<=3){
            player.setCurrentChance(player.getCurrentChance()+1);
            player.setChances(3- player.getCurrentChance());
        }

        if(player.getHealth()>=0){
            player.setHealth(100/player.getCurrentChance());
        }
        isMoveLeft = false;
        isMoveRight = false;
        root.getChildren().add(player);
    }
    public static void refresh(){

        Timer disappearTimer = new Timer();
        disappearTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                disappearTimer.cancel();
            }
        }, 10000); // 1000 milliseconds = 1 second
    }
}
