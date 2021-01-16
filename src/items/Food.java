package items;

import java.awt.Color;
import java.awt.Font;
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

	private float floatingCeiling, floatingFloor;
	private boolean goingUp = true;

	private float textX, textY;
	private boolean showText = false;
	private Font font = Main.getFont(12);

	public Food(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = 32;
		height = 32;
		textX = x;
		textY = y;
		velY = 0.1f;
		floatingCeiling = y - 1 * 15;
		floatingFloor = y;
	}

	public void tick(ArrayList<GameObject> object) {
		if (goingUp) {
			velY = 0.3f;
			if (y >= floatingFloor) {
				goingUp = false;
				y = floatingFloor;
			}
		}
		else {
			velY = -0.3f;
			if (y <= floatingCeiling) {
				goingUp = true;
				y = floatingCeiling;
			}
		}
		y += velY;

		if (handler.player.getBounds().intersects(getBounds())) {
			showText = true;
			if (KeyInput.pressedUse) {
				handler.removeObject(this);
				PlayerInfo.maxHealth += 3;
				PlayerInfo.health += 3;
				KeyInput.pressedUse = false;
			}
		}
		else
			showText = false;
	}

	public void render(Graphics g) {
		g.drawImage(tex.item[0], (int) x, (int) y, width, height, null);

		if (showText) {
			g.setColor(Color.white);
			g.setFont(font);
			String text = "+1.5 hearts";
			g.drawString(text, (int) textX - text.length() * 3, (int) textY - 10);
		}
		
		if (KeyInput.showCollisionBoxes) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.draw(getBounds());
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x - 10, (int) y - 20, width + 16, height + 40);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	public void takeDamage(int damage) {}

}
