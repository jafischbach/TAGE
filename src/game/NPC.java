package game;

import java.io.Serializable;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Abstract class for creating interactive NPCs.
 * 
 * Define a new NPC by extending this class and overriding
 * relevant methods.
 */
public abstract class NPC implements Serializable {

	public static final long serialVersionUID = 1L;

	private static Random rand = new Random();
	
	private String name;		// NPC's name
	private String descLabel;	// Label for NPC description
	private int health;			// NPC's current health

	/**
	 * Creates a new NPC object with the given description label
	 * and NPC name. A description with the given label must already
	 * exist in the Game's map of NPC descriptions.
	 * 
	 * @param label description label
	 * @param name item name
	 */
	public NPC(String label, String name) {
		this.name = name;
		descLabel = label;
		if (!Game.npcDescs.containsKey(descLabel))
			throw new InvalidLabelException(label);
	}
	
	/**
	 * Creates a new NPC with the given name.
	 * 
	 * @param name NPC name
	 */
	public NPC(String name) {
		this.name = name;
		descLabel = name;
		health = 100;
	}

	/**
	 * Updates this NPC's description to the description
	 * with the given label. The given label must already
	 * exist in the Game's map of NPS descriptions.
	 * 
	 * @param label description label
	 */
	public void setDesc(String label) {
		if (Game.npcDescs.containsKey(label))
			descLabel = label;
		else
			throw new InvalidLabelException(label);
	}

	/**
	 * Adds a new description with the given label to the Game's
	 * map of NPC descriptions and updates this NPC's
	 * description label.
	 * 
	 * @param label description label
	 * @param desc NPC description
	 */
	public void setDesc(String label, String desc) {
		Game.npcDescs.put(label, desc);
		descLabel = label;
	}

	/**
	 * Returns this NPC's name.
	 * 
	 * @return NPC's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Updates this NPC's name to the given name.
	 * 
	 * @param name NPC's new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns this NPC's current health.
	 * 
	 * @return NPC's health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Updates this NPC's health to the given value.
	 * 
	 * @param health new value for NPC's health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Lowers this NPC's health by the given value.
	 * 
	 * @param thisMuch amount to reduce health by
	 * @return true if NPC's health remains above zero
	 */
	public boolean reduceHealth(int thisMuch) {
		health -= thisMuch;
		if (health <= 0)
			return false;
		else
			return true;
	}

	/**
	 * Increases this NPC's health by the given value.
	 * 
	 * @param thisMuch amount to increase health by
	 */
	public void addHealth(int thisMuch) {
		health += thisMuch;
	}

	public int roll(int sides) {
		return rand.nextInt(sides);
	}
	
	/**
	 * Displays dialog for this NPC.
	 * 
	 * @param s NPC dialog
	 */
	public void say(String s) {
		Game.print(name + ": " + s);
	}

	/**
	 * This method allows NPC to respond to a dialog choice
	 * made by the player. 
	 * 
	 * @param choice player's dialog choice.
	 */
	public void response(int choice) {
		throw new NoResponseException();
	}

	/**
	 * Displays the given dialog choices to the player and
	 * allows the player to respond.
	 * 
	 * @param options array of dialog choices to display to player
	 */
	public void getResponse(String[] options) {
		String s = "";
		for (int i = 0; i < options.length; i++) {
			s += "OPTION " + (i + 1) + ": " + options[i] + "\n\n";
		}
		Game.print(s.substring(0, s.length() - 2));
		s = "Select an option (1-" + options.length + "): ";
		try {
			if (Game.CONSOLE) {
				int choice = Game.getInt(s, "Dialog");
				if (choice > 0 && choice <= options.length)
					response(choice);
				else {
					Game.print("That is not a valid option.");
					getResponse(options);
				}
			} else {
				Game.print(s);
				Game.convoResponseGUI(this, options.length);
			}
		} catch (CancelledInputException ex) {
			JOptionPane.showMessageDialog(GameGUI.window, "You must select an option.", "Error",
					JOptionPane.INFORMATION_MESSAGE);
			getResponse(options);
		}
	}

	/**
	 * Displays a generic message when player tries to talk
	 * to this NPC.
	 * 
	 * Extending classes should override this method if the
	 * player can talk to the NPC.
	 */
	public void talk() {
		Game.print(name + " has nothing to say.");
	}

	/**
	 * This method allows the player to try to attack this
	 * NPC with the player's equipped weapon.
	 */
	public void attack() {
		String weapon = Game.player.getEquipped();
		if (weapon == null)
			Game.print("You do not have a weapon equipped.");
		else
			attack(Game.player.getEquipped());
	}
	
	/**
	 * Displays a generic message when the player tries to
	 * attack this NPC.
	 * 
	 * Extending classes should override this method if the
	 * player can attack the NPC.
	 * 
	 * @param weaponName name of weapon player is attacking with
	 */
	public void attack(String weaponName) {
		Game.print("You cannot attack " + name + " with a " + weaponName + ".");
	}

	/**
	 * Displays the description of this NPC.
	 */
	public void look() {
		Game.print(Game.npcDescs.get(descLabel));
	}

	/**
	 * Displays a generic message when the player tries to
	 * give an item to this NPC.
	 * 
	 * Extending classes should override this method if the
	 * player can give an item to the NPC.
	 * 
	 * @param itemName name of item player is trying to give
	 */
	public void give(String itemName) {
		Game.print(name + " doesn't want " + itemName + ".");
	}
	
	public void uniqueCommand(String command) {
		Game.print("You can't do that!");
	}

}
