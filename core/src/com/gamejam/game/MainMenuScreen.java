package com.gamejam.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen {
	// Constants
	final private GameJam game;
	
	private OrthographicCamera camera;
	
	public MainMenuScreen(GameJam game) {
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
