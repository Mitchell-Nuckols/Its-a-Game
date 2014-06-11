package com.google.game.gfx;

public class Sprite
{
	public final int size;
	private int x;
	private int y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	// Creating the sprite
	// Syntax goes as follows:
	// "public static Sprite <what you want to call the sprite> = new Sprite(<size [usually 16]>, <x-offset>, <y-offset>, <sheet name>);"
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		this.size = size;
		pixels = new int[this.size * this.size];
		
		// Setting the coordinates
		this.x = x * size; 
		this.y = y * size;
		this.sheet = sheet;
		
		loadSprite();
	}
	
	private void loadSprite()
	{
		for(int y = 0; y < size; y++)
		{
			for(int x = 0; x < size; x++)
			{
				// Finds sprite on sheet and sets it to the array of pixels
				pixels[x + y * size] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.size]; 
			}
		}
	}
}