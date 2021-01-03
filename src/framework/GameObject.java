package framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GameObject {
	
	protected float x, y;
	protected int width, height;
	protected ObjectId id;
	protected float velX = 0, velY = 0;
	
	protected boolean dashing = false;
	protected boolean hanging = false;
	protected long dashStart = 0L;
	
	protected int maxJumps = 2;
	protected int jumpCount = 0;
	
	protected boolean falling = true;
	protected boolean jumping = false;
	protected int facing = 1;
	
	protected boolean climbable = false;

	public GameObject(float x, float y, ObjectId id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick(ArrayList<GameObject> object);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public boolean getHanging() {
		return hanging;
	}
	
	public void setHanging(boolean hanging) {
		this.hanging = hanging;
	}
	
	public boolean isFalling() {
		return falling;
	}
	
	public int getFacing() { 
		return facing; 
	}
	
	public void setFalling(boolean falling) { 
		this.falling = falling;
	}
	
	public boolean isJumping() { 
		return jumping;
	}
	
	public void setJumping(boolean jumping) { 
		this.jumping = jumping; 
	}
	
	public float getX() { 
		return x; 
	}
	
	public float getY() { 
		return y; 
	}
	
	public void setX(float x) { 
		this.x = x; 
	}
	
	public void setY(float y) {
		this.y = y; 
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getVelX() { 
		return velX; 
	}
	
	public float getVelY() { 
		return velY; 
	}
	
	public void setVelX(float velX) { 
		this.velX = velX; 
	}
	
	public void setVelY(float velY) { 
		this.velY = velY; 
	}
	
	public boolean getClimbable() {
		return climbable;
	}
	
	public ObjectId getId() { 
		return id; 
	}
	
}
