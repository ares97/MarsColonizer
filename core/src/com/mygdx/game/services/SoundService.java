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
    private float bgMusicVolume;
    public boolean muteSound;
    public boolean muteMusic;

    public SoundService() {
        initAudio();
    }

    private void initAudio() {
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/bgMusic.ogg"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/click.ogg"));
        bgMusicVolume = 0.7f;
    }

    public void playMusic() {
        muteMusic=false;
        bgMusic.play();
        bgMusic.setLooping(true);
        bgMusic.setVolume(bgMusicVolume);
    }

    public void playClick() {
        if (!muteSound) {
            clickSound.setVolume(clickSound.play(), 0.2f);
        }
    }

    public void disposeAudio() {
        bgMusic.dispose();
        clickSound.dispose();
    }

    public void setMuteSound(boolean mute) {
        muteSound = mute;
    }

    public void setMuteMusic(boolean mute)
    {
        muteMusic = mute;

        if(muteMusic){
            bgMusic.setVolume(0.0f);
        }
        else{
            bgMusic.setVolume(bgMusicVolume);
        }
    }


}
