package game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Each room in the game is represented by an object of this class.
 * Room objects are linked together to form the game world.
 */
public class Room implements Serializable {

	public static final long serialVersionUID = 1L;

	// Directions the player can travel in.
	public static final int EAST = 0;
	public static final int WEST = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	public static final int UP = 4;
	public static final int DOWN = 5;
	
	// Label for this room. This is used by the game to identify
	// each room. Room labels should not change dynamically.
	private String roomLabel;

	// Label for this room's current description.
	// Description label is initially the same as the room label.
	// Description label may be changed dynamically if the room's
	// description should change.
	// Actual descriptions, indexed by label, are stored in a global Game data structure.
	private String descLabel;

	private String roomName; // Name of this room
	
	// Array containing labels of adjacent rooms
	// Array indexes are directions as defined by the declared constants above.
	private String[] go; 
	
	// Items in this room, indexed by name
	protected HashMap<String, Item> items;
	
	// NPCs in this room, indexed by name
	protected HashMap<String, NPC> npcs; 
	
	// Simple items in this room
	// Simple items are items that the player can look at but not
	// otherwise interact with.
	private HashMap<String, String> simpleItems; 
	
	private boolean isLocked; // Is this room locked?
	private boolean entered; // Has the player entered this room yet?

	/**
	 * Creates a new Room object with the given room label and room name.
	 * Room label must match a label in the rooms.txt or rooms.dat input file.
	 * @param label room label
	 * @param name room name
	 */
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

	/**
	 * Adds the given item to this room.
	 * @param item item to add to this room
	 */
	public void addItem(Item item) {
		addItem(item, item.getName());
	}

	/**
	 * Adds the given item with the given name to this room.
	 * The name can be used by the player to refer to the item.
	 * Use this method to introduce an alias for an existing item.
	 * @param item item to add
	 * @param name name of item
	 */
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

	/**
	 * Removes the item with the given name from this room.
	 * @param name name of item to remove
	 */
	public void removeItem(String name) {
		if (items != null) {
			Item removedItem = items.remove(name);
			for(Map.Entry<String, Item> entry : items.entrySet())
				if (removedItem == entry.getValue())
					items.remove(entry.getKey());
		}
	}

	/**
	 * Returns true if an item with the given name exists in this room.
	 * @param name name of item
	 * @return true if item is in the room
	 */
	public boolean hasItem(String name) {
		boolean hasItem = items == null ? false : items.containsKey(name);
		boolean hasSimpleItem = simpleItems == null ? false : simpleItems.containsKey(name);
		return hasItem || hasSimpleItem;
	}

	/**
	 * Returns the item with the given name if that item
	 * is present in the room. Returns null otherwise.
	 * @param name name of item
	 * @return item with the given name, or null
	 */
	public Item getItem(String name) {
		return items == null ? null : items.get(name);
	}

	/**
	 * Adds the given NPC to this room.
	 * @param npc NPC to add
	 */
	public void addNPC(NPC npc) {
		addNPC(npc, npc.getName());
	}
	
	/**
	 * Adds the given NPC with the given name to this room.
	 * The name can be used by the player to refer to the NPC.
	 * Use this method to introduce an alias for an existing NPC.
	 * @param npc NPC to add
	 * @param name name of NPC
	 */
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

	/**
	 * Removes the NPC with the given name from this room.
	 * @param name name of NPC
	 */
	public void removeNPC(String name) {
		if (npcs != null) {
			NPC removedNPC = npcs.remove(name);
			for(Map.Entry<String, NPC> entry : npcs.entrySet())
				if (removedNPC == entry.getValue())
					npcs.remove(entry.getKey());
		}
	}

	/**
	 * Returns true if an NPC with the given name exists
	 * in this room.
	 * @param name name of NPC
	 * @return true if NPC is present
	 */
	public boolean hasNPC(String name) {
		return npcs == null ? false : npcs.containsKey(name);
	}

	/**
	 * Returns the NPC with the given name if that NPC is
	 * present in this room. Returns false otherwise.
	 * @param name name of NPC
	 * @return true if NPC is present
	 */
	public NPC getNPC(String name) {
		return npcs == null? null : npcs.get(name);
	}
	
	/**
	 * Adds an exit from this room to the given room in the
	 * given direction. Use this method to link rooms together
	 * so that the player can navigate from one to the other.
	 * @param room room to connect to this room
	 * @param direction direction player must travel to enter the given room
	 */
	public void addExit(Room room, int direction) {
		go[direction] = room.getRoomLabel();
	}

	/**
	 * Returns the name of this room.
	 * @return room name
	 */
	public String getName() {
		return roomName;
	}

	/**
	 * Sets the name of this room to the given name.
	 * @param name new name for this room
	 */
	public void setName(String name) {
		roomName = name;
	}

	/**
	 * Returns this room's description. This method is called
	 * automatically whenever the player enters the room from
	 * an adjacent room. This method will not be called 
	 * automatically if the player is teleported to this room
	 * using Game.setCurrentRoom().
	 * @return room's description
	 */
	public String getDesc() {
		if (!entered) {
			enteringRoom();
		}
		return Game.roomDescs.get(descLabel);
	}

	/**
	 * Updates this room's description by updating the description
	 * label. The given label must match a label in the rooms.txt or
	 * rooms.dat input file.
	 * @param label new description label
	 */
	public void setDesc(String label) {
		if (Game.roomDescs.containsKey(label))
			descLabel = label;
		else
			throw new InvalidLabelException(label);
	}

	/**
	 * Sets this room's description to the given description and
	 * updates the description label. The given label can be a new
	 * label and does not have to match a label in the rooms.txt or
	 * rooms.dat input file. Since labels must be unique, if the given
	 * label does match an existing description label, the existing 
	 * description will be replaced with the given description.
	 * @param label new description label
	 * @param desc new description
	 */
	public void setDesc(String label, String desc) {
		Game.roomDescs.put(label, desc);
	}

	/**
	 * Sets the locked state of this room: true if room is
	 * locked. A player cannot enter a locked room unless
	 * teleported there using Game.setCurrentRoom().
	 * @param isLocked should the room be locked?
	 */
	public void setLocked(boolean isLocked) {
		if (isLocked == false)
			Game.visitedRooms.add(this);
		this.isLocked = isLocked;
	}

	/**
	 * Returns true if this room is locked.
	 * @return true if locked
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * Returns this room's room label.
	 * @return room label
	 */
	public String getRoomLabel() {
		return roomLabel;
	}

	/**
	 * Returns true if a simple item with the given name is
	 * present in this room.
	 * @param name name of simple item
	 * @return true if simple item is present
	 */
	public boolean hasSimpleItem(String name) {
		return simpleItems == null ? false : simpleItems.containsKey(name);
	}
	
	/**
	 * Adds a simple item with the given name and label to this room.
	 * The label must uniquely identify the item. The name can be used
	 * by the player to refer to the item. Use this method to add an
	 * alias for an existing simple item.
	 * @param name name of simple item
	 * @param label label identifying simple item
	 */
	public void addSimpleItem(String name, String label) {
		if (simpleItems == null)
			simpleItems = new HashMap<String, String>();
		simpleItems.put(name, label);
	}
	
	/**
	 * Adds a simple item with the given name, label, and description to
	 * this room. The label must uniquely identify the item. The name can be used
	 * by the player to refer to the item.
	 * @param name name of simple item
	 * @param label label identifying simple item
	 * @param desc simple item's description
	 */
	public void addSimpleItem(String name, String label, String desc) {
		addSimpleItem(name, label);
		Game.addSimpleItem(label, desc);
	}

	/**
	 * Returns the description of the simple item with the given name,
	 * if that simple item exists in this room. Returns null of the item
	 * is not present.
	 * @param name name of simple item
	 * @return simple item's description, or null
	 */
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

	/**
	 * Returns the room the player enters when travelling East from this room.
	 * @return room to the east of this room
	 * @throws InvalidDirectionException if player cannot travel east
	 */
	public Room goEast() {
		return go(EAST);
	}

	/**
	 * Returns the room the player enters when travelling West from this room.
	 * @return room to the wast of this room
	 * @throws InvalidDirectionException if player cannot travel west
	 */
	public Room goWest() {
		return go(WEST);
	}

	/**
	 * Returns the room the player enters when travelling North from this room.
	 * @return room to the north of this room
	 * @throws InvalidDirectionException if player cannot travel north
	 */
	public Room goNorth() {
		return go(NORTH);
	}

	/**
	 * Returns the room the player enters when travelling South from this room.
	 * @return room to the south of this room
	 * @throws InvalidDirectionException if player cannot south
	 */
	public Room goSouth() {
		return go(SOUTH);
	}

	/**
	 * Returns the room the player enters when travelling Up from this room.
	 * @return room above this room
	 * @throws InvalidDirectionException if player cannot travel up
	 */
	public Room goUp() {
		return go(UP);
	}

	/**
	 * Returns the room the player enters when travelling Down from this room.
	 * @return room below this room
	 * @throws InvalidDirectionException if player cannot travel down
	 */
	public Room goDown() {
		return go(DOWN);
	}

	/**
	 * Returns the room the player enters when travelling in the given direction.
	 * @param direction direction the player is travelling
	 * @return room player will travel to
	 * @throws InvalidDirectionException if player cannot travel in the given direction
	 */
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

	/**
	 * This method is called by the getDesc() method, indicating that the player
	 * is entering the room. The purpose of this method is to fetch the Item and NPC
	 * objects for this room from Game's global data structures.
	 */
	private void enteringRoom() {
		entered = true;
		if (Game.roomItems.containsKey(roomLabel))
			items = Game.roomItems.get(roomLabel);
		if (Game.roomNPCS.containsKey(roomLabel))
			npcs = Game.roomNPCS.get(roomLabel);
		Game.visitedRooms.add(this);
	}
	
	/**
	 * Returns true if this room has the given room label.
	 * @param label room label
	 * @return true if this room has the given room label
	 */
	public boolean equals(String label) {
		return roomLabel.equals(label);
	}

}
