package framework;

import java.awt.image.BufferedImage;

import window.BufferedImageLoader;

public class Texture {
	
	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	private BufferedImage enemy_sheet = null;
	private BufferedImage VFX = null;
	
	public BufferedImage[] player = new BufferedImage[15];
	public BufferedImage[] playerClimb = new BufferedImage[14];
	public BufferedImage[] playerWalk = new BufferedImage[14];
	public BufferedImage[] playerJump = new BufferedImage[4];
	public BufferedImage[] woodenSword = new BufferedImage[14];
	public BufferedImage[] playerPunch = new BufferedImage[2];
	public BufferedImage[] playerCrouch = new BufferedImage[2];
	public BufferedImage[] playerDash = new BufferedImage[2];
	
	public BufferedImage[] dustEffect = new BufferedImage[5];
	public BufferedImage[] attackEffect = new BufferedImage[8];
	
	public BufferedImage[] blocks = new BufferedImage[10];
	
	public BufferedImage[] arrow = new BufferedImage[4];
	
	public Texture() {
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			block_sheet = loader.loadImage("/block_sheet.png");
			player_sheet = loader.loadImage("/player_sheet.png");
			VFX = loader.loadImage("/VFX.png");
//			enemy_sheet = loader.loadImage("/enemy_sheet.png");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		getTextures();
	}
	
	private void getTextures() {
		//player
		for (int i = 0; i < 7; i++)
			player[i] = player_sheet.getSubimage(1 + 33 * i, 1, 32, 32);
		for (int i = 0; i < 7; i++) 
			player[i + 7] = player_sheet.getSubimage(1 + 33 * i, 34, 32, 32);
		for (int i = 0; i < 14; i++)
			playerClimb[i] = player_sheet.getSubimage(1 + 33 * i, 67, 32, 63);
		for (int i = 0; i < 4; i++)
			playerJump[i] = player_sheet.getSubimage(1 + 33 * i, 131, 32, 32);
		
		for (int i = 0; i < blocks.length; i++)
			blocks[i] = block_sheet.getSubimage(1 + 33 * i, 1, 32, 32);
		
		for (int i = 0; i < 2; i++)
			playerPunch[i] = player_sheet.getSubimage(1 + 33 * i, 164, 32, 32);
		for (int i = 0; i < 2; i++)
			playerCrouch[i] = player_sheet.getSubimage(1 + 33 * i, 197, 32, 32);
		for (int i = 0; i < 2; i++)
			playerDash[i] = player_sheet.getSubimage(1 + 33 * i, 230, 32, 32);
		
		for (int i = 0; i < 4; i++)
			arrow[i] = block_sheet.getSubimage(1 + 33 * i, 34, 32, 32);
		
		//effects
		for (int i = 0; i < 5; i++)
			dustEffect[i] = VFX.getSubimage(1 + 33 * i, 1, 32, 32);
		for (int i = 0; i < 4; i++)
			attackEffect[i] = VFX.getSubimage(1 + 130 * i, 34, 129, 129);
		for (int i = 0; i < 4; i++)
			attackEffect[i + 4] = VFX.getSubimage(1 + 130 * i, 34 + 129, 129, 129);
	}
}
