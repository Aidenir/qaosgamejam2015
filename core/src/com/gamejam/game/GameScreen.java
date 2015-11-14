package com.gamejam.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen{
	
	// Constants
	final private GameJam game;
	final public Player player;
	final private float myPlayerStartXPostion = 500;
	final private float baseX = 100;
	final private int baseY;
	final private long starttime;
	final private float myStartWaitDistance = (baseX - myPlayerStartXPostion) * -1;
	private final float myGoFontTimeLimit = 4;
	
	//bg elements
	private Background myBackground;
	
	private Train myTrain;
	private float myTrainSpeed;
	private float myTrainDistanceTraveled;
	private float myGoFontTime;
	private boolean myDrawGoText;
	
	private boolean hasSeenBig = false;
	private boolean hasSeenSmall = false;
	private boolean paused = false;
	private boolean hasRendered = false;
	private int type;
	//Enemies
	private ArrayList<Enemy> enemies;
	private long lastEnemySpawn;
	
	private OrthographicCamera camera;
	private boolean gameOver;
	private int score;
	private int nextTime;
	private Music trainMusic;
	private Music backgroundMusic;

	public GameScreen(GameJam game) {
		this.baseY = 200;
		this.gameOver = false;
		this.game = game;
		this.player = new Player(this.game);
		this.player.SetPosition(myPlayerStartXPostion, baseY);
		this.myTrainDistanceTraveled = 0;
		this.myBackground = new Background();

		myBackground.Init();
		this.myTrain = new Train();
		myTrain.Init();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.screenWidth, game.screenHeight);
		myTrainSpeed = -100;
		this.enemies = new ArrayList<Enemy>();
		starttime = System.currentTimeMillis();
		camera.setToOrtho(false, game.screenWidth, game.screenHeight);
		myTrainSpeed = -400;
		this.enemies = new ArrayList<Enemy>();
		nextTime = MathUtils.random(1, 6);

		trainMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Train.wav"));
		
		trainMusic.setLooping(true);
		trainMusic.setVolume(0.2f);
		trainMusic.play();
		
		//backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Takashi Tateishi - Wily Stage 1-2.mp3"));
		
		//backgroundMusic.setLooping(true);
		//backgroundMusic.setVolume(0.2f);
		//backgroundMusic.play();
	}
	
	public void Update(float aDeltaTime)
	{
		float trainDistaneThisFrame = aDeltaTime * -myTrainSpeed;
		myTrainDistanceTraveled += trainDistaneThisFrame;
		
		if(myTrainDistanceTraveled < myStartWaitDistance)
		{
			player.SetRelativePosition(-trainDistaneThisFrame, 0);
		}
	
		camera.update();
		myBackground.Update(aDeltaTime);
		myTrain.Update(aDeltaTime, myTrainSpeed);
	}
	
	
	public void render(float delta) {


		game.batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(gameOver) this.dispose();

		long currTime = System.currentTimeMillis();
		
		Update(delta);
		
		// Render Scene
		game.batch.begin();

		myBackground.Draw(game.batch);
		myTrain.Draw(game.batch);
		game.batch.end();
		
		for(int i = 0; i < this.enemies.size(); ++i){
			this.enemies.get(i).render(delta, myTrainSpeed);

			if(this.enemies.get(i).enemySprite.getX() < -100)
			{
				this.enemies.remove(i);
				--i;
			}
		}
		
		spawnEnemy();
		
		player.update(delta);
		//Render time

	
		
		game.font.setColor(new Color(0,1,0,1));
		game.font.getData().setScale(3);

		game.font.setColor(new Color(0,0,0,1));
		game.font.getData().setScale(1f);
		game.batch.begin();
		game.font.draw(game.batch, "" + (currTime - starttime) / 1000 + "s", 10, game.screenHeight - 10);
		game.batch.end();
		
		
		
		}
	

	public void spawnEnemy(){
		//Spawn only every x second
		long curr = System.currentTimeMillis();
		if(curr -lastEnemySpawn < nextTime *100){
			return;
		}
		nextTime = MathUtils.random(7, 20);

		Enemy en = new Enemy(this.game, game.screenWidth + 200, baseY-20);
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
		trainMusic.stop();
		game.setScreen(new GameOverScreen(game,score));

	}

	public void gameOver() {
		long currTime = System.currentTimeMillis();
		this.score = (int) ((currTime - starttime) / 1000);
		//this.enemies.clear();
		this.gameOver = true;
	}
}
