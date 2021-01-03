package window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import objects.Block;
import objects.Player;

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
	GameObject player;
	
	public static int playerStartX, playerStartY;
	
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
					addObject(new Block(xx * 32, yy * 32, 0, false, ObjectId.Block), MIDDLE_LAYER);
				if (red == 255 && green == 0 && blue == 220)
					addObject(new Block(xx * 32, yy * 32, 1, false, ObjectId.Block), MIDDLE_LAYER);
				if (red == 255 && green == 0 && blue == 110)
					addObject(new Block(xx * 32, yy * 32, 2, false, ObjectId.Block), MIDDLE_LAYER);
				if (red == 200 && green == 255 && blue == 200) 
					addObject(new Block(xx * 32, yy * 32, 0, true, ObjectId.Block), MIDDLE_LAYER);
				if (red == 127 && green == 51 && blue == 0)
					addObject(new Block(xx * 32, yy * 32, 3, false, ObjectId.Block), MIDDLE_LAYER);
				if (red == 127 && green == 0 && blue == 0)
					addObject(new Block(xx * 32, yy * 32, 4, false, ObjectId.Block), MIDDLE_LAYER);
				
				if (red == 0 && green == 0 && blue == 255) {
					player = new Player(xx * 32, yy * 32, this, cam, ObjectId.Player);
					addObject(player, MIDDLE_LAYER);
					playerStartX = xx * 32;
					playerStartY = yy * 32;
				}
			}
		}
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
