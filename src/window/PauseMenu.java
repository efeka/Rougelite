package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;

public class PauseMenu extends GameObject {
	
	public static final int WIDTH = 350, HEIGHT = 400;

	public PauseMenu(float x, float y, ObjectId id) {
		super(x, y, id);
		width = WIDTH;
		height = HEIGHT;
	}

	public void tick(ArrayList<GameObject> object) {
	
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRect((int) x, (int) y, width, height);
	}

	public Rectangle getBounds() {
		return null;
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

}
