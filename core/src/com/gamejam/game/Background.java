package com.gamejam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {

	private final float mySpeed = -300;
	private final float myScale = 2.3f;
	private final int myImageWidth = 600;
	private final int myImageHeigth = 360;
	private final float myYStartPosition = 200;
	private final float myHalfDistance = myImageWidth * myScale * 0.9f;
	private final float myDistanceToMove = myImageWidth * myScale * 2;
	private Sprite myBackground1;
	private Sprite myBackground2;
	
	public void Init()
	{
		Texture background1 = new Texture(Gdx.files.internal("background1.jpg"));
		Texture background2 = new Texture(Gdx.files.internal("background2.jpg"));
		myBackground1 = new Sprite(background1,myImageWidth,myImageHeigth);
		myBackground2 = new Sprite(background2,myImageWidth,myImageHeigth);
		
		myBackground1.setPosition(0, myYStartPosition);
		myBackground2.setPosition(myImageWidth * myScale, myYStartPosition);
		
		myBackground1.setScale(myScale);
		myBackground2.setScale(myScale);
	}
	
	public void Update(float aDeltaTime)
	{
		myBackground1.setPosition(myBackground1.getX() + aDeltaTime * mySpeed, myBackground1.getY());
		MoveBackground(myBackground1, -myHalfDistance, myDistanceToMove);
		
		myBackground2.setPosition(myBackground2.getX() + aDeltaTime * mySpeed, myBackground2.getY());
		MoveBackground(myBackground2, -myHalfDistance, myDistanceToMove);
		
	}
	
	private void MoveBackground(Sprite aSprite ,float aMaxDistance,float aMoveDistance)
	{
		if(aSprite.getX() < aMaxDistance)
		{
			aSprite.setPosition(aSprite.getX() + aMoveDistance, aSprite.getY());
		}
	}
	
	public void Draw(SpriteBatch aBatch)
	{
		myBackground1.draw(aBatch);
		myBackground2.draw(aBatch);
	}


	
}
