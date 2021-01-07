package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class ShooterTrap extends GameObject {
	
	private long shootTimer = 0L;
	private int shootCooldown;
	private Handler handler;
	
	private int direction;
	public static final int SHOOT_LEFT = 0;
	public static final int SHOOT_RIGHT = 1;
	public static final int SHOOT_UP = 2;
	public static final int SHOOT_DOWN = 3;
	
	public ShooterTrap(float x, float y, int direction, int shootCooldown, Handler handler, ObjectId id) {
		super(x, y, id);
		this.shootCooldown = shootCooldown;
		this.direction = direction;
		this.handler = handler;
		width = height = 32;
	}

	public void tick(ArrayList<GameObject> object) {
		if (System.currentTimeMillis() - shootTimer > shootCooldown) {
			shootTimer = System.currentTimeMillis();
			switch(direction) {
			case SHOOT_LEFT:
				handler.addObject(new ArrowProjectile((int) x - width, (int) y + 8, ArrowProjectile.LEFT, handler, ObjectId.ArrowProjectile), Handler.MIDDLE_LAYER);
				break;
			case SHOOT_RIGHT:
				handler.addObject(new ArrowProjectile((int) x + width, (int) y + 8, ArrowProjectile.RIGHT, handler, ObjectId.ArrowProjectile), Handler.MIDDLE_LAYER);
				break;
			case SHOOT_UP:
				handler.addObject(new ArrowProjectile((int) x + 8, (int) y - height, ArrowProjectile.UP, handler, ObjectId.ArrowProjectile), Handler.MIDDLE_LAYER);
				break;
			case SHOOT_DOWN:
				handler.addObject(new ArrowProjectile((int) x + 8, (int) y + height, ArrowProjectile.DOWN, handler, ObjectId.ArrowProjectile), Handler.MIDDLE_LAYER);
				break;
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int) x, (int) y, width, height);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

}
