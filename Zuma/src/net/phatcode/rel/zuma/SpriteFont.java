package net.phatcode.rel.zuma;

import java.awt.Graphics2D;

/**
 * @author Anya
 *
 */

public class SpriteFont
{

	private ImageAtlas fontAtlas;
	
	int charWidth = 16;
	int charHeight = 16;
	
	public SpriteFont( ImageAtlas imageAtlas )
	{
		fontAtlas = imageAtlas;
		charWidth = fontAtlas.getSprite(0).getWidth();
		charHeight = fontAtlas.getSprite(0).getHeight();
	}
	
	public void print( int x, int y, String text, Screen screen, Graphics2D g )
	{
		int len = text.length(); 
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32;
			g.drawImage( fontAtlas.getSprite(index), x, y, screen );
			x += charWidth; 
		}
		
	}

	public void printCenter( int x, int y, int screenWidth, String text, Screen screen, Graphics2D g )
	{
		int len = text.length(); 
		x = (screenWidth - (len * charWidth))/2;
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32;
			g.drawImage( fontAtlas.getSprite(index), x, y, screen );
			x += charWidth; 
		}
		
	}

	public void setCharWidth( int charWidth )
	{
		this.charWidth = charWidth;
	}
	
	public void setCharHeight( int charHeight )
	{
		this.charHeight = charHeight;
	}
	
	public int getCharWidth()
	{
		return charWidth;
	}

	public int getCharHeight()
	{
		return charHeight;
	}
	
}
