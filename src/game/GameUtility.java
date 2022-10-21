package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class GameUtility {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter filename to convert: ");
		fileName = input.nextLine();
		input.close();
		convertGameFiles();
	}

	private static final int OFFSET = 113;

	private static HashMap<String, String> descs = new HashMap<String, String>();
	private static String fileName;

	public static void convertGameFiles() {
		try {
			populateDescs();
			writeFile();
		} catch (FileNotFoundException ex) {
			System.out.println("File " + fileName + " not found.");
		}
	}

	private static void writeFile() {
		try {
			File outFile = new File(fileName.substring(0, fileName.length() - 3) + "dat");
			outFile.createNewFile();
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(outFile));
			stream.writeObject(descs);
			stream.close();
			System.out.println("File converted.");
		} catch (FileNotFoundException ex) {
			System.out.println("Error accessing file.");
		} catch (IOException ex) {
			System.out.println("Error creating file.");
			ex.printStackTrace();
		}
	}

	private static void populateDescs() throws FileNotFoundException {
		Scanner descReader = new Scanner(new File(fileName));
		while (descReader.hasNextLine()) {
			String label = descReader.nextLine();
			String desc = descReader.nextLine();
			while (descReader.hasNextLine()) {
				String line = descReader.nextLine();
				if (line.equals("#"))
					break;
				desc += line;
			}
			descs.put(obscure(label), obscure(desc));
		}
	}

	private static String obscure(String s) {
		String o = "";
		for (char c : s.toCharArray())
			o += (char) (c + OFFSET);
		return o;
	}

}
