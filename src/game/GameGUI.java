package game;

import javax.swing.*;
import java.awt.*;

public class GameGUI {

	public static final long serialVersionUID = 1L;
	
	private static final int FONT_SIZE = 26;
	
	public static JTextArea display;
	public static JTextField command;
	
	public static void buildWindow() {
		JFrame window = new JFrame();
		window.setTitle(Game.TITLE);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setLayout(new BorderLayout());
		
		display = new JTextArea();
		display.setFont(new Font(null, Font.PLAIN, FONT_SIZE));
		window.add(display, BorderLayout.CENTER);
		
		JPanel commandPanel = new JPanel(new GridLayout(2, 1));
		JLabel commandLabel = new JLabel("What do you want to do?");
		commandLabel.setFont(new Font(null, Font.ITALIC, FONT_SIZE));
		commandPanel.add(commandLabel);
		command = new JTextField();
		command.setFont(new Font(null, Font.PLAIN, FONT_SIZE));
		commandPanel.add(command);
		window.add(commandPanel, BorderLayout.SOUTH);
		
		window.setSize(800, 800);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
