package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.SplashScreen;
import com.mygdx.game.services.SoundService;

public class MyGame extends Game {
    public final static int GAME_WIDTH = 480;
    public final static int GAME_HEIGHT = 700;

    public final static String TITLE = "Mars Colonizer";

    public SoundService soundService;


    @Override
    public void create() {
        initAudio();
        setScreen(new SplashScreen(this));
    }

    private void initAudio() {
        soundService = new SoundService();
        soundService.playBgMusic();
    }
}
