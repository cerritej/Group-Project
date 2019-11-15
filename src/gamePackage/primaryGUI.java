package gamePackage;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/***********************************************************************
 * The primaryGUI class creates the components and general functions
 * that can be displayed when the GUI is executed.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (10/25/18)
 ***********************************************************************/
class primaryGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	/******************************************************************
	 * Main method that runs the primaryGUI class.
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 ******************************************************************/
	primaryGUI() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		super("Soul Survivor");

		// Instantiates panels for the mainMenu.
		JLayeredPane backgroundPane = new JLayeredPane();
		JLayeredPane mainMenuButtonsPane = new JLayeredPane();

		// Creates a layout manager to handle the organization.
		OverlayLayout overlayBackgroundPanel = new OverlayLayout(backgroundPane);

		// Sets the layouts for the  panels.
		backgroundPane.setLayout(overlayBackgroundPanel);
		mainMenuButtonsPane.setLayout(null);

		// Creates a background for the GUI using the assigned photo.
		JLabel mainMenu = new JLabel("");
		mainMenu.setMaximumSize(new Dimension(1920, 1080));
		mainMenu.setIcon(new ImageIcon("resources\\ui\\Main Menu.jpg"));
		backgroundPane.add(mainMenu);

		// Creates and aligns the start button for the main menu.
		JButton startBtn = new JButton("");
		startBtn.setOpaque(true);
		startBtn.setContentAreaFilled(false);
		startBtn.setBorderPainted(true);
		mainMenuButtonsPane.add(startBtn);
		startBtn.setBounds(10, 905, 620, 75);

		// Creates and aligns the load button for the main menu.
		JButton loadBtn = new JButton("");
		loadBtn.setOpaque(true);
		loadBtn.setContentAreaFilled(false);
		loadBtn.setBorderPainted(true);
		mainMenuButtonsPane.add(loadBtn);
		loadBtn.setBounds(10, 1003, 570, 75);

		// Creates and aligns the options button for the main menu.
		JButton optionBtn = new JButton("");
		optionBtn.setOpaque(true);
		optionBtn.setContentAreaFilled(false);
		optionBtn.setBorderPainted(true);
		mainMenuButtonsPane.add(optionBtn);
		optionBtn.setBounds(1500, 1003, 410, 75);

		// Creates and aligns the credits button for the main menu.
		JButton secretBtn = new JButton("");
		secretBtn.setOpaque(true);
		secretBtn.setContentAreaFilled(false);
		secretBtn.setBorderPainted(true);
		mainMenuButtonsPane.add(secretBtn);
		secretBtn.setBounds(10, 10, 970, 75);

		// Allows an audio clip to play in the background on the main menu.
		AudioInputStream music = AudioSystem.getAudioInputStream(new File("resources\\music\\Atmospheric Lab.wav"));
		AudioFormat format = music.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip audioClip = (Clip) AudioSystem.getLine(info);

		// Opens and plays the assigned main menu audio clip.
		audioClip.open(music);
		audioClip.start();
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);

		// Adds the primary JPanels of the GUI.
		add(mainMenuButtonsPane);
		add(backgroundPane);

		// Defines the size and general appearance of the frame.
		setSize(1920, 1080);
		mainMenuButtonsPane.setSize(1920, 1080);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);

		// Performs an exit action when the escape key is pressed on the keyboard.
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};

		// Searches for the escape key input that is pressed by the player.
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKey, "ESCAPE");
		getRootPane().getActionMap().put("ESCAPE", escapeAction);
	}
}
