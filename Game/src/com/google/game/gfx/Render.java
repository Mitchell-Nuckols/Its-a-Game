package com.google.game.gfx;

import java.util.Random;
import com.google.game.level.tile.Tile;

public class Render
{
	// Variables
	public int width, height;
	public int[] pixels;
	public int xOffset, yOffset;
	
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
	
	// renders the tiles (offset based)
	public void renderTile(int xpx, int ypx, Tile tile)
	{
		xpx -= xOffset;
		ypx -= yOffset;
		
		for(int y = 0; y < tile.sprite.size; y++)
		{
			int ya = y + ypx;
			
			for(int x = 0; x < tile.sprite.size; x++)
			{
				int xa = x + xpx;				
				if(xa < -tile.sprite.size || xa >= width || ya < 0 || ya >= height) break; // Stops rendering tiles if they are not on the screen
				if(xa < 0) xa = 0;
				
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.size];
			}
		}
	}
	
	// Renders an object multiple sprites long
	public void renderInChunks(int xpx, int ypx, Sprite sprite)
	{
		xpx -= xOffset;
		ypx -= yOffset;
		
		for(int y = 0; y < 32; y++)
		{
			int ya = y + ypx;
			
			for(int x = 0; x < 32; x++)
			{
				int xa = x + xpx;				
				if(xa < -32 || xa >= width || ya < 0 || ya >= height) break; // Stops rendering tiles if they are not on the screen
				if(xa < 0) xa = 0;
				
				int color = sprite.pixels[x + y * 32];
				if(color != 0xFFFF00DC)
				{
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.size];
				}
			}
		}
	}
	
	// Renders the player
	public void renderPlayer(int xpx, int ypx, Sprite sprite)
	{
		xpx -= xOffset;
		ypx -= yOffset;
		
		for(int y = 0; y < 16; y++)
		{
			int ya = y + ypx;
			
			for(int x = 0; x < 16; x++)
			{
				int xa = x + xpx;				
				if(xa < -16 || xa >= width || ya < 0 || ya >= height) break; // Stops rendering tiles if they are not on the screen
				if(xa < 0) xa = 0;
				
				int color = sprite.pixels[x + y * 16];
				if(color != 0xFFFF00DC)
				{
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.size];
				}
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}