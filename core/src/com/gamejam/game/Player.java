package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
	
	final private GameJam game;

	//Graphics and stuff for player
	private Sprite playerSprite;
	private Texture playerImg;
	private int playerImgWidth;
	private int playerImgHeight;
	
	public Player(GameJam game){
		this.game = game;
		
		playerImg = new Texture(Gdx.files.internal("playerSprite.png"));
		playerSprite = new Sprite(playerImg, 76,136);
		playerSprite.setX(50);
		playerSprite.setY(50);
	}
	
	public void update(float delta){
		this.handleInput();
		
		game.batch.begin();
		playerSprite.draw(game.batch);
		game.batch.end();
	}
	
	public void handleInput(){
		
	}
	
	public void dispose(){
		
	}
}
