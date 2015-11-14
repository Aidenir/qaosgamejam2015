package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameOverScreen implements Screen{
	private final GameJam game;
	private OrthographicCamera camera;

	public GameOverScreen(GameJam game) {
		this.game = game;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 800,480);
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
		
		this.camera.update();
		game.batch.setProjectionMatrix(this.camera.combined);
		
		game.batch.begin();
		game.font.draw(game.batch, "The police caught you, sadface ;(", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to play again", 100, 100);
		game.batch.end();
		if(Gdx.input.isTouched()){
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
