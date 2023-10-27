package com.example.invaders.controller;


import com.example.invaders.exception.exceptionHandle;
import com.example.invaders.model.Bullet;
import com.example.invaders.model.Player;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.invaders.SpaceInvaderApp.*;


public class playerController {
    static Player player;
    static ArrayList<Integer> previousScore = new ArrayList<Integer>();
    public static boolean isMoveLeft = false;
    static boolean isMoveRight = false;
    public static long lastPlayerShotTime = 0;

    public static final long PLAYER_SHOOT_COOLDOWN = 500_000_000; // 0.5 seconds (adjust as needed)
    static Logger logger = LogManager.getLogger(playerController.class);
    static exceptionHandle exception = new exceptionHandle();

    public playerController(Player player) {

        this.player = player;

    }

    public void moveLeft() {
        isMoveLeft = true;
        isMoveRight = false;

        if (isMoveLeft) {
            player.setTranslateX(player.getTranslateX() - 5);
        }


    }

    public void moveRight() {
        isMoveLeft = false;
        isMoveRight = true;
        if (isMoveRight) {
            player.setTranslateX(player.getTranslateX() + 5);

        }
    }

    public static Player getPlayer() {
        return player;
    }


    public static Boolean checkCollision() {

        if (player.getTranslateX() >= 560) {
            player.setTranslateX(560);
            exception.showTalkingDialog("I've hit \n the right wall",player,exceptionPane,"right" );
            logger.debug("Dialog textbox is shown");
            isMoveRight=false;
          return false;
        } else if (player.getTranslateX() <= 0) {
            player.setTranslateX(0);
            exception.showTalkingDialog("I've hit \n the left wall",player,exceptionPane,"left" );
            logger.debug("Dialog textbox is shown");
            isMoveLeft = false;
            return false;
        }
        return true;
    }

    public void shoot() {
        Bullet.shoot(player);
    }

    public void shootSpecial() {
        Bullet.shootSpecial(player);
    }

    public static void respawn() {
        root.getChildren().remove(player);
        player.setTranslateX(300);
        player.setTranslateY(580);
        player.setDead(false);

        if (player.getCurrentChance() <= 3) {
            player.setChances(3 - player.getCurrentChance());
            player.setCurrentChance(player.getCurrentChance() + 1);
        }

        if (player.getHealth() >= 0) {
            player.setHealth(100 / player.getCurrentChance());
        }
        isMoveLeft = false;
        isMoveRight = false;

        root.getChildren().add(player);
        logger.warn("Player respawned!");
    }



    public static int ShowPreviousScore() {
        int previous = previousScore.get(previousScore.size() - 2);
        return previous;
    }


}