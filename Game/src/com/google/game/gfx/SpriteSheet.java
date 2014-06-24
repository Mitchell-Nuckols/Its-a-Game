package com.google.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	private final String basePath = "/textures/";
	private String spritePath;
	public final int size;
	public int w, h, width, height;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/Sheet.png", 256);
	//public static SpriteSheet gui = new SpriteSheet("/textures/gui/gui.png", 256);
	
	// Most complicated file tree EVAR!!
	//public static SpriteSheet devLevel_textures = new SpriteSheet("/assets/devLevel/textures/devLevel_textures.png", 64);
	
	public SpriteSheet(String path, int size)
	{
		this.w = size;
		this.h = size;
		
		this.spritePath = path;
		this.size = size;
		pixels = new int[this.size * this.size];
		
		loadImg();
	}
	
	/*public SpriteSheet(String path, int width, int height)
	{
		this.spritePath = path;
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;
		pixels = new int[this.size];
		
		loadImg();
	}*/
	
	private void loadImg()
	{
		try
		{
			BufferedImage img = ImageIO.read(SpriteSheet.class.getResource(spritePath));
			
			int w = 256;
			int h = 256;
			
			img.getRGB(0, 0, w, h, pixels, 0, w); // Translates image into pixels
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}