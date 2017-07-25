package com.mygdx.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.MyGame;

/**
 * Created by ares on 25.07.17.
 */
public class ScoreService {
    public final static String SCORE_BASALT = "com.mygdx.game.prefs.scoreBasalt";
    public final static String SCORE_PASSIVE_BASALT = "com.mygdx.game.prefs.scorePassiveBasalt";
    public final static String SCORE_DIAMONDS = "com.mygdx.game.prefs.scoreDiamond";

    public long basalt;
    public long passiveBasalt;

    private Preferences prefs;


    public ScoreService() {
        initPrefs();
        loadScoreDataFromPrefs();
    }

    private void initPrefs() {
        prefs = Gdx.app.getPreferences(MyGame.GAME_PREFS);
    }

    private void loadScoreDataFromPrefs() {
        basalt = prefs.getInteger(SCORE_BASALT);
        passiveBasalt = prefs.getInteger(SCORE_PASSIVE_BASALT);
    }

    public void saveScoreDataToPrefs() {
        prefs.putLong(SCORE_BASALT, basalt);
        prefs.putLong(SCORE_PASSIVE_BASALT, passiveBasalt);

        prefs.flush();
    }
}
