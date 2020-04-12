package me.xxfreakdevxx.de.game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTextField;

import me.xxfreakdevxx.de.game.skyblock.Generator;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {
	
	/* Window */
	public static int windowWidth = 1000;
	public static int windowHeight = 700;
	
	private boolean isRunning = false;
	public static int fps_current = 0;
	public static int fps_maximal = -1;
	public static double tickSpeed = 120;
	private Thread thread;
	private Camera camera;
	private TextureAtlas textureAtlas;
	public MouseAdapter mouseAdapter;
	
	//Manager, Handler, etc.
	public KeyInput keyinput = null;
	public Window window = null;
	
	static Game instance;
	public static Game getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		instance = this;
		setBackground(Color.WHITE);
		window = new Window(windowWidth, windowHeight, "SkyBlock Algorithmus für Raster-Erstellung", this);
		start();
	}
	
	public void preInit() {
		keyinput = new KeyInput();
		mouseAdapter = new MouseInput();
		this.addKeyListener(keyinput);
		this.addMouseListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		camera = new Camera(0,0);
		textureAtlas = new TextureAtlas();
	}
	public void init() {
		readyToRender = true; //Starte die Render-Funktion
		

	}
	public void postInit() {
		Generator.generate();
	}
	public void startGame() {
		System.err.println("Es wurde kein Ursprung gesetzt um das Spiel zu starten!");
	}
	
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		preInit();
		init();
		postInit();
		startGame();
	}
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@Override
	public void run() {
		/*
		 * GameLoop - Made by Notch
		 */
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = tickSpeed;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		boolean allowRender = false;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				//update++;
				delta--;
			}
			if(fps_maximal == -1 ) allowRender = true;
			if(frames != fps_maximal || allowRender) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps_current = frames;
				frames = 0;
			}
		}
		stop();
	}
	
	public void setWindowTitle(String title) {
		if(window != null) window.frame.setTitle(title);
	}
	public void tick() {
		Game.windowWidth = window.frame.getWidth();
		Game.windowHeight = window.frame.getHeight();
		if(keyinput != null) keyinput.tick();
	}
	private boolean readyToRender = false;
	public void render() {
		if(readyToRender == false) return;
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(240,240,240));
		g2d.clearRect(0, 0, Game.windowWidth, Game.windowHeight);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Game.windowWidth, Game.windowHeight);
		g2d.setColor(Color.BLACK);
		g2d.translate(-camera.getX(), -camera.getY());
		
		Generator.renderIslands(g);
		
		g2d.translate(camera.getX(), camera.getY());
		
		if(KeyInput.text.equals("")) g.drawString("Rastergröße: 'DRÜCKE EINE ZAHL' -> Drücke Enter um neu zu generieren", 10, 15);
		else g.drawString("Rastergröße: '"+KeyInput.text +"' -> Drücke Enter um neu zu generieren", 10, 15);
		g.setColor(Color.YELLOW);
		g.drawString("Drücke Backspace um die etzte Ziffer zu löschen.", 10, 28);
		g.drawString("Erlaubt sind nur Zahlen als Rastergröße.", 10, 40);
		g.drawString("Benutze die Pfeiltasten um die Kamera zu bewegen.", 10, 52);
		
		g.dispose();
		bs.show();
	}
	
	public TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}
	
	public static Camera getCamera() {
		return getInstance().camera;
	}
	
	@SuppressWarnings("deprecation")
	public static String getTimeInString() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Calendar date = sdf.getCalendar();
		Date d = date.getTime();
		return d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	}
	@SuppressWarnings("deprecation")
	public static String getDateInString() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Calendar date = sdf.getCalendar();
		Date d = date.getTime();
		int day = d.getDate();
		int mon = d.getMonth();
		int year = d.getYear();
		return day+":"+mon+":"+year;
	}
	public static void log(String prefix, String... strings) {
		if(prefix.equals("")) prefix = "Debug";
		for(String s : strings) {
			System.out.println("["+Game.getTimeInString()+"]["+prefix+"] "+s);
		}
	}
}
