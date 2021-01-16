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

public class BurningAnimation extends GameObject {

	private Handler handler;
	private Texture tex = Main.getTex();

	private int xOffset, yOffset;
	private int scaleX, scaleY;
	private Animation burningAnim;
	public boolean show = false;
	private boolean onPlayer;

	public BurningAnimation(float x, float y, int xOffset, int yOffset, int scaleX, int scaleY, boolean onPlayer, boolean reverse, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.onPlayer = onPlayer;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		width = height = 32;
		if (!reverse)
			burningAnim = new Animation(4, tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5], tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5], tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5], tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5], tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5], tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5], tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5], tex.burningEffect[0], tex.burningEffect[1], tex.burningEffect[2], tex.burningEffect[3], tex.burningEffect[4], tex.burningEffect[5]);
		else
			burningAnim = new Animation(4, tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0], tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0], tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0], tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0], tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0], tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0], tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0], tex.burningEffect[5], tex.burningEffect[4], tex.burningEffect[3], tex.burningEffect[2], tex.burningEffect[1], tex.burningEffect[0]);
			
	}

	public void tick(ArrayList<GameObject> object) {
		if (onPlayer) {
			x = handler.player.getX() + xOffset + handler.player.getVelX();
			y = handler.player.getY() + yOffset + handler.player.getVelY();	
		}
		
		if (show)
			burningAnim.runAnimation();
	}

	public void render(Graphics g) {
		if (!show)
			return;

		burningAnim.drawAnimation(g, (int) x, (int) y, scaleX, scaleY);
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
