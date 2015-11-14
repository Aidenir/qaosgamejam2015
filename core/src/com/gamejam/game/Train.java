package com.gamejam.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Train 
{
	public enum eTrainTileTypes
	{
		WINDOW_TILE,
		END_TILE
	}
	
	private Sprite myWindowTile;
	private Sprite myEndTile;
	private Sprite[] wheels;
	

	private final float myScale = 1.0f;
	private final int myAmountOfTrainParts = 8;
	private final float myTileYPosition = 20;
	
	private ArrayList<Sprite> myTrainTiles;
	
	public void Init()
	{		
		Texture windowTexture = new Texture(Gdx.files.internal("trainWindows.png"));
		Texture endTexture = new Texture(Gdx.files.internal("trainEndPiece.png"));
		
		myWindowTile = new Sprite(windowTexture);
		myWindowTile.setScale(myScale);
		myEndTile = new Sprite(endTexture);
		myEndTile.setScale(myScale);
		
		myTrainTiles = new ArrayList<Sprite>();
		
		Sprite firstSprite = new Sprite(myEndTile);
		firstSprite.setPosition(50, myTileYPosition);
		
		myTrainTiles.add(firstSprite);
		
		for(int i=1; i < myAmountOfTrainParts; i++)
		{
			AddTrainTileToEnd();
		}
		
		wheels = new Sprite[4];
		Texture wheel = new Texture(Gdx.files.internal("wheel.png"));
		for(int i = 0; i < 4; ++i){
			wheels[i] = new Sprite(wheel);
			wheels[i].setY(-10);
			wheels[i].setX(100 + 100*i);
		}
		
	}
	
	void AddTrainTileToEnd()
	{
		float xPosition = myTrainTiles.get(myTrainTiles.size() - 1).getX();
		float lastTileWidth = myTrainTiles.get(myTrainTiles.size() - 1).getWidth() * myScale;
		
		Sprite newSprite = new Sprite(myWindowTile);
		newSprite.setPosition(xPosition + lastTileWidth, myTileYPosition);
		myTrainTiles.add(newSprite);	
	}
	
	void RemoveFirstTrainTile()
	{
		myTrainTiles.remove(0);
	}
	
	public void Update(float aDeltaTime, float aTrainSpeed)
	{
		CheckIfTileIsIfOutsideScreen(0);
		float movementThisFrame = aDeltaTime * aTrainSpeed;
		for(int i = 0; i < 4; ++i){
			wheels[i].setX(wheels[i].getX() + movementThisFrame);
			if(wheels[i].getX() < -100){
				wheels[i].setX(1920);
			}
			wheels[i].rotate(40);
		}
		UpdateTrainTilePositions(movementThisFrame);
	}
	
	private void CheckIfTileIsIfOutsideScreen(int aTrainTileIndex)
	{
		if(myTrainTiles.get(aTrainTileIndex).getX() < myTrainTiles.get(aTrainTileIndex).getWidth() * myScale * -1.05)
		{
			myTrainTiles.remove(aTrainTileIndex);
			AddTrainTileToEnd();
		}
	}
	
	private void UpdateTrainTilePositions(float aTrainMovement)
	{
		for(int i=0; i < myTrainTiles.size(); i++)	
		{
			myTrainTiles.get(i).setPosition(myTrainTiles.get(i).getX() + aTrainMovement, myTileYPosition);
		}
	}
	
	public void Draw(SpriteBatch aBatch)
	{
		for(int i = 0; i < 4; ++i){
			wheels[i].draw(aBatch);
		}
		for(int i=0; i < myTrainTiles.size(); i++)	
		{
			myTrainTiles.get(i).draw(aBatch);
		}

	}
}
