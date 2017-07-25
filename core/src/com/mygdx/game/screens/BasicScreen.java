package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGame;

/**
 * Created by ares on 25.07.17.
 */
public abstract class BasicScreen implements Screen {

    protected MyGame myGame;

    protected Stage stage;
    protected OrthographicCamera camera;
    protected SpriteBatch spriteBatch;

    public BasicScreen(MyGame myGame){
        this.myGame = myGame;

        createCamera();

        stage = new Stage(new StretchViewport(MyGame.GAME_WIDTH, MyGame.GAME_HEIGHT,camera));
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();

        init();
    }

    protected abstract void init();

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGame.GAME_WIDTH, MyGame.GAME_HEIGHT);
        camera.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    protected void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        myGame.scoreService.saveScoreDataToPrefs();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        myGame.dispose();
    }
}
