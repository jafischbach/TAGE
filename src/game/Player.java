package game;

import java.util.HashMap;

public class Player {

	public static String name;
	public static HashMap<String, Item> inventory = new HashMap<String, Item>();
	
	private static int health = 100;
	private static String equippedWeapon;
	
	public static void addItem(String name, Item item) {
		inventory.put(name, item);
	}
	
	public static void addItem(Item item) {
		inventory.put(item.getName(), item);
	}
	
	public static Item getItem(String name) {
		return inventory.get(name);
	}
	
	public static void removeItem(String name) {
		inventory.remove(name);
		if (name.equals(equippedWeapon))
			equippedWeapon = null;
	}
	
	public static boolean has(String name) {
		return inventory.containsKey(name);
	}
	
	public static void printInventory() {
		if (inventory.keySet().isEmpty())
			Game.print("You are carrying nothing!");
		else {
			Game.println("You are carrying:");
			for(String item : inventory.keySet())
				if (item.equals(equippedWeapon))
					Game.println(item + " (equipped)");
				else	
					Game.println(item);
			Game.println("");
		}
	}
	
	public static void equip(String weapon) {
		if (has(weapon)) {
			equippedWeapon = weapon;
			Game.print("You equip the "+weapon+".");
		} else {
			Game.print("You don't have a "+weapon+"!");
		}
	}
	
	public static String getEquipped() {
		return equippedWeapon;
	}
	
	
	/**
	 * Returns this player's current health.
	 * 
	 * @return player's health
	 */
	public static int getHealth() {
		return health;
	}

	/**
	 * Updates this player's health to the given value.
	 * 
	 * @param health new value for player's health
	 */
	public static void setHealth(int health) {
		Player.health = health;
	}

	/**
	 * Lowers this player's health by the given value.
	 * 
	 * @param thisMuch amount to reduce health by
	 * @return true if player's health remains above zero
	 */
	public static boolean reduceHealth(int thisMuch) {
		health -= thisMuch;
		if (health <= 0)
			return false;
		else
			return true;
	}

	/**
	 * Increases players's health by the given value.
	 * 
	 * @param thisMuch amount to increase health by
	 */
	public static void addHealth(int thisMuch) {
		health += thisMuch;
	}
	
}
