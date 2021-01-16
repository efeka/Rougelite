package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import window.Main;

public class Pedestal extends GameObject {

	private Texture tex = Main.getTex();

	public Pedestal(float x, float y, BufferedImage background, ObjectId id) {
		super(x, y, id);
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

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

}
