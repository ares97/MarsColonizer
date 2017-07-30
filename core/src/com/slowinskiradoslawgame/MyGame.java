package com.slowinski;

import com.badlogic.gdx.Game;
import com.slowinski.screens.SplashScreen;
import com.slowinski.services.ScoreService;
import com.slowinski.services.SoundService;

public class MyGame extends Game {
    public final static String GAME_PREFS = "com.mygdx.game.prefs";

    public final static int GAME_WIDTH = 480;
    public final static int GAME_HEIGHT = 700;

    public final static String TITLE = "Mars Colonizer";
    public static boolean adShown = false;
    public SoundService soundService;
    public ScoreService scoreService;
    public AdHandler handler;

    public MyGame(AdHandler handler) {
        this.handler = handler;
    }


    @Override
    public void create() {
        initAudio();
        initScoreService();
        setScreen(new SplashScreen(this));
    }

    private void initScoreService() {
        scoreService = new ScoreService();
    }

    private void initAudio() {
        soundService = new SoundService();
        soundService.playMusic();
    }

    public void showVideoAd() {
        handler.showAds(true);
    }
}
