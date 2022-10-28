package game;

import java.util.HashMap;

public class Player {

	public static String name;
	public static HashMap<String, Item> inventory = new HashMap<String, Item>();
	
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
	
}
