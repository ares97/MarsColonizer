package com.mygdx.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.IClickCallback;
import com.mygdx.game.MyGame;
import com.mygdx.game.ui.DialogMessage;

/**
 * Created by ares on 25.07.17.
 */
public class ScoreService {
    public final static String SCORE_BASALT = "com.mygdx.game.prefs.scoreBasalt";
    public final static String BASALT_PER_CLICK = "com.mygdx.game.prefs.basaltPerClick";
    public final static String SCORE_PASSIVE_BASALT = "com.mygdx.game.prefs.scorePassiveBasalt";
    public final static String SCORE_DIAMONDS = "com.mygdx.game.prefs.scoreDiamonds";
    public final static String LAST_ACTIVITY = "com.mygdx.game.prefs.lastActivity";
    public final static String OFFLINE_PASSIVE_MULTIPLY = "com.mygdx.game.prefs.offlinePassiveMultiply";

    public final float STARTING_OFFLINE_INCOME_MULTIPLY = 0.5f;
    public final int STARTING_BASALT_PER_CLICK = 1;

    private long basalt;
    private long passiveBasalt;
    private int basaltPerClick;
    private long diamonds;
    private float offlinePassiveIncomeMultiple;
    private long offlineIncome;

    private void workOutPassiveIncomeWhileOffline() {
        long lastActivity = prefs.getLong(LAST_ACTIVITY);
        offlineIncome = TimeUtils.timeSinceMillis(lastActivity) / 1000; // mili -> sec
        offlineIncome *= offlinePassiveIncomeMultiple;
        if(passiveBasalt > 0) {
            offlineIncome *= passiveBasalt;
        }
    }

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
        workOutPassiveIncomeWhileOffline();
    }

    private void initPrefs() {
        prefs = Gdx.app.getPreferences(MyGame.GAME_PREFS);
    }

    private void loadScoreDataFromPrefs() {
        basalt = prefs.getLong(SCORE_BASALT);
        passiveBasalt = prefs.getLong(SCORE_PASSIVE_BASALT);
        basaltPerClick = prefs.getInteger(SCORE_PASSIVE_BASALT);
        diamonds = prefs.getLong(SCORE_DIAMONDS);
        offlinePassiveIncomeMultiple = prefs.getFloat(OFFLINE_PASSIVE_MULTIPLY);

        if (basaltPerClick <= 0)
            basaltPerClick = STARTING_BASALT_PER_CLICK;
        if (offlinePassiveIncomeMultiple <= 0)
            offlinePassiveIncomeMultiple = STARTING_OFFLINE_INCOME_MULTIPLY;

    }

    public void saveScoreDataToPrefs() {
        prefs.putLong(SCORE_BASALT, basalt);
        prefs.putLong(SCORE_PASSIVE_BASALT, passiveBasalt);
        prefs.putInteger(BASALT_PER_CLICK, basaltPerClick);
        prefs.putLong(SCORE_DIAMONDS, diamonds);
        prefs.putLong(LAST_ACTIVITY, TimeUtils.millis());
        prefs.putFloat(OFFLINE_PASSIVE_MULTIPLY, offlinePassiveIncomeMultiple);

        prefs.flush();
    }

    public void addBasaltForClick() {
        basalt += basaltPerClick;
    }

    public void addToPassiveBasalt(int amount) {
        passiveBasalt += amount;
    }

    public void addToBasalt(long i) {
        basalt += i;
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

    public long getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(long diamonds) {
        this.diamonds = diamonds;
    }

    public void addToDiamonds(int i) {
        diamonds += i;
    }

    public float getOfflinePassiveIncomeMultiple() {
        return offlinePassiveIncomeMultiple;
    }

    public void setOfflinePassiveIncomeMultiple(float offlinePassiveIncomeMultiple) {
        this.offlinePassiveIncomeMultiple = offlinePassiveIncomeMultiple;
    }

    public long getOfflineIncome() {
        return offlineIncome;
    }

    public void setOfflineIncome(long offlineIncome) {
        this.offlineIncome = offlineIncome;
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public void setPrefs(Preferences prefs) {
        this.prefs = prefs;
    }
}
