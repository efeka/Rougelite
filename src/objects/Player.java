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
import one_time_animations.PlayerClimbAnimation;
import one_time_animations.Trail;
import window.Animation;
import window.Camera;
import window.Handler;
import window.Main;

public class Player extends GameObject {

	private float gravity = 0.5f;
	private final float MAX_SPEED = 15;

	private Handler handler;
	private Camera cam;
	private Texture tex = Main.getTex();

	private int hangX = 0, hangY = 0;
	private long climbTime = 0L;
	public long attackTime = 0L;
	private long invulnerableTimer = 0L;

	private boolean alive = true;

	private Animation playerWalk, playerWalkLeft;
	private Animation invulIdleRight, invulIdleLeft, invulWalk, invulWalkLeft, invulJump, invulJumpLeft, invulFall, invulFallLeft, invulCrouch, invulCrouchLeft;

	public Player(float x, float y, Handler handler, Camera cam, ObjectId id) {
		super(x, y, id);
		width = height = 48;
		this.handler = handler;
		this.cam = cam;
		playerWalk = new Animation(4, tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6], tex.player[5], tex.player[4]);
		playerWalkLeft = new Animation(4, tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9], tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9], tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9], tex.player[12], tex.player[11], tex.player[10], tex.player[9], tex.player[8], tex.player[7], tex.player[8], tex.player[9]);

		invulWalk = new Animation(4, tex.playerInvulnerable[1], tex.playerInvulnerable[2], tex.playerInvulnerable[3], tex.playerInvulnerable[4], tex.playerInvulnerable[5], tex.playerInvulnerable[6], tex.playerInvulnerable[5], tex.playerInvulnerable[4], tex.playerInvulnerable[1], tex.playerInvulnerable[2], tex.playerInvulnerable[3], tex.playerInvulnerable[4], tex.playerInvulnerable[5], tex.playerInvulnerable[6], tex.playerInvulnerable[5], tex.playerInvulnerable[4], tex.playerInvulnerable[1], tex.playerInvulnerable[2], tex.playerInvulnerable[3], tex.playerInvulnerable[4], tex.playerInvulnerable[5], tex.playerInvulnerable[6], tex.playerInvulnerable[5], tex.playerInvulnerable[4]);
		invulWalkLeft = new Animation(4, tex.playerInvulnerable[12], tex.playerInvulnerable[11], tex.playerInvulnerable[10], tex.playerInvulnerable[9], tex.playerInvulnerable[8], tex.playerInvulnerable[7], tex.playerInvulnerable[8], tex.playerInvulnerable[9], tex.playerInvulnerable[12], tex.playerInvulnerable[11], tex.playerInvulnerable[10], tex.playerInvulnerable[9], tex.playerInvulnerable[8], tex.playerInvulnerable[7], tex.playerInvulnerable[8], tex.playerInvulnerable[9], tex.playerInvulnerable[12], tex.playerInvulnerable[11], tex.playerInvulnerable[10], tex.playerInvulnerable[9], tex.playerInvulnerable[8], tex.playerInvulnerable[7], tex.playerInvulnerable[8], tex.playerInvulnerable[9]);
		invulIdleRight = new Animation(4, tex.player[0], tex.playerInvulnerable[0]);
		invulIdleLeft = new Animation(4, tex.player[13], tex.playerInvulnerable[13]);
		invulJump = new Animation(4, tex.playerJump[0], tex.playerJumpInvulnerable[0]);
		invulFall = new Animation(4, tex.playerJump[1], tex.playerJumpInvulnerable[1]);
		invulJumpLeft = new Animation(4, tex.playerJump[3], tex.playerJumpInvulnerable[3]);
		invulFallLeft = new Animation(4, tex.playerJump[2], tex.playerJumpInvulnerable[2]);
		invulCrouch = new Animation(4, tex.playerCrouch[0], tex.playerCrouchInvulnerable[0]);
		invulCrouchLeft = new Animation(4, tex.playerCrouch[1], tex.playerCrouchInvulnerable[1]);
	}

	public void tick(ArrayList<GameObject> object) {
		if (cam.isMoving()) {
			x -= cam.moveX;
			y -= cam.moveY;
		}

		if (System.currentTimeMillis() - invulnerableTimer > 1000) 
			invulnerable = false;


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
			if (!dashing) {
				y += velY;
			}
			else {
				invulnerable = true;
				if (facing == 1)
					handler.addObject(new Trail(x, y, tex.playerDash[0], 0.11f, handler, ObjectId.Trail), Handler.MIDDLE_LAYER);
				else
					handler.addObject(new Trail(x, y, tex.playerDash[1], 0.11f, handler, ObjectId.Trail), Handler.MIDDLE_LAYER);

				if (System.currentTimeMillis() - dashStart > 150) {
					dashing = false;
					if (KeyInput.rightPressed || KeyInput.leftPressed)
						velX = 5 * facing;
					else
						velX = 0;
				}
			}

			if (attacking) {
				velX = 0;
				if (System.currentTimeMillis() - attackTime > 300) 
					attacking = false;
			}
			else {
				if (!dashing && KeyInput.rightPressed)
					velX = 5;
				else if (!dashing && KeyInput.leftPressed)
					velX = -5;
			}

			if (crouching)
				velX = 0;

			x += velX;

			if (velX > 0) facing = 1;
			if (velX < 0) facing = -1;

			if (falling || jumping) {
				velY += gravity;

				if (velY > MAX_SPEED)
					velY = MAX_SPEED;
			}
		}

		if (crouching) {
			height = 32;
			velX = 0;
		}
		else
			height = 48;

		try {
			collision(object);
		}
		catch (Exception e) {}

		playerWalk.runAnimation();
		playerWalkLeft.runAnimation();
		invulWalk.runAnimation();
		invulWalkLeft.runAnimation();
		invulIdleRight.runAnimation();
		invulIdleLeft.runAnimation();
		invulJump.runAnimation();
		invulJumpLeft.runAnimation();
		invulFall.runAnimation();
		invulFallLeft.runAnimation();
		invulCrouch.runAnimation();
		invulCrouchLeft.runAnimation();
	}

	public void takeDamage(int damage) {
		if (alive && !invulnerable) {
			PlayerInfo.health -= damage;
			if (PlayerInfo.health <= 0)
				alive = false;
			invulnerableTimer = System.currentTimeMillis();
			invulnerable = true;
		}
	}

	private void collision(ArrayList<GameObject> object) {
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if ((tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.ShooterTrap || tempObject.getId() == ObjectId.ChangingShooterTrap) && tempObject.getCollidable()) {
				if (getBoundsBot().intersects(tempObject.getBounds()) && !cam.isMoving()) {
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
					hasDoubleJump = true;
				}
				else
					falling = true;

				if (getBoundsSides().intersects(tempObject.getBounds()) && !cam.isMoving()) {
					if (velX > 0) 
						x = tempObject.getX() - width + 5;
					else if (velX < 0)
						x = tempObject.getX() + tempObject.getWidth() - 5;
				}

				if (getBoundsTop().intersects(tempObject.getBounds()) && !cam.isMoving()) {
					y = tempObject.getY() + tempObject.getHeight();
					velY = 0;
				}

				if (!hanging && facing == 1 && tempObject.getClimbable() && !getHangCheckTopRight().intersects(tempObject.getBounds()) && getHangCheckBotRight().intersects(tempObject.getBounds())) {
					hanging = true;
					dashing = false;
					hangX = (int) tempObject.getX() - width;
					hangY = (int) tempObject.getY();
					climbTime = System.currentTimeMillis();
					handler.layer2.add(new PlayerClimbAnimation((int) hangX + 28, (int) hangY - 38, facing, handler, ObjectId.PlayerClimbAnimation));
				}
				else if (!hanging && facing == -1 && tempObject.getClimbable() && !getHangCheckTopLeft().intersects(tempObject.getBounds()) && getHangCheckBotLeft().intersects(tempObject.getBounds())) {
					hanging = true;
					dashing = false;
					hangX = (int) tempObject.getX() + 32;
					hangY = (int) tempObject.getY();
					climbTime = System.currentTimeMillis();
					handler.layer2.add(new PlayerClimbAnimation((int) hangX - 28, (int) hangY - 38, facing, handler, ObjectId.PlayerClimbAnimation));
				}

			}

			if (tempObject.getId() == ObjectId.BasicEnemy) {
				if (attacking) {
					if (getAttackBounds().intersects(tempObject.getBounds())) {
						((TempEnemy) tempObject).takeDamage(25);
					}
				}
			}
		}

	}

	public void render(Graphics g) {
		//dashing
		if (dashing) {
			if (facing == 1)
				g.drawImage(tex.playerDash[0], (int) x, (int) y, width, height, null);
			else
				g.drawImage(tex.playerDash[1], (int) x, (int) y, width, height, null);
		}
		//crouching
		else if (crouching) {
			if (facing == 1) {
				if (invulnerable)
					invulCrouch.drawAnimation(g, (int) x, (int) y - 16, width, 48);
				else
					g.drawImage(tex.playerCrouch[0], (int) x, (int) y - 16, width, 48, null);
			}
			else if (facing == -1) {
				if (invulnerable)
					invulCrouchLeft.drawAnimation(g, (int) x, (int) y - 16, width, 48);
				else
					g.drawImage(tex.playerCrouch[1], (int) x, (int) y - 16, width, 48, null);
			}
		}
		//jumping
		else if (jumping && !hanging && !dashing) {
			if (facing == 1) {
				if (velY < 0) {
					if (invulnerable)
						invulJump.drawAnimation(g, (int) x, (int) y, width, height);
					else
						g.drawImage(tex.playerJump[0], (int) x, (int) y, width, height, null);
				}
				else if (velY > 0) {
					if (invulnerable)
						invulFall.drawAnimation(g, (int) x, (int) y, width, height);
					else
						g.drawImage(tex.playerJump[1], (int) x, (int) y, width, height, null);
				}
			}
			else if (facing == -1) {
				if (velY < 0) {
					if (invulnerable)
						invulJumpLeft.drawAnimation(g, (int) x, (int) y, width, height);
					else
						g.drawImage(tex.playerJump[3], (int) x, (int) y, width, height, null);
				}
				else if (velY > 0) {
					if (invulnerable)
						invulFallLeft.drawAnimation(g, (int) x, (int) y, width, height);
					else
						g.drawImage(tex.playerJump[2], (int) x, (int) y, width, height, null);
				}
			}
		}
		//attacking
		else if (attacking) {
			if (facing == 1) 
				g.drawImage(tex.playerPunch[0], (int) x, (int) y, width, height, null);
			else if (facing == -1)
				g.drawImage(tex.playerPunch[1], (int) x, (int) y, width, height, null);
		}
		//idle
		else if (velX == 0) {
			if (!hanging) {
				if (facing == 1) {
					if (invulnerable)
						invulIdleRight.drawAnimation(g, (int) x, (int) y, width, height);
					else
						g.drawImage(tex.player[0], (int) x, (int) y, width, height, null);
				}
				else if (facing == -1) {
					if (invulnerable)
						invulIdleLeft.drawAnimation(g, (int) x, (int) y, width, height);
					else
						g.drawImage(tex.player[13], (int) x, (int) y, width, height, null);
				}
			}
		}
		//walking
		else if (velX != 0) {
			if (facing == 1) {
				if (invulnerable)
					invulWalk.drawAnimation(g, (int) x, (int) y, width, height);
				else
					playerWalk.drawAnimation(g, (int) x, (int) y, width, height);
			}
			else {
				if (invulnerable)
					invulWalkLeft.drawAnimation(g, (int) x, (int) y, width, height);
				else
					playerWalkLeft.drawAnimation(g, (int) x, (int) y, width, height);
			}
		}

		if (KeyInput.showCollisionBoxes) {
			g.setColor(Color.green);
			g.drawRect((int) x + width / 2 - width / 4, (int) y + height / 2 + height / 4 + 6, (int) width / 2, (int) height / 4 - 6);

			g.setColor(Color.blue);
			g.drawRect((int) x + width / 2 - width / 4, (int) y + 3, (int) width / 2, (int) height / 4);

			g.setColor(Color.magenta);
			g.drawRect((int) x - width / 4 + width / 6, (int) y, width / 3, 10);

			g.setColor(Color.pink);
			g.drawRect((int) x - width / 4 + width / 6, (int) y + 10, width / 3, 10);

			g.setColor(Color.yellow);
			g.drawRect((int) x + width * 3 / 4, (int) y, width / 3, 10);

			g.setColor(Color.white);
			g.drawRect((int) x + width * 3 / 4, (int) y + 10, width / 3, 10);

			g.setColor(Color.red);
			g.drawRect((int) x + (int) velX + 12, (int) y + 10, width * 1 / 2  + (int) velX / 3, height - 20);

			if (attacking) {
				g.setColor(Color.LIGHT_GRAY);
				if (facing == 1)
					g.drawRect((int) x + 32, (int) y + 8, 64, 32);
				else
					g.drawRect((int) x - 48, (int) y + 8, 64, 32);
			}

			g.setColor(Color.YELLOW);
			g.drawRect((int) x + width / 4, (int) y, (int) width / 2, (int) height);
		}
	}

	public Rectangle getBoundsSides() {
		return new Rectangle((int) x + (int) velX + 12, (int) y + 10, width * 1 / 2  + (int) velX / 3, height - 20);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x + width / 4, (int) y, (int) width / 2, (int) height);
	}

	public Rectangle getBoundsBot() {
		return new Rectangle((int) x + width / 2 - width / 4, (int) y + height / 2 + height / 4 + 6, (int) width / 2, (int) height / 4 - 6);
	}

	public Rectangle getBoundsTop() {
		if (!crouching)
			return new Rectangle((int) x + width / 2 - width / 4, (int) y, (int) width / 2, (int) height / 4);
		else
			return new Rectangle((int) x + width / 2 - width / 4, (int) y + 10, (int) width / 2, (int) height / 4);
	}

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

	public Rectangle getAttackBounds() {
		if (facing == 1)
			return new Rectangle((int) x + 32, (int) y + 8, 64, 32);
		else
			return new Rectangle((int) x - 48, (int) y + 8, 64, 32);
	}

	//	public Rectangle getBoundsRight() {
	//		return new Rectangle((int) x + width - 10 + (int) velX, (int) y + height * 1 / 5, 10 + (int) velX / 3, height * 3 / 5);
	//	}
	//
	//	public Rectangle getBoundsLeft() {
	//		return new Rectangle((int) x, (int) y + height * 1 / 5, 10, height * 3 / 5);
	//	}

}
