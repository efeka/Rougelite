package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import window.Handler;

public class TempEnemy extends GameObject {

	private float gravity = 0.5f;
	private final float MAX_SPEED = 15;

	Handler handler;
	
	private int health = 5;
	boolean alive = true;
	private long invulnerableTimer = 0L;

	public TempEnemy(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = height = 32;
	}

	public void tick(ArrayList<GameObject> object) {
		if (System.currentTimeMillis() - invulnerableTimer > 1000) 
			invulnerable = false;

		x += velX;
		y += velY;

		if (velX > 0) facing = 1;
		if (velX < 0) facing = -1;

		if (falling || jumping) {
			velY += gravity;

			if (velY > MAX_SPEED)
				velY = MAX_SPEED;
		}
		
		collision(handler.layer2);
	}
	
	private void collision(ArrayList<GameObject> object) {
		if (alive && !handler.player.getDashing() && getBounds().intersects(handler.player.getBounds())) 
			handler.player.takeDamage(2);
		
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject.getId() == ObjectId.Block && tempObject.getCollidable()) {
				if (getBounds().intersects(tempObject.getBounds()))
					y = tempObject.getY() - height;
			}
		}
	}

	public void takeDamage(int damage) {
		if (alive && !invulnerable) {
			health -= damage;
			if (health <= 0) {
				alive = false;
				health = 0;
			}
			invulnerableTimer = System.currentTimeMillis();
			invulnerable = true;
		}
	}

	public void render(Graphics g) {
		switch(health) {
		case 5:
			g.setColor(new Color(0, 255, 0));
			break;
		case 4:
			g.setColor(new Color(100, 200, 0));
			break;
		case 3:
			g.setColor(new Color(150, 150, 0));
			break;
		case 2:
			g.setColor(new Color(255, 100, 0));
			break;
		case 1:
			g.setColor(new Color(255, 0, 0));
			break;
		case 0:
			g.setColor(Color.DARK_GRAY);
			break;
		}
		g.fillRect((int) x, (int) y, width, height);

		if (invulnerable) {
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect((int) x, (int) y, width, height);
		}

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

}
