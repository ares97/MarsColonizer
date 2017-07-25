package com.mygdx.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by ares on 25.07.17.
 */

public class SoundService {
    private Music bgMusic;
    private Sound clickSound;
    private Sound bookSound;
    private Sound moneySound;


    public SoundService() {
        initAudio();
    }

    private void initAudio() {
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/bgMusic.ogg"));

    }

    public void playBgMusic() {
        bgMusic.play();
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.5f);
    }


}
