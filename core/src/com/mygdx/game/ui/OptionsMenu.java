package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by ares on 27.07.17.
 */
public class OptionsMenu extends Actor {
    private ScrollMenu menu;

    public OptionsMenu(){

        menu = new ScrollMenu();
        menu.setHeight(200);
        initContent();
    }

    private void initContent() {
        menu.content.add(new Image(menu.skin.getDrawable("icon_sound_on")));
        menu.content.add(new Image(menu.skin.getDrawable("icon_music")));
    }
}
