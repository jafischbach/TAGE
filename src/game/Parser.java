package game;

import java.util.Arrays;
import java.util.List;

/**
 * Processes commands entered by the player. Parser is not case sensitive.
 */
public class Parser {
	
	// Various lists of words/letters used during parsing.
	private static final List<String> complexCommands = Arrays.asList("give", "attack");
	private static final List<String> simpleCommands = Arrays.asList("look", "save", "load", "help", "help item", "help npc");
	private static final List<String> directions = Arrays.asList("north", "south", "east", "west", "up", "down");
	private static final List<String> extraneousWords = Arrays.asList("at", "a", "the", "on", "in", "inside");
	private static final List<String> lookQualifiers = Arrays.asList("behind", "beneath", "under");
	private static final List<String> travelWords = Arrays.asList("go", "walk", "run", "climb", "travel");
	private static final List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

	/**
	 * Processes the NPC interactions "give" and "attack".
	 * @param r current room
	 * @param action interaction initiated by player ("give" or "attack")
	 * @param command entire command entered by player
	 */
	private static void processComplexCommand(Room r, String action, String command) {
		if (r.npcs == null)
			throw new InvalidActionException("There's no one here, dude!");
		if (action.equals("give")) {
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
		} else if (action.equals("attack")) {
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

	/**
	 * Processes the simple "look", "save", "load", and "help" commands.
	 * For "look", the player is looking at the room, not an item or NPC.
	 * @param r current room
	 * @param command command entered by player
	 */
	private static void processSimpleCommand(Room r, String command) {
		if (command.equals("look")) {
			if (Game.CONSOLE)
				Game.print(r.getDesc());
		} else if (command.equals("save"))
			SaveLoad.saveGame();
		else if (command.equals("load"))
			SaveLoad.loadGame();
		else if (command.equals("help"))
			Game.help();
		else if (command.equals("help item"))
			Game.itemHelp();
		else if (command.equals("help npc"))
			Game.npcHelp();
		else
			Game.processCommand(command.charAt(0)+"");
	}

	/**
	 * Processes the "look" interaction. The player is trying to look
	 * at an item or an NPC.
	 * @param r current room
	 * @param rest the string entered by player after the word "look"
	 */
	private static void processLook(Room r, String rest) {
		int spaceIndex = rest.indexOf(' ');
		if (spaceIndex != -1) {
			String where = rest.substring(0, spaceIndex);
			if (lookQualifiers.contains(where)) {
				String itemName = rest.substring(spaceIndex+1, rest.length());
				Item i = getItem(r, itemName);
				if (i != null)
					i.look(where);
				else if (r.hasSimpleItem(itemName))
					Game.print("You see nothing interesting.");
				else if (r.hasNPC(itemName))
					Game.print("Don't be rude.");
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

	// Removes redundant whitespace from the given string.
	// Leaves single spaces in between words.
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
	
	// Removes from the given string all words contained in the
	// extraneousWords list.
	private static String removeExtraneousWords(String in) {
		String[] s = in.split(" ");
		String out = s[0];
		for(int i=1; i<s.length; i++)
			if (!extraneousWords.contains(s[i]))
				out += " " + s[i];
		return out;
	}
	
	/**
	 * Returns the item with the given item name. The item must
	 * be in the current room or in the player's inventory.
	 * @param r current room
	 * @param itemName name of item
	 * @return item or null if the item is not present
	 */
	private static Item getItem(Room r, String itemName) {
		Item i = Game.player.getItem(itemName);
		if (i == null)
			i = r.getItem(itemName);
		return i;
	}
	
	/**
	 * Returns the NPC with the given name. The NPC must be in the
	 * current room.
	 * @param r current room
	 * @param name name of NPC
	 * @return NPC or null is NPC is not present
	 */
	private static NPC getNPC(Room r, String name) {
		NPC npc = r.getNPC(name);
		if (npc == null && r.hasItem(name))
			throw new InvalidActionException("You can't do that to the " + name + ", weirdo.");
		return npc;
	}
	
	/**
	 * Processes the given command. Command is assumed to be lower-case.
	 * @param r current room
	 * @param command command entered by player
	 */
	protected static void processCommand(Room r, String command) {
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
					if (action.equals("look") || action.equals("search"))
						processLook(r, rest);
					else if (action.equals("talk") || action.equals("speak")) {
						rest = rest.replaceFirst("to ", "");
						getNPC(r, rest).talk();
					} else if (action.equals("take") || action.equals("get")) {
						getItem(r, rest).take(action);
					} else if (action.equals("pick")) {
						if (rest.substring(0,3).equals("up ")) {
							String name = rest.replaceFirst("up ", "");
							Item i = getItem(r, name);
							if (i == null)
								getNPC(r, name).uniqueCommand("pick up");
							else
								i.take("pick up");
						} else
							getItem(r, rest).uniqueCommand("pick");
					} else if (action.equals("move") || action.equals("push")
							|| action.equals("pull"))
						getItem(r, rest).move(action);
					else if (action.equals("use"))
						getItem(r, rest).use();
					else if (action.equals("open") || action.equals("unlock")) {
						Item i = getItem(r, rest);
						if (i == null && rest.contains("door"))
							Game.print("Either go in that direction or use an item.");
						else
							i.open();
					} else if (action.equals("close"))
						getItem(r, rest).close();
					else if (action.equals("equip"))
						Game.player.equip(rest);
					else if (travelWords.contains(action)) {
						rest = rest.replaceFirst("to ", "");
						if(directions.contains(rest.split(" ")[0].toLowerCase()))
							Game.processCommand(rest.charAt(0)+"");
						else
							Game.print("You can't go "+rest+"!");
					} else {
						Item i = getItem(r, rest);
						if (i == null)
							getNPC(r, rest).uniqueCommand(action);
						else
							i.uniqueCommand(action);
					}
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
