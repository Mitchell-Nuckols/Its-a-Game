package com.google.game.gfx;

import java.util.Random;

public class Render
{
	// Variables
	private int width, height;
	public int[] pixels;
	
	// Sets the tile-map size
	public final int mapSize = 64; // Size of the map in square tiles
	public final int mapSizeMask = mapSize-1;
	public int[] tiles = new int[mapSize * mapSize];
	private Random rand = new Random();
	
	// Constructor
	public Render(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		
		// Sets random color for each of the tiles
		for(int i = 0; i < mapSize * mapSize; i++)
		{
			tiles[i] = rand.nextInt(0xFFFFFF);
		}
	}
	
	// Clears the screen
	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 0;
		}
	}
	
	// No, it's not a constructor!
	public void render(int xOffset, int yOffset)
	{
		for(int y = 0; y < height; y++)
		{
			int ypx = y + yOffset;
			
			if(ypx < 0 || ypx >= height) continue;
			for(int x = 0; x < width; x++)
			{
				int xpx = x + xOffset;
				
				if(xpx < 0 || xpx >= width) continue;
				//int tileIndex = ((xx >> 4) & mapSizeMask) + ((yy >> 4) & mapSizeMask) * mapSize; // Sets size of tile and finds coordinates
				//Every 16 pixels square is a tile (2 to the power of 4 is 16)
				
				pixels[xpx + ypx * width] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.size];
			}
		}
	}
}