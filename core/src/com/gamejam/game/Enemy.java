package com.gamejam.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Enemy {

	GameJam game;
	
	private int moveSpeed = 0;
	public Sprite enemySprite;
	private boolean hasHurt = false;
	private boolean dead = false;
	
	//Emitters
	private ArrayList<Emitter> myEmitters;
	private EmitterData myEmitterData;
	private Texture myEmitterTexture;
	
	Animation dieAnimation;
	Texture animationSheet;
	TextureRegion[] animationFrames;
	TextureRegion currentFrame;
	float stateTime;
	final private int FRAME_COLS = 4;
	final private int FRAME_ROWS = 1;
	
	private int type;

	private int myTrainSpeed;

	public Enemy(GameJam game, int x, int y){
		this.game = game;
		type = MathUtils.random(0, 1);
		Texture img = new Texture(Gdx.files.internal("villian.png"));
		animationSheet = new Texture(Gdx.files.internal("villianDeath.png"));
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight()/FRAME_ROWS);
		animationFrames = new TextureRegion[FRAME_COLS*FRAME_ROWS];
		int index = 0;
		for(int i = 0; i < FRAME_ROWS; ++i){
			for(int j = 0 ; j < FRAME_COLS; ++j){
				animationFrames[index++] = tmp[i][j];
			}
		}
		dieAnimation = new Animation(0.08f, animationFrames);
		stateTime = 0f;
		
		
		enemySprite = new Sprite(img);
		if(type == 1){
			enemySprite.setScale(1.0f, 1.3f);
		}
		enemySprite.setX(x);
		enemySprite.setY(y);
		myTrainSpeed = -100;


		myEmitterData = new EmitterData();
		myEmitterData.myAmountPerSpawn = 100;
		myEmitterData.myDeltaAlpha = -3.0f;
		myEmitterData.myDeltaScale = -1.f;
		myEmitterData.myDeltaVelocity = -100.0f;
		myEmitterData.myGravityForce = -1600.f;
		myEmitterData.myLifeTime = 30.f;
		myEmitterData.myMaxPosX = 15.f;
		myEmitterData.myMaxPosY = 15.f;
		myEmitterData.myMinPosX = -15.f;
		myEmitterData.myMinPosY = -15.f;
		myEmitterData.myMaxVelocityX = 1500.f;
		myEmitterData.myMaxVelocityY = 2500.f;
		myEmitterData.myMinVelocityX = 500.f;
		myEmitterData.myMinVelocityY = 600.f;
		myEmitterData.myParticleLifeTime = 1.0f;
		myEmitterData.mySpawnPerSecond = 100.f;
		myEmitterData.myStartAlpha = 1.f;
		myEmitterData.myStartScale = 1.f;
		myEmitterData.myTexture = new Texture(Gdx.files.internal("blood.png"));
		this.myEmitters = new ArrayList<Emitter>();

	}
	
	private void UpdateEmitters(float aDeltaTime)
	{
		for(int i = myEmitters.size() - 1; i >= 0; i-- )
		{
			if(myEmitters.get(i).IsDead() == false)
			{
				myEmitters.get(i).Update(aDeltaTime, myTrainSpeed);				
			}
			else
			{
				myEmitters.remove(i);
			}
		}
	}
	
	public void render(float delta , float trainSpeed){
		//First move
		enemySprite.setX(enemySprite.getX() + (trainSpeed +  moveSpeed) * delta);
		if(enemySprite.getX() < -100){
			this.dispose();
		}
		//Then check collision with player
		Player player = ((GameScreen)game.getScreen()).player;
		float px = player.playerSprite.getX() + player.playerSprite.getWidth()/2;
		float py = player.playerSprite.getY() + player.playerSprite.getHeight()/2;
		float ex = enemySprite.getX() + enemySprite.getWidth()/2;
		float ey = enemySprite.getY() + enemySprite.getHeight()/2; 
		if((px > ex - 50 && px < ex+50) && (py < ey + 20 || type == 1) && py > ey - 20){ 
			//this.enemySprite.setY(300);
			if(!hasHurt){
				player.decreaseLife(1);
				hasHurt = true;
			}
		}else if(px > ex + 50 && !hasHurt){
			//Enemy die
			stateTime += Gdx.graphics.getDeltaTime();
			if(!dead){
				Emitter emitter = new Emitter();
				emitter.Init(myEmitterData);
				emitter.Start();
				emitter.myPositionX = 100.f;
				emitter.myPositionY = 250.f;
				
				myEmitters.add(emitter);
			}
			dead = true;
			currentFrame = dieAnimation.getKeyFrame(stateTime, true);
			enemySprite.setRegion(currentFrame);
		}
		
		//Then render
		game.batch.begin();
		enemySprite.draw(game.batch);
		game.batch.end();
		
		UpdateEmitters(delta);
		for(int j = 0; j < myEmitters.size(); j++)
		{
			game.batch.begin();
			myEmitters.get(j).Draw(game.batch);
			game.batch.end();
		}
	}
	
	
	public void dispose(){
	}

}
