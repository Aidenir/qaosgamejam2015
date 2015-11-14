package com.gamejam.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



public class Emitter 
{
	private EmitterData myData;
	private ArrayList<Particle> myParticles;
	private float myTimeSinceLastEmitt;
	private float myTotalTime;
	private Sprite mySprite;
	private Random myRandom;
	public float myPositionX;
	public float myPositionY;
	
	public boolean IsDead()
	{
		if(myData.myLifeTime < myTotalTime)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	
	public void Init(EmitterData someData)
	{
		myData = someData;
		myTimeSinceLastEmitt = 1 / myData.mySpawnPerSecond;
		myTotalTime = 0;
		mySprite = new Sprite(myData.myTexture);
		myRandom = new Random(1337);
		myParticles = new ArrayList<Particle>();
	}
	
	
	public void Update(float aDeltaTime, float aTrainSpeed)
	{
		myTotalTime += aDeltaTime;
		if(myTotalTime < myData.myLifeTime)
		{
			if(myTimeSinceLastEmitt > (1 / myData.mySpawnPerSecond))
			{
				Emitt();
				myTimeSinceLastEmitt = 0;
			}
		}
		UpdateParticles(aDeltaTime, aTrainSpeed);
	}
	
	private void UpdateParticles(float aDeltaTime, float aTrainSpeed)
	{
		for(int i = myParticles.size() - 1; i >= 0 ; i--)
		{
			if(myParticles.get(i).myRemainingLifeTime > 0 && myParticles.get(i).myAlpha > 0)
			{
				myParticles.get(i).myRemainingLifeTime -= aDeltaTime;
				myParticles.get(i).myAlpha += myData.myDeltaAlpha * aDeltaTime;
				myParticles.get(i).myScale += myData.myDeltaScale * aDeltaTime;
				myParticles.get(i).myVelocityX += myData.myDeltaVelocity *aDeltaTime;
				myParticles.get(i).myVelocityY += myData.myDeltaVelocity * aDeltaTime;
				myParticles.get(i).myXPosition += (myParticles.get(i).myVelocityX * aDeltaTime) + (aDeltaTime * aTrainSpeed);
				
				Vector2 vector = new Vector2(myParticles.get(i).myVelocityX , myParticles.get(i).myVelocityY);
				vector.nor();
				
				myParticles.get(i).myYPosition += (myParticles.get(i).myVelocityY * aDeltaTime) + (vector.y * myData.myGravityForce * aDeltaTime);
			}
			else
			{
				myParticles.remove(i);
			}
		}
	}
	
	public void Draw(SpriteBatch aBatch)
	{
		for(int i = 0; i < myParticles.size(); i++)
		{
			Particle particle = myParticles.get(i);
			
			mySprite.setScale(particle.myScale);
			mySprite.setPosition(particle.myXPosition, particle.myYPosition);
			
			mySprite.draw(aBatch, particle.myAlpha);
		}
	}
	
	public void Start()
	{
		myTotalTime = 0;
		myTimeSinceLastEmitt = 1;
	}
	
	public void Stop()
	{
		myTotalTime = myData.myLifeTime;
	}
	
	private void Emitt()
	{
		
		for(int i = 0; i < myData.myAmountPerSpawn; i++)
		{
			Particle particle = new Particle();
			
			particle.myAlpha = myData.myStartAlpha;
			particle.myScale = myData.myStartScale;
			particle.myRemainingLifeTime = myData.myParticleLifeTime;
			
			particle.myXPosition = GetRandomFloat(myData.myMinPosX, myData.myMaxPosX) + myPositionX;
			particle.myYPosition = GetRandomFloat(myData.myMinPosY, myData.myMaxPosY) + myPositionY;
			
			particle.myVelocityX = GetRandomFloat(myData.myMinVelocityX, myData.myMaxVelocityX);
			particle.myVelocityY = GetRandomFloat(myData.myMinVelocityY, myData.myMaxVelocityY);
		
			myParticles.add(particle);
		}
	}
	
	private float GetRandomFloat(float aMin, float aMax)
	{
		float value = aMax - aMin;
		
		float random = myRandom.nextFloat();
		random *= value;
		
		random += aMin;
		return random;
	}
}
