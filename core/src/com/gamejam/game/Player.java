package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	final private GameJam game;

	//Graphics and stuff for player
	private Sprite playerSprite;
	private Texture playerImg;
	private int playerImgWidth;
	private int playerImgHeight;

	private boolean isJumping;
	private int jumpSpeed = 600;
	private int jumpHeight = 300;

	private boolean goingDown;
	
	public Player(GameJam game){
		this.game = game;
		
		playerImg = new Texture(Gdx.files.internal("playerSprite.png"));
		playerSprite = new Sprite(playerImg, 76,136);
		playerSprite.setX(50);
		playerSprite.setY(50);
		isJumping = false;
		
		Gdx.input.setInputProcessor(new GestureDetector(new SwipeGestureHandler()));

	}
	
	public void update(float delta){
		this.handleInput();
		
		if(isJumping){
			if(playerSprite.getY() > jumpHeight){
				this.goingDown = true;
			}
			else if(playerSprite.getY() <= 50){
				isJumping = false;
				goingDown = false;
			}
			
			if(goingDown){
				playerSprite.setY(playerSprite.getY() - jumpSpeed * delta);
				if(playerSprite.getY() < 50 ) playerSprite.setY(50);
			}else{
				playerSprite.setY(playerSprite.getY() + jumpSpeed * delta);
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
	
	public void swipeUp(){
		jump();
	}
	public void swipeDown(){
		
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
}
