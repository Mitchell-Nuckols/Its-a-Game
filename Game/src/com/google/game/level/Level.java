package com.google.game.level;

import com.google.game.gfx.Render;

public class Level
{
	protected int width, height;
	protected int[] tiles;
	
	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		
		generateLevel();
	}

	// Loads a level file
	public Level(String path)
	{
		loadLevel(path);
	}
	
	private void loadLevel(String path)
	{
		
	}

	// randomly generates a level
	protected void generateLevel()
	{
		
	}
	
	// Updates level at 60 TPS
	public void update()
	{
		
	}
	
	public void render(int xScroll, int yScroll, Render rend)
	{
		
	}
	
	// Manages the level time cycle
	private void time()
	{
		
	}
}