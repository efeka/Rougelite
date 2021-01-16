package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Handler;
import window.Main;

public class BigShooterTrap extends GameObject {

	private Handler handler;
	private Texture tex = Main.getTex();

	private float aimX = 0, aimY = 0;
	private float relativeX = 0, relativeY = 0;
	private double angle = 0;
	private double hypot = 0;
	
	private long shootTimer = 0L;
	private int shootCooldown = 2000;
	
	private int distance = 0;
	private int range = 500;

	public BigShooterTrap(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = height = 48;
	}

	public void tick(ArrayList<GameObject> object) {
		aimX = handler.player.getX() + handler.player.getWidth() / 2;
		aimY = handler.player.getY() + handler.player.getHeight() / 2;

		if (System.currentTimeMillis() - shootTimer > shootCooldown) {
			shootTimer = System.currentTimeMillis();
			
			relativeX = aimX - (x + width / 2);
			relativeY = aimY - (y + height / 2);
			angle = Math.atan2(relativeY, relativeX) + 1.5596856728972892 * 3;
			
			hypot = Math.hypot(aimX - x, aimY - y);
			velX = (float) (5 * (aimX - x) / hypot);
			velY = (float) (5 * (aimY - y) / hypot);
			
			int fromX = (int) (x + width / 2);
			int fromY = (int) (y + height / 2);
			int diffX = (int) ((fromX - aimX) * (fromX - aimX));
			int diffY = (int) ((fromY - aimY) * (fromY - aimY));
			distance = (int) Math.sqrt(diffX + diffY);
			
			if (distance < range)
				handler.addObject(new BallProjectile(x , y, velX, velY, handler, ObjectId.BallProjectile), Handler.MIDDLE_LAYER);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawRect((int) x, (int) y, width, height);
		
		int barrelW = tex.cannonTrap[0].getWidth();
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(angle, x + width/2, y + height/2);
		g2d.drawImage(tex.cannonTrap[0], (int) x + width/2 - barrelW/2, (int) y + height/2, null);
		g2d.rotate(-angle, x + width/2, y + height/2);

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
