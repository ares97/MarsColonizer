package com.slowinski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.slowinski.IClickCallback;

/**
 * Created by ares on 27.07.17.
 */
public class OptionsButton extends Button {

    public OptionsButton(IClickCallback callback) {
        super(getButtonStyle());
        init(callback);
    }

    private static ButtonStyle getButtonStyle() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("img/ui-blue.atlas"));
        Skin skin = new Skin(atlas);
        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.down = skin.getDrawable("icon_tools");
        buttonStyle.up = skin.getDrawable("icon_tools");
        return buttonStyle;
    }

    private void init(final IClickCallback callback) {
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                callback.onClick();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
