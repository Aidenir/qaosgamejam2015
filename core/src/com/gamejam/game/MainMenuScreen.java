package com.gamejam.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen {
	// Constants
	final private GameJam game;
	
	private OrthographicCamera camera;
	float animation = 0;
	
	public MainMenuScreen(GameJam game) {
		this.game = game;
		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, game.screenWidth,game.screenHeight);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		//Clear the screen
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.font.setColor(new Color(1,1,1,1));
		this.camera.update();
		game.batch.setProjectionMatrix(this.camera.combined);
		
		game.batch.begin();
		int max = game.screenHeight;
		
		animation += 0.1;
		//if(animation > 3.14) animation = 0;
	
		game.font.getData().setScale(0.9f);

		game.font.draw(game.batch, "Escape the angry police officers", 100, max -50);
		game.font.draw(game.batch, "Swipe up to jump", 100, max - 100);
		game.font.draw(game.batch, "Swipe down to slide", 100, max - 150);
		float multi = (float) Math.cos(animation);
		game.font.getData().setScale(0.9f + multi *0.1f);

		game.font.draw(game.batch, "Tap anywhere to begin playing", 100, max - 220);
		game.font.getData().setScale(0.9f);

		game.font.getData().setScale(1.0f);		
		game.font.draw(game.batch, "High Score:", 100, max - 300);

		for(int i = 0; i < game.highScore.size() ; ++i){
			game.font.draw(game.batch, game.highScore.get(i) + "s", 100, max - 350 - 50*i);
		}
		
		if(Gdx.input.justTouched()){
			this.game.setScreen(new GameScreen(this.game));
		}
		game.batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
