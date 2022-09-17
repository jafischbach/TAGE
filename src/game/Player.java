package game;

import java.util.HashMap;

import items.Item;

public class Player {

	public static String name;
	public static HashMap<String, Item> inventory = new HashMap<String, Item>();
	
	public static void addItem(String name, Item item) {
		inventory.put(name, item);
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
			System.out.println("You are carrying:");
			for(String item : inventory.keySet())
				System.out.println(item);
			System.out.println();
		}
	}
	
}
