package com.gamejam.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameJam extends Game {
	
	// Reuse these!
	public SpriteBatch batch;
	public BitmapFont font;
	
	public int screenWidth;
	public int screenHeight;
	
	public GameJam(int width, int height){
		this.screenWidth = width;
		this.screenHeight = height;
	}

	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

	}

	public void render () {

	}
	
	public void dispose(){
		batch.dispose();
		font.dispose();
	}

}
