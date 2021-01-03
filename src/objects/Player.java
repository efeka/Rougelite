package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import one_time_animations.PlayerClimbAnimation;
import window.Animation;
import window.Camera;
import window.Handler;
import window.Main;

public class Player extends GameObject {

	private float gravity = 0.5f;
	private final float MAX_SPEED = 15;

	private Handler handler;
	private Camera cam;

	private int hangX = 0, hangY = 0;
	private long climbTime = 0L;

	Texture tex = Main.getTex();

	private Animation playerWalk, playerWalkLeft;

	public Player(float x, float y, Handler handler, Camera cam, ObjectId id) {
		super(x, y, id);
		width = height = 48;
		this.handler = handler;
		this.cam = cam;
		playerWalk = new Animation(4, tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4]);
		playerWalkLeft = new Animation(4, tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9], tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9], tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9], tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9]);
	}

	public void tick(ArrayList<GameObject> object) {
		if (hanging) {
			jumping = false;
			cam.move(x, y, hangX + width * 1 / 2 * facing, hangY - height, 36);
			if (System.currentTimeMillis() - climbTime > 600) {
				x = hangX + width * 1 / 2 * facing;
				y = hangY - height;
				hanging = false;
			}
			else {
				velX = 0;
				velY = 0;
				falling = false;
			}
		}
		else {
			x += velX;
			if (!dashing)
				y += velY;
			else {
				if (System.currentTimeMillis() - dashStart > 250) {
					dashing = false;
					if (KeyInput.rightPressed || KeyInput.leftPressed)
						velX = 5 * facing;
					else
						velX = 0;
				}
			}

			if (velX > 0) facing = 1;
			if (velX < 0) facing = -1;

			if (falling || jumping) {
				velY += gravity;

				if (velY > MAX_SPEED)
					velY = MAX_SPEED;
			}
		}

		try {
			collision(object);
		}
		catch (Exception e) {}

		playerWalk.runAnimation();
		playerWalkLeft.runAnimation();
	}

	private void collision(ArrayList<GameObject> object) {
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if (tempObject.getId() == ObjectId.Block) {
				if (getBoundsBot().intersects(tempObject.getBounds())) {
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				}
				else
					falling = true;

				if (getBoundsSides().intersects(tempObject.getBounds())) {
					if (velX > 0) 
						x = tempObject.getX() - width + 5;
					else if (velX < 0)
						x = tempObject.getX() + 32 - 5;
				}

				if (getBoundsTop().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + 32;
					velY = 0;
				}

				if (!hanging && facing == 1 && tempObject.getClimbable() && !getHangCheckTopRight().intersects(tempObject.getBounds()) && getHangCheckBotRight().intersects(tempObject.getBounds())) {
					hanging = true;
					hangX = (int) tempObject.getX() - width;
					hangY = (int) tempObject.getY();
					climbTime = System.currentTimeMillis();
					handler.layer2.add(new PlayerClimbAnimation((int) hangX + 28, (int) hangY - 38, facing, handler, ObjectId.PlayerClimbAnimation));
				}
				else if (!hanging && facing == -1 && tempObject.getClimbable() && !getHangCheckTopLeft().intersects(tempObject.getBounds()) && getHangCheckBotLeft().intersects(tempObject.getBounds())) {
					hanging = true;
					hangX = (int) tempObject.getX() + 32;
					hangY = (int) tempObject.getY();
					climbTime = System.currentTimeMillis();
					handler.layer2.add(new PlayerClimbAnimation((int) hangX - 28, (int) hangY - 38, facing, handler, ObjectId.PlayerClimbAnimation));
				}

			}
		}

	}

	public void render(Graphics g) {
		//jumping
		if (jumping && !hanging) {
			if (facing == 1) {
				if (velY < 0)
					g.drawImage(tex.playerJump[0], (int) x, (int) y, width, height, null);
				else if (velY > 0)
					g.drawImage(tex.playerJump[1], (int) x, (int) y, width, height, null);
			}
			else if (facing == -1) {
				if (velY < 0)
					g.drawImage(tex.playerJump[3], (int) x, (int) y, width, height, null);
				else if (velY > 0)
					g.drawImage(tex.playerJump[2], (int) x, (int) y, width, height, null);
			}
		}
		//idle
		else if (velX == 0) {
			if (!hanging) {
				if (facing == 1) {
					g.drawImage(tex.player[0], (int) x, (int) y, width, height, null);
				}
				else if (facing == -1) {
					g.drawImage(tex.player[13], (int) x - 5, (int) y, width, height, null);
				}
			}
		}
		//walking
		else if (velX != 0) {
			if (facing == 1)
				playerWalk.drawAnimation(g, (int) x, (int) y, width, height);
			else {
				playerWalkLeft.drawAnimation(g, (int) x - 5, (int) y, width, height);
			}
		}
		//		else if (jumping) {
		//			if (facing == 1)
		//				g.drawImage(tex.playerJump[0], (int)x, (int)y, 48, 96, null);
		//			else if (facing == -1)
		//				g.drawImage(tex.playerJump[1], (int)x, (int)y, 48, 96, null);
		//		}
		//		else {
		//			if (velX != 0) {
		//				if (facing == 1) {
		//					playerWalk.drawAnimation(g, (int)x, (int)y, 48, 96);
		//				}
		//				else {
		//					playerWalkLeft.drawAnimation(g, (int)x, (int)y, 48, 96);
		//					if (!playerWalkLeft.getPlayedOnce())
		//						System.out.println("played once");
		//				}
		//			}
		//			else
		//				if (facing == 1)
		//					g.drawImage(tex.player[0], (int)x, (int)y, 48, 96, null);
		//				else
		//					g.drawImage(tex.player[7], (int)x, (int)y, 48, 96, null);
		//
		//		}

		if (KeyInput.showCollisionBoxes) {
			g.setColor(Color.green);
			g.drawRect((int) x + width / 2 - width / 4, (int) y + height / 2 + height / 4 + 6, (int) width / 2, (int) height / 4 - 6);

			g.setColor(Color.blue);
			g.drawRect((int) x + width / 2 - width / 4, (int) y, (int) width / 2, (int) height / 4);

			//			g.setColor(Color.cyan);
			//			g.drawRect((int) x + width - 10 + (int) velX, (int) y + height * 1 / 5, 10 + (int) velX / 3, height * 3 / 5);
			//
			//			g.setColor(Color.red);
			//			g.drawRect((int) x, (int) y + height * 1 / 5, 10, height * 3 / 5);

			g.setColor(Color.magenta);
			g.drawRect((int) x - width / 4 + width / 6, (int) y, width / 3, 10);

			g.setColor(Color.pink);
			g.drawRect((int) x - width / 4 + width / 6, (int) y + 10, width / 3, 10);

			g.setColor(Color.yellow);
			g.drawRect((int) x + width * 3 / 4, (int) y, width / 3, 10);

			g.setColor(Color.white);
			g.drawRect((int) x + width * 3 / 4, (int) y + 10, width / 3, 10);

			g.setColor(Color.red);
			g.drawRect((int) x + (int) velX + 10, (int) y + 10, width * 1 / 2  + (int) velX / 3, height - 20);
		}
	}

	public Rectangle getBoundsSides() {
		return new Rectangle((int) x + (int) velX + 10, (int) y + 10, width * 1 / 2  + (int) velX / 3, height - 20);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x + width / 2 - width / 4, (int) y + height / 2, (int) width / 2, (int) height / 2);
	}

	public Rectangle getBoundsBot() {
		return new Rectangle((int) x + width / 2 - width / 4, (int) y + height / 2 + height / 4 + 6, (int) width / 2, (int) height / 4 - 6);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) x + width / 2 - width / 4, (int) y, (int) width / 2, (int) height / 4);
	}

	//	public Rectangle getBoundsRight() {
	//		return new Rectangle((int) x + width - 10 + (int) velX, (int) y + height * 1 / 5, 10 + (int) velX / 3, height * 3 / 5);
	//	}
	//
	//	public Rectangle getBoundsLeft() {
	//		return new Rectangle((int) x, (int) y + height * 1 / 5, 10, height * 3 / 5);
	//	}

	public Rectangle getHangCheckTopLeft() {
		return new Rectangle((int) x - width / 4 + width / 6, (int) y, width / 3, 10);
	}

	public Rectangle getHangCheckBotLeft() {
		return new Rectangle((int) x - width / 4 + width / 6, (int) y + 10, width / 3, 10);
	}

	public Rectangle getHangCheckTopRight() {
		return new Rectangle((int) x + width * 3 / 4, (int) y, width / 3, 10);
	}

	public Rectangle getHangCheckBotRight() {
		return new Rectangle((int) x + width * 3 / 4, (int) y + 10, width / 3, 10);
	}

}
