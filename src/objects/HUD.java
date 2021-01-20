package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Handler;
import window.Main;
import window.Window;

public class HUD extends GameObject {
	
	private Handler handler;
	private Texture tex = Main.getTex();
	private Font font = Main.getFont(23);

	public HUD(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
	}

	public void tick(ArrayList<GameObject> object) {
		x = handler.player.getX() - Window.getWidth() / 2;
		y = handler.player.getY() - Window.getHeight() / 2;
	}

	public void render(Graphics g) {
		int scaleX = 160;
		int scaleY = 64 * 3 / 2;
		g.drawImage(tex.hud, (int) x, (int) y, scaleX, scaleY, null);
		
		int maxHearts = PlayerInfo.maxHealth;
		int hearts = PlayerInfo.health;
		boolean hasHalfMaxHeart = maxHearts % 2 == 1;
		
		if (hasHalfMaxHeart)
			maxHearts -= 1;
		
		int drawX = 0;
		for (; drawX < maxHearts / 2; drawX++) 
			g.drawImage(tex.hearts[2], (int) x + 124 + drawX * 33, (int) y + 15, 32, 32, null);
		
		if (hasHalfMaxHeart) 
			g.drawImage(tex.hearts[3], (int) x + 124 + drawX * 33, (int) y + 15, 32, 32, null);
		
		boolean hasHalfHeart = hearts % 2 == 1;
		if (hasHalfHeart)
			hearts -= 1;
		
		drawX = 0;
		for (; drawX < hearts / 2; drawX++) 
			g.drawImage(tex.hearts[0], (int) x + 124 + drawX * 33, (int) y + 15, 32, 32, null);
		
		if (hasHalfHeart) 
			g.drawImage(tex.hearts[1], (int) x + 124 + drawX * 33, (int) y + 15, 32, 32, null);

		
		int armor = PlayerInfo.armor;
		boolean hasHalfArmor = armor % 2 == 1;
		if (hasHalfArmor)
			armor -= 1;
		
		drawX = maxHearts / 2;
		if (hasHalfMaxHeart)
			drawX++;
		for (int i = 0; i < armor / 2; i++, drawX++)
			g.drawImage(tex.hearts[4], (int) x + 124 + drawX * 33, (int) y + 15, 32, 32, null);
		if (hasHalfArmor)
			g.drawImage(tex.hearts[5], (int) x + 124 + drawX * 33, (int) y + 15, 32, 32, null);
		
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(":" + PlayerInfo.gold, (int) x + 126 + 28, (int) y + 75);
		g.drawImage(tex.gold[3], (int) x + 126, (int) y + 55, 26, 26, null);
	}

	public Rectangle getBounds() {
		return null;
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	public void takeDamage(int damage) {}

}
