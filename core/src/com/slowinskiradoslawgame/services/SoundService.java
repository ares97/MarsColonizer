package com.slowinskiradoslawgame.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.slowinskiradoslawgame.MyGame;

/**
 * Created by ares on 25.07.17.
 */

public class SoundService {
    public final static String MUTE_MUSIC = "com.mygdx.game.prefs.muteMusic";
    public final static String MUTE_SOUND = "com.mygdx.game.prefs.muteSound";
    public boolean muteSound;
    public boolean muteMusic;
    private Music bgMusic;
    private Sound clickSound;
    private Sound shotSound;
    private float bgMusicVolume;
    private Preferences prefs;

    public SoundService() {
        initAudio();
    }

    private void initAudio() {
        loadAudioDataFromPrefs();
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/bgMusic.ogg"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/click.ogg"));
        shotSound = Gdx.audio.newSound(Gdx.files.internal("audio/spaceShot.ogg"));
        bgMusicVolume = 0.6f;
    }

    private void loadAudioDataFromPrefs() {
        prefs = Gdx.app.getPreferences(MyGame.GAME_PREFS);
        muteSound = prefs.getBoolean(MUTE_SOUND);
        muteMusic = prefs.getBoolean(MUTE_MUSIC);
    }

    public void playMusic() {
        bgMusic.play();
        bgMusic.setLooping(true);
        if (!muteMusic) {
            bgMusic.setVolume(bgMusicVolume);
        } else {
            bgMusic.setVolume(0);
        }
    }

    public void playClick() {
        if (!muteSound) {
            clickSound.play(0.2f);
        }
    }

    public void disposeAudio() {
        bgMusic.dispose();
        clickSound.dispose();
        shotSound.dispose();
    }

    public void saveAudioToPrefs() {
        prefs.putBoolean(MUTE_MUSIC, muteMusic);
        prefs.putBoolean(MUTE_SOUND, muteSound);
        prefs.flush();
    }

    public void playShot(){
        if(!muteSound){
            shotSound.play(0.2f);
        }
    }

    public void setMuteSound(boolean mute) {
        muteSound = mute;
    }

    public void setMuteMusic(boolean mute) {
        muteMusic = mute;

        if (muteMusic) {
            bgMusic.setVolume(0.0f);
        } else {
            bgMusic.setVolume(bgMusicVolume);
        }
    }

}
