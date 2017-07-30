package com.slowinski.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by ares on 26.07.17.
 */
public class GameLabel extends Label {
    public GameLabel(int x, int y) {
        super("Basalt: 666", getLabelStyle());
        setX(x);
        setY(y);
    }


    private static LabelStyle getLabelStyle() {
        LabelStyle style = new LabelStyle();
        style.font = new BitmapFont();
        return style;
    }
}
