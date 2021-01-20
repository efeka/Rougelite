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

public class StompingTrap extends GameObject {

	private Handler handler;
	private Texture tex = Main.getTex();

	private int extendHeight = 48;
	private boolean goingUp = true;

	private int maxUpTime = 60, currentUpTime = 0;
	private int cycleTimer = 0, cycleCooldown = 120;

	private float initialY;

	private boolean[] spikes = new boolean[7];
	private boolean checked = false;

	public StompingTrap(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		initialY = y;
		width = 32;
		height = 0;
	}

	public void tick(ArrayList<GameObject> object) {
		if (!checked) {
			for (int i = 0; i < spikes.length; i++) 
				spikes[i] = collision(handler.layer2, i - 3);
			checked = true;
		}
		
		if (height < 0)
			height = 0;

		if (!goingUp && height == 0) {
			if (cycleTimer < cycleCooldown) {
				cycleTimer++;
			}
			else {
				goingUp = true;
				cycleTimer = 0;
			}
		}

		if (goingUp) {
			if (height < extendHeight) 
				height++;
		}
		else {
			if (height > 0) 
				height -= 6;
		}
		if (height == extendHeight) {
			if (currentUpTime < maxUpTime)
				currentUpTime++;
			else {
				currentUpTime = 0;
				goingUp = false;	
			}
		}
		
		for (int i = 0; i < spikes.length; i++) {
			if (i != 3 && spikes[i] && cycleTimer == 0 && height <= 0 && !goingUp)
				handler.addObject(new PopUpSpike(x + 32 * (i - 3), initialY, handler, ObjectId.PopUpSpike), Handler.MIDDLE_LAYER);			
		}
	}

	private boolean collision(ArrayList<GameObject> object, int spikeNumber) {
		boolean bot = false, top = false; 
		for (int i = 0; i < handler.layer2.size(); i++) {
			GameObject tempObject = handler.layer2.get(i);
			if (tempObject.getCollidable() && (tempObject.getId() == ObjectId.Block)) {
				if (getSpikeBotBounds(spikeNumber).intersects(tempObject.getBounds())) 
					bot = true;
				if (getSpikeTopBounds(spikeNumber).intersects(tempObject.getBounds()))
					top = true;
			}
		}
		return bot && !top;
	}

	public void render(Graphics g) {
		//g.drawImage(tex.stompingTrap[0], (int) x, (int) initialY + 32 - height - 15, 32, 64, null);
		g.drawImage(tex.stompingTrap[0], (int) x, (int) initialY + 32 - height - 15, width, height + 10, null);
		g.drawImage(tex.stompingTrap[1], (int) x, (int) initialY + 32 - height - 15, width, 5, null);
		g.drawImage(tex.stompingTrap[1], (int) x, (int) initialY + 27, width, 5, null);

		if (KeyInput.showCollisionBoxes) {
			g.setColor(Color.white);
			Graphics2D g2d = (Graphics2D) g;
			g2d.draw(getBounds());

			for (int i = -3; i < 4; i++) {
				g2d.setColor(Color.green);
				g2d.draw(getSpikeBotBounds(i));
				g2d.setColor(Color.orange);
				g2d.draw(getSpikeTopBounds(i));
			}
		}
	}

	public Rectangle getSpikeBotBounds(int i) {
		return new Rectangle((int) x + 32 * i + 8, (int) y + 16, 16, 32);
	}
	
	public Rectangle getSpikeTopBounds(int i) {
		return new Rectangle((int) x + 32 * i + 8, (int) y + 4, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x + 2, (int) y + 32 - height - 10, width - 4, height + 10);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	public void takeDamage(int damage) {

	}

}
