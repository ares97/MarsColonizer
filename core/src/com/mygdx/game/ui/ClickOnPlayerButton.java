package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.IClickCallback;
import com.mygdx.game.entites.Player;

/**
 * Created by ares on 25.07.17.
 */
public class ClickOnPlayerButton extends Button {
    public ClickOnPlayerButton(IClickCallback callback) {
        super(new ButtonStyle());
        init(callback);
    }

    private void init(final IClickCallback callback) {
        setWidth(Player.PLAYER_WIDTH);
        setHeight(Player.PLAYER_HEIGHT);

        setX(Player.STARTING_X);
        setY(Player.STARTING_Y);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                callback.onClick();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
