package com.slowinski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.slowinski.IClickCallback;

/**
 * Created by ares on 27.07.17.
 */
public class MenuButton extends Button {

    public MenuButton(IClickCallback callback, String str) {
        super(getButtonStyle());
        init(callback);
        add(new Label(str, new Label.LabelStyle(new BitmapFont(), Color.GOLD)));
    }

    private static ButtonStyle getButtonStyle() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("img/ui-blue.atlas"));
        Skin skin = new Skin(atlas);
        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.down = skin.getDrawable("button_05");
        buttonStyle.up = skin.getDrawable("button_05");
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
