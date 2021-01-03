package framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

	private ArrayList<GameObject> empty = new ArrayList<GameObject>();
	//	Texture tex = Game.getInstance();

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//		if (!tex.gameOver) {
		if (true) {
			for (int i = 0; i < handler.layer2.size(); i++) {
				GameObject tempObject = handler.layer2.get(i);
				if (tempObject.getId() == ObjectId.Player) {
					if (key == KeyEvent.VK_D && !tempObject.dashing) {
						tempObject.setVelX(5);
						rightPressed = true;
						lastPressedIsLeft = false;
					}
					if (key == KeyEvent.VK_A && !tempObject.dashing) {
						tempObject.setVelX(-5);
						leftPressed = true;
						lastPressedIsLeft = true;
					}
					if (key == KeyEvent.VK_SPACE && !tempObject.isJumping()) {
						tempObject.setJumping(true);
						tempObject.setVelY(-11);
						if (!tempObject.getHanging())
							handler.layer3.add(new DustAnimation(tempObject.getX(), tempObject.getY() + tempObject.getHeight() - 48, handler, ObjectId.DustAnimation));
					}
					if (key == KeyEvent.VK_SHIFT && System.currentTimeMillis() - dashCooldown > 1000) {
						dashCooldown = System.currentTimeMillis();
						tempObject.dashing = true;
						tempObject.setVelX(20 * tempObject.getFacing());
						tempObject.dashStart = System.currentTimeMillis();
					}
					if (key == KeyEvent.VK_R) {
						tempObject.setX(Handler.playerStartX);
						tempObject.setY(Handler.playerStartY);
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
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if (tempObject.getId() == ObjectId.Player) {
				if (key == KeyEvent.VK_D) {
					rightPressed = false;
					if (leftPressed && lastPressedIsLeft)
						tempObject.setVelX(-5);
					else
						tempObject.setVelX(0);
				}
				if (key == KeyEvent.VK_A) {
					leftPressed = false;
					if (rightPressed && !lastPressedIsLeft)
						tempObject.setVelX(5);
					else
						tempObject.setVelX(0);
				}
			}
		}
	}

}
