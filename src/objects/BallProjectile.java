package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import window.Animation;
import window.Handler;
import window.Main;

public class BallProjectile extends GameObject {
	
	private int direction;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int TOP_LEFT = 4;
	public static final int BOT_LEFT = 5;
	public static final int TOP_RIGHT = 6;
	public static final int BOT_RIGHT = 7;
	
	private Handler handler;
	
	private Texture tex = Main.getTex();
	private Animation ballAnim;

	public BallProjectile(float x, float y, int direction, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		width = height = 16;
		
		ballAnim = new Animation(2, tex.ball[0], tex.ball[1], tex.ball[2], tex.ball[3]);
		
		switch(direction) {
		case LEFT:
			velX = -4;
			velY = 0;
			break;
		case RIGHT:
			velX = 4;
			velY = 0;
			break;
		case UP:
			velX = 0;
			velY = -4;
			break;
		case DOWN:
			velX = 0;
			velY = 4;
			break;
		case TOP_LEFT:
			velX = -4;
			velY = -4;
			break;
		case BOT_LEFT:
			velX = -4;
			velY = 4;
			break;
		case TOP_RIGHT:
			velX = 4;
			velY = -4;
			break;
		case BOT_RIGHT:
			velX = 4;
			velY = 4;
			break;
		}
	}

	public void tick(ArrayList<GameObject> object) {
		x += velX;
		y += velY;
		
		ballAnim.runAnimation();
		
		collision(handler.layer2);
	}
	
	private void collision(ArrayList<GameObject> object) {
		if (!handler.player.getInvulnerable()) {
			if (getBounds().intersects(handler.player.getBounds())) {
				object.remove(this);
				handler.player.takeDamage(1);
			}
		}
		
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if ((tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.ShooterTrap) && tempObject.getCollidable()) {
				if (getBounds().intersects(tempObject.getBounds()))
					object.remove(this);
			}
		}
	}

	public void render(Graphics g) {
		ballAnim.drawAnimation(g, (int) x, (int) y, width, height);
		
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
