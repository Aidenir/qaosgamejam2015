package com.gamejam.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen{
	
	// Constants
	final private GameJam game;
	final public Player player;
	final private int baseY;
	final private long starttime;
	
	//bg elements
	private Background myBackground;
	
	private Train myTrain;
	private float myTrainSpeed;
	
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
		this.myTrain = new Train();
		myTrain.Init();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.screenWidth, game.screenHeight);
		myTrainSpeed = -100;
		this.enemies = new ArrayList<Enemy>();
		starttime = System.currentTimeMillis();
		
	}
	
	public void Update(float aDeltaTime)
	{
		camera.update();
		myBackground.Update(aDeltaTime);
		myTrain.Update(aDeltaTime, myTrainSpeed);
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		long currTime = System.currentTimeMillis();
		game.batch.setProjectionMatrix(camera.combined);
		
		Update(delta);
		
		// Render Scene
		game.batch.begin();

		myBackground.Draw(game.batch);
		myTrain.Draw(game.batch);

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
		//Render time
		game.batch.begin();

		game.font.setColor(new Color(0,1,0,1));
		game.font.getData().setScale(3);
		game.font.draw(game.batch, "" + (currTime - starttime) / 1000 + "s", 10, game.screenHeight - 10);
		game.batch.end();

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

	public void gameOver() {
		long currTime = System.currentTimeMillis();
		int score = (int) ((currTime - starttime) / 1000);
		game.setScreen(new GameOverScreen(game,score));
	}
}
