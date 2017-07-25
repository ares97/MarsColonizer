package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MarsColonizer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MarsColonizer(), config);

		config.height= MarsColonizer.HEIGHT;
		config.width= MarsColonizer.WIDTH;
		config.title = MarsColonizer.TITLE;
		config.resizable=false;
	}
}
