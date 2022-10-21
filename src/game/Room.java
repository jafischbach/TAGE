package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Room implements Serializable {

	public static final long serialVersionUID = 1L;

	public static final int EAST = 0;
	public static final int WEST = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	public static final int UP = 4;
	public static final int DOWN = 5;

	private String descLabel;
	private Room[] go;
	protected HashMap<String, Item> items;
	protected HashMap<String, NPC> npcs;
	private ArrayList<String> simpleItems;
	private String roomLabel;
	private boolean isLocked;

	public Room(String label) {
		roomLabel = label;
		if (!Game.roomDescs.containsKey(label))
			throw new InvalidLabelException(label);
		descLabel = label;
		isLocked = false;
		go = new Room[6];
		Game.addRoom(label, this);
	}

	public void addItem(Item item) {
		if (items == null)
			items = new HashMap<String, Item>();
		items.put(item.getName(), item);
	}

	public void removeItem(String name) {
		if (items != null)
			items.remove(name);
	}

	public boolean hasItem(String name) {
		return items.containsKey(name);
	}

	public Item getItem(String name) {
		return items.get(name);
	}

	public void addNPC(NPC npc) {
		if (npcs == null)
			npcs = new HashMap<String, NPC>();
		npcs.put(npc.getName(), npc);
	}

	public void removeNPC(String name) {
		if (npcs != null)
			npcs.remove(name);
	}

	public void addExit(Room room, int direction) {
		go[direction] = room;
	}

	public String getDesc() {
		return Game.roomDescs.get(descLabel);
	}

	public void setDesc(String label) {
		if (Game.roomDescs.containsKey(label))
			descLabel = label;
		else
			throw new InvalidLabelException(label);
	}

	public void setDesc(String label, String desc) {
		Game.roomDescs.put(label, desc);
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void addSimpleItem(String name, String desc) {
		if (simpleItems == null)
			simpleItems = new ArrayList<String>();
		simpleItems.add(name);
		Game.addSimpleItem(name, desc);
	}

	public String getSimpleItemDesc(String name) {
		if (simpleItems != null && simpleItems.contains(name)) {
			try {
				String desc = Game.getSimpleItem(name);
				if (desc != null)
					return desc;
				else
					return null;
			} catch (NullPointerException ex) {
				return null;
			}
		} else {
			return null;
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
		return roomLabel.equals(label);
	}

}
