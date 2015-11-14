package com.gamejam.game;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

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
		//get a preferences instance
		Preferences prefs = Gdx.app.getPreferences("gamejame");
		Json json = new Json();
		highScore = null;
		highScore = json.fromJson(ArrayList.class, prefs.getString("highscore"));
		

		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
		this.setScreen(new IntroScreen(this));
		if(highScore == null) this.highScore = new ArrayList<Integer>();
	}


	public void registerHighScore(int score){
		highScore.add(score);
		Collections.sort(highScore);
		if(highScore.size() > 3){
			highScore.remove(0);
		}

		Collections.reverse(highScore);
		
		//get a preferences instance
		Preferences prefs = Gdx.app.getPreferences("gamejame");
		Json json = new Json();
		prefs.putString("highscore", json.toJson(highScore));
		//persist preferences
		prefs.flush();

		
	}
	
	public void render () {
		super.render();

	}
	
	public void dispose(){
		batch.dispose();
		font.dispose();
	}

}
