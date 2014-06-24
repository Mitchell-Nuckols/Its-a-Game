package com.google.game.gfx;

public class Sprite
{
	public final int size;
	private int x;
	private int y;
	public int[] pixels;
	public int[] gui;
	public int w, h;
	
	private SpriteSheet sheet;
	
	// Creating the sprite
	// Syntax goes as follows:
	// "public static Sprite <what you want to call the sprite> = new Sprite(<size [usually 16]>, <x-offset>, <y-offset>, <sheet name>);"
	// TO DO IN CHUNKS:
	// 1. Make multiple sprites (house0, house1, house2, house3, etc.) for each 16x16 chunk
	// 2. Render all the pieces in the Tile class but add or subtract 16 appropriately
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite nullTile = new Sprite(16, 0x000000);
	public static Sprite flowers = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite dirt = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite planks = new Sprite(16, 5, 0, SpriteSheet.tiles);
	public static Sprite solidgrass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite cobble = new Sprite(16, 6, 0, SpriteSheet.tiles);
	public static Sprite stonePathway = new Sprite(16, 8, 0, SpriteSheet.tiles);
	public static Sprite elevatedGround = new Sprite(16, 9, 0, SpriteSheet.tiles);
	public static Sprite elevatedGround_Below = new Sprite(16, 10, 0, SpriteSheet.tiles);
	
	public static Sprite water0 = new Sprite(16, 11, 0, SpriteSheet.tiles);
	public static Sprite water1 = new Sprite(16, 12, 0, SpriteSheet.tiles);
	
	public static Sprite stoneStairs = new Sprite(16, 13, 0, SpriteSheet.tiles);
	
	// devLevel assets
	
	
	// Player sprites and animations
	public static Sprite playerUp = new Sprite(16, 3, 14, SpriteSheet.tiles);
	public static Sprite playerDown = new Sprite(16, 1, 15, SpriteSheet.tiles);
	public static Sprite playerLeft = new Sprite(16, 2, 15, SpriteSheet.tiles);
	public static Sprite playerRight = new Sprite(16, 4, 15, SpriteSheet.tiles);
	
	public static Sprite playerUp_1 = new Sprite(16, 0, 15, SpriteSheet.tiles);
	public static Sprite playerDown_1 = new Sprite(16, 0, 14, SpriteSheet.tiles);
	public static Sprite playerLeft_1 = new Sprite(16, 4, 14, SpriteSheet.tiles);
	public static Sprite playerRight_1 = new Sprite(16, 5, 15, SpriteSheet.tiles);
	
	public static Sprite playerUp_2 = new Sprite(16, 1, 14, SpriteSheet.tiles);
	public static Sprite playerDown_2 = new Sprite(16, 2, 14, SpriteSheet.tiles);
	public static Sprite playerLeft_2 = new Sprite(16, 3, 15, SpriteSheet.tiles);
	public static Sprite playerRight_2 = new Sprite(16, 5, 14, SpriteSheet.tiles);
	
	public static Sprite house0_0 = new Sprite(32, 0, 6, SpriteSheet.tiles);
	
	public static Sprite fountain_0 = new Sprite(16, 2, 12, SpriteSheet.tiles);
	public static Sprite fountain_1 = new Sprite(16, 3, 12, SpriteSheet.tiles);
	public static Sprite fountain_2 = new Sprite(16, 2, 13, SpriteSheet.tiles);
	public static Sprite fountain_3 = new Sprite(16, 3, 13, SpriteSheet.tiles);
	
	// GUI
	//public static Sprite health = new Sprite(12, 87, 0, 0, SpriteSheet.gui);
	
	
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
	
	public Sprite(int w, int h, int x, int y, SpriteSheet sheet)
	{
		this.size = w * h;
		
		gui = new int[this.size];
		
		// Setting the coordinates
		this.x = x * w; 
		this.y = y * h;
		this.sheet = sheet;
		
		loadSprite();
	}
	
	// Can be used to set a tile to just a complete color
	public Sprite(int size, int color)
	{
		this.size = size;
		pixels = new int[this.size * this.size];
		setColor(color);
	}
	
	private void setColor(int color)
	{
		for(int i = 0; i < this.size * this.size; i++)
		{
			pixels[i] = color;
		}
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