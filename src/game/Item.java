package game;

import java.io.Serializable;

/**
 * Abstract class for creating interactive items.
 * 
 * Define a new item by extending this class and overriding
 * relevant methods.
 */
public abstract class Item implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private String name;		// Item name
	private String descLabel;	// Label for item description
	
	/**
	 * Creates a new item object with the given description label
	 * and item name. A description with the given label must already
	 * exist in the Game's map of item descriptions (itemDescs).
	 * 
	 * @param label description label
	 * @param name item name
	 */
	public Item(String label, String name) {
		this.name = name;
		descLabel = label;
		if (!Game.itemDescs.containsKey(descLabel))
			throw new InvalidLabelException(label);
	}
	
	/**
	 * Creates a new item object with the given item name.
	 * 
	 * @param name item name
	 */
	public Item(String name) {
		this.name = name;
	}
	
	/**
	 * Updates this item's description to the description
	 * with the given label. The given label must already
	 * exist in the Game's map of item descriptions (itemDescs).
	 * 
	 * @param label description label
	 */
	public void setDesc(String label) {
		if (Game.itemDescs.containsKey(label))
			descLabel = label;
		else
			throw new InvalidLabelException(label);
	}
	
	/**
	 * Adds a new description with the given label to the Game's
	 * map of item descriptions (itemDescs) and updates this
	 * item's description label.
	 * 
	 * @param label description label
	 * @param desc item description
	 */
	public void setDesc(String label, String desc) {
		Game.itemDescs.put(label, desc);
		descLabel = label;
	}
	
	/**
	 * Returns this item's name.
	 * @return item name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Displays this item's description when player looks
	 * at this item.
	 */
	public void look() {
		Game.print(Game.itemDescs.get(descLabel));
	}
	
	/**
	 * Displays a generic message when player tries to
	 * use this item. 
	 * 
	 * Extending classes should override this method if 
	 * the item can be used.
	 */
	public void use() {
		Game.print("You can't use the "+name+"!");
	}
	
	/**
	 * Displays a generic message when player tries to
	 * take this item.
	 * 
	 * Extending classes should override this method if
	 * the item can be taken.
	 */
	public void take() {
		if (Player.has(name))
			Game.print("You already have the "+name+".");
		else
			Game.print("You can't take the "+name+"!");
	}
	
	/**
	 * Displays a generic message when player tries to
	 * move this object.
	 * 
	 * Extending classes should override this method if
	 * the item can be moved.
	 */
	public void move() {
		Game.print(name+" doesn't move.");
	}
	
	/**
	 * Displays a generic message when player tries to
	 * open this object.
	 * 
	 * Extending classes should override this method if
	 * the item can be opened.
	 */
	public void open() {
		Game.print(name+" doesn't open.");
	}
	
	/**
	 * Displays a generic message when player tries to
	 * close this object.
	 * 
	 * Extending classes should override this method if
	 * the item can be closed.
	 */
	public void close() {
		Game.print("You can't close the "+name+"!");
	}
	
	/**
	 * Responds to a command unique to this item if such
	 * a command exists.
	 * 
	 * Extending classes should override this method if
	 * the item should respond to a unique command.
	 * 
	 * @param command command issued by player
	 */
	public void uniqueCommand(String command) {
		Game.print("You can't do that with the "+name+".");
	}
	
}
