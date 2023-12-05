package net.phatcode.rel.zuma;

import java.net.URL;

public class ImageTextureDataSnake implements ImageTextureData
{

	
	public URL getUrl()
	{
		return this.getClass().getClassLoader().getResource(fileName);
	}
	
	public int[] getArray()
	{
		return textureCoords;
	}
	
	public int getNumImages()
	{
		return textureCoords.length/4;
	}
	
	private String fileName = "gfx/snake.png";
	private int textureCoords[] = 
		{
			1,	1,	24,	24,	 // 0
			27,	1,	24,	24,	 // 1
			53,	1,	24,	24,	 // 2
			79,	1,	24,	24,	 // 3
			1,	27,	24,	24,	 // 4
			1,	53,	24,	24,	 // 5
			105,1,	20,	20,	 // 6
			1,	79,	20,	20,	 // 7
			1,	101,20,	20,	 // 8
			27,	27,	20,	20,	 // 9
			49,	27,	20,	20,	 // 10
			71,	27,	20,	20,	 // 11
			93,	27,	20,	20,	 // 12
			27,	49,	20,	20,	 // 13
			103,49,	16,	16,	 // 14
			27,	93,	16,	16,	 // 15
			27,	111,16,	16,	 // 16
			49,	49,	16,	16,	 // 17
			67,	49,	16,	16,	 // 18
			85,	49,	16,	16,	 // 19
			27,	71,	20,	20,	 // 20
			85,	85,	12,	12,	 // 21
			99,	85,	12,	12,	 // 22
			115,27,	12,	12,	 // 23
			49,	67,	16,	16,	 // 24
			49,	85,	16,	16,	 // 25
			49,	103,16,	16,	 // 26
			67,	67,	16,	16,	 // 27
			85,	67,	16,	16,	 // 28
			103,67,	16,	16,	 // 29
			67,	85,	16,	16,	 // 30
			67,	103,16,	16,	 // 31
		};
 
}
