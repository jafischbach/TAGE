package characters;

import java.io.Serializable;
import java.util.InputMismatchException;

import game.*;

public class NPC implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private String name;
	private String descLabel;
	private int health;
	
	public NPC(String name) {
		this.name = name;
		descLabel = name;
		health = 100;
	}
	
	public String getDesc() {
		if (Game.npcDescs.containsKey(descLabel))
			return Game.npcDescs.get(descLabel);
		else
			throw new InvalidLabelException(descLabel);
	}
	
	public void setDesc(String label) {
		if (Game.npcDescs.containsKey(label))
			descLabel = label;
		else
			throw new InvalidLabelException(label);
	}
	
	public void setDesc(String label, String desc) {
		Game.npcDescs.put(label, desc);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean reduceHealth(int thisMuch) {
		health -= thisMuch;
		if (health < 0)
			return false;
		else
			return true;
	}
	
	public void addHealth(int thisMuch) {
		health += thisMuch;
	}
	
	public void say(String s) {
		Game.print("\n"+name+": "+s);
	}
	
	public int getConvoOption(String[] options) {
		for(int i=0; i<options.length; i++) {
			System.out.println("OPTION "+(i+1)+": "+options[i]+"\n");
		}
		try {
			System.out.print("Select an option (1-"+options.length+"): ");
			int choice = Game.input.nextInt();
			Game.input.nextLine(); // consumer the '\n'
			if (choice > 0 && choice <= options.length)
				return choice;
			else {
				Game.print("That is not a valid option.");
				return getConvoOption(options);
			}
		} catch (InputMismatchException ex) {
			Game.input.nextLine(); // empty buffer
			Game.print("Please enter a number.");
			return getConvoOption(options);
		}
	}
	
	public void talk() {
		Game.print(name + " has nothing to say.");
	}
	
	public void attack(String weaponName) {
		Game.print("You cannot attack "+name+".");
	}
	
	public void look() {
		Game.print(Game.npcDescs.get(descLabel));
	}
	
	public void give(String itemName) {
		Game.print(name+" doesn't want "+itemName+".");
	}
	
}
