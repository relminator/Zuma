package net.phatcode.rel.zuma;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Anya
 *
 */

public class Keyboard implements KeyListener
{
	 	private static Keyboard instance = new Keyboard();

	 	private boolean[] keysPressed = new boolean[256];
	 	private boolean[] keysReleased = new boolean[256];
	 	
	 	private boolean anyKeyPressed = false;
	 	
	 	private Keyboard()
	 	{
	 		
	 	}
	 	
	 	public static Keyboard getInstance()
	 	{
	 		return instance;
	 	}
	 	
	 	@Override
	 	public void keyPressed( KeyEvent e )
	 	{
	 		int keyId = (e.getKeyCode() & 255);
	 		keysPressed[keyId] = true;
	 		keysReleased[keyId] = false;
	 		anyKeyPressed = true;
	 	}

	 	@Override
	 	public void keyReleased( KeyEvent e )
	 	{
	 		int keyId = (e.getKeyCode() & 255);
	 		keysPressed[keyId] = false;
	 		keysReleased[keyId] = true;
	 		anyKeyPressed = false;
	 	}

	 	@Override
	 	public void keyTyped( KeyEvent e )
	 	{
	 		
	 	}
	 	
	 	public boolean isKeyPressed( int key ) 
	 	{
	 		return keysPressed[key];
	 	}
	 	
	 	public boolean isKeyReleased( int key ) 
	 	{
	 		return keysReleased[key];
	 	}
	 	
	 	public boolean isAnyKeyPressed()
	 	{
	 		return anyKeyPressed;
	 	}
	 	
	 	public void resetKeys() 
	 	{
	 		for( int i = 0; i < 256; i++ )
	 		{
	 			keysReleased[i]= false;
	 		}
	 	}
	 	
	 	
}
