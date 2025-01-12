package com.slowinskiradoslawgame.controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.slowinskiradoslawgame.MyGame;
import com.slowinskiradoslawgame.entites.FlyingObject;
import com.slowinskiradoslawgame.entites.FlyingObjectType;

/**
 * Created by ares on 25.07.17.
 */
public class FlyingObjectsController {
    private MyGame game;
    private Stage stage;
    private int spawnTime;

    public FlyingObjectsController(MyGame myGame, Stage stage) {
        this.game = myGame;
        this.stage = stage;
        init();
    }

    private void init() {

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        addRandomFlyingObjectToStage();
                        randomizeSpawnTime();
                    }
                }, spawnTime);
            }
        }, 10, 10);
    }

    private void randomizeSpawnTime() {
        spawnTime = MathUtils.random(5, 25);
    }

    private void addRandomFlyingObjectToStage() {
        FlyingObject flyingObject = new FlyingObject(game, getRandomFlyingObjectType());
        stage.addActor(flyingObject);
        flyingObject.performFlyingAction();
    }

    private FlyingObjectType getRandomFlyingObjectType() {
        FlyingObjectType[] objectTypes = FlyingObjectType.values();
        int randomType = MathUtils.random(0, objectTypes.length - 1);

        return objectTypes[randomType];
    }
}
