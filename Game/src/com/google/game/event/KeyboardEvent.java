package com.google.game.event;

import java.awt.event.*;
import com.google.game.Game;

public class KeyboardEvent implements KeyListener
{
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	char invChar = 69;
	public static final int invKey = KeyEvent.VK_E;
	
	public void updateKeys()
	{
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
	}
	
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public boolean isKeyPressed(int key)
	{
		return keys[key];
	}
}