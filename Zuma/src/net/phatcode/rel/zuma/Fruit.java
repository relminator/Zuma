package net.phatcode.rel.zuma;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Fruit extends Entity
{

	enum Type
	{
		NORMAL,
		SPECIAL,
	}
	
	Type type = Type.NORMAL;
	
	public Fruit()
	{
		speed = 0;
	}
	
	public void reset( LevelMap levelMap, Type type )
	{
		boolean valid = false;
		int[][] map = levelMap.getMap();
		while(!valid)
		{
			int mx = (Constants.SCREEN_WIDTH-200) / Constants.TILE_SIZE;
			int my = Constants.SCREEN_HEIGHT / Constants.TILE_SIZE;
			
			x = 4 + (float) (Math.random() * (mx-8));
			y = 4 + (float) (Math.random() * (my-8));
			x = x * Constants.TILE_SIZE * Constants.TILE_SIZE / Constants.TILE_SIZE;
			y = y * Constants.TILE_SIZE * Constants.TILE_SIZE / Constants.TILE_SIZE;
			valid = (map[(int) (x/Constants.TILE_SIZE)][(int) (y/Constants.TILE_SIZE)] == 0) && 
					(map[(int) (x/Constants.TILE_SIZE) + 1][(int) (y/Constants.TILE_SIZE)] == 0) &&
					(map[(int) (x/Constants.TILE_SIZE)][(int) (y/Constants.TILE_SIZE) + 1] == 0) && 
					(map[(int) (x/Constants.TILE_SIZE) - 1][(int) (y/Constants.TILE_SIZE)] == 0) &&
					(map[(int) (x/Constants.TILE_SIZE)][(int) (y/Constants.TILE_SIZE) -1 ] == 0) ;
		}
		frame = 0;
		baseFrame = 24;
		numFrames = 8;	
		dx = 0;
		dy = 0;
		this.type = type;
	}
	
	public void update()
	{
		
		x += dx;
		y += dy;
		if( (++ticks & 3) == 0)
		{
			frame = (++frame) % numFrames;
		}
						
	}
	
	public void render( Screen screen, Graphics2D g,
						ImageAtlas imageAtlas )
	{

		if( type == Type.SPECIAL )
		{
			BufferedImage sprite = imageAtlas.getSprite( (ticks/3) % 3 );
			g.drawImage( sprite, 
				 	 (int)x-4,
				 	 (int)y-4,
				 	 screen );
				
		}
		else
		{
			BufferedImage sprite = imageAtlas.getSprite( baseFrame + frame );
			g.drawImage( sprite, 
				 	 (int)x,
				 	 (int)y,
				 	 screen );
		}
		
	}	
	
	public int getScore()
	{
		if(type == Type.SPECIAL )
		{
			return 10;
		}
		
		return 5;
	}

	public Type getType()
	{
		return type;
	}
	

}
