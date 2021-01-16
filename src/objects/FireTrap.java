package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import one_time_animations.BurningAnimation;
import window.Animation;
import window.Handler;
import window.Main;

public class FireTrap extends GameObject {

	private Handler handler;
	private Texture tex = Main.getTex();
	
	private int attackTimer = 120;
	private int timer = 0;
	
	private Animation fireAnim;
	private BurningAnimation burningAnim1, burningAnim2;
	
	public FireTrap(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = height = 32;
		burningAnim1 = new BurningAnimation(x - 10, y - 96, 0, 0, 32, 107, false, false, handler, ObjectId.BurningAnimation);
		burningAnim2 = new BurningAnimation(x + 7, y - 96, 0, 0, 32, 107, false, true, handler, ObjectId.BurningAnimation);
		handler.addObject(burningAnim1, Handler.MIDDLE_LAYER);
		handler.addObject(burningAnim2, Handler.MIDDLE_LAYER);
	}

	public void tick(ArrayList<GameObject> object) {
		if (attacking && getAttackBounds().intersects(handler.player.getBounds())) { 
			handler.player.takeDamage(1);
			handler.player.setOnFire(true);
		}
		
		timer++;
		if (timer == attackTimer) 
			attacking = true;
		if (timer == attackTimer * 2) {
			attacking = false;
			timer = 0;
		}

	}

	public void render(Graphics g) {
		g.drawImage(tex.fireTrap, (int) x, (int) y, width, height, null);
		
		burningAnim1.show = attacking;
		burningAnim2.show = attacking;
		
		if (KeyInput.showCollisionBoxes) {
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setColor(Color.white);
			g2d.draw(getBounds());
			
			g2d.setColor(Color.yellow);
			g2d.draw(getAttackBounds());
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getAttackBounds() {
		return new Rectangle((int) x, (int) y - 96, width, 96);
	}

	public void takeDamage(int damage) {

	}
	
}
