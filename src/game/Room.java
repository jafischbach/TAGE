package game;

import java.io.Serializable;
import java.util.HashMap;

import items.Item;
import characters.NPC;
import game.*;

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
	private HashMap<String, NPC> npcs;
	private String label;
	private boolean isLocked;

	public Room(String label) {
		this.label = label;
		isLocked = false;
		desc = Game.roomDescs.get(label);
		if (desc == null) {
			throw new InvalidLabelException(label);
		}
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
		int index = command.indexOf(' ');
		if (index < 0)
			throw new InvalidActionException("Invalid command.");
		String action = command.substring(0, index);
		if (command.length() == action.length())
			throw new InvalidActionException("Invalid command.");
		if (action.equalsIgnoreCase("give")) {
			if (npcs == null)
				throw new InvalidActionException("There's no one here, dude!");
			index = command.indexOf(" to ");
			if (index < 0)
				throw new InvalidActionException("Give what to whom?");
			String itemName = command.substring(5, index);
			String npcName = command.substring(index + 4, command.length());
			NPC npc = npcs.get(npcName);
			if (npc == null)
				Game.print("There is no " + npcName + " in the room.");
			else {
				if (Player.has(itemName))
					npc.give(itemName);
				else
					Game.print("You don't have a "+itemName);
			}
		} else if (action.equalsIgnoreCase("attack")) {
			if (npcs == null)
				throw new InvalidActionException("There's no one here, dude!");
			index = command.indexOf(" with ");
			if (index < 0)
				throw new InvalidActionException("Attack whom with what?");
			String npcName = command.substring(7, index);
			String weaponName = command.substring(index + 6, command.length());
			NPC npc = npcs.get(npcName);
			if (npc == null)
				Game.print("There is no " + npcName + " in the room.");
			else {
				if (Player.has(weaponName))
					npc.attack(weaponName);
				else
					Game.print("You don't have a "+weaponName+".");
			}
		} else {
			String itemName = command.substring(index + 1);
			Item i = Player.getItem(itemName);
			if (i == null && items != null)
				i = items.get(itemName);
			try {
				if (action.equalsIgnoreCase("look"))
					if (i == null)
						npcs.get(itemName).look();
					else
						i.look();
				else if (action.equalsIgnoreCase("talk"))
					npcs.get(itemName).talk();
				else if (action.equalsIgnoreCase("take"))
					i.take();
				else if (action.equalsIgnoreCase("move"))
					i.move();
				else if (action.equalsIgnoreCase("use"))
					i.use();
				else if (action.equalsIgnoreCase("open"))
					i.open();
				else if (action.equalsIgnoreCase("close"))
					i.close();
				else
					Game.print("Invalid command.");
			} catch (NullPointerException ex) {
				Game.print("You don't see a "+itemName+" in this room!");
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
