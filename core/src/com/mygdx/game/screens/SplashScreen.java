package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGame;


/**
 * Created by ares on 25.07.17.
 */
public class SplashScreen extends BasicScreen {
    private Texture splashImg;

    public SplashScreen(final MyGame myGame){
        super(myGame);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                myGame.setScreen(new GameplayScreen(myGame));
            }
        },1);
    }

    @Override
    protected void init() {
        splashImg = new  Texture("img/splashImg.png");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(splashImg,MyGame.WIDTH/2-200,MyGame.HEIGHT/2-200,400,400);
        spriteBatch.end();
    }
}
