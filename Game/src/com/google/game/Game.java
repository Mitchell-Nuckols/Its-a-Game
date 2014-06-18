package com.google.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.google.game.entity.mob.Player;
import com.google.game.event.KeyboardEvent;
import com.google.game.gfx.Render;
import com.google.game.level.Level;
import com.google.game.level.LevelLoad;
import com.google.game.level.RandomLevel;
import com.google.game.level.SpawnLevel;

// Sorry for using your domain name, Google
// If you're reading this then you're either: a) me b) a fellow developer or c) some bad-ass hacker...
// Or you're looking at the Github page :3
//SPECIAL THANKS GO TO:
//TheCherno for providing the tutorials I used to make this game
//
// TODO: character and player system
// TODO: multiplayer
// TODO: item and inventory system
// TODO: find more people to help develop the game so I'm not so lonely :(
public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	// Version number (change if edit is made that will be in next release) & title
	public static String version = "alpha (no release)";
	public static String title = "Game";
	public static String notes = "I'm going back to basics for now...";
	public static String devStage = "Development paused";

	// Keyboard
	private KeyboardEvent key;
	
	// Level
	private Level level;
	public static String levelLoc = "/levels/devLevel.png";
	private LevelLoad rand;
	
	// Player
	private Player player;
	
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
	public boolean debug;
	private boolean debugTog;
	public boolean respawn = false;
	
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
		
		// Define the level
		rand = new LevelLoad();
		
		level = new SpawnLevel(levelLoc);
		
		// Define the player
		player = new Player(7 * 16, 12 * 16, key);
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
	
	//int x = 0, y = 0;
	
	// Update/tick method
	public void tick()
	{
		// Checks for keys that are being pressed and moves the screen appropriately
		key.updateKeys();
		
		if(key.isKeyPressed(KeyboardEvent.debugKey) && !debugTog)
		{
			debug = !debug;
			debugTog = true;
			
			if(debug)
			{
				print("Debug mode ON");
			}
			
		}else if(!key.isKeyPressed(KeyboardEvent.debugKey) && debugTog)
		{
			debugTog = !debugTog;
			
			if(!debug)
			{
				print("Debug mode OFF");
			}
		}
		
		// Debug commands
		if(key.respawn && debug)
		{
			player.x = 16 * 7;
			player.y = 16 * 12;
		}
		
		if(key.mapSwap && debug)
		{
			levelLoc = "/levels/houseBase.png";
			
		}
		
		// Updates the player and other entities
		player.updateEntities();
	}
	
	// Render method... Everything here is what is rendered on the screen
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
		
		int xScroll = player.x - render.width / 2;
		int yScroll = player.y - render.height / 2;
		
		level.render(xScroll, yScroll, render);
		player.render(render);
		
		// Copies pixel array from the Render class to the pixel array that is going to be rendered
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = render.pixels[i];
		}
		
		Graphics gfx = bufferstrat.getDrawGraphics();
		// Do graphics here
		gfx.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		// Debug screen
		if(debug)
		{
			gfx.setColor(Color.WHITE);
			gfx.setFont(new Font("Consolas", 0, 30));
			gfx.drawString("X: " + player.x + " Y: " + player.y, this.getWidth() - 880, this.getHeight() - 450);
			
			gfx.setFont(new Font("Consolas", 0, 10));
			gfx.drawString(notes,this.getWidth() - 880 , this.getHeight() - 420);
			gfx.drawString(devStage,this.getWidth() - 880 , this.getHeight() - 390);
		}
		
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