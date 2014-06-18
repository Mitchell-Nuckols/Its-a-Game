package com.google.game.level;

import com.google.game.Game;
import com.google.game.gfx.Render;
import com.google.game.gfx.SpriteSheet;
import com.google.game.level.tile.Tile;

public class Level
{
	//protected Tile[] tiles;
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	
	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		
		generateLevel();
	}

	// Loads a level file
	public Level(String path)
	{
		loadLevel(path);
		generateLevel();
	}
	
	protected void loadLevel(String path)
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
		// Defines the render region or, what to keep rendered
		rend.setOffset(xScroll, yScroll); // Sets the offset
		
		int x0 = xScroll >> 4; // shifted right 4 (">> 4") is the same as dividing by 16 (the size of our tiles)
		int x1 = (xScroll + rend.width + SpriteSheet.tiles.size) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + rend.height + SpriteSheet.tiles.size) >> 4;
		
		for(int y = y0; y < y1; y++)
		{
			for(int x = x0; x < x1; x++)
			{
				getTile(x, y).render(x, y, rend); // Computer knows what tile is where and what it is and renders it
			}
		}
	}
	
	// Renders what this method returns
	// Works as a random tile generator
	public Tile getTile(int x, int y)
	{
		if(x < 0 || x >= width || y < 0 || y >= height) return Tile.nullTile; // Fixes array-index-out-of-bounds exception
		
		if(tiles[x + y * width] == 0xFF008821) return Tile.grass;
		if(tiles[x + y * width] == 0xFFFFD800) return Tile.flowers;
		if(tiles[x + y * width] == 0xFF7C5840) return Tile.dirt;
		if(tiles[x + y * width] == 0xFF542100) return Tile.planks;
		if(tiles[x + y * width] == 0xFF808080) return Tile.cobble;
		if(tiles[x + y * width] == 0xFFAEFFFF) return Tile.house0_0;
		return Tile.grass;
	}
	
	// Manages the level time cycle
	private void time()
	{
		
	}
}