package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import window.Handler;
import window.Main;

public class ArrowProjectile extends GameObject {
	
	private Handler handler;
	private Texture tex = Main.getTex();
	
	private int direction;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	public ArrowProjectile(float x, float y, int direction, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.direction = direction;
		
		switch(direction) {
		case LEFT:
			velX = -6;
			velY = 0;
			break;
		case RIGHT:
			velX = 6;
			velY = 0;
			break;
		case UP:
			velX = 0;
			velY = -6;
			break;
		case DOWN:
			velX = 0;
			velY = 6;
			break;
		}
		
		if (velY == 0) {
			width = 32;
			height = 16;
		}
		else if (velX == 0) {
			width = 16;
			height = 32;
		}
	}

	public void tick(ArrayList<GameObject> object) {
		x += velX;
		y += velY;
		
		collision(handler.layer2);
	}
	
	private void collision(ArrayList<GameObject> object) {
		if (!handler.player.getDashing()) {
			if (getBounds().intersects(handler.player.getBounds())) {
				object.remove(this);
				handler.player.takeDamage(1);
			}
		}
		
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if ((tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.ShooterTrap || tempObject.getId() == ObjectId.ChangingShooterTrap) && tempObject.getCollidable()) {
				if (getBounds().intersects(tempObject.getBounds()))
					object.remove(this);
			}
		}
	}

	public void render(Graphics g) {
		switch(direction) {
		case LEFT:
			g.drawImage(tex.arrow[direction], (int) x, (int) y - 8, 32, 32, null);
			break;
		case RIGHT:
			g.drawImage(tex.arrow[direction], (int) x, (int) y - 8, 32, 32, null);
			break;
		case UP:
			g.drawImage(tex.arrow[direction], (int) x - 8, (int) y, 32, 32, null);
			break;
		case DOWN:
			g.drawImage(tex.arrow[direction], (int) x - 8, (int) y, 32, 32, null);
			break;
		}
		
		if (KeyInput.showCollisionBoxes) 
			g.drawRect((int) x, (int) y, width, height);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

}
