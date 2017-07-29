package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AdHandler;
import com.mygdx.game.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGame(new AdHandler() {
			@Override
			public void showAds(boolean show) {

			}
		}), config);

		config.height= MyGame.GAME_HEIGHT;
		config.width= MyGame.GAME_WIDTH;
		config.title = MyGame.TITLE;
		config.resizable=false;
	}
}
