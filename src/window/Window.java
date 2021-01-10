package window;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	
	public static JFrame frame;
	
	public Window(int w, int h, String title, Main game) {
		game.setPreferredSize(new Dimension(w, h));
		game.setMaximumSize(new Dimension(w, h));
		game.setMinimumSize(new Dimension(w, h));
		
		frame = new JFrame(title);
		frame.add(game);
		frame.pack();
		frame.requestFocus();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}
	
	public static int getWidth() {
		return frame.getWidth();
	}
	
	public static int getHeight() {
		return frame.getHeight();
	}
}
