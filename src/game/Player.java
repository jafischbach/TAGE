package game;

import java.io.Serializable;
import java.util.HashMap;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int health;
	private String equippedWeapon;
	private HashMap<String, Item> inventory;
	
	public Player() {
		this("Steve", 100);
	}
	
	public Player(String name) {
		this(name, 100);
	}
	
	public Player(String name, int health) {
		this.name = name;
		this.health = health;
		inventory = new HashMap<String, Item>();
	}
	
	public void say(String s) {
		Game.print(name + ": " + s);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addItem(String name, Item item) {
		inventory.put(name, item);
	}
	
	public void addItem(Item item) {
		inventory.put(item.getName(), item);
	}
	
	public Item getItem(String name) {
		return inventory.get(name);
	}
	
	public void removeItem(String name) {
		inventory.remove(name);
		if (name.equals(equippedWeapon))
			equippedWeapon = null;
	}
	
	public void clearInventory() {
		inventory.clear();
	}
	
	public boolean has(String name) {
		return inventory.containsKey(name);
	}
	
	public void printInventory() {
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
	
	public void equip(String weapon) {
		if (has(weapon)) {
			equippedWeapon = weapon;
			Game.print("You equip the "+weapon+".");
		} else {
			Game.print("You don't have a "+weapon+"!");
		}
	}
	
	public String getEquipped() {
		return equippedWeapon;
	}
	
	
	/**
	 * Returns this player's current health.
	 * 
	 * @return player's health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Updates this player's health to the given value.
	 * 
	 * @param health new value for player's health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Lowers this player's health by the given value.
	 * 
	 * @param thisMuch amount to reduce health by
	 * @return true if player's health remains above zero
	 */
	public boolean reduceHealth(int thisMuch) {
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
	public void addHealth(int thisMuch) {
		health += thisMuch;
	}
	
}
