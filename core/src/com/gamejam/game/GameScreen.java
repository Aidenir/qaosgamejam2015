package com.gamejam.game;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen{
	
	// Constants
	final private GameJam game;
	final private Player player;
	
	public GameScreen(GameJam game) {
		this.game = game;
		this.player = new Player(this.game);
	}
	
	public void render(float delta) {
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
