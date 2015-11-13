package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;

public class GameScreen implements Screen{
	
	// Constants
	final private GameJam game;
	final private Player player;
	
	//bg elements
	private Background myBackground;
	private Sprite train;
	
	//tMp shit
	private Sprite enemy;
	
	
	private OrthographicCamera camera;

	public GameScreen(GameJam game) {
		this.game = game;
		this.player = new Player(this.game);
		this.myBackground = new Background();
		myBackground.Init();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
		
		Texture trainImg = new Texture(Gdx.files.internal("playerSprite.png"));
		
		train = new Sprite(trainImg, 76,136);
		train.setY(400);
		
		setupEnemy();
	}
	
	public void Update(float aDeltaTime)
	{
		camera.update();
		myBackground.Update(aDeltaTime);
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 	1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		
		Update(delta);
		
		// Render the train
		game.batch.begin();
		
		myBackground.Draw(game.batch);
		
		for(int i = 0; i < 10; ++i){
			train.setX(i * 80);
			train.draw(game.batch);
		}
		game.batch.end();
		
		updateEnemy();
		player.update(delta);

	}

	
	public void setupEnemy(){
		Texture img = new Texture(Gdx.files.internal("playerSprite.png"));
		enemy = new Sprite(img, 76,136);
		enemy.setScale(1.0f);
		enemy.setY(100);
		enemy.setX(game.screenWidth + 300);
	}
	
	public void updateEnemy(){
		game.batch.begin();
		enemy.setX(enemy.getX() - 200 * Gdx.graphics.getDeltaTime());
		if(enemy.getX() < - 100){
			enemy.setX(game.screenWidth + 300);
		}
		enemy.draw(game.batch);
		game.batch.end();
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
