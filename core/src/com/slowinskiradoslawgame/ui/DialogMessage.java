package com.slowinski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.slowinski.IClickCallback;
import com.slowinski.MyGame;

/**
 * Created by ares on 26.07.17.
 */
public class DialogMessage extends Dialog {
    public DialogMessage(IClickCallback callback) {
        super("", getDialogButtonStyle());

        init(callback);
    }

    public DialogMessage() {
        super("", getDialogButtonStyle());

        init();
    }

    private static WindowStyle getDialogButtonStyle() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("img/ui-blue.atlas"));
        Skin skin = new Skin(atlas);
        WindowStyle style = new WindowStyle();
        style.background = skin.getDrawable("textbox_01");
        style.titleFont = new BitmapFont();
        return style;
    }

    private void init() {
        setWidth(MyGame.GAME_WIDTH);
        setHeight(180);
        setPosition(330, 100);
    }

    private void init(final IClickCallback callback) {
        setWidth(MyGame.GAME_WIDTH);
        setHeight(180);
        setPosition(330, 275);
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                callback.onClick();
                remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void setText(String str, Color color) {
        text(new Label(str, new Label.LabelStyle(new BitmapFont(), color)));
    }
}
