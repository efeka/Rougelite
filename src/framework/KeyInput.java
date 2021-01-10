package framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import objects.PlayerInfo;
import one_time_animations.DustAnimation;
import window.Handler;
import window.Main;

public class KeyInput extends KeyAdapter{

	Handler handler;

	long dashCooldown = 0L;

	public static boolean rightPressed = false, rightReleased = false;
	public static boolean leftPressed = false, leftReleased = false;
	private boolean lastPressedIsLeft = false;

	public static boolean showCollisionBoxes = false;

	Texture tex = Main.getTex();

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//		if (!tex.gameOver) {

		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if (tempObject.getId() == ObjectId.Player) {
				if (key == KeyEvent.VK_D && !tempObject.dashing && !tempObject.crouching) {
					if (!tempObject.hanging)
						tempObject.setVelX(5);
					rightPressed = true;
					lastPressedIsLeft = false;
				}
				if (key == KeyEvent.VK_A && !tempObject.dashing && !tempObject.crouching) {
					if (!tempObject.hanging && !tempObject.attacking)
						tempObject.setVelX(-5);
					leftPressed = true;
					lastPressedIsLeft = true;
				}
				if (key == KeyEvent.VK_S && !tempObject.dashing) {
					if (!tempObject.hanging && !tempObject.attacking && !tempObject.jumping) {
						if (!tempObject.getCrouching())
							tempObject.setY(tempObject.getY() + 16);
						tempObject.setCrouching(true);
					}
				}
				if (key == KeyEvent.VK_SPACE && (!tempObject.crouching && (tempObject.isJumping() && tempObject.hasDoubleJump) || !tempObject.isJumping())) {
					if (tempObject.isJumping() && tempObject.hasDoubleJump)
						tempObject.hasDoubleJump = false;
					tempObject.setJumping(true);
					tempObject.setVelY(-9);
					if (!tempObject.getHanging())
						handler.layer3.add(new DustAnimation(tempObject.getX(), tempObject.getY() + tempObject.getHeight() - 48, handler, ObjectId.DustAnimation));
				}
				if (key == KeyEvent.VK_SHIFT && !tempObject.getCrouching() && !tempObject.hanging && System.currentTimeMillis() - dashCooldown > 1000) {
					dashCooldown = System.currentTimeMillis();
					tempObject.dashing = true;
					tempObject.setVelY(0);
					tempObject.setVelX(20 * tempObject.getFacing());
					tempObject.dashStart = System.currentTimeMillis();
				}
				if (key == KeyEvent.VK_R) {
					tempObject.setX(Handler.playerStartX);
					tempObject.setY(Handler.playerStartY);
					PlayerInfo.health = PlayerInfo.maxHealth;
				}
			}
		}
		if ((key == KeyEvent.VK_ESCAPE)) {
			Main.state = Main.STATE.MENU;
		}
		if (key == KeyEvent.VK_X) {
			showCollisionBoxes = !showCollisionBoxes;
		}
	}


	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if (tempObject.getId() == ObjectId.Player) {
				if (key == KeyEvent.VK_D) {
					rightPressed = false;
					if (leftPressed && lastPressedIsLeft && !tempObject.dashing)
						tempObject.setVelX(-5);
					else
						tempObject.setVelX(0);
				}
				if (key == KeyEvent.VK_A) {
					leftPressed = false;
					if (rightPressed && !lastPressedIsLeft && !tempObject.dashing)
						tempObject.setVelX(5);
					else
						tempObject.setVelX(0);
				}
				if (key == KeyEvent.VK_S && !tempObject.dashing) 
					tempObject.setCrouching(false);
			}
		}
	}

}
