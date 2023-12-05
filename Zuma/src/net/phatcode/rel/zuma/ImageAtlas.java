package net.phatcode.rel.zuma;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Anya
 *
 */

public class ImageAtlas
{

	private BufferedImage image;
	private BufferedImage[] sprites; 
	
	private int width;
	private int height;
	
	public ImageAtlas( ImageTextureData textureData )
	{
		
		try 
		{
			image = ImageIO.read( textureData.getUrl() );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
		}
        
		width = image.getWidth();
        height = image.getHeight();
        
        
        int numImages = textureData.getNumImages();
        int texcoords[] = textureData.getArray();
        
        sprites = new BufferedImage[numImages];
        
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        for( int i = 0; i < numImages; i++ )
        {
        	
        	int j = i * 4;
            int x = texcoords[j];
            int y = texcoords[j+1];
            int w = texcoords[j+2];
            int h = texcoords[j+3];
            
            BufferedImage im = image.getSubimage( x, y, w, h );
            sprites[i] = gc.createCompatibleImage( im.getWidth(), im.getHeight(), Transparency.BITMASK );
            sprites[i].getGraphics().drawImage( im, 0, 0, null );
    		
        }
        
	}
	
	
	public ImageAtlas( ImageTextureData textureData, int tileWidth, int tileHeight )
	{
		
		try 
		{
			image = ImageIO.read( textureData.getUrl() );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
		}
        
		width = image.getWidth(null);
        height = image.getHeight(null);
        
        
        int numImages = (width/tileWidth) * (height/tileHeight);
        
        sprites = new BufferedImage[numImages];
       
        int i = 0;
    	
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        for( int y = 0; y < (height/tileHeight); y++)
    	{
    		for( int x = 0; x < (width/tileWidth); x++) 
    		{
    	
    			BufferedImage im = image.getSubimage( x * tileWidth, y * tileHeight, tileWidth, tileHeight );
                sprites[i] = gc.createCompatibleImage( im.getWidth(), im.getHeight(), Transparency.BITMASK );
                sprites[i].getGraphics().drawImage( im, 0, 0, null );
        		
                i++;
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
		
	public BufferedImage getImage() 
    {
        return image;
    }

	public BufferedImage getSprite( int index )
	{
		return sprites[index];
	}
	
	public int getNumImages()
	{
		return sprites.length;
	}
	
}
