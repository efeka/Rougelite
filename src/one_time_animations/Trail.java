package one_time_animations;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Handler;
import window.Main;

public class Trail extends GameObject {

	private float alpha = 1;
	private float life;
	Texture tex = Main.getTex();
	
	BufferedImage image;
	
	private Handler handler;
	
	public Trail(float x, float y, BufferedImage image, float life, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.life = life;
		this.image = image;
	}

	public void tick(ArrayList<GameObject> object) {
		if (alpha > life) {
			alpha -= life - 0.01f;
		}
		else
			handler.removeObject(this);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.drawImage(image, (int) x, (int) y, handler.player.getWidth(), handler.player.getHeight(), null);
		g2d.setComposite(makeTransparent(1));
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}

	public Rectangle getBounds() {
		return null;
	}
	
	public Rectangle getAttackBounds() {
		return null;
	}

}
