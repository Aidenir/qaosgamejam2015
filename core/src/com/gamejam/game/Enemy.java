package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy {

	GameJam game;
	
	private int moveSpeed = 0;
	public Sprite enemySprite;
	private boolean hasHurt = false;

	public Enemy(GameJam game, int x, int y){
		this.game = game;
		Texture img = new Texture(Gdx.files.internal("playerSprite.png"));
		enemySprite = new Sprite(img, 76,136);
		enemySprite.setX(x);
		enemySprite.setY(y);
	}
	
	public void render(float delta , float trainSpeed){
		//First move
		enemySprite.setX(enemySprite.getX() + (trainSpeed +  moveSpeed) * delta);
		if(enemySprite.getX() < -100){
			this.dispose();
		}
		//Then check collision with player
		Player player = ((GameScreen)game.getScreen()).player;
		if(player.playerSprite.getBoundingRectangle().overlaps(this.enemySprite.getBoundingRectangle())){
			//this.enemySprite.setY(300);
			if(!hasHurt){
				player.decreaseLife(1);
				Gdx.input.vibrate(300);
				hasHurt = true;
			}
		}
		
		//Then render
		game.batch.begin();
		enemySprite.draw(game.batch);
		game.batch.end();
	}
	
	
	public void dispose(){
	}

}
