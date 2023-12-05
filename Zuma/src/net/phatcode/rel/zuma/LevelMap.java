package net.phatcode.rel.zuma;

/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


import java.awt.Graphics2D;

public class LevelMap
{
	private int width = 0;
	private int height = 0;
	
	private int tileSize = Constants.TILE_SIZE;
	
	private int map[][];
	private int frame = 0;
	private int ticks = 0;
	
	public LevelMap()
	{
		reload();
	}

	public void reload()
	{
		
		String[] textMap = 
		{
				//012345678901234567890123456789012345678901234567890
				"                               ",		// 0
				"                               ",		// 1	
				"                     +         ",		// 2	
				"                     +         ",		// 3	
				"                     +         ",		// 4	
				"                               ",		// 5	
				"           +                   ",		// 6	
				"                               ",		// 7	
				"                               ",		// 8	
				"    ++++         +             ",		// 9	
				"                               ",		// 0	
				"                               ",		// 1	
				"              +++++++++        ",		// 2	
				"                               ",		// 3	
				"                               ",		// 3	
				"      +++++++                  ",		// 3	
				"                               ",		// 3	
				"                               ",		// 3	
				"                +              ",		// 3	
				"                +              ",		// 3	
				"                +              ",		// 3	
				"                +              ",		// 3	
				"                               ",		// 3	
				"                               ",		// 3	
				"       ++++++                  ",		// 3	
				"                               ",		// 3	
				"                               ",		// 3	
				"         +                     ",		// 3	
				"         +                     ",		// 4	
				"                               ",		// 4	
				
		};

		this.width = textMap[0].length();
		this.height = textMap.length;
		
		map = new int[this.width][this.height];
		
		for( int y = 0; y < height; y++ )	
		{
			for( int x = 0; x < width; x++ )
			{
				map[x][y] = 0;
			}
		}
				
		for( int y = 0; y < height; y++ )	
		{
		
			int len = textMap[y].length(); 
			for( int x = 0; x < len; x++ )
			{
				int index = textMap[y].charAt(x);
				if( index > 0 )
				{
					switch( index )
					{
						case '+':
							map[x][y] = 21;
							break;
					}
				}
			}
		}
		
	}

	public void render( Screen screen, Graphics2D g,
						ImageAtlas imageAtlas )
	{
		if( (++ticks & 3) == 0 ) frame++;
		
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ ) 
			{
				int i = map[x][y];		// get map index
				if( i > 0 )
				{

					int sx = (x * tileSize)+3;      //Calculate where to put a
					int sy = (y * tileSize)+3;      //particular tile
					g.drawImage( imageAtlas.getSprite(i + ((frame + x + y) % 3)), 
							 	 sx,
							 	 sy,
							 	 screen );
			
				}
			}
		}
		
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getTileSize()
	{
		return tileSize;
	}

	public int[][] getMap()
	{
		return map;
	}
		
}
