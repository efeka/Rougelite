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

public class PlayerClimbAnimation extends GameObject {
	
	private static boolean playing = false;
	
	Animation playerClimb;
	Texture tex = Main.getTex();
	Handler handler;
	
	public PlayerClimbAnimation(float x, float y, int facing, Handler handler, ObjectId id) {
		super(x, y, id);
		if (playing)
			return;
		playing = true;
		this.handler = handler;
		if (facing == 1)
			playerClimb = new Animation(4, tex.playerClimb[0], tex.playerClimb[1], tex.playerClimb[2], tex.playerClimb[3], tex.playerClimb[4], tex.playerClimb[5], tex.playerClimb[6]);
		else
			playerClimb = new Animation(4, tex.playerClimb[13], tex.playerClimb[12], tex.playerClimb[11], tex.playerClimb[10], tex.playerClimb[9], tex.playerClimb[8], tex.playerClimb[7]);
	}

	public void tick(ArrayList<GameObject> object) {
		playerClimb.runAnimation();		
	}

	public void render(Graphics g) {
		playerClimb.drawAnimation(g, (int) x, (int) y, 50, 80);
		if (playerClimb.getPlayedOnce()) {
			playing = false;
			handler.removeObject(this); 
		}
		
	}

	public Rectangle getBounds() {
		return null;
	}

	public Rectangle getAttackBounds() {
		return null;
	}
	
}
