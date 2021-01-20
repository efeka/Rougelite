package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import window.Handler;
import window.Main;

public class LaserAttack extends GameObject {

	private Handler handler;
	private Texture tex = Main.getTex();

	private boolean rayCast = false;
	private float beginX, beginY, endX = 0, endY = 0;
	private double angle;
	
	private int laserTime = 0, laserLife = 60;

	public LaserAttack(float x, float y, float velX, float velY, double angle, Handler handler, ObjectId id) {
		super(x, y, id);
		this.velX = velX * 3;
		this.velY = velY * 3;
		this.handler = handler;
		this.angle = angle + 1.5596856728972892 * 2;
		beginX = x;
		beginY = y;
	}

	public void tick(ArrayList<GameObject> object) {
		x += velX;
		y += velY;

		if (laserTime < laserLife)
			laserTime++;
		else
			handler.removeObject(this);

		collision(handler.layer2);
	}

	private void collision(ArrayList<GameObject> object) {
		if (getPolygonBounds().intersects(handler.player.getBounds())) {
			handler.player.takeDamage(2);
		}
		
		if (!rayCast) {
			for (int i = 0; i < handler.layer2.size(); i++) {
				GameObject tempObject = handler.layer2.get(i);
				if ((tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.ShooterTrap) && tempObject.getCollidable()) {
					if (getBounds().intersects(tempObject.getBounds())) {
						if (!rayCast) {
							rayCast = true;
							endX = x;
							endY = y;
							velX = 0;
							velY = 0;
						}
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		if (rayCast) {
			float distX = endX - beginX;
			float distY = endY - beginY;
			float hypot = (float) Math.sqrt(distX * distX + distY * distY);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.rotate(angle, beginX + 8, beginY + 8);
			g.drawImage(tex.fireLaser[0], (int) beginX + 8, (int) beginY + 8, 16, (int) hypot, null);	
			g2d.rotate(-angle, beginX + 8, beginY + 8);
			
			if (KeyInput.showCollisionBoxes) {
				int[] xPoints = { (int) beginX + 8, (int) beginX + 8, (int) endX, (int) endX + 8};
				int[] yPoints = { (int) beginY + 8, (int) beginY + 8, (int) endY + 8, (int) endY};
				
				g.setColor(Color.white);
				g.drawPolygon(xPoints, yPoints, 4);
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 16, 16);
	}
	
	public Polygon getPolygonBounds() {
		if (rayCast) {
			int[] xPoints = { (int) beginX, (int) beginX + 10, (int) endX, (int) endX + 10};
			int[] yPoints = { (int) beginY, (int) beginY + 10, (int) endY + 10, (int) endY};
			return new Polygon(xPoints, yPoints, 4);
		}
		else
			return new Polygon();
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	public void takeDamage(int damage) {

	}

}
