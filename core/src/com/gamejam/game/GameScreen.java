package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen{
	
	// Constants
	final private GameJam game;
	final private Player player;
	
	private OrthographicCamera camera;

	public GameScreen(GameJam game) {
		this.game = game;
		this.player = new Player(this.game);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 	1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		
		camera.update();
		player.update(delta);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
