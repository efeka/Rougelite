package items;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import objects.PlayerInfo;
import window.Handler;
import window.Main;

public class Food extends GameObject {
	
	private Handler handler;
	private Texture tex = Main.getTex();
	
	private float radians = 0;

	public Food(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = 32;
		height = 32;
		velY = 0.05f;
	}

	public void tick(ArrayList<GameObject> object) {
		radians += velY;
		y += Math.cos(radians) * 0.3;
		if ((int) radians == 50)
			radians = 0;
		
		if (handler.player.getBounds().intersects(getBounds())) {
			if (KeyInput.pressedUse) {
				handler.removeObject(this);
				PlayerInfo.maxHealth += 3;
				PlayerInfo.health += 3;
				KeyInput.pressedUse = false;
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(tex.item[0], (int) x, (int) y, width, height, null);
	
		if (KeyInput.showCollisionBoxes) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.draw(getBounds());
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

}
