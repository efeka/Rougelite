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

public class AttackAnimation extends GameObject {
	
	Animation attackAnim;
	Texture tex = Main.getTex();
	Handler handler;

	public AttackAnimation(float x, float y, int facing, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.facing = facing;
		if (facing == 1)
			attackAnim = new Animation(3, tex.attackEffect[0], tex.attackEffect[1], tex.attackEffect[2], tex.attackEffect[3]);
		else
			attackAnim = new Animation(3, tex.attackEffect[7], tex.attackEffect[6], tex.attackEffect[5], tex.attackEffect[4]);
	}

	public void tick(ArrayList<GameObject> object) {
		if (facing == 1) {
			x = handler.player.getX() + 48;
			y = handler.player.getY() + 8;			
		}
		else {
			x = handler.player.getX() - 64;
			y = handler.player.getY() + 8;		
		}
		attackAnim.runAnimation();		
	}

	public void render(Graphics g) {
		if (facing == 1)
			attackAnim.drawAnimation(g, (int) x, (int) y, 64, 32);
		else
			attackAnim.drawAnimation(g, (int) x, (int) y, 64, 32);
		
		if (attackAnim.getPlayedOnce())
			handler.removeObject(this); 
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
