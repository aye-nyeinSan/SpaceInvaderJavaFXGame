package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;
import com.example.invaders.model.Bullet;
import com.example.invaders.model.Player;
import com.example.invaders.model.Sprite;

import java.util.List;
import java.util.stream.Collectors;


public class playerController {
    static Player player ;
    boolean isMoveLeft= false;
    boolean isMoveRight= false;
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
            System.out.println("player is collided with wall");
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
}
