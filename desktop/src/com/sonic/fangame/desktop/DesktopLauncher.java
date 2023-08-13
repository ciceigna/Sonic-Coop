package com.sonic.fangame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sonic.fangame.SonicCoop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Sonic Co-op";
		config.foregroundFPS = 60;
		new LwjglApplication(new SonicCoop(), config);
	}
}
