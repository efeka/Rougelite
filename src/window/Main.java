package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.MouseInput;
import framework.ObjectId;
import framework.Texture;

public class Main extends Canvas implements Runnable {
	
	/*
	 YAPILCAKLAR:
	 ------------
	
	 ELEMENTLER:
	 + ates sureli damage ama oldurmez
	 - buz slow atar
	 - elektrik periyodik olarak stun
	 
	 PLAYER:
	 + melee atak
	 + atak animasyonu
	 + playerin cani ve damage yemesi
	 + damage yedikten sonra invulnerable efekti
	 + dash cooldownini goster
	 - damage yediginde playeri notify edicek bi efekt
	 - envanter ekrani
	 - level sistemi
	 - skill agaci
	 
	 TUZAKLAR:
	 + ok atan tuzak
	 + (x) ve (+) sekillerinde donusumlu ates eden tuzak
	 + yerden periyodik ates cikaran tuzak
	 - yerden cikip saga sola oynayan bicak
	 - menziline girdiginde sana hedef alip chargelayip ates eden cannon
	  
	 DUSMAN TIPLERI:
	!- her temaya ozel dusman bul
	 + hayvan gibi kovalayip ziplayabilen melee dusman
	 + sen yaklasinca yerden cikip ates eden dusman
	 - kovalayan, ates ederken duran, sadece arkasindan damage yiyen dusman
	 - elemental vuran dusman
	 - duvarin icinden vurabilen dusman
	 - attiklari duvara carpan dusman (1li 2li 3lu ates edebilir) sana hedef alir
	 - mermisi kovalamaya calisan dusman
	 - attigi sey duvardan seken dusman
	 - senden kacip etrafina ates edip kacmaya devam eden dusman
	 - zeminden firlayip ates eden map boyunca takip eden solucan dusman
	 
	 BOSSLAR:
	 - senin elementaline gore armoru olan boss
	
	 ITEMLER:
	 + maksimum cani artirip dolduran item
	 - x saniyede bir damage bloklayan item
	  
	 BOLUM TEMALARI:
	 - col
	 - orman
	 
	 PICKUP ITEMLERI
	 + para
	 - aldiginda jumplari 0layan sey
	 + can dolduran yemek
	 - potion
	 */
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	
	private boolean running = false;
	private Thread thread;
	
	BufferedImageLoader loader = new BufferedImageLoader();
	public BufferedImage level = null, level2 = null, level3 = null;
	public static int LEVEL = 1;
	
	public static Texture tex;
	PauseMenu pauseMenu; 
	Handler handler;
	Camera cam;
	private static Font font;
	
	public static enum STATE{
		MENU,
		PAUSE_MENU,
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
		handler.LoadImageLevel(level);
		pauseMenu = new PauseMenu(WIDTH / 2 - PauseMenu.WIDTH / 2, HEIGHT / 2 - PauseMenu.HEIGHT / 2, ObjectId.PauseMenu);
		addKeyListener(new KeyInput(handler));
		addMouseListener(new MouseInput(handler));
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
				if (state == STATE.PAUSE_MENU)
					pauseMenu.tick(new ArrayList<GameObject>());
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
		else if (state == STATE.PAUSE_MENU) {
			pauseMenu.render(g);
		}
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
	
	public static Font getFont(int points) {
		InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("FiveByFive.ttf");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont((float) points);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return font;
	}
	
	public static void main(String[] args) {
		new Window(WIDTH, HEIGHT, "ROUGELITE", new Main());
	}
	
}
