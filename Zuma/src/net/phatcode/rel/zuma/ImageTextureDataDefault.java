package net.phatcode.rel.zuma;

import java.net.URL;

/**
 * @author Anya
 *
 */

public class ImageTextureDataDefault implements ImageTextureData
{

	private String fileName = "";
	
	public ImageTextureDataDefault( String file )
	{
		fileName = file;
	}
	
	@Override
	public URL getUrl()
	{
		return this.getClass().getClassLoader().getResource(fileName);
	}

	@Override
	public int[] getArray()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumImages()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
