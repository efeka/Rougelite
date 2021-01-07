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
	protected long dashStart = 0L;
	protected boolean hanging = false;
	protected boolean attacking = false;
	protected boolean crouching = false;
	protected boolean hasDoubleJump = true;
	protected boolean invulnerable = false;
	
	protected boolean falling = true;
	protected boolean jumping = false;
	protected int facing = 1;
	
	protected boolean climbable = false;
	protected boolean collidable = true;

	public GameObject(float x, float y, ObjectId id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick(ArrayList<GameObject> object);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract Rectangle getAttackBounds();
	
	public boolean getInvulnerable() {
		return invulnerable;
	}
	
	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}
	
	public boolean getCollidable() {
		return collidable;
	}
	
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	
	public boolean getCrouching() {
		return crouching;
	}
	
	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
	}
	
	public boolean getAttacking() {
		return attacking;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
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
	
	public void setFacing(int facing) {
		this.facing = facing;
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
