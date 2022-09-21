package game;
import java.io.Serializable;
import java.util.HashMap;

import items.Item;

public class Room implements Serializable {
	
	public static final long serialVersionUID = 1L; 

	public static final int EAST = 0;
	public static final int WEST = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	public static final int UP = 4;
	public static final int DOWN = 5;
	
	private String desc;
	private Room[] go;
	private HashMap<String, Item> items;
	private String label;
	private boolean isLocked;

	public Room(String label) {
		this.label = label;
		isLocked = false;
		desc = Game.roomDescs.get(label);
		items = new HashMap<String, Item>();
		if (desc == null) {
			throw new InvalidLabelException(label);
		}
		go = new Room[6];
		Game.addRoom(label, this);
	}
	
	public void addItem(Item item) {
		items.put(item.getName(), item);
	}
	
	public void removeItem(String name) {
		items.remove(name);
	}

	public void addExit(Room room, int direction) {
		go[direction] = room;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String label) {
		desc = Game.roomDescs.get(label);
		if (desc == null)
			desc = label;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public void action(String command) {
		String[] parsedCommand = new String[2]; //command.split(" ");
		int index = command.indexOf(' ');
		parsedCommand[0] = command.substring(0, index);
		parsedCommand[1] = command.substring(index+1);
		if (parsedCommand.length != 2)
			Game.print("Invalid command.");
		else {
			Item i = Player.getItem(parsedCommand[1]);
			if (i == null)
				i = items.get(parsedCommand[1]);
			if (i == null)
				Game.print("You can't do that!");
			else {
				String action = parsedCommand[0];
				if (action.equalsIgnoreCase("look"))
					i.look();
				else if (action.equalsIgnoreCase("take"))
					i.take();
				else if (action.equalsIgnoreCase("use"))
					i.use();
				else if (action.equalsIgnoreCase("open"))
					i.open();
				else if (action.equalsIgnoreCase("close"))
					i.close();
				else
					Game.print("Invalid command.");
			}
		}
	}

	public Room goEast() {
		return go(EAST);
	}

	public Room goWest() {
		return go(WEST);
	}

	public Room goNorth() {
		return go(NORTH);
	}

	public Room goSouth() {
		return go(SOUTH);
	}

	public Room goUp() {
		return go(UP);
	}

	public Room goDown() {
		return go(DOWN);
	}

	private Room go(int direction) {
		Room r = go[direction];
		if (r != null)
			if (r.isLocked())
				throw new InvalidDirectionException("You can't go that way right now.");
			else
				return go[direction];
		else
			throw new InvalidDirectionException("You can't go that way!");
	}

	public boolean equals(String label) {
		return this.label.equals(label);
	}
	
}
