package com.example.invaders.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Sprite extends Rectangle {
        public boolean dead = false;
        public final String type;
        boolean isMoveLeft;
        boolean  isMoveRight;

      public  Sprite(int x, int y, int w, int h, String type, Color color){
            super(w,h,color);

            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }



    public void moveUp(){
        setTranslateY(getTranslateY() - 5);
    }


    public void moveDown(){
        setTranslateY(getTranslateY() + 5);
    }
    }



