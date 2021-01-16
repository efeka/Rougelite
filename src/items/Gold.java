package items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import objects.PlayerInfo;
import window.Animation;
import window.Handler;
import window.Main;

public class Gold extends GameObject {
	
	private Handler handler;
	private Texture tex = Main.getTex();
	private Animation goldAnim;
	
	public Gold(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = height = 24;
		goldAnim = new Animation(4, tex.gold[0], tex.gold[1], tex.gold[2], tex.gold[3], tex.gold[4], tex.gold[5], tex.gold[0], tex.gold[1], tex.gold[2], tex.gold[3], tex.gold[4], tex.gold[5], tex.gold[0], tex.gold[1], tex.gold[2], tex.gold[3], tex.gold[4], tex.gold[5], tex.gold[0], tex.gold[1], tex.gold[2], tex.gold[3], tex.gold[4], tex.gold[5], tex.gold[0], tex.gold[1], tex.gold[2], tex.gold[3], tex.gold[4], tex.gold[5]);
	}

	public void tick(ArrayList<GameObject> object) {
		if (getBounds().intersects(handler.player.getBounds())) {
			handler.removeObject(this);
			PlayerInfo.gold++;
		}
		goldAnim.runAnimation();
	}

	public void render(Graphics g) {
		goldAnim.drawAnimation(g, (int) x, (int) y, width * 6 / 7, height * 6 / 7);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width * 6 / 7, height * 6 / 7);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	public void takeDamage(int damage) {}

}
