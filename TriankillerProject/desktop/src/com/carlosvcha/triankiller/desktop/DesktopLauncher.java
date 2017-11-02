package com.carlosvcha.triankiller.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.carlosvcha.triankiller.Scene;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.resizable = false;
            config.width = 640;
            config.height = 480;
            config.fullscreen = false;
            new LwjglApplication(new Scene(), config);
	}
}
