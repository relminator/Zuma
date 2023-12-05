/**
 * 
 */
package net.phatcode.rel.zuma;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anya
 *
 */
public class Zuma extends Entity
{

	enum Direction
	{
		NEUTRAL, UP, DOWN, LEFT, RIGHT
	}
	
	@SuppressWarnings("unused")
	private Direction direction = Direction.NEUTRAL;
	private Direction nextDirection = Direction.NEUTRAL;
	private double rotation = 0;
	private int maxSegments = 1;
	private int maxLinks = Constants.SCREEN_WIDTH/Constants.TILE_SIZE *
			Constants.SCREEN_HEIGHT/Constants.TILE_SIZE;
	private List<Entity> links = new ArrayList<Entity>();
	private List<Vector2D> drawLinks = new ArrayList<Vector2D>();	
	private float speedCount = 0;
	private int bodyFrame = 0;
	public Zuma()
	{
		reset();
	}
	
	public void reset()
	{
		speed = 1.5f;
		x = 18 * Constants.TILE_SIZE;
		y = 15 * Constants.TILE_SIZE;
		snap2Grid();
		
		maxSegments = 1;
		direction = Direction.RIGHT;
		nextDirection = Direction.NEUTRAL;
		frame = 0;
		baseFrame = 10;
		numFrames = 4;	
		dx = 0;
		dy = 0;
		links.clear();
		drawLinks.clear();
		speedCount = 0;
		for(int i = 0; i < maxLinks * Constants.TILE_SIZE; i++)
		{
			addLink();
		}
		
	}
	
	public void update()
	{
		
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_RIGHT))
		{
			nextDirection = Direction.RIGHT;
		}
		
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_LEFT))
		{
			nextDirection = Direction.LEFT;
		}
		
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_UP))
		{
			nextDirection = Direction.UP;
		}
		
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_DOWN))
		{
			nextDirection = Direction.DOWN;
		}
	
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_SPACE))
		{
			nextDirection = Direction.NEUTRAL;
		}
	

		if( canChangeDirection() )
		{
			switch( nextDirection )
			{
				case UP:
					dx = 0;
					dy = -speed;
					direction = nextDirection;
					rotation = -Math.PI/2;
					break;
				case DOWN:
					dx = 0;
					dy = speed;
					direction = nextDirection;
					rotation = Math.PI/2;
					break;
				case LEFT:
					dx = -speed;
					dy = 0;
					direction = nextDirection;
					rotation = Math.PI;
					break;
				case RIGHT:
					dx = speed;
					dy = 0;
					direction = nextDirection;
					rotation = 0;
					break;
				default:
					dx = 0;
					dy = 0;
					
			}
			snap2Grid();
		}
		
		x += dx;
		y += dy;
		if( x > Constants.SCREEN_WIDTH -210 ) x = 0;
		if( x < 0) x =  Constants.SCREEN_WIDTH -210;
		if( y > Constants.SCREEN_HEIGHT-10 ) y = 0;
		if( y < 0) y =  Constants.SCREEN_HEIGHT-10;
		
		if( (++ticks & 3) == 0)
		{
			frame = (++frame) % numFrames;
			bodyFrame = (++bodyFrame) % 6;
		}
		
		
		// links
		links.get(0).setX(x); 
		links.get(0).setY(y);
		
		int size = links.size();
		for( int i = size-1; i > 0; i-- )
		{
			Entity e = links.get(i-1);
			links.get(i).setX(e.getX());
			links.get(i).setY(e.getY());
		}
		
		
		drawLinks.clear();
		if( maxSegments > 0 )
		{
			for( int i = 0; i < size; i++ )
			{
			
				Entity e = links.get(i);
				
				speedCount += speed;
				if( (speedCount >= Constants.TILE_SIZE) )
				{
					addDrawLink( e.getX(), e.getY() );
					speedCount = 0;
					if( (drawLinks.size()+1) > maxSegments)
					{
						break;
					}
				}
			}	
			
		}
				
	}
	
	public void render( Screen screen, Graphics2D g,
						ImageAtlas imageAtlas )
	{

		// links
		int size = drawLinks.size();
		for( int i = 0; i < size; i++ )
		{
			Vector2D e = drawLinks.get(i);
			float sx = e.x;
			float sy = e.y;
			int f = 0;
			BufferedImage sprite;
			if(i < size - 1 )
			{
				f = 14 + ((bodyFrame + i) % 6);
				sprite = imageAtlas.getSprite( f );
				int w2 = sprite.getWidth() / 7;
				int h2 = sprite.getHeight() / 7;
				AffineTransform playerTransform = new AffineTransform();
				playerTransform.setToIdentity();
				playerTransform.translate(sx + w2, 
										  sy + h2);
				float r = (ticks + i) * 0.2f;
				w2 = sprite.getWidth() / 2;
				h2 = sprite.getHeight() / 2;
				playerTransform.rotate(r, w2, h2);
				g.drawImage(sprite, playerTransform, screen);
					
			}
			else
			{
				f = 6 + frame;
				sprite = imageAtlas.getSprite( f );
				AffineTransform playerTransform = new AffineTransform();
				playerTransform.setToIdentity();
				playerTransform.translate(sx, 
										  sy);
				g.drawImage(sprite, playerTransform, screen);
			}
			
		}

		// head
		BufferedImage sprite = imageAtlas.getSprite( baseFrame + frame );
		int w2 = sprite.getWidth() / 2;
		int h2 = sprite.getHeight() / 2;
		float scale = 1.0f;
		AffineTransform playerTransform = new AffineTransform();
		playerTransform.setToIdentity();
		playerTransform.translate(x - scale, 
								  y - scale);
		playerTransform.scale(scale, scale);
		playerTransform.rotate(rotation, w2*scale, h2*scale);
		g.drawImage(sprite, playerTransform, screen);
		
	}	
	
	private void addLink()
	{
		Entity link = new Entity();
		links.add(link);
	}
	
	private void addDrawLink( float x, float y)
	{
		Vector2D link = new Vector2D(x,y);
		drawLinks.add(link);
	}

	public void incSegments()
	{
		this.maxSegments++;
	}
	
	public void incSpeed()
	{
		this.speed += 0.5;
		if(speed > 6) speed = 6;
	}
	
	public void snap2Grid()
	{
		x = (int)(x * Constants.TILE_SIZE)/Constants.TILE_SIZE;
		y = (int)(y * Constants.TILE_SIZE)/Constants.TILE_SIZE;
	}
	
	public boolean ateItself()
	{
		int size = drawLinks.size();
		for( int i = 1; i < size; ++i )
		{
			Vector2D e = drawLinks.get(i);
			float sx = e.x;
			float sy = e.y;
			float edx = x - sx;
			float edy = y - sy;
			float distance = (float)Math.sqrt(edx * edx + edy * edy);
			if( distance <= 8 )
			{
				return true;
			}
		}

		return false;
	}
	
	boolean hitMaze( LevelMap levelMap )
	{
		int tx = (int) ((x+8) / Constants.TILE_SIZE);
		int ty = (int) ((y+8) / Constants.TILE_SIZE);
		int[][] map = levelMap.getMap();
		return ( map[tx][ty] > 0 );
		
	}
	
	public int getLength()
	{
		return drawLinks.size();
	}
	
}
