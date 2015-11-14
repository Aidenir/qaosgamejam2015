package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class IntroScreen implements Screen {
	GameJam game;
	private OrthographicCamera camera;
	private Sprite[] images;
	private int numberOfImages = 3;
	private int image = 0;
	

	public IntroScreen(GameJam game) {
		this.game = game;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, game.screenWidth,game.screenHeight);
		this.images = new Sprite[numberOfImages];
		for(int i = 0;i < numberOfImages ; ++i){
			Texture tmp = new Texture(Gdx.files.internal("intro" + 1 + ".png"));
			images[i] = new Sprite(tmp, game.screenWidth, game.screenHeight);
			images[i].setX(0);
			images[i].setY(0);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		//Clear the screen
		Gdx.gl.glClearColor(1,1,1,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.font.setColor(new Color(1,1,1,1));
		this.camera.update();
		game.batch.setProjectionMatrix(this.camera.combined);
		

		if(Gdx.input.justTouched()){
			if(Gdx.input.getX() > game.screenWidth / 2){
				image++;
			}else{
				image--;
			}
			if(image >= numberOfImages){
				this.game.setScreen(new MainMenuScreen(this.game));
				image = numberOfImages - 1;
			}else if (image < 0){
				image = 0;
			}
		}
		game.batch.begin();
		images[image].draw(game.batch);
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
