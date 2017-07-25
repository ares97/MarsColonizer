package com.mygdx.game.entites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MyGame;

/**
 * Created by ares on 25.07.17.
 */
public class Player extends Image {
    public final static int PLAYER_WIDTH = 300;
    public final static int PLAYER_HEIGHT = 275;

    public final static int STARTING_X = MyGame.GAME_WIDTH /2 - PLAYER_WIDTH/2;
    public final static int STARTING_Y = MyGame.GAME_HEIGHT /2 - PLAYER_HEIGHT/3;


    public Player() {
        super(new Texture("img/mars.png"));

        setOrigin(MyGame.GAME_WIDTH / 2, MyGame.GAME_HEIGHT / 2);
        setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        setPosition(STARTING_X, STARTING_Y);
    }

    public void performClickEffect(){
        int xMoveAmount = MathUtils.random(-20,20);
        int yMoveAmount = MathUtils.random(-7,7);
        float moveDuration = 1.0f;

        SequenceAction moveAction = new SequenceAction(
                Actions.moveBy(xMoveAmount,yMoveAmount,moveDuration, Interpolation.bounceOut),
                Actions.moveBy(-xMoveAmount,-yMoveAmount,moveDuration, Interpolation.bounce)
        );

        addAction(moveAction);
    }
}
