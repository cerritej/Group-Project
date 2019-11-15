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

	private JButton startBtn = new JButton("");
	private JButton loadBtn = new JButton("");
	private JButton optionBtn = new JButton("");
	private JButton secretBtn = new JButton("");

	private static final long serialVersionUID = 1L;

	/******************************************************************
	 * Main method that runs the primaryGUI class.
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 ******************************************************************/
	primaryGUI() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		super("Soul Survivor");

		// Allows an audio clip to play in the background on the main menu.
		AudioInputStream music = AudioSystem.getAudioInputStream(new File("resources\\music\\Atmospheric Lab.wav"));
		AudioFormat format = music.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip audioClip = (Clip) AudioSystem.getLine(info);

		// Opens and plays the assigned main menu audio clip.
		audioClip.open(music);
		audioClip.start();
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);

		// Instantiates panels for the mainMenu.
		JLayeredPane backgroundPane = new JLayeredPane();
		JLayeredPane mainMenuButtonsPane = new JLayeredPane();

		// Creates a layout manager to handle the organization.
		OverlayLayout overlayBackgroundPanel = new OverlayLayout(backgroundPane);

		// Sets the layouts for the panels.
		backgroundPane.setLayout(overlayBackgroundPanel);
		mainMenuButtonsPane.setLayout(null);

		// Creates a background for the GUI using the assigned photo.
		JLabel mainMenu = new JLabel("");
		mainMenu.setMaximumSize(new Dimension(1920, 1080));
		mainMenu.setIcon(new ImageIcon("resources\\ui\\Main Menu.jpg"));
		backgroundPane.add(mainMenu);

		// ---------- Start Button ------------ //

		// Creates and aligns the start button for the main menu.
		startBtn.setOpaque(true);
		startBtn.setContentAreaFilled(false);
		startBtn.setBorderPainted(false);

		mainMenuButtonsPane.add(startBtn);
		startBtn.setBounds(10, 905, 620, 75);

		// The functionality of the startBtn.
		startBtn.addActionListener(event -> {
			if(event.getSource() == startBtn){
				startBtn.setVisible(false);
				loadBtn.setVisible(false);
				optionBtn.setVisible(false);
				secretBtn.setVisible(false);

				mainMenu.setIcon(new ImageIcon("resources\\photos\\introduction\\Intro Sequence 1.jpg"));
				audioClip.stop();

				AudioInputStream music1 = null;
				try {
					music1 = AudioSystem.getAudioInputStream(new File("resources\\music\\Soul Survivor.wav"));
				} catch (UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}

				AudioFormat format1 = music1.getFormat();
				DataLine.Info info1 = new DataLine.Info(Clip.class, format1);
				Clip audioClip1 = null;
				try {
					audioClip1 = (Clip) AudioSystem.getLine(info1);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}

				// Opens and plays the updated music track for the primary game.
				try {
					audioClip1.open(music1);
				} catch (LineUnavailableException | IOException e) {
					e.printStackTrace();
				}

				audioClip1.start();
				audioClip1.loop(Clip.LOOP_CONTINUOUSLY);
			}
		});

		// ---------- Load Button ------------ //

		// Creates and aligns the load button for the main menu.
		loadBtn.setOpaque(true);
		loadBtn.setContentAreaFilled(false);
		loadBtn.setBorderPainted(false);

		mainMenuButtonsPane.add(loadBtn);
		loadBtn.setBounds(10, 1003, 570, 75);

		// ---------- Options Button ------------ //

		// Creates and aligns the options button for the main menu.
		optionBtn.setOpaque(true);
		optionBtn.setContentAreaFilled(false);
		optionBtn.setBorderPainted(false);

		mainMenuButtonsPane.add(optionBtn);
		optionBtn.setBounds(1500, 1003, 410, 75);

		// ---------- Credits Button ------------ //

		// Creates and aligns the credits button for the main menu.
		secretBtn.setOpaque(true);
		secretBtn.setContentAreaFilled(false);
		secretBtn.setBorderPainted(false);

		mainMenuButtonsPane.add(secretBtn);
		secretBtn.setBounds(10, 10, 970, 75);

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
