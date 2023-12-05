package net.phatcode.rel.zuma;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/**
 * @author Anya
 *
 */

public class Game
{

	enum GameState
	{
		START,
		PLAY,
		WIN,
		LOOSE,
		GAME_OVER
	}
	
	private double accumulator = 0;

	@SuppressWarnings("unused")
	private int fps = 0;
	private int framesPerSecond = 0;
	private double previousTime = 0;
	private double oldTime = 0;
	
	private static final int maxStartCountdown = 60 * 6; 
	private int startCountdown = maxStartCountdown; 
	private boolean blinkMe = true;
	
	private SpriteFont fontDigi = new SpriteFont( new ImageAtlas( new ImageTextureDataDefault("gfx/font16x1024.png"), 16, 16 ) );
	
	private ImageAtlas zumaImages = new ImageAtlas( new ImageTextureDataSnake() );
	private ImageAtlas bgImage = new ImageAtlas( new ImageTextureDataDefault("gfx/zumabg.png"), 640, 480 );
	private ImageAtlas sideImage = new ImageAtlas( new ImageTextureDataDefault("gfx/side.png"), 200, 480 );
	
	private Zuma zuma = new Zuma();
	private Fruit fruit = new Fruit();
	private int fruitsEaten = 0;
	private LevelMap maze = new LevelMap();
	
	private int score = 0;
	private int topScore = 0;
	
	Flap  coinSfx = new Flap();
	Loose  loseSfx = new Loose();
	
	private GameState gameState = GameState.START;
	
	
	public Game()
	{
	}

	public void init( BufferStrategy strategy, Screen screen )
	{
		 
		Graphics2D g2D = (Graphics2D) strategy.getDrawGraphics();
		g2D.setColor(Color.BLACK);
		g2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

		Toolkit.getDefaultToolkit().sync();

		g2D.dispose();
		strategy.show();
	
		reset();
		
	}
	
	private void reset()
	{
		
		gameState = GameState.START;
		startCountdown = maxStartCountdown; 
		score = 0;
		
		zuma.reset();
		fruit.reset(maze, Fruit.Type.NORMAL);
		fruitsEaten = 0;
	}
	
	private void update() 
	{

		if( (startCountdown & 63) == 0 ) 
			blinkMe = !blinkMe;
		
		switch( gameState )
		{
			case START:
				if( --startCountdown < 0 ) 
				{	gameState = GameState.PLAY;
					startCountdown = maxStartCountdown;
				}
				break;
			case PLAY:
				zuma.update();
				handleCollisions();
				break;
			case WIN:
				if( --startCountdown < 0 )
				{
					gameState = GameState.START;
					reset();
					startCountdown = maxStartCountdown;
				}
				break;
			case LOOSE:
				if( --startCountdown < 0 )
				{
					gameState = GameState.START;
					reset();
					startCountdown = maxStartCountdown;
				}
				break;
			case GAME_OVER:
				if( --startCountdown < 0 )
				{
					gameState = GameState.START;
					reset();
					startCountdown = maxStartCountdown;
				}
				break;
		}
		
		fruit.update();
		Keyboard.getInstance().resetKeys();
		
		
		
	}
	
	private void handleCollisions()
	{
		float dx = (fruit.getX()) - zuma.getX();
		float dy = (fruit.getY()) - zuma.getY();
		float distance = (float)Math.sqrt(dx * dx + dy * dy);
		if( distance <= 8 )
		{
			fruitsEaten++;
			score += fruit.getScore() + (1 + (zuma.getLength()) * Math.random());
			if( score > 9999 ) score = 9999; 
			if( score > topScore ) topScore = score;
			coinSfx.play();
			zuma.incSegments();
			
			if( fruit.getType() == Fruit.Type.SPECIAL )
			{
				zuma.incSpeed();
				zuma.snap2Grid();
			}
			
			if(fruitsEaten % 10 == 0)
			{	
			   fruit.reset(maze, Fruit.Type.SPECIAL);
			}
			else
			{
			   fruit.reset(maze, Fruit.Type.NORMAL);
			}
		}
		
		if( zuma.ateItself() )
		{
			gameState = GameState.LOOSE;
			startCountdown = maxStartCountdown;
			loseSfx.play();
		}
		if( zuma.hitMaze(maze) )
		{
			gameState = GameState.LOOSE;
			startCountdown = maxStartCountdown;
			loseSfx.play();
		}
	}

	

	public void run( BufferStrategy strategy, Screen screen ) 
	{

		double dt = getDeltaTime(getSystemTime());
		
		init( strategy, screen  );
		
		while (true) 
		{

			dt = getDeltaTime(getSystemTime());
			if( dt > Constants.FIXED_TIME_STEP ) dt = Constants.FIXED_TIME_STEP;
			
			accumulator += dt;
			
			while( accumulator >= Constants.FIXED_TIME_STEP ) 
			{

				accumulator -= Constants.FIXED_TIME_STEP;
				update();
								
			}

			
			Graphics2D g2D = (Graphics2D) strategy.getDrawGraphics();
			g2D.setColor(Color.BLACK);
			g2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

			render( g2D, screen );
			g2D.dispose();
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
			

			if( Keyboard.getInstance().isKeyPressed( KeyEvent.VK_ESCAPE ) )
			{
				System.exit(0);
			}
			
			try 
			{
				Thread.sleep(1);
			} 
			catch (InterruptedException e)
			{
				System.out.println("interrupted");
			}
			
		}

	}

	
	
	public Keyboard getKeyHandler()
	{
		return Keyboard.getInstance();
	}
	
	
	public double getSystemTime() 
	{
		return System.nanoTime() / 1000000000.0;
	}

	public double getDeltaTime( double timerInSeconds ) 
	{

		double currentTime = timerInSeconds;
		double elapsedTime = currentTime - oldTime;
		oldTime = currentTime;

		framesPerSecond++;

		if ((currentTime - previousTime) > 1.0) 
		{
			previousTime = currentTime;
			fps = framesPerSecond;
			framesPerSecond = 0;
		}

		return elapsedTime;
	}
	
	private void render( Graphics2D g2D, Screen screen ) 
	{
		switch( gameState )
		{
			case START:
				renderStart( g2D, screen );
				break;
			case PLAY:
				renderPlay( g2D, screen );
				break;
			case WIN:
				renderWin( g2D, screen );
				break;
			case LOOSE:
				renderLoose( g2D, screen );
				break;
			case GAME_OVER:
				renderPlay( g2D, screen );
				break;
		}
	}
	
	private void renderPlay( Graphics2D g2D, Screen screen ) 
	{

		g2D.drawImage( bgImage.getSprite( 0 ), 
				 0,
				 0,
		 	     screen );

		maze.render( screen, g2D, zumaImages );
		fruit.render( screen, g2D, zumaImages );
		zuma.render( screen, g2D, zumaImages );

		g2D.drawImage( sideImage.getSprite( 0 ), 
				 440,
				 0,
		 	     screen );

		printHUD( g2D, screen );
		
	}

	private void renderStart( Graphics2D g2D, Screen screen ) 
	{
		
		g2D.drawImage( bgImage.getSprite( 0 ), 
				 0,
				 0,
		 	     screen );

		maze.render( screen, g2D, zumaImages );
		fruit.render( screen, g2D, zumaImages );
		zuma.render( screen, g2D, zumaImages );
		
		g2D.drawImage( sideImage.getSprite( 0 ), 
				 440,
				 0,
		 	     screen );

		if( blinkMe )
		{
			fontDigi.printCenter( 0,  Constants.SCREEN_HEIGHT/2 - 30, 
								  Constants.SCREEN_WIDTH,
								  "R E A D Y",
								  screen, g2D );
		}

		fontDigi.printCenter( 0,  Constants.SCREEN_HEIGHT/2 + 8, 
				  			  Constants.SCREEN_WIDTH,
				  			  "" + startCountdown / 60,
				  			  screen, g2D );
		
		printHUD( g2D, screen );
		
	}

	private void renderWin( Graphics2D g2D, Screen screen ) 
	{
		
		g2D.drawImage( bgImage.getSprite( 0 ), 
				 0,
				 0,
		 	     screen );

		maze.render( screen, g2D, zumaImages );
		fruit.render( screen, g2D, zumaImages );
		zuma.render(  screen, g2D, zumaImages );

		g2D.drawImage( sideImage.getSprite( 0 ), 
				 440,
				 0,
		 	     screen );

		if( blinkMe )
		{
			fontDigi.printCenter( 0,  Constants.SCREEN_HEIGHT/2 - 30, 
								  Constants.SCREEN_WIDTH,
								  "YOU WIN",
								  screen, g2D );
		}

		printHUD( g2D, screen );
				
	}

	private void renderLoose( Graphics2D g2D, Screen screen ) 
	{
		
		g2D.drawImage( bgImage.getSprite( 0 ), 
				 0,
				 0,
		 	     screen );

		maze.render( screen, g2D, zumaImages );
		fruit.render( screen, g2D, zumaImages );
		zuma.render( screen, g2D, zumaImages );
		
		g2D.drawImage( sideImage.getSprite( 0 ), 
				 440,
				 0,
		 	     screen );

		if( blinkMe )
		{
			fontDigi.printCenter( 0,  Constants.SCREEN_HEIGHT/2 - 30, 
								  Constants.SCREEN_WIDTH,
								  "YOU LOST",
								  screen, g2D );
		}

		printHUD( g2D, screen );
				
	}

	private void printHUD( Graphics2D g2D, Screen screen )
	{
		fontDigi.print( 440,  0, 
				  "    ZUMA",
				  screen, g2D );
		fontDigi.print( 440,  20, 
				  "  TATAY NI",
				  screen, g2D );
		fontDigi.print( 440,  40, 
				  "   GALEMA",
				  screen, g2D );

		fontDigi.print( 440,  140, 
				  " SCORE:" + Utils.int2Score(score, "0000"),
				  screen, g2D );
		fontDigi.print( 440,  180, 
				  "HISCORE:" + Utils.int2Score(topScore, "0000"),
				  screen, g2D );

		}
	
}
