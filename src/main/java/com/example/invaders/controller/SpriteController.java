package com.example.invaders.controller;

import com.example.invaders.SpaceInvaderApp;

import com.example.invaders.model.Sprite;

import java.util.List;
import java.util.stream.Collectors;

public class SpriteController {
   static List<Sprite> sprites(){

       return SpaceInvaderApp.getRoot().getChildren().stream().map(n->(Sprite)n).collect(Collectors.toList());
    }

}
