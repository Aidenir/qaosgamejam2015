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
	
	//Graphics and stuff for player
	Animation runAnimation;
	Texture playerSheet;
	TextureRegion[] playerFrames;
	TextureRegion currentFrame;
	float stateTime;
	
	public Sprite playerSprite;
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

	private boolean goingDown;
	private boolean isSliding;
	private boolean rising;
	private int slideSpeed = 150;
	private int slideDepth = 150;
	private Texture playerJump; 
	
	public Player(GameJam game){
		this.game = game;
		
		playerImg = new Texture(Gdx.files.internal("playerSprite.png"));
		playerSlide = new Texture(Gdx.files.internal("playerKick.png"));
		playerJump = new Texture(Gdx.files.internal("playerJump.png"));
		playerSprite = new Sprite(playerImg, 85,102);
		playerSprite.setScale(2);
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
		game.batch.end();
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
		life--;
		if(life == 0){
			((GameScreen) game.getScreen()).gameOver();
		}
	}
}
