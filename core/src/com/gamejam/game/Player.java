package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	final private GameJam game;
	final private int FRAME_COLS = 4;
	final private int FRAME_ROWS = 1;
	final private float totalBlinkingTime = 1;
	final private float lifeXPosition = 900;
	final private float lifeYPosition = 650;
	
	//Graphics and stuff for player
	Animation runAnimation;
	Texture playerSheet;
	TextureRegion[] playerFrames;
	TextureRegion currentFrame;
	float stateTime;
	
	public Sprite playerSprite;
	private Sprite playerLife1;
	private Sprite playerLife2;
	private Sprite playerLife3;
	private Sprite playerLife4;
	private Texture playerImg;
	private Texture playerSlide;
	private int playerImgWidth;
	private int playerImgHeight;

	private boolean isJumping;
	private int jumpSpeed = 900;
	private int jumpHeight = 500;
	private int baseY = 200;
	private int baseX = 100;
	private int life = 4;
	private float blinkingTime;
	private float lastBlinkTime;

	private boolean goingDown;
	private boolean isSliding;
	private boolean rising;
	private int slideSpeed = 150;
	private int slideDepth = 50;
	private Texture playerJump; 
	
	public Player(GameJam game){
		this.game = game;
		
		playerImg = new Texture(Gdx.files.internal("playerSprite.png"));
		playerSlide = new Texture(Gdx.files.internal("playerKick.png"));
		playerJump = new Texture(Gdx.files.internal("playerJump.png"));
		playerSprite = new Sprite(playerImg);
		playerSprite.setScale(1.5f);
		playerSprite.setX(baseX);
		playerSprite.setY(baseY);
		isJumping = false;
		
		playerSheet = new Texture(Gdx.files.internal("playerRunning.png"));
		TextureRegion[][] tmp = TextureRegion.split(playerSheet, playerSheet.getWidth()/FRAME_COLS, playerSheet.getHeight()/FRAME_ROWS);
		playerFrames = new TextureRegion[FRAME_COLS*FRAME_ROWS];
		int index = 0;
		for(int i = 0; i < FRAME_ROWS; ++i){
			for(int j = 0 ; j < FRAME_COLS; ++j){
				playerFrames[index++] = tmp[i][j];
			}
		}
		runAnimation = new Animation(0.16f, playerFrames);
		stateTime = 0f;
		
		blinkingTime = 0;
		lastBlinkTime = 0;
		
		Texture life1Texture = new Texture(Gdx.files.internal("playerSprite.png"));
		Texture life2Texture = new Texture(Gdx.files.internal("playerSprite.png"));
		Texture life3Texture = new Texture(Gdx.files.internal("playerSprite.png"));
		Texture life4Texture = new Texture(Gdx.files.internal("playerSprite.png"));
		
		playerLife1 = new Sprite(life1Texture);
		playerLife1.setPosition(lifeXPosition, lifeYPosition);
		playerLife2 = new Sprite(life1Texture);
		playerLife2.setPosition(lifeXPosition, lifeYPosition);
		playerLife3 = new Sprite(life1Texture);
		playerLife3.setPosition(lifeXPosition, lifeYPosition);
		playerLife4 = new Sprite(life1Texture);
		playerLife4.setPosition(lifeXPosition, lifeYPosition);
		
		Gdx.input.setInputProcessor(new GestureDetector(new SwipeGestureHandler()));

	}
	
	public void SetPosition(float anX, float anY)
	{
		playerSprite.setPosition(anX, anY);
	}
	
	public void SetRelativePosition(float anX, float anY)
	{
		playerSprite.setPosition(playerSprite.getX() + anX, playerSprite.getY()+ anY);
	}
	
	public void update(float delta){
		this.handleInput();
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = runAnimation.getKeyFrame(stateTime, true);
		playerSprite.setRegion(currentFrame);
		
		HandleBlinking(delta);
		
		//Handle jumping
		if(isJumping){
			playerSprite.setTexture(playerJump);
			playerSprite.setRegion(playerJump);
			if(goingDown){
				playerSprite.setY(playerSprite.getY() - jumpSpeed * delta);
				if(playerSprite.getY() < baseY ) playerSprite.setY(baseY);
			}else{
				playerSprite.setY(playerSprite.getY() + jumpSpeed * delta);
			}
			
			if(playerSprite.getY() > jumpHeight){
				this.goingDown = true;
			}
			else if(playerSprite.getY() <= baseY){
				isJumping = false;
				goingDown = false;
			}
		}
		//Handle sliding
		if(isSliding){

			playerSprite.setTexture(playerSlide);
			playerSprite.setRegion(playerSlide);
			
			if(rising){
				playerSprite.setY(playerSprite.getY() + slideSpeed  * delta);
				if(playerSprite.getY() > baseY) playerSprite.setY(baseY);
			}
			else{
				playerSprite.setY(playerSprite.getY() - slideSpeed * delta);
			}
			
			if(playerSprite.getY() < baseY - slideDepth){
				this.rising = true;
			}else if(playerSprite.getY() >= baseY){
				isSliding = false;
				rising = false;
				playerSprite.setTexture(playerSheet);
			}
		}		
		game.batch.begin();
		playerSprite.draw(game.batch);
		DrawLife();
		game.batch.end();
	}
	
	private void DrawLife()
	{
		if(life == 4)
		{
			playerLife4.draw(game.batch);
		}
		else if(life == 3)
		{
			playerLife3.draw(game.batch);
		}
		else if(life == 2)
		{
			playerLife2.draw(game.batch);
		}
		else if(life == 1)
		{
			playerLife1.draw(game.batch);
		}
	}
	
	private void HandleBlinking(float aDelta)
		{
			blinkingTime -= aDelta;
			if(blinkingTime > 0)
			{
				lastBlinkTime += aDelta;
				if(lastBlinkTime > 0.2f )
				{
					lastBlinkTime = 0.f;
				}
				else if(lastBlinkTime < 0.1f)
				{
					playerSprite.setAlpha(0.7f);
				}
				else
				{
					playerSprite.setAlpha(1.f);
				}	
			}
			else
			{
				lastBlinkTime = 0;
				playerSprite.setAlpha(1.f);
			}
			
		}
	
	public void handleInput(){
		
	}
	
	public void jump(){
		if(isJumping) return;
		this.isJumping = true;
		this.goingDown = false;
	}
	
	
	public void slide(){
		if(isSliding) return;
		this.isSliding = true;
		this.rising = false;
	}
	
	public void swipeUp(){
		jump();
	}
	public void swipeDown(){
		slide();
	}
	public void swipeRight(){
		
	}
	public void swipeLeft(){
		
	}
	public void dispose(){
		
	}
	
	public class SwipeGestureHandler implements GestureListener{

		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			// TODO Auto-generated method stub
			return false;
		}
		

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			//Swipe up
			if(Math.abs(velocityX) < Math.abs(velocityY)){
				if(velocityY > 0 ){
					swipeDown();
				}else{
					swipeUp();
				}
			}else{
				if(velocityX > 0 ){
					swipeRight();
				}else{
					swipeLeft();
				}
			}
			return true;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			return false;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
			// TODO Auto-generated method stub
			return false;
		}


	}

	public void decreaseLife(int i) {
		
		if(blinkingTime < 0)
		{
			life--;
			Gdx.input.vibrate(300);
			blinkingTime = totalBlinkingTime;
			if(life == 0){
				((GameScreen) game.getScreen()).gameOver();
			}
		}
		
	}
}
