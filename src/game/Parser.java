package game;

import java.util.Arrays;
import java.util.List;

public class Parser {

	private static final List<String> complexCommands = Arrays.asList("give", "attack");
	private static final List<String> simpleCommands = Arrays.asList("look", "save", "load", "help", "help item", "help npc");
	private static final List<String> directions = Arrays.asList("north", "south", "east", "west", "up", "down");
	private static final List<String> extraneousWords = Arrays.asList("at", "a", "the", "on", "in", "inside");
	private static final List<String> lookQualifiers = Arrays.asList("behind", "beneath", "under");
	private static final List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

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
		else
			Game.processCommand(command.charAt(0)+"");
	}

	private static void processLook(Room r, String rest) {
		int spaceIndex = rest.indexOf(' ');
		if (spaceIndex != -1) {
			String where = rest.substring(0, spaceIndex);
			if (lookQualifiers.contains(where)) {
				String itemName = rest.substring(spaceIndex+1, rest.length());
				Item i = getItem(r, itemName);
				if (i != null)
					i.look(where);
				else if (Game.getSimpleItem(itemName) != null)
					Game.print("You see nothing interesting.");
				else
					Game.print("There is no " + itemName + " here!");
				return;
			}
		}
		Item i = getItem(r, rest);
		if (i != null) {
			i.look();
		}else {
			String itemDesc = r.getSimpleItemDesc(rest);
			if (itemDesc != null)
				Game.print(itemDesc);
			else {
				NPC npc = getNPC(r, rest);
				if (npc != null)
					npc.look();
				else if (vowels.contains(rest.charAt(0)))
					Game.print("You don't see an " + rest + " here.");
				else
					Game.print("You don't see a " + rest + " here.");
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
	
	private static String removeExtraneousWords(String in) {
		String[] s = in.split(" ");
		String out = s[0];
		for(int i=1; i<s.length; i++)
			if (!extraneousWords.contains(s[i]))
				out += " " + s[i];
		return out;
	}
	
	private static Item getItem(Room r, String itemName) {
		Item i = Game.player.getItem(itemName);
		if (i == null)
			i = r.getItem(itemName);
		return i;
	}
	
	private static NPC getNPC(Room r, String name) {
		NPC npc = r.getNPC(name);
		if (npc == null) {
			if (r.hasItem(name))
				throw new InvalidActionException("You can't do that to the " + name + ", weirdo.");
//			else
//				throw new InvalidActionException("There is no " + name + " in the room.");
		}
		return npc;
	}
	
	public static void processCommand(Room r, String command) {
		command = removeRedundantWS(command);
		command = removeExtraneousWords(command);
		if (simpleCommands.contains(command) || directions.contains(command))
			processSimpleCommand(r, command);
		else {
			int space = command.indexOf(" ");
			String action = command.substring(0, space);
			if (complexCommands.contains(action)) {
				processComplexCommand(r, action, command);
			} else {
				String rest = command.substring(space+1);
				try {
					if (action.equalsIgnoreCase("look"))
						processLook(r, rest);
					else if (action.equalsIgnoreCase("talk") || action.equalsIgnoreCase("speak")) {
						rest = rest.replaceFirst("to ", "");
						getNPC(r, rest).talk();
					} else if (action.equalsIgnoreCase("take") || action.equalsIgnoreCase("get")
							|| action.equalsIgnoreCase("pick")) {
						rest = rest.replaceFirst("up ", "");
						getItem(r, rest).take();
					} else if (action.equalsIgnoreCase("move") || action.equalsIgnoreCase("push")
							|| action.equalsIgnoreCase("pull"))
						getItem(r, rest).move();
					else if (action.equalsIgnoreCase("use"))
						getItem(r, rest).use();
					else if (action.equalsIgnoreCase("open")) {
						Item i = getItem(r, rest);
						if (i == null && rest.contains("door"))
							Game.print("Either go in that direction or use an item.");
						else
							i.open();
					} else if (action.equalsIgnoreCase("close"))
						getItem(r, rest).close();
					else if (action.equalsIgnoreCase("equip"))
						Game.player.equip(rest);
					else if (action.equalsIgnoreCase("go")) {
						rest = rest.replaceFirst("to ", "");
						if(directions.contains(rest.toLowerCase()))
							Game.processCommand(rest.charAt(0)+"");
						else
							Game.print("You can't go "+rest+"!");
					} else
						getItem(r, rest).uniqueCommand(action);
				} catch (NullPointerException ex) {
					String itemDesc = r.getSimpleItemDesc(rest);
					if (itemDesc != null || getNPC(r, rest) != null)
						Game.print("You can't do that with " + rest + ".");
					else
						Game.print("You can't do that!");
				}
			}
		}
	}

}
