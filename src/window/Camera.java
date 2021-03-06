package window;

import framework.GameObject;

public class Camera {

	private float x, y;

	public float moveX = 0, moveY = 0;
	public int stepCount = 0, maxSteps = 0;
	private boolean moving = false;

	public Camera(float x, float y) {
		this.x = (int) x;
		this.y = (int) y;
	}

	public void tick(GameObject player) {
		if (stepCount++ < maxSteps) {
			x += moveX;
			y += moveY;
		}
		else {
			moving = false;
			moveX = moveY = 0;
			x = (int) (-player.getX() + Window.getWidth() / 2);
			y = (int) (-player.getY() + Window.getHeight() / 2);
		}
	}

	public void move(float currX, float currY, float destX, float destY, int steps) {
		if (moving)
			return;
		
		moveX = (currX - destX) / steps;
		moveY = (currY - destY) / steps;
		maxSteps = steps;
		stepCount = 0;
		moving = true;
	}
	
	public boolean isMoving() {
		return moving;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = (int) x;
	}

	public void setY(float y) {
		this.y = (int) y;
	}
}
