package window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import items.Food;
import objects.Block;
import objects.ChangingShooterTrap;
import objects.HUD;
import objects.Pedestal;
import objects.Player;
import objects.ShooterTrap;
import objects.TempEnemy;

public class Handler {
	
	private Camera cam;
	
	private GameObject tempObject;
	
	public BufferedImage level1 = null, level2 = null, level3 = null;
	
	public static final int BOTTOM_LAYER = 0;
	public static final int MIDDLE_LAYER = 1;
	public static final int TOP_LAYER = 2;
	
	public ArrayList<GameObject> layer1 = new ArrayList<GameObject>();
	public ArrayList<GameObject> layer2 = new ArrayList<GameObject>();
	public ArrayList<GameObject> layer3 = new ArrayList<GameObject>();
	
	public Player player;
	public static int playerStartX, playerStartY;
	
	public ArrayList<GameObject> pedestals = new ArrayList<GameObject>(); 
	
	public Handler(Camera cam) {
		this.cam = cam;

		BufferedImageLoader loader = new BufferedImageLoader();
		level1 = loader.loadImage("/level1.png");
//		level2 = loader.loadImage("/level2.png");
//		level3 = loader.loadImage("/level3.png");
	}
	
	public void LoadImageLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < w; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if (red == 178 && green == 0 && blue == 255) 
					addObject(new Block(xx * 32, yy * 32, 0, false, true, ObjectId.Block), MIDDLE_LAYER);
				if (red == 255 && green == 0 && blue == 220)
					addObject(new Block(xx * 32, yy * 32, 1, false, true, ObjectId.Block), MIDDLE_LAYER);
				if (red == 255 && green == 0 && blue == 110)
					addObject(new Block(xx * 32, yy * 32, 2, false, true, ObjectId.Block), MIDDLE_LAYER);
				if (red == 200 && green == 255 && blue == 200) 
					addObject(new Block(xx * 32, yy * 32, 0, true, true, ObjectId.Block), MIDDLE_LAYER);
				if (red == 127 && green == 51 && blue == 0)
					addObject(new Block(xx * 32, yy * 32, 3, false, true, ObjectId.Block), MIDDLE_LAYER);
				
				//background
				if (red == 127 && green == 0 && blue == 0)
					addObject(new Block(xx * 32, yy * 32, 4, false, false, ObjectId.Block), BOTTOM_LAYER);
				
				//enemies
				if (red == 255 && green == 0 && blue == 0)
					addObject(new TempEnemy(xx * 32, yy * 32, this, ObjectId.BasicEnemy), MIDDLE_LAYER);
				if (red == 182 && green == 255 && blue == 0)
					addObject(new ShooterTrap(xx * 32, yy * 32, ShooterTrap.SHOOT_LEFT, 1500, this, ObjectId.ShooterTrap), MIDDLE_LAYER);
				if (red == 76 && green == 255 && blue == 0)
					addObject(new ShooterTrap(xx * 32, yy * 32, ShooterTrap.SHOOT_RIGHT, 1500, this, ObjectId.ShooterTrap), MIDDLE_LAYER);
				if (red == 0 && green == 255 && blue == 144)
					addObject(new ShooterTrap(xx * 32, yy * 32, ShooterTrap.SHOOT_UP, 1500, this, ObjectId.ShooterTrap), MIDDLE_LAYER);
				if (red == 0 && green == 183 && blue == 100)
					addObject(new ShooterTrap(xx * 32, yy * 32, ShooterTrap.SHOOT_DOWN, 1500, this, ObjectId.ShooterTrap), MIDDLE_LAYER);
				if (red == 0 && green == 127 && blue == 14)
					addObject(new ChangingShooterTrap(xx * 32, yy * 32, 1500, this, ObjectId.ChangingShooterTrap), MIDDLE_LAYER);
				
				//player
				if (red == 0 && green == 0 && blue == 255) {
					player = new Player(xx * 32, yy * 32, this, cam, ObjectId.Player);
					addObject(player, MIDDLE_LAYER);
					playerStartX = xx * 32;
					playerStartY = yy * 32;
				}
				
				//items
				if (red == 94 && green == 106 && blue == 130) {
					addObject(new Food(xx * 32 + 10, yy * 32 - 33, this, ObjectId.Food), BOTTOM_LAYER);
					addObject(new Pedestal(xx * 32, yy * 32, this, ObjectId.Pedestal), BOTTOM_LAYER);
				}
			}
		}
		addObject(new HUD(0, 0, cam, this, ObjectId.HUD), TOP_LAYER);
	}
	
	private GameObject createRandomItem() {
		return new Food(0, 0, this, ObjectId.Food);
	}
	
	public void tick() {
		for (int i = 0; i < layer2.size(); i++) {
			tempObject = layer2.get(i);
			tempObject.tick(layer2);
		}
		for (int i = 0; i < layer1.size(); i++) {
			tempObject = layer1.get(i);
			tempObject.tick(layer1);
		}
		for (int i = 0; i < layer3.size(); i++) {
			tempObject = layer3.get(i);
			tempObject.tick(layer3);
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < layer1.size(); i++) {
			tempObject = layer1.get(i);
			tempObject.render(g);
		}
		for (int i = 0; i < layer2.size(); i++) {
			tempObject = layer2.get(i);
			tempObject.render(g);
		}
		player.render(g);
		for (int i = 0; i < layer3.size(); i++) {
			tempObject = layer3.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object, int layer) {
		switch(layer) {
		case BOTTOM_LAYER:
			layer1.add(object);
			break;
		case MIDDLE_LAYER:
			layer2.add(object);
			break;
		case TOP_LAYER:
			layer3.add(object);
			break;
		}
	}
	
	public void removeObject(GameObject object) {
		if (layer2.contains(object)) {
			layer2.remove(object);
			return;
		}
		
		if (layer1.contains(object)) {
			layer1.remove(object);
			return;
		}
		
		if (layer3.contains(object)) {
			layer3.remove(object);
			return;
		}
	}
	
}
