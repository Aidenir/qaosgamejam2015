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
		game.font.getData().setScale(1);
		this.camera.update();
		game.batch.setProjectionMatrix(this.camera.combined);
		
		game.batch.begin();
		game.font.getData().setScale(2);		
		game.font.draw(game.batch, "High Score:", game.screenWidth/2, game.screenHeight - (game.screenHeight / 3) );

		for(int i = 0; i < game.highScore.size() ; ++i){
			game.font.draw(game.batch, game.highScore.get(i) + "s", game.screenWidth/2, game.screenHeight - (game.screenHeight / 3) - (i + 1)*40);
		}
		game.font.getData().setScale(1);


		game.font.draw(game.batch, "Escape the angry police officers", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to begin playing", 100, 100);
		game.batch.end();
				
		if(Gdx.input.justTouched()){
			this.game.setScreen(new GameScreen(this.game));
		}
		
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
