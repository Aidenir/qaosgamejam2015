package com.gamejam.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamejam.game.GameJam;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Game Jam";
		config.width = 1024;
		config.height = 576;
		new LwjglApplication(new GameJam(config.width,config.height), config);
	}
}
