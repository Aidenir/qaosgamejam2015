package com.gamejam.game;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameJam extends Game {
	
	// Reuse these!
	public SpriteBatch batch;
	public BitmapFont font;
	
	public int screenWidth;
	public int screenHeight;
	
	public ArrayList<Integer> highScore;
	
	public GameJam(int width, int height){
		this.screenWidth = width;
		this.screenHeight = height;
	}

	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
		this.highScore = new ArrayList<Integer>();
	}


	public void registerHighScore(int score){
		Collections.sort(highScore);
		if(score > highScore.get(0)){
			highScore.remove(0);
			highScore.add(score);
		}
		Collections.sort(highScore);
		Collections.reverse(highScore);
		
	}
	
	public void render () {
		super.render();

	}
	
	public void dispose(){
		batch.dispose();
		font.dispose();
	}

}
