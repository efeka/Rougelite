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

public class PopUpSpikeAnimation extends GameObject {
	
	Animation spikeAnim;
	Texture tex = Main.getTex();
	Handler handler;

	public PopUpSpikeAnimation(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		spikeAnim = new Animation(2, tex.popupSpike[0], tex.popupSpike[1], tex.popupSpike[2], tex.popupSpike[3], tex.popupSpike[4], tex.popupSpike[5], tex.popupSpike[6], tex.popupSpike[7], tex.popupSpike[8], tex.popupSpike[9], tex.popupSpike[10], tex.popupSpike[11], tex.popupSpike[12]);
		
	}

	public void tick(ArrayList<GameObject> object) {
		spikeAnim.runAnimation();		
	}

	public void render(Graphics g) {
		spikeAnim.drawAnimation(g, (int) x, (int) y, 32, 32);
		if (spikeAnim.getPlayedOnce())
			handler.removeObject(this); 

	}

	public Rectangle getBounds() {
		return null;
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	public void takeDamage(int damage) {
	
	}
}

