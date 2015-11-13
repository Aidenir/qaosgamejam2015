package com.gamejam.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.gamejam.game.GameJam;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1024, 768);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new GameJam(1024,768);
        }
}