package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGame;

/**
 * Created by ares on 27.07.17.
 */
public class ScrollMenu extends ScrollPane {
    public Table content;
    public Skin skinBlue;
    public Skin skinGray;
    public ScrollPaneStyle style;

    public ScrollMenu() {
        super(null);
        setDebug(true);
        initSkins();
        initStyle();
        initMenuContent();
    }


    private void initStyle() {
        style = new ScrollPaneStyle(
                skinBlue.getDrawable("window_03"),
                null,null,null,null);

        setStyle(style);

        setWidth((float) (MyGame.GAME_WIDTH/1.5));
        setHeight((float) (MyGame.GAME_HEIGHT/1.5));
        setOrigin(MyGame.GAME_WIDTH/2,MyGame.GAME_HEIGHT/2);
        setPosition((MyGame.GAME_WIDTH-getWidth())/2,
                (MyGame.GAME_HEIGHT-getHeight())/2-70);
    }

    private void initSkins() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("img/ui-blue.atlas"));
        skinBlue = new Skin(atlas);
        TextureAtlas atlasGray = new TextureAtlas(Gdx.files.internal("img/ui-gray.atlas"));
        skinGray = new Skin(atlasGray);
    }

    private void initMenuContent() {
        content = new Table();
        setWidget(content);

    }

}
