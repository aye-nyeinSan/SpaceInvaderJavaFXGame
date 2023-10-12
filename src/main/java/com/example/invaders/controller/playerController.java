package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;
import com.example.invaders.model.Bullet;
import com.example.invaders.model.Player;
import com.example.invaders.model.Sprite;
import static com.example.invaders.controller.SpriteController.sprites;

public class playerController {
    static Player player ;
    boolean isMoveLeft= false;
    boolean isMoveRight= false;

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

    public void update() {

        sprites().forEach(s -> {
            switch (s.type){
                case "playerbullet":
                      s.moveUp();
        sprites().stream().filter(e->e.type.equals("enemy")).forEach(enemy->{
            if(s.getBoundsInParent().intersects(enemy.getBoundsInParent()))
            {
                enemy.dead = true;
                s.dead = true;
            }
        });
        }
    });
        SpaceInvaderApp.getRoot().getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

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
}
