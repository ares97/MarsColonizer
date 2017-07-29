package com.mygdx.game.entites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.IClickCallback;
import com.mygdx.game.MyGame;
import com.mygdx.game.ui.DialogMessage;

/**
 * Created by ares on 25.07.17.
 */
public class FlyingObject extends Image {
    private static final String ASTRONAUT = "img/astronaut.png";
    private static final String METEOR = "img/meteor.png";
    private static final String UFO = "img/ufo.png";
    private final static int WIDTH = 75;
    private final static int HEIGHT = 75;
    private final static int STARTING_X_1 = 0;
    private final static int STARTING_X_2 = MyGame.GAME_WIDTH;
    private final static int STARTING_Y = -100;
    private MyGame myGame;
    private FlyingObjectType objectType;
    private int startingX;

    public FlyingObject(MyGame game, FlyingObjectType type) {
        super(new Texture(getFlyingObjectType(type)));
        this.myGame = game;
        this.objectType = type;

        this.setOrigin(WIDTH / 2, HEIGHT / 2);
        this.setSize(WIDTH, HEIGHT);

        startingX = MathUtils.randomBoolean() ? STARTING_X_1 : STARTING_X_2;
        this.setPosition(startingX, STARTING_Y);

        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                reactOnClick();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private static String getFlyingObjectType(FlyingObjectType type) {
        if (FlyingObjectType.UFO.equals(type)) {
            return UFO;

        } else if (FlyingObjectType.ASTRONAUT.equals(type)) {
            return ASTRONAUT;

        } else if (FlyingObjectType.METEOR.equals(type)) {
            return METEOR;
        }

        return "";
    }

    private void reactOnClick() {
        final DialogMessage dialogMssg = new DialogMessage(new IClickCallback() {
            @Override
            public void onClick() {
            }
        });

        if (FlyingObjectType.ASTRONAUT.equals(objectType)) {
            myGame.scoreService.addToPassiveBasalt(1);
            dialogMssg.setText("That's looks like You gonna have slave!\n(+1 basalt/sec)", Color.CORAL);
        } else if (FlyingObjectType.UFO.equals(objectType)) {
            myGame.scoreService.addToDiamonds(1);
            dialogMssg.setText("After breaking space ship apart you've found diamond in it!\n(+1 diamond)", Color.GOLD);
        } else if (FlyingObjectType.METEOR.equals(objectType)) {
            myGame.scoreService.addToBasalt(75);
            dialogMssg.setText("Just more basalt...\n(+75 basalt)", Color.GRAY);
        }

        getStage().addActor(dialogMssg);
        this.remove();
    }

    public void performFlyingAction() {
        int xSign = 0;
        if (startingX == STARTING_X_1) {
            xSign = 1;
        } else {
            xSign = -1;
        }

        float time1 = MathUtils.random(0.6f, 2.5f);
        float time2 = MathUtils.random(0.6f, 2.5f);

        int randomYEffect = MathUtils.random(-100, 500);

        Action a = Actions.parallel(
                Actions.moveBy(xSign * 300 + (MathUtils.random(-200, 200)), 200 + randomYEffect, time1),
                Actions.rotateBy(360, time1)
        );

        Action b = Actions.parallel(
                Actions.moveBy(xSign * -500 + (MathUtils.random(-200, 200)), 900, time2),
                Actions.rotateBy(360, time2)
        );

        Action c = Actions.run(new Runnable() {

            @Override
            public void run() {
                FlyingObject.this.remove();
            }
        });

        this.addAction(Actions.sequence(a, b, c));
    }
}
