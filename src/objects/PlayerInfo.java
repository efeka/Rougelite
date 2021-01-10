package objects;

import java.util.ArrayList;

import framework.GameObject;

public class PlayerInfo {
	
	//1 health = 1 half heart
	public static int maxHealth = 10;
	public static int health = 10;
	public static boolean alive = true;
	public static int damage = 2;
	
	public static ArrayList<GameObject> inventory = new ArrayList<GameObject>();
}
