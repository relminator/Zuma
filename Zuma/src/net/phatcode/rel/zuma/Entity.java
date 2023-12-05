package net.phatcode.rel.zuma;

/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


import java.awt.Graphics2D;

public class Entity
{

	protected float width = 16;
	protected float height = 16;
	
	protected float x;
	protected float y;
	protected float dx;
	protected float dy;
	
	protected float speed = 3.0f;
	
	protected int frame = 0;
	protected int baseFrame = 0;
	protected int numFrames =  1;
	
	protected int tileX;
	protected int tileY;
	
	protected int ticks = 0;
	
	public Entity()
	{
	}
	
	
	public void update()
	{
	}
	
	public void render( Screen screen, Graphics2D g, ImageAtlas imageAtlas )
	{
		
		g.drawImage( imageAtlas.getSprite( baseFrame + frame ), 
			 	 	 (int)x,
			 	     (int)y,
			 	     screen );
	}

	public boolean canChangeDirection()
	{
		//boolean tx = ( ( (int)Math.floor(x) ) % Constants.TILE_SIZE ) == 0;
		//boolean ty = ( ( (int)Math.floor(y) ) % Constants.TILE_SIZE ) == 0;
		//return (tx && ty);
		int tx = (int)(x * Constants.TILE_SIZE);
		int ty = (int)(y * Constants.TILE_SIZE);
		
		int ttx = (int)Math.floor(x) * Constants.TILE_SIZE;
		int tty = (int)Math.floor(y) * Constants.TILE_SIZE;
		int distX = Math.abs(tx-ttx);
		int distY = Math.abs(ty-tty);
		
		return ((distX < 1) && (distY < 1));
	}
	
	public void snapToGrid()
	{
		x = ((int)x / Constants.TILE_SIZE) * Constants.TILE_SIZE;
		y = ((int)y / Constants.TILE_SIZE) * Constants.TILE_SIZE;
	}
	public float getWidth()
	{
		return width;
	}


	public float getHeight()
	{
		return height;
	}


	public float getX()
	{
		return x;
	}


	public float getY()
	{
		return y;
	}


	public float getSpeed()
	{
		return speed;
	}


	public int getBaseFrame()
	{
		return baseFrame;
	}


	public int getNumFrames()
	{
		return numFrames;
	}


	public void setWidth(float width)
	{
		this.width = width;
	}


	public void setHeight(float height)
	{
		this.height = height;
	}


	public void setX(float x)
	{
		this.x = x;
	}

	public void addX(float x)
	{
		this.x += x;
	}


	public void setY(float y)
	{
		this.y = y;
	}

	public void addY(float y)
	{
		this.y += y;
	}

	public float getDx()
	{
		return dx;
	}


	public float getDy()
	{
		return dy;
	}


	public void setDx(float dx)
	{
		this.dx = dx;
	}


	public void setDy(float dy)
	{
		this.dy = dy;
	}

	public void addDy(float y)
	{
		this.dy += y;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}


	public void setBaseFrame(int baseFrame)
	{
		this.baseFrame = baseFrame;
	}


	public void setNumFrames(int numFrames)
	{
		this.numFrames = numFrames;
	}


	public int getTileX()
	{
		return tileX;
	}


	public int getTileY()
	{
		return tileY;
	}


	public void setTileX( int tileX )
	{
		this.tileX = tileX;
	}


	public void setTileY( int tileY )
	{
		this.tileY = tileY;
	}
	
	

}
