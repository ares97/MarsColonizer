package com.mygdx.game.entites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MyGame;

/**
 * Created by ares on 25.07.17.
 */
public class Player extends Image {
    private final static int PLAYER_WIDTH = 300;
    private final static int PLAYER_HEIGHT = 300;

    private final static int STARTING_X = MyGame.GAME_WIDTH /2 - PLAYER_WIDTH/2;
    private final static int STARTING_Y = MyGame.GAME_HEIGHT /2 - PLAYER_HEIGHT/3;


    public Player() {
        super(new Texture("img/mars.png"));

        setOrigin(MyGame.GAME_WIDTH / 2, MyGame.GAME_HEIGHT / 2);
        setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        setPosition(STARTING_X, STARTING_Y);
    }
}
