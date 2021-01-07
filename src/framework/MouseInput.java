package framework;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import one_time_animations.AttackAnimation;
import window.Handler;
import window.Main;

public class MouseInput implements MouseListener {

	Texture tex = Main.getTex();
	Handler handler;
	
	private long attackCooldown = 0L;

	public MouseInput(Handler handler) {
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (!handler.player.jumping && !handler.player.crouching && !handler.player.dashing && System.currentTimeMillis() - attackCooldown > 500) {
			handler.addObject(new AttackAnimation(handler.player.getX() + 16, handler.player.getY() + 5, handler.player.facing, handler, ObjectId.AttackAnimation), handler.TOP_LAYER);
			attackCooldown = System.currentTimeMillis();
			handler.player.setVelX(0);
			handler.player.attacking = true;
			handler.player.attackTime = System.currentTimeMillis();
		}
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
