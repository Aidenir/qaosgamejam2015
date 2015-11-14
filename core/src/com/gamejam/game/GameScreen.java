package com.gamejam.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;

public class GameScreen implements Screen{
	
	// Constants
	final private GameJam game;
	final public Player player;
	final private int baseY;
	
	//bg elements
	private Background myBackground;
	
	//Enemies
	private ArrayList<Enemy> enemies;
	private long lastEnemySpawn;
	
	
	private OrthographicCamera camera;

	public GameScreen(GameJam game) {
		this.baseY = 200;
		this.game = game;
		this.player = new Player(this.game);
		this.myBackground = new Background();
		myBackground.Init();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);

		
		this.enemies = new ArrayList<Enemy>();
		
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

		game.batch.end();
		
		for(int i = 0; i < this.enemies.size(); ++i){
			this.enemies.get(i).render(delta);
			if(this.enemies.get(i).enemySprite.getX() < -baseY){
				this.enemies.remove(i);
				--i;
			}
		}
		
		if(enemies.size() < 2){
			spawnEnemy();
		}
		player.update(delta);

	}

	public void spawnEnemy(){
		//Spawn only every x second
		int rand = MathUtils.random(2, 7);
		if(System.currentTimeMillis() - rand * 1000 < lastEnemySpawn){
			return;
		}
		Enemy en = new Enemy(this.game, game.screenWidth + 200, baseY);
		this.enemies.add(en);
		lastEnemySpawn = System.currentTimeMillis();
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
	}

	@Override
	public void dispose() {
		this.enemies.clear();

	}
}
