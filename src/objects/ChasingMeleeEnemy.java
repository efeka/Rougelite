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
import window.Handler;
import window.Main;

public class ChasingMeleeEnemy extends GameObject {

	private Handler handler;
	private Texture tex = Main.getTex();

	private float gravity = 0.5f;
	private final float MAX_SPEED = 15;
	private float x1 = 0, x2 = 0;
	
	private long invulnerableTimer = 0L;
	private boolean alive = true;
	private int health = 100;
	private boolean attackComplete = false;

	public ChasingMeleeEnemy(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = height = 32;
	}

	public void tick(ArrayList<GameObject> object) {
		if (System.currentTimeMillis() - invulnerableTimer > 1000) 
			invulnerable = false;
		
		velX = calculateVelX(handler.player.getX());
		x += velX;
		y += velY;

		x1 = x;		
		collision(handler.layer2);
		x2 = x;
		
		float diff = Math.abs(x1 - x2);

		if (!attacking && !jumping && diff == 3) {
			jumping = true;
			velY = -9;
		}

		if (velX > 0) facing = 1;
		if (velX < 0) facing = -1;

		if (falling || jumping) {
			velY += gravity;

			if (velY > MAX_SPEED)
				velY = MAX_SPEED;
		}

	}

	private void collision(ArrayList<GameObject> object) {
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if ((tempObject.getId() == ObjectId.Block) && tempObject.getCollidable()) {
				if (getBoundsBot().intersects(tempObject.getBounds())) {
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				}
				else
					falling = true;
				
				if (getBoundsTop().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + tempObject.getHeight();
					velY = 0;
				}
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.getX() - width;
					velX = 0;
				}
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					x = tempObject.getX() + tempObject.getWidth();
					velX = 0;
				}

			}
		}
	}

	private float calculateVelX(float playerX) {
		float diff = Math.abs(playerX - x);
		int tolerence = 60;
		if (diff < tolerence && diff > -tolerence) {
			attacking = true;
			return 0;
		}
		attacking = false;

		if (x < playerX)
			return 3;
		else
			return -3;
	}
	
	public void takeDamage(int damage) {
		if (alive && !invulnerable) {
			velY = -4;
			velX = 0;
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
		if (!attacking)
			g.setColor(Color.red);
		else
			g.setColor(Color.blue);
		g.fillRect((int) x, (int) y, width, height);
		if (invulnerable) {
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect((int) x, (int) y, width, height);
		}
			
		if (KeyInput.showCollisionBoxes) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(Color.orange);
			g2d.draw(getBounds());

			g2d.setColor(Color.cyan);
			g2d.draw(getBoundsBot());

			g2d.setColor(Color.white);
			g2d.draw(getBoundsRight());

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.draw(getBoundsLeft());

			g2d.setColor(Color.yellow);
			g2d.draw(getBoundsTop());
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getBoundsBot() {
		return new Rectangle((int) x + 10, (int) y + height - 10, width - 20, 10);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) x + 10, (int) y + 5, width - 20, 10);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle((int) x + width - 5, (int) y + 10, 5, height - 20);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y + 10, 5, height - 20);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

}
