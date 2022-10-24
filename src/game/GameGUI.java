package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import world.World;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameGUI {

	public static final int FONT_SIZE = 18;
	public static final String ICON_FILENAME = "bartenderIcon.png";

	public static JLabel roomNameLabel;
	public static JTextArea roomDisplay;
	public static JTextArea display;
	public static JTextField command;
	public static JFrame window;
	public static JMenuItem saveMenuItem;
	private static String lastCommand = "";

	public static void displayRoom(Room r) {
		roomNameLabel.setText(r.getName());
		roomDisplay.setText(r.getDesc());
	}
	
	public static void setResponseColor(Color c) {
		display.setForeground(c);
	}

	public static void setRoomColor(Color c) {
		roomDisplay.setForeground(c);
	}
	
	public static void buildWindow() {
		window = new JFrame();
		window.setTitle(Game.TITLE);
		try {
			window.setIconImage(ImageIO.read(new File(ICON_FILENAME)));
		} catch(IOException ex) {
			
		}
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setJMenuBar(buildMenuBar());

		window.setLayout(new BorderLayout());
		
		roomNameLabel = new JLabel("Room");
		roomNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		roomNameLabel.setFont(new Font(null, Font.BOLD, FONT_SIZE));
		window.add(roomNameLabel, BorderLayout.NORTH);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.5;
		mainPanel.add(buildRoomDisplay(), c);
		c.gridy = 1;
		c.gridheight = 2;
		c.ipady = 100;
		mainPanel.add(buildDisplay(), c);
		window.add(mainPanel, BorderLayout.CENTER);
		window.add(buildCommandPanel(), BorderLayout.SOUTH);

		window.setSize(800, 550);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	private static JScrollPane buildRoomDisplay() {
		roomDisplay = new JTextArea();
		roomDisplay.setFont(new Font(null, Font.PLAIN, FONT_SIZE));
		roomDisplay.setEditable(false);
		roomDisplay.setFocusable(false);
		roomDisplay.setLineWrap(true);
		roomDisplay.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret) roomDisplay.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		roomDisplay.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createEtchedBorder()));
		JScrollPane scrollPane = new JScrollPane(roomDisplay);
		return scrollPane;
	}

	private static JScrollPane buildDisplay() {
		display = new JTextArea();
		display.setFont(new Font(null, Font.PLAIN, FONT_SIZE));
		setResponseColor(Color.BLUE);
		display.setEditable(false);
		display.setFocusable(false);
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret) display.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		display.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createEtchedBorder()));
		JScrollPane scrollPane = new JScrollPane(display);
		return scrollPane;
	}

	private static JPanel buildCommandPanel() {
		JPanel commandPanel = new JPanel(new GridLayout(2, 1));
		commandPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel commandLabel = new JLabel("What do you want to do?");
		commandLabel.setFont(new Font(null, Font.ITALIC, FONT_SIZE));
		commandPanel.add(commandLabel);
		command = new JTextField();
		command.setFont(new Font(null, Font.PLAIN, FONT_SIZE));
		command.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent event) {
				if (event.getKeyChar() == '\n') {
					display.setText("");
					if (Game.convo) {
						try {
							int choice = Integer.parseInt(command.getText());
							if (choice > 0 && choice <= Game.convoOptions) {
								Game.convo = false;
								Game.character.response(choice);
							} else {
								Game.print("That is not a valid option.");
							}
						} catch (NumberFormatException ex) {
							Game.print("You must enter a number.");
						}
					} else {
						//Game.print("--------------------------------------");
						Game.processCommand(command.getText());
					}
					lastCommand = command.getText();
					command.setText("");
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					command.setText(lastCommand);
				}
			}
		});
		commandPanel.add(command);
		return commandPanel;
	}

	private static JMenuBar buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(window,
						"Are you sure you want to start a new game? You will lose all unsaved progress.",
						"New Game", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					Player.inventory = new HashMap<String, Item>();
					Game.startGame();
					World.buildWorld();
					display.setText("");
					Game.printRoom();
					command.setEditable(true);
					saveMenuItem.setEnabled(true);
				}
			}
		});
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveLoad.saveGame();
			}
		});
		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveLoad.loadGame();
				command.setEditable(true);
				saveMenuItem.setEnabled(true);
			}
		});
		JMenuItem clearMenuItem = new JMenuItem("Clear text");
		clearMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText("");
			}
		});
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(loadMenuItem);
		fileMenu.add(clearMenuItem);
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpMenuItem = new JMenuItem("Command help");
		helpMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.help();
			}
		});
		JMenuItem helpItemsMenuItem = new JMenuItem("Items help");
		helpItemsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.itemHelp();
			}
		});
		JMenuItem helpNpcsMenuItem = new JMenuItem("Character help");
		helpNpcsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.npcHelp();
			}
		});
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = Game.TITLE + "\n";
				s += "Version: " + Game.VERSION + "\n\n";
				s += "Developer: " + Game.DEVELOPER + "\n\n";
				s += "FlossTAGE Version: beta";
				JOptionPane.showMessageDialog(window, s, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(helpMenuItem);
		helpMenu.add(helpItemsMenuItem);
		helpMenu.add(helpNpcsMenuItem);
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);

		return menuBar;
	}

}
