package items;

import java.io.Serializable;
import java.util.HashMap;

import game.*;
import game.InvalidLabelException;

public class Item implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private String name;
	private String descLabel;
	
	public Item(String label, String name) {
		this.name = name;
		descLabel = label;
		if (!Game.itemDescs.containsKey(descLabel))
			throw new InvalidLabelException(label);
	}
	
	public Item(String name) {
		this.name = name;
	}
	
	public void setDesc(String label) {
		if (Game.itemDescs.containsKey(label))
			descLabel = label;
		else
			throw new InvalidLabelException(label);
	}
	
	public void setDesc(String label, String desc) {
		Game.itemDescs.put(label, desc);
	}
	
	public String getName() {
		return name;
	}
	
	public void look() {
		Game.print(Game.itemDescs.get(descLabel));
	}
	
	public void use() {
		Game.print("You can't use the "+name+"!");
	}
	
	public void take() {
		if (Player.has(name))
			Game.print("You already have the "+name+".");
		else
			Game.print("You can't take the "+name+"!");
	}
	
	public void move() {
		Game.print(name+" doesn't move.");
	}
	
	public void open() {
		Game.print(name+" doesn't open.");
	}
	
	public void close() {
		Game.print("You can't close the "+name+"!");
	}
	
}
