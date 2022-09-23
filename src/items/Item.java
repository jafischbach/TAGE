package items;

import java.io.Serializable;

import game.Game;
import game.InvalidLabelException;

public class Item implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private String name;
	private String desc;
	
	public Item(String label, String name) {
		this.name = name;
		desc = Game.itemDescs.get(label);
		if (desc == null)
			throw new InvalidLabelException(label);
	}
	
	public Item(String name) {
		this.name = name;
	}
	
	public void setDesc(String label) {
		desc = Game.itemDescs.get(label);
		if (desc == null)
			desc = label;
	}
	
	public String getName() {
		return name;
	}
	
	public void look() {
		Game.print(desc);
	}
	
	public void use() {
		Game.print("You can't use "+name+"!");
	}
	
	public void take() {
		Game.print("You can't take "+name+"!");
	}
	
	public void move() {
		Game.print(name+" doesn't move.");
	}
	
	public void open() {
		Game.print(name+" doesn't open.");
	}
	
	public void close() {
		Game.print("You can't close "+name+"!");
	}
	
}
