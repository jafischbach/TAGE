package game;

import java.util.Arrays;
import java.util.List;

public class Parser {

	private static final List<String> complexCommands = Arrays.asList("give", "attack");
	private static final List<String> simpleCommands = Arrays.asList("look", "save", "load", "help", "help item", "help npc");

	private static NPC getNPC(Room r, String name) {
		NPC npc = r.npcs.get(name);
		if (npc == null) {
			if (r.hasItem(name))
				throw new InvalidActionException("You can't do that to the " + name + ", weirdo.");
			else
				throw new InvalidActionException("There is no " + name + " in the room.");
		} else {
			return npc;
		}
	}

	private static void processComplexCommand(Room r, String action, String command) {
		if (r.npcs == null)
			throw new InvalidActionException("There's no one here, dude!");
		if (action.equalsIgnoreCase("give")) {
			int i = command.indexOf(" to ");
			if (i < 0)
				throw new InvalidActionException("Give what to whom?");
			String itemName = command.substring(5, i);
			String npcName = command.substring(i+4);
			NPC npc = getNPC(r, npcName);
			if (Game.player.has(itemName))
				npc.give(itemName);
			else
				Game.print("You don't have a " + itemName + ".");
		} else if (action.equalsIgnoreCase("attack")) {
//			if (command.length() == 6)
//				throw new InvalidActionException("Attack whom?");
			int i = command.indexOf(" with ");
			if (i < 0) {
				String npcName = command.substring(7);
				getNPC(r, npcName).attack();
			} else {
				String npcName = command.substring(7, i);
				String weaponName = command.substring(i+6);
				NPC npc = getNPC(r, npcName);
				if (Game.player.has(weaponName))
					npc.attack(weaponName);
				else
					Game.print("You don't have a " + weaponName + ".");
			}
		}
	}

	private static void processSimpleCommand(Room r, String command) {
		if (command.equalsIgnoreCase("look")) {
			if (Game.CONSOLE)
				Game.print(r.getDesc());
		} else if (command.equalsIgnoreCase("save"))
			SaveLoad.saveGame();
		else if (command.equalsIgnoreCase("load"))
			SaveLoad.loadGame();
		else if (command.equalsIgnoreCase("help"))
			Game.help();
		else if (command.equalsIgnoreCase("help item"))
			Game.itemHelp();
		else if (command.equalsIgnoreCase("help npc"))
			Game.npcHelp();
	}

	private static void processLook(Room r, Item i, String itemName) {
		if (i != null)
			i.look();
		else {
			String itemDesc = r.getSimpleItemDesc(itemName);
			if (itemDesc != null)
				Game.print(itemDesc);
			else {
				NPC npc = r.npcs.get(itemName);
				if (npc != null)
					npc.look();
				else
					Game.print("You don't see a " + itemName + " here.");
			}
		}
	}

	private static String removeRedundantWS(String in) {
		String out = "";
		for(int i=0; i<in.length();) {
			char c = in.charAt(i++);
			out += c;
			if (Character.isWhitespace(c)) {
				for(; Character.isWhitespace(in.charAt(i)); i++);
			}
		}
		return out;
	}
	
	public static void processCommand(Room r, String command) {
		command = removeRedundantWS(command);
		if (simpleCommands.contains(command))
			processSimpleCommand(r, command);
		else {
			int space = command.indexOf(" ");
			String action = command.substring(0, space);
			if (complexCommands.contains(action)) {
				processComplexCommand(r, action, command);
			} else {
				String itemName = command.substring(space+1);
				Item i = Game.player.getItem(itemName);
				NPC c = r.npcs == null ? null : r.npcs.get(itemName);
				if (i == null && r.items != null)
					i = r.items.get(itemName);
				try {
					if (action.equalsIgnoreCase("look"))
						processLook(r, i, itemName);
					else if (action.equalsIgnoreCase("talk")) {
						c.talk();
					} else if (action.equalsIgnoreCase("take"))
						i.take();
					else if (action.equalsIgnoreCase("move"))
						i.move();
					else if (action.equalsIgnoreCase("use"))
						i.use();
					else if (action.equalsIgnoreCase("open"))
						i.open();
					else if (action.equalsIgnoreCase("close"))
						i.close();
					else if (action.equalsIgnoreCase("equip"))
						Game.player.equip(itemName);
					else
						i.uniqueCommand(action);
				} catch (NullPointerException ex) {
					String itemDesc = r.getSimpleItemDesc(itemName);
					if (itemDesc != null || c != null)
						Game.print("You can't do that with " + itemName + ".");
					else
						Game.print("You don't see a " + itemName + " in this room!");
				}
			}
		}
	}

}
