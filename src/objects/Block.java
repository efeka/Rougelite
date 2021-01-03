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

	private int width = 32;
	private int height = 32;

	public Block(float x, float y, int type, boolean climbable, ObjectId id) {
		super(x, y, id);
		this.type = type;
		this.climbable = climbable;
	}

	public void tick(ArrayList<GameObject> object) {}

	public void render(Graphics g) {
		g.drawImage(tex.blocks[type], (int) x, (int) y, null);

		if (KeyInput.showCollisionBoxes) {
			if (!climbable)
				g.setColor(Color.white);
			else
				g.setColor(Color.black);
			g.drawRect((int) x, (int) y, width, height);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}
