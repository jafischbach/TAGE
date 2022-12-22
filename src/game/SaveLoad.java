package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class SaveLoad {

	public static String[] getSaves() {
		File files = new File(System.getProperty("user.dir") + "\\saves");
		if (files.exists()) {
			String[] list = files.list(new FilenameFilter() {
				public boolean accept(File f, String name) {
					int len = name.length();
					return len < 4 ? false : name.substring(len - 3, len).equals("sav");
				}
			});
			return list;
		} else {
			return null;
		}
	}

	public static int getOption(String s, int min, int max, String title) throws CancelledInputException {
		int option = Game.getInt(s + "\nEnter option (" + min + "-" + max + "): ", title);
		if (option < min || option > max) {
			if (Game.CONSOLE)
				Game.print("Invalid option.");
			else
				JOptionPane.showMessageDialog(GameGUI.window, "Invalid option.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			return getOption(s, min, max, title);
		}
		return option;
	}

	public static void saveGame() {
		int option;
		int fileID = 1;
		boolean cancel = false;
		String[] saves = getSaves();
		if (saves == null) {
			File files = new File(System.getProperty("user.dir") + "\\saves");
			files.mkdir();
			option = 0;
		} else {
			String s = "0: New Save\n";
			int i;
			for (i = 0; i < saves.length; i++)
				s += (i + 1) + ": " + saves[i] + "\n";
			s += (i + 1) + ": Cancel\n";
			try {
				option = getOption(s, 0, i + 1, "Save Game");
				if (option == 0)
					fileID = i + 1;
				else if (option == i + 1)
					cancel = true;
				else
					fileID = option;
			} catch (CancelledInputException ex) {
				cancel = true;
			}
		}
		if (cancel) {
			Game.print("Save cancelled.");
		} else {
			saveGame(fileID);
		}
	}

	public static void saveGame(int fileID) {
		try {
			File saveFile = new File(System.getProperty("user.dir") + "\\saves\\save" + fileID + ".sav");
			if (saveFile.exists()) {
				char choice = Game.getYesNo("Overwrite save file (y/n)? ");
				if (choice != 'y') {
					Game.print("Save cancelled.");
					return;
				}
			}
			saveFile.createNewFile();
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(saveFile));
			stream.writeObject(Game.player);
			stream.writeObject(Game.visitedRooms);
			stream.writeObject(Game.flags);
			stream.writeObject(Game.currentRoom);
			stream.close();
			Game.print("Game saved.");
		} catch (FileNotFoundException ex) {
			Game.print("Error accessing save file.");
		} catch (IOException ex) {
			Game.print("Error creating save file.");
			ex.printStackTrace();
		}
	}

	public static void loadGame() {
		String[] saves = getSaves();
		if (saves == null)
			Game.print("No saved games.");
		else {
			int i;
			String s = "";
			for (i = 0; i < saves.length; i++)
				s += (i + 1) + ": " + saves[i] + "\n";
			s += (i + 1) + ": Cancel\n";
			try {
				int option = getOption(s, 1, i + 1, "Load Game");
				if (option == i + 1) {
					Game.print("Load cancelled.");
				} else {
					loadGame(option);
				}
			} catch (CancelledInputException ex) {
				Game.print("Load cancelled.");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadGame(int fileID) {
		try {
			File loadFile = new File(System.getProperty("user.dir") + "\\saves\\save" + fileID + ".sav");
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(loadFile));
			Game.player = (Player) stream.readObject();
			Game.visitedRooms = (ArrayList<Room>) stream.readObject();
			Game.flags = (HashMap<String, Integer>) stream.readObject();
			Game.currentRoom = (Room) stream.readObject();
			updateRooms();
			Game.printRoom();
			if (!Game.CONSOLE) 
				GameGUI.display.setText("");
			stream.close();
		} catch (FileNotFoundException ex) {
			Game.print("Save file not found.");
		} catch (IOException ex) {
			Game.print("Error loading save file.");
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void updateRooms() {
		for(Room r : Game.visitedRooms)
			Game.rooms.put(r.getRoomLabel(), r);
	}
	
}
