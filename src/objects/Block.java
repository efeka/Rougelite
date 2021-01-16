package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import window.Main;

public class Block extends GameObject {

	Texture tex = Main.getTex();
	private int type;

	public Block(float x, float y, int type, boolean climbable, boolean collidable, ObjectId id) {
		super(x, y, id);
		this.type = type;
		this.climbable = climbable;
		this.collidable = collidable;
		width = height = 32;
	}

	public void tick(ArrayList<GameObject> object) {}

	public void render(Graphics g) {
		g.drawImage(tex.blocks[type], (int) x, (int) y, null);

		if (KeyInput.showCollisionBoxes) {
			if (!climbable)
				g.setColor(Color.white);
			else
				g.setColor(Color.yellow);
			g.drawRect((int) x, (int) y, width, height);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

}
