package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.SplashScreen;
import com.mygdx.game.services.ScoreService;
import com.mygdx.game.services.SoundService;

public class MyGame extends Game {
    public final static String GAME_PREFS = "com.mygdx.game.prefs";

    public final static int GAME_WIDTH = 480;
    public final static int GAME_HEIGHT = 700;

    public final static String TITLE = "Mars Colonizer";

    public SoundService soundService;
    public ScoreService scoreService;

    public AdHandler handler;
    boolean toggle;

    public MyGame(AdHandler handler){
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

    public void showVideoAd(){
        handler.showAds(true);
    }
}
