package com.mygdx.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.MyGame;

/**
 * Created by ares on 25.07.17.
 */
public class ScoreService {
    public final static String SCORE_BASALT = "com.mygdx.game.prefs.scoreBasalt";
    public final static String BASALT_PER_CLICK = "com.mygdx.game.prefs.basaltPerClick";
    public final static String SCORE_PASSIVE_BASALT = "com.mygdx.game.prefs.scorePassiveBasalt";

    private long basalt;
    private long passiveBasalt;
    private int basaltPerClick;

    public int getBasaltPerClick() {
        return basaltPerClick;
    }

    public void setBasaltPerClick(int basaltPerClick) {
        this.basaltPerClick = basaltPerClick;
    }

    private Preferences prefs;


    public ScoreService() {
        initPrefs();
        loadScoreDataFromPrefs();
    }

    private void initPrefs() {
        prefs = Gdx.app.getPreferences(MyGame.GAME_PREFS);
    }

    private void loadScoreDataFromPrefs() {
        basalt = prefs.getLong(SCORE_BASALT);
        passiveBasalt = prefs.getLong(SCORE_PASSIVE_BASALT);
        basaltPerClick = prefs.getInteger(SCORE_PASSIVE_BASALT);

        if(basaltPerClick <= 0)
            basaltPerClick = 1;
    }

    public void saveScoreDataToPrefs() {
        prefs.putLong(SCORE_BASALT, basalt);
        prefs.putLong(SCORE_PASSIVE_BASALT, passiveBasalt);
        prefs.putInteger(BASALT_PER_CLICK, basaltPerClick);

        prefs.flush();
    }

    public void addBasaltForClick() {
        basalt += basaltPerClick;
    }

    public void addToPassiveBasalt(int amount) {
        passiveBasalt += passiveBasalt;
    }


    // getters and setters ---------------

    public long getBasalt() {
        return basalt;
    }

    public void setBasalt(long basalt) {
        this.basalt = basalt;
    }

    public long getPassiveBasalt() {
        return passiveBasalt;
    }

    public void setPassiveBasalt(long passiveBasalt) {
        this.passiveBasalt = passiveBasalt;
    }
}
