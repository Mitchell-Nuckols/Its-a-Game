package com.google.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.google.game.event.KeyboardEvent;
import com.google.game.gfx.Render;

// Sorry for using your domain name, Google
// If you're reading this then you're either: a) me b) a fellow developer or c) some bad-ass hacker
public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	// Version number (change if edit is made that will be in next release) & title
	public static String version = "alpha (no release)";
	public static String title = "Game";

	// Keyboard
	private KeyboardEvent key;
	
	// Window
	private JFrame frame;
	private BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Creates image
	private int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData(); // Allows access to the image
	private Render render;
	// Define: Raster - rectangular array of pixels
	
	// Variables
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public boolean invOpen;
	private boolean invOpenTog;
	
	private boolean running = false;
	
	// Constructor
	public Game()
	{
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		frame = new JFrame();
		render = new Render(width, height);
		key = new KeyboardEvent();
		
		addKeyListener(key);
	}
	
	// Thread stuffs
	private Thread thread;
	
	public synchronized void start()
	{
		running = true;
		
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	int x = 0, y = 0;
	
	// Update/tick method
	public void tick()
	{
		// Checks for keys that are being pressed and moves the screen appropriately
		key.updateKeys();
		
		if(key.up) y++;
		if(key.down) y--;
		if(key.left) x++;
		if(key.right) x--;
		
		if(key.isKeyPressed(KeyboardEvent.invKey) && !invOpenTog)
		{
			print("inventory opened!");
			invOpen = !invOpen;
			invOpenTog = true;
			return;
		}else if(!key.isKeyPressed(KeyboardEvent.invKey) && invOpenTog)
		{
			print("inventory closed");
			invOpenTog = !invOpenTog;
			return;
		}
	}
	
	// Render method
	// IN CHRONOLOGICAL ORDER!!
	public void render()
	{
		BufferStrategy bufferstrat = getBufferStrategy();
		
		if(bufferstrat == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		render.clear();
		render.render(x, y);
		
		// Copies pixel array from the Render class to the pixel array that is going to be rendered
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = render.pixels[i];
		}
		
		Graphics gfx = bufferstrat.getDrawGraphics();
		// Do graphics here
		gfx.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		gfx.dispose(); // Disposes of the stored graphics that have already been processed
		bufferstrat.show();
	}
	
	// Run method
	public void run()
	{
		// Timer variables
		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		
		// FPS counter
		int frames = 0;
		// Tick counter
		int ticks = 0;
		
		// Makes the window focused so you don't have to click on it to move and interact
		requestFocus();
		
		while(running)
		{
			// Continuing the timer
			long now = System.nanoTime();
			delta += (now - lastTime) / ns; // Measures time passed
			lastTime = now;
			
			while(delta >= 1)
			{
				tick();
				ticks++;
				
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) // Resets FPS counter after 1 second
			{
				timer += 1000;
				
				frame.setTitle(title + " | " + version + " | " + "FPS: " + frames + " | " + "TPS: " + ticks);
				ticks = 0;
				frames = 0;
			}
		}
	}
	
	// Easy methods
	public static void print(String str)
	{
		System.out.println("" + str);
	}
	
	// Main method
	public static void main(String[] args)
	{
		print("Hello World!");
		print(title + ", " + " Version: " + version);
		print("Running on Java version: " + System.getProperty("java.version"));
		
		Game game = new Game();
		
		// Initializes the window/frame
		game.frame.setResizable(false);
		game.frame.setTitle(title + " | " + version);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}