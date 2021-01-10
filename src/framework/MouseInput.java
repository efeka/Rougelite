package framework;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import one_time_animations.AttackAnimation;
import window.Handler;
import window.Window;

public class MouseInput implements MouseListener {

	private Handler handler;

	private long attackCooldown = 0L;
	
	public static int mouseX = 0, mouseY = 0;

	public MouseInput(Handler handler) {
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!handler.player.jumping && !handler.player.crouching && !handler.player.dashing && System.currentTimeMillis() - attackCooldown > 500) {
				handler.addObject(new AttackAnimation(handler.player.getX() + 16, handler.player.getY() + 5, handler.player.facing, handler, ObjectId.AttackAnimation), Handler.TOP_LAYER);
				attackCooldown = System.currentTimeMillis();
				handler.player.setVelX(0);
				handler.player.attacking = true;
				handler.player.attackTime = System.currentTimeMillis();
			}
		}
	}
	

	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
