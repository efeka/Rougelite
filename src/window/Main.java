package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import objects.Block;
import objects.Player;

public class Main extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private boolean running = false;
	private Thread thread;
	
	BufferedImageLoader loader = new BufferedImageLoader();
	public BufferedImage level = null, level2 = null, level3 = null;
	public static int LEVEL = 1;
	
	public static Texture tex;
	
	Handler handler;
	Camera cam;
	
	public static enum STATE{
		MENU,
		GAME,
		SKINS,
		LEVELS,
		SETTINGS
	};
	public static STATE state = STATE.GAME;
	
	private void init() {
		tex = new Texture();
		cam = new Camera(0, 0);
		handler = new Handler(cam);
//		menu = new Menu(0, 0, ObjectId.Menu);
//		
//		clouds = loader.loadImage("/clouds.png");
//
		handler.LoadImageLevel(level);
		
		this.addKeyListener(new KeyInput(handler));
//		this.addMouseListener(new MouseInput());
	}
	public synchronized void start() {
		if (running) 
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		if (LEVEL == 1)
			level = loader.loadImage("/level1.png");
		else if (LEVEL == 2)
			level = loader.loadImage("/level2.png");
		else if (LEVEL == 3)
			level = loader.loadImage("/level3.png"); 
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
//				if (Game.state == Game.STATE.MENU)
//					Menu.tick();
				tick();
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}
	
	private void tick() {
		if (state == STATE.GAME) {
			handler.tick();
			for (int i = 0; i < handler.layer2.size(); i++) 
				if (handler.layer2.get(i).getId() == ObjectId.Player) 
					cam.tick(handler.layer2.get(i));
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		if (state == STATE.GAME) {
			
			//background
			g.setColor(new Color(30, 30, 60));
			g.fillRect(0, 0, getWidth(), getHeight());

//			hud.render(g);
			//===cam beginning===
			g2d.translate(cam.getX(), cam.getY());

//			for (int xx = 0; xx < clouds.getWidth() * 5; xx += clouds.getWidth()) 
//				g.drawImage(clouds, xx, 700, 2000, 2000, this);
			
//			hud.render(g);
			handler.render(g);
			

			g2d.translate(cam.getX(), -cam.getY());
			//===cam ending===
		}
//		else if (state == STATE.MENU) {
//			menu.render(g);
//		}
//		
//		else if (state == STATE.SKINS) {
//			Skins.render(g);
//		}
//		
//		else if (state == STATE.LEVELS) {
//			levels.render(g);
//		}
//		
//		else if (state == STATE.SETTINGS) {
//			settings.render(g);
//		}
		
		g.dispose();
		bs.show();
	}
	
	public static Texture getTex() {
		return tex;
	}
	
	public static void main(String[] args) {
		new Window(WIDTH, HEIGHT, "Platformer", new Main());
	}
	
}
