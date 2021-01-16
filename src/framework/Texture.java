package framework;

import java.awt.image.BufferedImage;

import window.BufferedImageLoader;

public class Texture {
	
	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;
	private BufferedImage enemy_sheet = null;
	private BufferedImage VFX = null;
	private BufferedImage HUD = null;
	private BufferedImage items = null;
	
	public BufferedImage[] player = new BufferedImage[15];
	public BufferedImage[] playerInvulnerable = new BufferedImage[15];
	public BufferedImage[] playerClimb = new BufferedImage[14];
	public BufferedImage[] playerWalk = new BufferedImage[14];
	public BufferedImage[] playerJump = new BufferedImage[4];
	public BufferedImage[] playerJumpInvulnerable = new BufferedImage[4];
	public BufferedImage[] playerPunch = new BufferedImage[2];
	public BufferedImage[] playerCrouch = new BufferedImage[2];
	public BufferedImage[] playerCrouchInvulnerable = new BufferedImage[2];
	public BufferedImage[] playerDash = new BufferedImage[2];
	
	public BufferedImage[] dustEffect = new BufferedImage[5];
	public BufferedImage[] verticalDustEffect = new BufferedImage[5];
	public BufferedImage[] attackEffect = new BufferedImage[8];
	
	public BufferedImage[] blocks = new BufferedImage[10];
	
	public BufferedImage[] arrow = new BufferedImage[4];
	public BufferedImage[] ball = new BufferedImage[4];
	public BufferedImage[] shooterTrap = new BufferedImage[4];
	public BufferedImage[] changingShooterTrap = new BufferedImage[2];
	public BufferedImage fireTrap;
	public BufferedImage[] fireLaser = new BufferedImage[6];
	public BufferedImage[] burningEffect = new BufferedImage[6];
	public BufferedImage[] cannonTrap = new BufferedImage[2];
	
	public BufferedImage[] hearts = new BufferedImage[5];
	public BufferedImage hud;
	
	public BufferedImage[] item = new BufferedImage[50];
	public BufferedImage pedestal;
	
	public BufferedImage[] gold = new BufferedImage[6];
	
	public Texture() {
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			block_sheet = loader.loadImage("/block_sheet.png");
			player_sheet = loader.loadImage("/player_sheet.png");
			enemy_sheet = loader.loadImage("/enemy_sheet.png");
			VFX = loader.loadImage("/VFX.png");
			HUD = loader.loadImage("/HUD.png");
			items = loader.loadImage("/items.png");
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
			playerInvulnerable[i] = player_sheet.getSubimage(232 + 33 * i, 1, 32, 32);
		for (int i = 0; i < 7; i++) 
			player[i + 7] = player_sheet.getSubimage(1 + 33 * i, 34, 32, 32);
		for (int i = 0; i < 7; i++)
			playerInvulnerable[i + 7] = player_sheet.getSubimage(232 + 33 * i, 34, 32, 32);
		for (int i = 0; i < 14; i++)
			playerClimb[i] = player_sheet.getSubimage(1 + 33 * i, 67, 32, 63);
		for (int i = 0; i < 4; i++)
			playerJump[i] = player_sheet.getSubimage(1 + 33 * i, 131, 32, 32);
		for (int i = 0; i < 4; i++)
			playerJumpInvulnerable[i] = player_sheet.getSubimage(133 + 33 * i, 131, 32, 32);
		for (int i = 0; i < 2; i++)
			playerPunch[i] = player_sheet.getSubimage(1 + 33 * i, 164, 32, 32);
		for (int i = 0; i < 2; i++)
			playerCrouch[i] = player_sheet.getSubimage(1 + 33 * i, 197, 32, 32);
		for (int i = 0; i < 2; i++)
			playerCrouchInvulnerable[i] = player_sheet.getSubimage(67 + 33 * i, 197, 32, 32);
		for (int i = 0; i < 2; i++)
			playerDash[i] = player_sheet.getSubimage(1 + 33 * i, 230, 32, 32);
		
		for (int i = 0; i < blocks.length; i++)
			blocks[i] = block_sheet.getSubimage(1 + 33 * i, 1, 32, 32);
		
		
		for (int i = 0; i < 4; i++)
			arrow[i] = enemy_sheet.getSubimage(1 + 33 * i, 1, 32, 32);
		for (int i = 0; i < 4; i++)
			ball[i] = enemy_sheet.getSubimage(133 + 25 * i, 1, 24, 24);
		for (int i = 0; i < 4; i++)
			shooterTrap[i] = enemy_sheet.getSubimage(1 + 33 * i, 34, 32, 32);
		
		changingShooterTrap[0] = enemy_sheet.getSubimage(1, 1 + 33 * 2, 32, 32);
		changingShooterTrap[1] = enemy_sheet.getSubimage(1 + 33, 1 + 33 * 2, 32, 32);
		
		cannonTrap[0] = enemy_sheet.getSubimage(1, 197, 48, 48);
		
		fireTrap = enemy_sheet.getSubimage(133, 34, 32, 32);
		
		//effects
		for (int i = 0; i < 5; i++)
			dustEffect[i] = VFX.getSubimage(1 + 33 * i, 1, 32, 32);
		for (int i = 5; i < 10; i++)
			verticalDustEffect[i - 5] = VFX.getSubimage(1 + 33 * i, 1, 32, 32);
		for (int i = 0; i < 4; i++)
			attackEffect[i] = VFX.getSubimage(1 + 130 * i, 34, 129, 129);
		for (int i = 0; i < 4; i++)
			attackEffect[i + 4] = VFX.getSubimage(1 + 130 * i, 34 + 129, 129, 129);
		for (int i = 0; i < 6; i++)
			burningEffect[i] = VFX.getSubimage(10 * 33 + 1 + i * 33, 1, 32, 32);
		
		//HUD
		for (int i = 0; i < 5; i++) 
			hearts[i] = HUD.getSubimage(1 + 33 * i, 1, 32, 32);
		hud = HUD.getSubimage(1, 34, 32 * 3, 64);
		
		//items
		for (int i = 0; i < 5; i++) 
			item[i] = items.getSubimage(1 + 33 * i, 34, 32, 32);
		
		pedestal = items.getSubimage(1, 1, 32, 32);
		
		for (int i = 0; i < 6; i++)
			gold[i] = items.getSubimage(34 + 33 * i, 34, 32, 32);
		
		
		for (int i = 0; i < 6; i++)
			fireLaser[i] = enemy_sheet.getSubimage(1 + 33 * i, 100, 32, 96);
		
	}
}
