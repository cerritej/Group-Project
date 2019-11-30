package gamePackage;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

	/* A dynamic value to help specify gameState conditions. */
	private int gameState = 0;

	/* Buttons for the main menu */
	private JButton startBtn = new JButton("");
	private JButton loadBtn = new JButton("");
	private JButton optionBtn = new JButton("");
	private JButton secretBtn = new JButton("");

	/* Buttons for the game screen */
	private JButton exitBtn = new JButton("");
	private JButton openObjBtn = new JButton("");
	private JButton closeObjBtn = new JButton("");
	private JButton inspectObjBtn = new JButton("");
	private JButton useObjBtn = new JButton("");
	private JButton takeItemBtn = new JButton("");
	private JButton beginDialogueBtn = new JButton("");

	/* Buttons for the exiting */
	private JButton confirmBtn = new JButton("Confirm");
	private JButton cancelBtn = new JButton("Cancel");

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
		AudioInputStream musicMainMenu = AudioSystem.getAudioInputStream(new File("resources\\music\\Atmospheric Lab.wav"));
		AudioFormat formatMusicMainMenu = musicMainMenu.getFormat();
		DataLine.Info infoMusicMainMenu = new DataLine.Info(Clip.class, formatMusicMainMenu);
		Clip audioClipMusicMainMenu = (Clip) AudioSystem.getLine(infoMusicMainMenu);

		// Opens and plays the assigned main menu audio clip.
		audioClipMusicMainMenu.open(musicMainMenu);
		audioClipMusicMainMenu.start();
		audioClipMusicMainMenu.loop(Clip.LOOP_CONTINUOUSLY);

		// Instantiates panels for the mainMenu.
		JLayeredPane backgroundPane = new JLayeredPane();
		JLayeredPane gameButtonsPane = new JLayeredPane();

		// Creates a layout manager to handle the organization.
		OverlayLayout overlayBackgroundPanel = new OverlayLayout(backgroundPane);

		// Sets the layouts for the panels.
		backgroundPane.setLayout(overlayBackgroundPanel);
		gameButtonsPane.setLayout(null);

		// Creates a background for the GUI using the assigned photo.
		JLabel mainMenu = new JLabel("");
		mainMenu.setMaximumSize(new Dimension(1920, 1080));
		mainMenu.setIcon(new ImageIcon("resources\\ui\\Main Menu.jpg"));
		backgroundPane.add(mainMenu);

		// ---------- Pause Message ------------ //

		// Creates and aligns the pause message.
		JLabel pauseMessage = new JLabel();
		pauseMessage.setOpaque(true);
		pauseMessage.setFont(new Font("Dialog", Font.BOLD, 20));
		pauseMessage.setText("WOULD YOU LIKE TO QUIT?");
		pauseMessage.setBackground(Color.black);
		pauseMessage.setForeground(Color.red);

		gameButtonsPane.add(pauseMessage);
		pauseMessage.setHorizontalAlignment(SwingConstants.CENTER);
		pauseMessage.setVerticalAlignment(SwingConstants.CENTER);
		pauseMessage.setBounds(1400,875,405,40);
		pauseMessage.setVisible(false);

		// ---------- Start Button ------------ //

		// Creates and aligns the start button for the main menu.
		startBtn.setOpaque(true);
		startBtn.setContentAreaFilled(false);
		startBtn.setBorderPainted(false);

		// Adds the start button.
		gameButtonsPane.add(startBtn);
		startBtn.setBounds(10, 905, 620, 75);

		// The functionality of the start button, changes all resources to the primary game screen.
		startBtn.addActionListener(beginGame -> {
			if(beginGame.getSource() == startBtn){
				// Acknowledges the gameState has changed to the game screen.
				gameState = 1;

				// Hide the main menu buttons.
				startBtn.setVisible(false);
				loadBtn.setVisible(false);
				optionBtn.setVisible(false);
				secretBtn.setVisible(false);

				// Reveal the overlay buttons.
				exitBtn.setVisible(true);
				openObjBtn.setVisible(true);
				closeObjBtn.setVisible(true);
				inspectObjBtn.setVisible(true);
				useObjBtn.setVisible(true);
				takeItemBtn.setVisible(true);
				beginDialogueBtn.setVisible(true);

				// Changes the background image to the game screen.
				mainMenu.setIcon(new ImageIcon("resources\\ui\\introduction\\Intro Sequence 1.jpg"));

				// Ends the main menu music.
				audioClipMusicMainMenu.stop();

				// Assigns an audio file to an AudioInputStream.
				AudioInputStream musicMainGame = null;
				try
				{
					musicMainGame = AudioSystem.getAudioInputStream(new File("resources\\music\\Soul Survivor.wav"));
				}

				catch (UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}

				// Gets information about the music track that has been assigned.
				AudioFormat formatMainGame = musicMainGame.getFormat();
				DataLine.Info infoMainGame = new DataLine.Info(Clip.class, formatMainGame);
				Clip audioClipMainGame = null;
				try
				{
					audioClipMainGame = (Clip) AudioSystem.getLine(infoMainGame);
				}

				catch (LineUnavailableException e) {
					e.printStackTrace();
				}

				// Opens the updated music track for the game screen.
				try
				{
					audioClipMainGame.open(musicMainGame);
				}

				catch (LineUnavailableException | IOException e) {
					e.printStackTrace();
				}

				// Starts and loops the updated music track for the game screen.
				audioClipMainGame.start();
				audioClipMainGame.loop(Clip.LOOP_CONTINUOUSLY);

				// Changes the intro sequence frame with each press of the enter key.
				KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
				Action enterAction = new AbstractAction() {
					// Initializes the intro sequence frame count.
					int counter = 1;

					public void actionPerformed(ActionEvent event) {
						// Increments through each intro sequence frame.
						if(!confirmBtn.isVisible() && !cancelBtn.isVisible()) {
							counter++;
							mainMenu.setIcon(new ImageIcon("resources\\ui\\introduction\\Intro Sequence " + counter + ".jpg"));
						}

						// Stops incrementing and begins actual game with first location.
						if(counter > 5){
							mainMenu.setIcon(new ImageIcon("resources\\ui\\locations\\Passenger Quarters.jpg"));
						}
					}
				};

				// Searches for the enter key input that is pressed by the player.
				getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKey, "ENTER");
				getRootPane().getActionMap().put("ENTER", enterAction);

				// Plays a sound when clicked.
				playSound("resources\\sounds\\Click Choice on Main Menu.wav");
			}
		});

		// ---------- Load Button ------------ //

		// Creates and aligns the load button for the main menu.
		loadBtn.setOpaque(true);
		loadBtn.setContentAreaFilled(false);
		loadBtn.setBorderPainted(false);

		// Adds the load button.
		gameButtonsPane.add(loadBtn);
		loadBtn.setBounds(10, 1003, 570, 75);

		// Plays a sound effect when button is clicked.
		loadBtn.addActionListener(loadGame -> {
			if(loadGame.getSource() == loadBtn) {
				playSound("resources\\sounds\\Click Choice on Main Menu.wav");
			}
		});

		// ---------- Options Button ------------ //

		// Creates and aligns the options button for the main menu.
		optionBtn.setOpaque(true);
		optionBtn.setContentAreaFilled(false);
		optionBtn.setBorderPainted(false);

		// Adds the option button.
		gameButtonsPane.add(optionBtn);
		optionBtn.setBounds(1500, 1003, 410, 75);

		// Plays a sound effect when button is clicked.
		optionBtn.addActionListener(optionMenu -> {
			if(optionMenu.getSource() == optionBtn) {
				playSound("resources\\sounds\\Click Choice on Main Menu.wav");
			}
		});

		// ---------- Credits Button ------------ //

		// Creates and aligns the credits button for the main menu.
		secretBtn.setOpaque(true);
		secretBtn.setContentAreaFilled(false);
		secretBtn.setBorderPainted(false);

		// Adds the credits button.
		gameButtonsPane.add(secretBtn);
		secretBtn.setBounds(10, 10, 970, 75);

		// Plays a sound effect when button is clicked.
		secretBtn.addActionListener(credits -> {
			if(credits.getSource() == secretBtn) {
				playSound("resources\\sounds\\Click Choice on Main Menu.wav");
			}
		});

		// ---------- Exit Button ------------ //

		// Creates and aligns the exit button for the game screen.
		exitBtn.setOpaque(true);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		exitBtn.setVisible(false);

		// Adds the exit button.
		gameButtonsPane.add(exitBtn);
		exitBtn.setBounds(28,950,80,80);

		// The functionality of the exit button, brings up exit choices.
		exitBtn.addActionListener(exitMenuDisplay -> {
			confirmBtn.setVisible(true);
			cancelBtn.setVisible(true);
			pauseMessage.setVisible(true);

			exitBtn.setVisible(false);
			openObjBtn.setVisible(false);
			closeObjBtn.setVisible(false);
			inspectObjBtn.setVisible(false);
			useObjBtn.setVisible(false);
			takeItemBtn.setVisible(false);
			beginDialogueBtn.setVisible(false);

			// Plays a sound effect when button is clicked.
			playSound("resources\\sounds\\Pause Menu.wav");
		});

		// ---------- Confirm Button ------------ //

		// Creates and aligns the confirm button for the game screen.
		confirmBtn.setOpaque(true);
		confirmBtn.setContentAreaFilled(true);
		confirmBtn.setBorderPainted(false);
		confirmBtn.setVisible(false);

		// Defines the style of confirm button for the game screen.
		confirmBtn.setBackground(Color.black);
		confirmBtn.setForeground(Color.white);
		confirmBtn.setFont(new Font("Dialog", Font.PLAIN, 20));

		// Adds the confirm button.
		gameButtonsPane.add(confirmBtn);
		confirmBtn.setBounds(1400,925,175,100);

		// The functionality of the confirm button, closes the game.
		confirmBtn.addActionListener(exitGame -> System.exit(0));

		// ---------- Cancel Button ------------ //

		// Creates and aligns the cancel button for the game screen.
		cancelBtn.setOpaque(true);
		cancelBtn.setContentAreaFilled(true);
		cancelBtn.setBorderPainted(false);
		cancelBtn.setVisible(false);

		// Defines the style of cancel button for the game screen.
		cancelBtn.setBackground(Color.black);
		cancelBtn.setForeground(Color.white);
		cancelBtn.setFont(new Font("Dialog", Font.PLAIN, 20));

		// Adds the cancel button.
		gameButtonsPane.add(cancelBtn);
		cancelBtn.setBounds(1630,925,175,100);

		// The functionality of the cancel button, resumes game.
		cancelBtn.addActionListener(resumeGame -> {
			confirmBtn.setVisible(false);
			cancelBtn.setVisible(false);
			pauseMessage.setVisible(false);

			// Performs the correct cancel button operations for main menu.
			if(gameState == 0) {
				startBtn.setVisible(true);
				loadBtn.setVisible(true);
				optionBtn.setVisible(true);
				secretBtn.setVisible(true);
			}

			// Performs the correct cancel button operations for game screen.
			if(gameState == 1) {
				exitBtn.setVisible(true);
				openObjBtn.setVisible(true);
				closeObjBtn.setVisible(true);
				inspectObjBtn.setVisible(true);
				useObjBtn.setVisible(true);
				takeItemBtn.setVisible(true);
				beginDialogueBtn.setVisible(true);
			}
		});

		// ---------- Open Object Button ------------ //

		// Creates and aligns the open object button for the game screen.
		openObjBtn.setOpaque(true);
		openObjBtn.setContentAreaFilled(false);
		openObjBtn.setBorderPainted(true);
		openObjBtn.setVisible(false);

		// Adds the open object button.
		gameButtonsPane.add(openObjBtn);
		openObjBtn.setBounds(131,925,175,130);

		// Plays a sound effect when button is clicked.
		openObjBtn.addActionListener(openAction -> {
			if(openAction.getSource() == openObjBtn) {
				playSound("resources\\sounds\\Click Action Button.wav");
			}
		});

		// ---------- Close Object Button ------------ //

		// Creates and aligns the close object button for the game screen.
		closeObjBtn.setOpaque(true);
		closeObjBtn.setContentAreaFilled(false);
		closeObjBtn.setBorderPainted(true);
		closeObjBtn.setVisible(false);

		// Adds the close object button.
		gameButtonsPane.add(closeObjBtn);
		closeObjBtn.setBounds(332,925,175,130);

		// Plays a sound effect when button is clicked.
		closeObjBtn.addActionListener(closeAction -> {
			if(closeAction.getSource() == closeObjBtn) {
				playSound("resources\\sounds\\Click Action Button.wav");
			}
		});

		// ---------- Inspect Object Button ------------ //

		// Creates and aligns the inspect object button for the game screen.
		inspectObjBtn.setOpaque(true);
		inspectObjBtn.setContentAreaFilled(false);
		inspectObjBtn.setBorderPainted(true);
		inspectObjBtn.setVisible(false);

		// Adds the inspect object button.
		gameButtonsPane.add(inspectObjBtn);
		inspectObjBtn.setBounds(532,924,175,130);

		// Plays a sound effect when button is clicked.
		inspectObjBtn.addActionListener(inspectAction -> {
			if(inspectAction.getSource() == inspectObjBtn) {
				playSound("resources\\sounds\\Click Action Button.wav");
			}
		});

		// ---------- Use Item Button ------------ //

		// Creates and aligns the use item button for the game screen.
		useObjBtn.setOpaque(true);
		useObjBtn.setContentAreaFilled(false);
		useObjBtn.setBorderPainted(true);
		useObjBtn.setVisible(false);

		// Adds the use item button.
		gameButtonsPane.add(useObjBtn);
		useObjBtn.setBounds(730,924,175,130);

		// Plays a sound effect when button is clicked.
		useObjBtn.addActionListener(useAction -> {
			if(useAction.getSource() == useObjBtn) {
				playSound("resources\\sounds\\Click Action Button.wav");
			}
		});

		// ---------- Take Item Button ------------ //

		// Creates and aligns the take item button for the game screen.
		takeItemBtn.setOpaque(true);
		takeItemBtn.setContentAreaFilled(false);
		takeItemBtn.setBorderPainted(true);
		takeItemBtn.setVisible(false);

		// Adds the take item button.
		gameButtonsPane.add(takeItemBtn);
		takeItemBtn.setBounds(927,923,175,130);

		// Plays a sound effect when button is clicked.
		takeItemBtn.addActionListener(takeAction -> {
			if(takeAction.getSource() == takeItemBtn) {
				playSound("resources\\sounds\\Click Action Button.wav");
			}
		});

		// ---------- Begin Dialogue Button ------------ //

		// Creates and aligns the begin dialogue button for the game screen.
		beginDialogueBtn.setOpaque(true);
		beginDialogueBtn.setContentAreaFilled(false);
		beginDialogueBtn.setBorderPainted(true);
		beginDialogueBtn.setVisible(false);

		// Adds the begin dialogue button.
		gameButtonsPane.add(beginDialogueBtn);
		beginDialogueBtn.setBounds(1124,923,175,130);

		// Plays a sound effect when button is clicked.
		beginDialogueBtn.addActionListener(dialogueAction -> {
			if(dialogueAction.getSource() == beginDialogueBtn) {
				playSound("resources\\sounds\\Click Action Button.wav");
			}
		});

		// Adds the primary JPanels of the GUI.
		add(gameButtonsPane);
		add(backgroundPane);

		// Defines the size and general appearance of the frame.
		setSize(1920, 1080);
		gameButtonsPane.setSize(1920, 1080);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);

		// ---------- Keyboard Interactions ------------ //

		// Displays the exit game button options.
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent event) {
				confirmBtn.setVisible(true);
				cancelBtn.setVisible(true);
				pauseMessage.setVisible(true);

				// Performs the correct cancel button operations for main menu.
				if (gameState == 0) {
					startBtn.setVisible(false);
					loadBtn.setVisible(false);
					optionBtn.setVisible(false);
					secretBtn.setVisible(false);
				}

				// Performs the correct cancel button operations for game screen.
				if (gameState == 1) {
					exitBtn.setVisible(false);
					openObjBtn.setVisible(false);
					closeObjBtn.setVisible(false);
					inspectObjBtn.setVisible(false);
					useObjBtn.setVisible(false);
					takeItemBtn.setVisible(false);
					beginDialogueBtn.setVisible(false);
				}

				// Plays a sound effect when clicked.
				playSound("resources\\sounds\\Pause Menu.wav");
			}
		};

		// Searches for the escape key input that is pressed by the player.
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKey, "ESCAPE");
		getRootPane().getActionMap().put("ESCAPE", escapeAction);
	}

	// Play sound effects that do not require looping.
	public void playSound(String soundName)
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip( );
			clip.open(audioInputStream);
			clip.start();
		}

		catch(Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace( );
		}
	}
}
