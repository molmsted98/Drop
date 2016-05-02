package com.tsuruta.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tsuruta.drop.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop";
		config.height = 1080;
		config.width = 1920;
		config.resizable = false;
		config.fullscreen = true;
		new LwjglApplication(new Game(), config);
	}
}
