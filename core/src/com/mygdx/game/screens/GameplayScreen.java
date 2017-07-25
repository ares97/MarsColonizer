package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.IClickCallback;
import com.mygdx.game.MyGame;
import com.mygdx.game.entites.Player;
import com.mygdx.game.ui.ClickOnPlayerButton;

/**
 * Created by ares on 25.07.17.
 */
public class GameplayScreen extends BasicScreen {
    private Image backgroundImg;
    private Player player;


    public GameplayScreen(MyGame myGame){
        super(myGame);
    }



    @Override
    protected void init() {
        initBackground();
        initPlayer();
        handleClickArea();

        // TODO add clouds and events under them
    }

    private void handleClickArea() {
        ClickOnPlayerButton clickOnPlayerButton = new ClickOnPlayerButton(new IClickCallback() {
            @Override
            public void onClick() {
                player.performClickEffect();
            }
        });
        stage.addActor(clickOnPlayerButton);
    }

    private void initPlayer() {
        player = new Player();
        stage.addActor(player);

    }

    private void initBackground() {
        backgroundImg = new Image(new Texture("img/backgroundImg2.png"));
        stage.addActor(backgroundImg);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update();

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();

    }

    private void update() {
        stage.act();
    }
}
