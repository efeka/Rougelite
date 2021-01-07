package one_time_animations;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Animation;
import window.Handler;
import window.Main;

public class DustAnimation extends GameObject {
	
	Animation dustAnim;
	Texture tex = Main.getTex();
	Handler handler;

	public DustAnimation(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		dustAnim = new Animation(1, tex.dustEffect[0], tex.dustEffect[1], tex.dustEffect[2], tex.dustEffect[3], tex.dustEffect[4]);
		
	}

	public void tick(ArrayList<GameObject> object) {
		dustAnim.runAnimation();		
	}

	public void render(Graphics g) {
		dustAnim.drawAnimation(g, (int) x, (int) y, 48, 48);
		if (dustAnim.getPlayedOnce())
			handler.removeObject(this); 

	}

	public Rectangle getBounds() {
		return null;
	}

	public Rectangle getAttackBounds() {
		return null;
	}
}
