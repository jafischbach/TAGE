package game;

import java.io.Serializable;
import java.util.HashMap;

public class Room implements Serializable {

	public static final long serialVersionUID = 1L;

	public static final int EAST = 0;
	public static final int WEST = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	public static final int UP = 4;
	public static final int DOWN = 5;

	private String descLabel;
	private String roomName;
	private String[] go;
	protected HashMap<String, Item> items;
	protected HashMap<String, NPC> npcs;
	private HashMap<String, String> simpleItems;
	private String roomLabel;
	private boolean isLocked;
	private boolean entered;

	public Room(String label, String name) {
		roomLabel = label;
		roomName = name;
		if (!Game.roomDescs.containsKey(label))
			throw new InvalidLabelException(label);
		descLabel = label;
		isLocked = false;
		entered = false;
		go = new String[6];
		Game.addRoom(label, this);
	}

	public void addItem(Item item) {
		addItem(item, item.getName());
	}

	public void addItem(Item item, String name) {
		if (!entered) {
			if (!Game.roomItems.containsKey(roomLabel))
				Game.roomItems.put(roomLabel, new HashMap<String, Item>());
			Game.roomItems.get(roomLabel).put(name, item);
		} else {
			if (items == null)
				items = new HashMap<String, Item>();
			items.put(name, item);
		}
	}

	public void removeItem(String name) {
		if (items != null)
			items.remove(name);
	}

	public boolean hasItem(String name) {
		boolean hasItem = items == null ? false : items.containsKey(name);
		boolean hasSimpleItem = simpleItems == null ? false : simpleItems.containsKey(name);
		return hasItem || hasSimpleItem;
	}

	public Item getItem(String name) {
		return items == null ? null : items.get(name);
	}

	public void addNPC(NPC npc) {
		addNPC(npc, npc.getName());
	}
	
	public void addNPC(NPC npc, String name) {
		if (!entered) {
			if (!Game.roomNPCS.containsKey(roomLabel))
				Game.roomNPCS.put(roomLabel, new HashMap<String, NPC>());
			Game.roomNPCS.get(roomLabel).put(name, npc);
		} else {
			if (npcs == null)
				npcs = new HashMap<String, NPC>();
			npcs.put(name, npc);
		}
	}

	public void removeNPC(String name) {
		if (npcs != null)
			npcs.remove(name);
	}

	public boolean hasNPC(String name) {
		return npcs == null ? false : npcs.containsKey(name);
	}

	public NPC getNPC(String name) {
		return npcs == null? null : npcs.get(name);
	}
	
	public void addExit(Room room, int direction) {
		go[direction] = room.getRoomLabel();
	}

	public String getName() {
		return roomName;
	}

	public void setName(String name) {
		roomName = name;
	}

	public String getDesc() {
		if (!entered) {
			enteringRoom();
		}
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

	public String getRoomLabel() {
		return roomLabel;
	}

	public boolean hasSimpleItem(String name) {
		return simpleItems == null ? false : simpleItems.containsKey(name);
	}
	
	public void addSimpleItem(String name, String label) {
		if (simpleItems == null)
			simpleItems = new HashMap<String, String>();
		simpleItems.put(name, label);
	}
	
	public void addSimpleItem(String name, String label, String desc) {
		addSimpleItem(name, label);
		Game.addSimpleItem(label, desc);
	}

	public String getSimpleItemDesc(String name) {
		if (simpleItems != null && simpleItems.containsKey(name)) {
			try {
				String desc = Game.getSimpleItem(simpleItems.get(name));
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
		Room r = Game.getRoom(go[direction]);
		if (r != null)
			if (r.isLocked())
				throw new InvalidDirectionException("You can't go that way right now.");
			else {
				return r;
			}
		else
			throw new InvalidDirectionException("You can't go that way!");
	}

	private void enteringRoom() {
		entered = true;
		if (Game.roomItems.containsKey(roomLabel))
			items = Game.roomItems.get(roomLabel);
		if (Game.roomNPCS.containsKey(roomLabel))
			npcs = Game.roomNPCS.get(roomLabel);
		Game.visitedRooms.add(this);
	}
	
	public boolean equals(String label) {
		return roomLabel.equals(label);
	}

}
