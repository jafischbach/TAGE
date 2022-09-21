package characters;

import java.io.Serializable;

import game.Game;

public class NPC implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private String name;
	private String desc;
	private int health;
	
	public NPC(String name) {
		this.name = name;
		health = 100;
		desc = Game.npcDescs.get(name);
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
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
	
	public void talk() {
		Game.print(name + " has nothing to say.");
	}
	
	public void attack(String weaponName) {
		Game.print("You cannot attack "+name+".");
	}
	
	public void look() {
		Game.print(desc);
	}
	
	public void give(String itemName) {
		Game.print("You can't give "+itemName+" to "+name+".");
	}
	
}
