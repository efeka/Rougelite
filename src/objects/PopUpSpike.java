package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import one_time_animations.PopUpSpikeAnimation;
import window.Handler;

public class PopUpSpike extends GameObject {

	private Handler handler;
	
	private int currentUpTime = 0, maxUpTime = 30;

	public PopUpSpike(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		handler.addObject(new PopUpSpikeAnimation(x, y, handler, ObjectId.PopUpSpikeAnimation), Handler.MIDDLE_LAYER);
		width = height = 32;
	}

	public void tick(ArrayList<GameObject> object) {	
		if (currentUpTime < maxUpTime)
			currentUpTime++;
		else
			handler.removeObject(this);
		
		collision(handler.layer2);
	}

	private void collision(ArrayList<GameObject> object) {
		if (!handler.player.getDashing()) 
			if (getBounds().intersects(handler.player.getBounds())) 
				handler.player.takeDamage(2);
	}

	public void render(Graphics g) {
		if (KeyInput.showCollisionBoxes) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.cyan);
			g2d.draw(getBoundsBot());
			
			g2d.setColor(Color.white);
			g2d.draw(getBounds());
		}
	}

	public Rectangle getBoundsBot() {
		return new Rectangle((int) x + 12, (int) y + 24, 8, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getAttackBounds() {
		return null;
	}

	public void takeDamage(int damage) {

	}

}
