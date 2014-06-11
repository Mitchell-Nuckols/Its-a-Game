package com.google.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	private final String basePath = "/textures/";
	private String spritePath;
	public final int size;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/Sheet.png", 256);
	
	public SpriteSheet(String path, int size)
	{
		this.spritePath = path;
		this.size = size;
		pixels = new int[this.size * this.size];
		
		loadImg();
	}
	
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