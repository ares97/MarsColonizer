package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.SplashScreen;

public class MyGame extends Game {
    public final static int GAME_WIDTH = 480;
    public final static int GAME_HEIGHT = 700;

    public final static String TITLE = "Mars Colonizer";



    @Override
    public void create() {
        setScreen(new SplashScreen(this));
    }
}
