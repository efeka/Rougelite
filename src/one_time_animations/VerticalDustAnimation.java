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

public class VerticalDustAnimation extends GameObject {
	
	Animation dustAnim;
	Texture tex = Main.getTex();
	Handler handler;

	public VerticalDustAnimation(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		dustAnim = new Animation(4, tex.verticalDustEffect[0], tex.verticalDustEffect[1], tex.verticalDustEffect[2], tex.verticalDustEffect[3], tex.verticalDustEffect[4]);
		
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
