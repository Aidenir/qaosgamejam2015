package com.gamejam.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamejam.game.GameJam;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Game Jam";
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new GameJam(1920,1080), config);
	}
}
