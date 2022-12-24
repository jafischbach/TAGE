package game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The game player is represented by an object of this class.
 */
public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name; // Player's name
	private int health; // Player's current health
	private String equippedWeapon; // Player's currently equipped weapon
	private HashMap<String, Item> inventory; // Player's inventory of items
	
	/**
	 * Creates a new player named "Steve" and starting
	 * health of 100.
	 */
	public Player() {
		this("Steve", 100);
	}
	
	/**
	 * Creates a new player with the given name and starting
	 * health of 100.
	 * @param name player's name
	 */
	public Player(String name) {
		this(name, 100);
	}
	
	/**
	 * Creates a new player with the given name and the given
	 * health value.
	 * @param name player's name
	 * @param health player's starting health
	 */
	public Player(String name, int health) {
		this.name = name;
		this.health = health;
		inventory = new HashMap<String, Item>();
	}
	
	/**
	 * Prints the given string as dialog from the player.
	 * @param s player's dialog
	 */
	public void say(String s) {
		Game.print(name + ": " + s);
	}
	
	/**
	 * Returns the player's name.
	 * @return player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the player's name to the given string.
	 * @param name player's new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Adds the given item with the given name to the
	 * player's inventory. Player will use the name to refer
	 * to the item. Use this method to create an alias for an
	 * item already in the player's inventory.
	 * @param name name of item
	 * @param item item to add
	 */
	public void addItem(String name, Item item) {
		inventory.put(name, item);
	}
	
	/**
	 * Adds the given item to the player's inventory.
	 * @param item item to add
	 */
	public void addItem(Item item) {
		inventory.put(item.getName(), item);
	}
	
	/**
	 * Gets the item with the given name from the
	 * player's inventory.
	 * @param name name of item
	 * @return item with the given name
	 */
	public Item getItem(String name) {
		return inventory.get(name);
	}
	
	/**
	 * Removes the item with the given name from the player's
	 * inventory.
	 * @param name name of item to remove
	 */
	public void removeItem(String name) {
		Item removedItem = inventory.remove(name);
		if (name.equals(equippedWeapon))
			equippedWeapon = null;
		for(Map.Entry<String, Item> entry : inventory.entrySet())
			if (removedItem == entry.getValue())
				inventory.remove(entry.getKey());
	}
	
	/**
	 * Removes all items from the player's inventory.
	 */
	public void clearInventory() {
		inventory.clear();
	}
	
	/**
	 * Returns true if the player has an item with the
	 * given name.
	 * @param name name of item
	 * @return true if player has the item
	 */
	public boolean has(String name) {
		return inventory.containsKey(name);
	}
	
	/**
	 * Prints the player's inventory.
	 */
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
	
	/**
	 * Equips the weapon with the given name, assuming
	 * the weapon is in the player's inventory.
	 * @param weapon name of weapon
	 */
	public void equip(String weapon) {
		if (has(weapon)) {
			equippedWeapon = weapon;
			Game.print("You equip the "+weapon+".");
		} else {
			Game.print("You don't have a "+weapon+"!");
		}
	}
	
	/**
	 * Returns the name of the equipped weapon.
	 * Returns null if there is no equipped weapon.
	 * @return name of equipped weapon, or null
	 */
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
