package com.gamejam.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.audio.Music;

public class Background {
	
	private final float myBridgeTime = 25.f;
	private final float myTunnleTime = 10.f;
	
	private final float myCopenHagenFarSpeed = -200;
	private final float myCopenHagenFarScale = 3.2f;
	private final float myCopenHagenFarYStartPosition = 400;
	private final float myCopenHagenNearSpeed = -800;
	private final float myCopenHagenNearScale = 2;
	private final float myCopenHagenNearYStartPosition = 350;
	private Sprite myCopenHagenFar1;
	private Sprite myCopenHagenFar2;
	private Sprite myCopenHagenNear1;
	private Sprite myCopenHagenNear2;
	
	private final float myTunnleSpeed = -5000;
	private final float myTunnleScale = 1.6f;
	private final float myTunnleYStartPosition = 130;
	private Sprite myTunnle1;
	private Sprite myTunnle2;
	
	private final float myWaterBackgroundSpeed = -50;
	private final float myWaterBackgroundScale = 2.f;
	private final float myWaterBackgroundYStartPosition = 210;
	private final float myBridgeBackgroundSpeed = -2800;
	private final float myBridgeBackgroundScale = 2.25f;
	private final float myBridgeBackgroundYStartPosition = 210;
	private Sprite myWaterBackground1;
	private Sprite myWaterBackground2;
	private Sprite myBridgeBackground1;
	private Sprite myBridgeBackground2;
	
	private Emitter mySparkleMachine;
	
	private BackgroundState myState;
	private float myStateChangeTime;
	
	private float myTotalTime;
	
	
	Animation SeagullAnimation;
	Texture animationSheet;
	TextureRegion[] animationFrames;
	TextureRegion currentFrame;
	float stateTime;
	final private int FRAME_COLS = 3;
	final private int FRAME_ROWS = 1;
	final private float seaGullSpeed = 100;
	Music mySeagullAudio;
	boolean hasPlayedSeaGullAudio;
	private Sprite mySeagull;
	
	public void SeagullUpdate(float aDeltaTime)
	{
		if(mySeagull.getX() < 300)
		{
			if(hasPlayedSeaGullAudio == false)
			{
				mySeagullAudio.setLooping(false);
				mySeagullAudio.setVolume(0.2f);
				mySeagullAudio.play();
				hasPlayedSeaGullAudio = true;
			}
		}
		
		if(mySeagull.getX() < -200)
		{
			hasPlayedSeaGullAudio = false;
			mySeagull.setPosition(2000, mySeagull.getX());
		}
		stateTime += aDeltaTime;
		mySeagull.setPosition(mySeagull.getWidth() - aDeltaTime * seaGullSpeed  , mySeagull.getHeight());
		currentFrame = SeagullAnimation.getKeyFrame(stateTime, true);
		mySeagull.setRegion(currentFrame);
	}
	
	public void Init()
	{
		myStateChangeTime = 0;
		myTotalTime = 0;
		myState = BackgroundState.COPENHAGEN;
		CopenhagenSetup();
		TunnleSetup();
		BridgeSetup();
		 
		EmitterData myEmitterData = new EmitterData();
		myEmitterData.myAmountPerSpawn = 100;
		myEmitterData.myDeltaAlpha = -3.0f;
		myEmitterData.myDeltaScale = -1.f;
		myEmitterData.myDeltaVelocity = -100.0f;
		myEmitterData.myGravityForce = -1600.f;
		myEmitterData.myLifeTime = 300.f;
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
		myEmitterData.myTexture = new Texture(Gdx.files.internal("Sparkly.png"));
		
		mySparkleMachine = new Emitter();
		
		mySparkleMachine.Init(myEmitterData);
		mySparkleMachine.myPositionX = 400;
		mySparkleMachine.myPositionY = 400;
		mySparkleMachine.Start();
		animationSheet = new Texture(Gdx.files.internal("gulls2-01.png"));
		mySeagull = new Sprite(animationSheet);
		mySeagull.setPosition(2000, 250);
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight()/FRAME_ROWS);
		animationFrames = new TextureRegion[FRAME_COLS*FRAME_ROWS];
		int index = 0;
		for(int i = 0; i < FRAME_ROWS; ++i){
			for(int j = 0 ; j < FRAME_COLS; ++j){
				animationFrames[index++] = tmp[i][j];
			}
		}
		SeagullAnimation = new Animation(0.08f, animationFrames);
		stateTime = 0f;
		
		mySeagullAudio = Gdx.audio.newMusic(Gdx.files.internal("Sounds/SeaGull.wav"));
	}
	
	private void CopenhagenSetup()
	{
		Texture copenHagenFar = new Texture(Gdx.files.internal("copenhagenFar.png"));
		Texture copenHagenNear = new Texture(Gdx.files.internal("copenHagenNear.png"));
		
		myCopenHagenFar1 = new Sprite(copenHagenFar);
		myCopenHagenFar2 = new Sprite(copenHagenFar);
		myCopenHagenNear1 = new Sprite(copenHagenNear);
		myCopenHagenNear2 = new Sprite(copenHagenNear);
		
		myCopenHagenFar1.setPosition(0, myCopenHagenFarYStartPosition);
		myCopenHagenFar2.setPosition(myCopenHagenFar2.getWidth() * myCopenHagenFarScale, myCopenHagenFarYStartPosition);
		
		myCopenHagenFar1.setScale(myCopenHagenFarScale);
		myCopenHagenFar2.setScale(myCopenHagenFarScale);
		
		myCopenHagenNear1.setPosition(0, myCopenHagenNearYStartPosition);
		myCopenHagenNear2.setPosition(myCopenHagenNear2.getWidth() * myCopenHagenNearScale, myCopenHagenNearYStartPosition);
		
		myCopenHagenNear1.setScale(myCopenHagenNearScale);
		myCopenHagenNear2.setScale(myCopenHagenNearScale);
	}
	
	private void TunnleSetup()
	{
		Texture tunnle = new Texture(Gdx.files.internal("TunnleBackground.png"));
		
		myTunnle1 = new Sprite(tunnle);
		myTunnle2 = new Sprite(tunnle);
		
		myTunnle1.setPosition(myTunnle2.getWidth() * myTunnleScale * 2, myTunnleYStartPosition);
		myTunnle2.setPosition(myTunnle2.getWidth() * myTunnleScale, myTunnleYStartPosition);
		
		myTunnle1.setScale(myTunnleScale);
		myTunnle2.setScale(myTunnleScale);
	}
	
	private void BridgeSetup() 
	{
		Texture waterBackground = new Texture(Gdx.files.internal("bg3.2-02.png"));
		Texture bridgeBackground = new Texture(Gdx.files.internal("bridge background.png"));
		
		myWaterBackground1 = new Sprite(waterBackground);
		myWaterBackground2 = new Sprite(waterBackground);
		myBridgeBackground1 = new Sprite(bridgeBackground);
		myBridgeBackground2 = new Sprite(bridgeBackground);
		
		myWaterBackground1.setPosition(myWaterBackground1.getWidth() * myWaterBackgroundScale * -2, myWaterBackgroundYStartPosition);
		myWaterBackground2.setPosition(myWaterBackground2.getWidth() * myWaterBackgroundScale * -1, myWaterBackgroundYStartPosition);
		
		myWaterBackground1.setScale(myWaterBackgroundScale);
		myWaterBackground2.setScale(myWaterBackgroundScale);
		
		myBridgeBackground1.setPosition(myBridgeBackground2.getWidth() * myBridgeBackgroundScale * 2, myBridgeBackgroundYStartPosition);
		myBridgeBackground2.setPosition(myBridgeBackground2.getWidth() * myBridgeBackgroundScale, myBridgeBackgroundYStartPosition);
		
		myBridgeBackground1.setScale(myBridgeBackgroundScale);
		myBridgeBackground2.setScale(myBridgeBackgroundScale);
	}
	
	public void Update(float aDeltaTime)
	{
		CheckStateChange(aDeltaTime);
		switch(myState)
		{
		case COPENHAGEN:
			CopenHagenUpdate(aDeltaTime);
			mySparkleMachine.Update(aDeltaTime, 0);
			break;
			
		case TUNNLE:
			TunnleUpdate(aDeltaTime);
			break;
			
		case BRIDGE:
			BridgeUpdate(aDeltaTime);
			mySparkleMachine.Update(aDeltaTime, 0);
			break;
			
		case COPENHAGEN_TO_TUNNLE:
			CopenHagenUpdate(aDeltaTime);
			TunnleUpdate(aDeltaTime);
			break;
			
		case TUNNLE_TO_BRIDGE:
			TunnleUpdate(aDeltaTime);
			BridgeUpdate(aDeltaTime);
			
			
			break;
			default:
				
				break;
		}
	}
	private void CheckStateChange(float aDeltaTime)
	{
		myTotalTime += aDeltaTime;
		myStateChangeTime -= aDeltaTime;
		
		if(myTotalTime > myBridgeTime)
		{
			if(myState == BackgroundState.TUNNLE_TO_BRIDGE)
			{
				if(myStateChangeTime < 0)
				{
					myState = BackgroundState.BRIDGE;
				}
			}
			else if(myState == BackgroundState.TUNNLE)
			{
				myStateChangeTime = 2.f;
				myState = BackgroundState.TUNNLE_TO_BRIDGE;
			}
		}
		else if(myTotalTime > myTunnleTime)
		{
			if(myState == BackgroundState.COPENHAGEN_TO_TUNNLE)
			{
				if(myStateChangeTime < 0)
				{
					myState = BackgroundState.TUNNLE;
				}
			}
			else if(myState == BackgroundState.COPENHAGEN)
			{
				myStateChangeTime = 7.f;
				myState = BackgroundState.COPENHAGEN_TO_TUNNLE;
			}
		}
	}
	private void CopenHagenUpdate(float aDeltaTime)
	{
		BackgroundUpdate(myCopenHagenFar1, myCopenHagenFar2, aDeltaTime, myCopenHagenFarSpeed, myCopenHagenFarScale);
		BackgroundUpdate(myCopenHagenNear1, myCopenHagenNear2, aDeltaTime, myCopenHagenNearSpeed, myCopenHagenNearScale);
	}
	
	private void TunnleUpdate(float aDeltaTime)
	{
		BackgroundUpdate(myTunnle1, myTunnle2, aDeltaTime, myTunnleSpeed, myTunnleScale);
	}
	
	private void BridgeUpdate(float aDeltaTime)
	{
		SeagullUpdate(aDeltaTime);
		BackgroundUpdate(myWaterBackground1, myWaterBackground2, aDeltaTime, myWaterBackgroundSpeed, myWaterBackgroundScale);
		BackgroundUpdate(myBridgeBackground1, myBridgeBackground2, aDeltaTime, myBridgeBackgroundSpeed, myBridgeBackgroundScale);
	}
	
	void BackgroundUpdate(Sprite afirstBackground, Sprite aSecondBackground, float aDeltaTime,float aSpeed, float aScale)
	{
		float halfDistance = afirstBackground.getWidth() * aScale * 0.9f;
		float distanceToMove = afirstBackground.getWidth() * aScale * 2;
		
		afirstBackground.setPosition(afirstBackground.getX() + aDeltaTime * aSpeed, afirstBackground.getY());
		MoveBackground(afirstBackground, -halfDistance, distanceToMove);
		
		aSecondBackground.setPosition(aSecondBackground.getX() + aDeltaTime * aSpeed, aSecondBackground.getY());
		MoveBackground(aSecondBackground, -halfDistance, distanceToMove);
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
		switch(myState)
		{
		case COPENHAGEN:
			myCopenHagenFar1.draw(aBatch);
			myCopenHagenFar2.draw(aBatch);
			myCopenHagenNear1.draw(aBatch);
			myCopenHagenNear2.draw(aBatch);
			
			mySparkleMachine.Draw(aBatch);
			break;
			
		case TUNNLE:
			myTunnle1.draw(aBatch);
			myTunnle2.draw(aBatch);
			break;
			
		case BRIDGE:
			myWaterBackground1.draw(aBatch);
			myWaterBackground2.draw(aBatch);
			myBridgeBackground1.draw(aBatch);
			myBridgeBackground2.draw(aBatch);
			mySparkleMachine.Draw(aBatch);
			mySeagull.draw(aBatch);
			break;
			
		case COPENHAGEN_TO_TUNNLE:
			myCopenHagenFar1.draw(aBatch);
			myCopenHagenFar2.draw(aBatch);
			myCopenHagenNear1.draw(aBatch);
			myCopenHagenNear2.draw(aBatch);
			myTunnle1.draw(aBatch);
			myTunnle2.draw(aBatch);
			break;
			
		case TUNNLE_TO_BRIDGE:
			myTunnle1.draw(aBatch);
			myTunnle2.draw(aBatch);
			myWaterBackground1.draw(aBatch);
			myWaterBackground2.draw(aBatch);
			myBridgeBackground1.draw(aBatch);
			myBridgeBackground2.draw(aBatch);
			mySeagull.draw(aBatch);
			break;

			default:
				
				break;
		}
		
		
	}


	
}
