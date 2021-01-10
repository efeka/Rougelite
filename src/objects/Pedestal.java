package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import window.Handler;
import window.Main;

public class Pedestal extends GameObject {
		
	private Handler handler;
	private Texture tex = Main.getTex();
	
	private GameObject item;
	
	public Pedestal(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.item = item;
		this.handler = handler;
		width = 48;
		height = 32;
	}

	public void tick(ArrayList<GameObject> object) {

	}
	
	public void render(Graphics g) {
		g.drawImage(tex.pedestal, (int) x, (int) y, 48, height, null);
		
		if (KeyInput.showCollisionBoxes) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.white);
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
