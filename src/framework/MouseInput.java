package framework;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import one_time_animations.AttackAnimation;
import window.Handler;

public class MouseInput implements MouseListener {

	private Handler handler;

	private long attackCooldown = 0L;

	public MouseInput(Handler handler) {
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			System.out.println(e.getX() + " " + e.getY());
			if (!handler.player.hanging && !handler.player.jumping && !handler.player.crouching && !handler.player.dashing && System.currentTimeMillis() - attackCooldown > 500) {
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
