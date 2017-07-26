package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.IClickCallback;
import com.mygdx.game.MyGame;
import com.mygdx.game.controllers.FlyingObjectsController;
import com.mygdx.game.entites.Player;
import com.mygdx.game.ui.ClickOnPlayerButton;
import com.mygdx.game.ui.DialogMessage;
import com.mygdx.game.ui.GameLabel;

/**
 * Created by ares on 25.07.17.
 */
public class GameplayScreen extends BasicScreen {
    private Image backgroundImg;
    private Player player;
    private FlyingObjectsController flyingObjectController;
    private GameLabel scoreLabel;


    public GameplayScreen(MyGame myGame) {
        super(myGame);
    }


    @Override
    protected void init() {
        initBackground();
        initPlayer();
        handleClickArea();
        initWelcomeDialog();
        initScoreLabel();
        initFlyingObjectController();
        initPassiveIncome();
        // TODO add clouds and events under them
    }


    private void initWelcomeDialog() {

        if (myGame.scoreService.getOfflineIncome() > 0) {
            DialogMessage dialogMessage = new DialogMessage(new IClickCallback() {
                @Override
                public void onClick() {
                    myGame.scoreService.addToBasalt(myGame.scoreService.getOfflineIncome());
                }
            });
            dialogMessage.setText("OFFLINE BA$ALT INCOME: " + myGame.scoreService.getOfflineIncome() + "!\n" +
                    "Watch ad to TRIPLE your offline income.", Color.GOLD);

            stage.addActor(dialogMessage);
        }
    }

    private void initPassiveIncome() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                myGame.scoreService.addToBasalt(myGame.scoreService.getPassiveBasalt());
            }
        }, 0, 1f, Integer.MAX_VALUE);
    }


    private void initScoreLabel() {
        scoreLabel = new GameLabel(40, 650);
        stage.addActor(scoreLabel);
    }

    private void initFlyingObjectController() {
        flyingObjectController = new FlyingObjectsController(myGame, stage);

    }

    private void handleClickArea() {
        ClickOnPlayerButton clickOnPlayerButton = new ClickOnPlayerButton(new IClickCallback() {
            @Override
            public void onClick() {
                player.performClickEffect();
                myGame.soundService.playClick();
                myGame.scoreService.addBasaltForClick();
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
        scoreLabel.setText("Basalt:  " + myGame.scoreService.getBasalt() +
                "\n" + myGame.scoreService.getPassiveBasalt() + " basalt/sec" +
                "\nDiamonds: " + myGame.scoreService.getDiamonds());
        stage.act();
    }

}
