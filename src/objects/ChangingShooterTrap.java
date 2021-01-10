package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import window.Handler;
import window.Main;

public class ChangingShooterTrap extends GameObject {
	
	private int shootingMode = 0;
	private Handler handler;
	private Texture tex = Main.getTex();
	
	private long shootTimer = 0L;
	private int shootCooldown;
	
	public ChangingShooterTrap(float x, float y, int shootCooldown, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.shootCooldown = shootCooldown;
		width = 32;
		height = 32;
	}

	public void tick(ArrayList<GameObject> object) {
		if (System.currentTimeMillis() - shootTimer > shootCooldown) {
			shootTimer = System.currentTimeMillis();
			
			shootingMode++;
			if (shootingMode % 2 == 0) {
				handler.addObject(new BallProjectile(x - 8, y + 8, BallProjectile.LEFT, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
				handler.addObject(new BallProjectile(x + width - 4, y + 8, BallProjectile.RIGHT, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
				handler.addObject(new BallProjectile(x + 8, y, BallProjectile.UP, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
				handler.addObject(new BallProjectile(x + 8, y + height - 4, BallProjectile.DOWN, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
			}
			else {
				handler.addObject(new BallProjectile(x - 3, y - 2, BallProjectile.TOP_LEFT, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
				handler.addObject(new BallProjectile(x + 18, y - 4, BallProjectile.TOP_RIGHT, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
				handler.addObject(new BallProjectile(x - 3, y + 18, BallProjectile.BOT_LEFT, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
				handler.addObject(new BallProjectile(x + 20, y + 18, BallProjectile.BOT_RIGHT, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
			}
			
			//reset it at some point to avoid exceeding the integer limit
			if (shootingMode == 1000)
				shootingMode = 1;
		}
	}

	public void render(Graphics g) {
		if (shootingMode % 2 == 0)
			g.drawImage(tex.changingShooterTrap[0], (int) x, (int) y, width, height, null);
		else
			g.drawImage(tex.changingShooterTrap[1], (int) x, (int) y, width, height, null);
		
		if (KeyInput.showCollisionBoxes) {
			g.setColor(Color.white);
			g.drawRect((int) x, (int) y, width, height);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

}
