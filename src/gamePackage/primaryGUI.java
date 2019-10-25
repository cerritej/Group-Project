package gamePackage;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

class primaryGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public primaryGUI() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		super("Soul Survivor");

		// Allows the panel to draw the specified components.
		JPanel panel = new JPanel() {
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};

		// Defines the layout for the GUI.
		LayoutManager overlay = new OverlayLayout(panel);
		panel.setLayout(overlay);

		// Creates and aligns the start button for the main menu.
		JButton startBtn = new JButton("");
		startBtn.setMaximumSize(new Dimension(25, 25));
		startBtn.setAlignmentX(CENTER_ALIGNMENT);
		startBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(startBtn);

		// Creates and aligns the load button for the main menu.
		JButton loadBtn = new JButton("");
		loadBtn.setMaximumSize(new Dimension(50, 50));
		loadBtn.setAlignmentX(CENTER_ALIGNMENT);
		loadBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(loadBtn);

		// Creates and aligns the options button for the main menu.
		JButton optnBtn = new JButton("");
		optnBtn.setMaximumSize(new Dimension(100, 100));
		optnBtn.setAlignmentX(CENTER_ALIGNMENT);
		optnBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(optnBtn);

		// Creates and aligns the credits button for the main menu.
		JButton secretBtn = new JButton("");
		secretBtn.setMaximumSize(new Dimension(200, 200));
		secretBtn.setAlignmentX(CENTER_ALIGNMENT);
		secretBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(secretBtn);

		// Creates a background for the GUI using the assigned photo.
		JLabel mainMenu = new JLabel("");
		mainMenu.setMaximumSize(new Dimension(1920, 1080));
		mainMenu.setAlignmentX(CENTER_ALIGNMENT);
		mainMenu.setAlignmentY(CENTER_ALIGNMENT);
		mainMenu.setIcon(new ImageIcon("resources\\ui\\Main Menu.jpg"));
		panel.add(mainMenu);

		// Allows an audio clip to play in the background on the main menu.
		AudioInputStream music = AudioSystem.getAudioInputStream(new File("resources\\music\\Atmospheric Lab.wav"));
		AudioFormat format = music.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip audioClip = (Clip) AudioSystem.getLine(info);

		// Opens and plays the assigned main menu audio clip.
		audioClip.open(music);
		audioClip.start();
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);

		// Adds and center aligns the primary JPanel of the GUI.
		add(panel, BorderLayout.CENTER);

		// Defines the size and general appearance of the frame.
		setSize(1920, 1080);
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
