package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Camera;
import window.Handler;
import window.Main;
import window.Window;

public class HUD extends GameObject {
	
	private Handler handler;
	private Camera cam;
	private Texture tex = Main.getTex();

	public HUD(float x, float y, Camera cam, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.cam = cam;
	}

	public void tick(ArrayList<GameObject> object) {
		x = handler.player.getX() - Window.getWidth() / 2;
		y = handler.player.getY() - Window.getHeight() / 2;
	}

	public void render(Graphics g) {
		int maxHearts = PlayerInfo.maxHealth;
		int hearts = PlayerInfo.health;
		int missing = PlayerInfo.maxHealth - hearts;
		boolean hasHalfMaxHeart = maxHearts % 2 == 1;
		
		if (hasHalfMaxHeart)
			maxHearts -= 1;
		
		int drawX = 0;
		for (drawX = 0; drawX < maxHearts / 2; drawX++) 
			g.drawImage(tex.hearts[3], (int) x + drawX * 33, (int) y, 32, 32, null);
		
		if (hasHalfMaxHeart) 
			g.drawImage(tex.hearts[4], (int) x + drawX * 33, (int) y, 32, 32, null);
		
		boolean hasHalfHeart = hearts % 2 == 1;
		if (hasHalfHeart)
			hearts -= 1;
		
		drawX = 0;
		for (drawX = 0; drawX < hearts / 2; drawX++) 
			g.drawImage(tex.hearts[0], (int) x + drawX * 33, (int) y, 32, 32, null);
		
		if (hasHalfHeart) 
			g.drawImage(tex.hearts[1], (int) x + drawX * 33, (int) y, 32, 32, null);
		
		
		
	}

	public Rectangle getBounds() {
		return null;
	}

	public Rectangle getAttackBounds() {
		return null;
	}

}
