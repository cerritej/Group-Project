package gamepackage;

import logicpackage.Location;
import logicpackage.Soundplayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;

/***********************************************************************
 * The PrimaryGUI class creates the components and general functions
 * that can be displayed when the GUI is executed.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson)
 * @version (10 / 25 / 19)
 ***********************************************************************/

class PrimaryGUI extends JFrame {
    /***********************************************************************
     * Specifies what section of the game the player is at.
     ***********************************************************************/
    private int gameState = 0;

    /***********************************************************************
     * Records the amount of times an action button is clicked.
     ***********************************************************************/
    private int clickCount = 0;

    /***********************************************************************
     * Tracks the timestamp of the main menu music track.
     ***********************************************************************/
    private long clipTimeMainMenu = 0;

    /***********************************************************************
     * Tracks the timestamp of the main game music track.
     ***********************************************************************/
    private long clipTimeMainGame = 0;

    /***********************************************************************
     * Displays game messages for the player.
     ***********************************************************************/
    private final JLabel notification = new JLabel();

    /***********************************************************************
     * Displays object selection for the player.
     ***********************************************************************/
    private final JLabel selection = new JLabel();

    /***********************************************************************
     * Starts the game.
     ***********************************************************************/
    private final JButton startBtn = new JButton("");

    /***********************************************************************
     * Loads the game.
     ***********************************************************************/
    private final JButton loadBtn = new JButton("");

    /***********************************************************************
     * Takes player to options menu.
     ***********************************************************************/
    private final JButton optionBtn = new JButton("");

    /***********************************************************************
     * Takes player to credits screen.
     ***********************************************************************/
    private final JButton secretBtn = new JButton("");

    /***********************************************************************
     * Displays the pause menu.
     ***********************************************************************/
    private final JButton exitBtn = new JButton("");

    /***********************************************************************
     * Opens the selected object.
     ***********************************************************************/
    private final JButton openObjBtn = new JButton("");

    /***********************************************************************
     * Closes the selected object.
     ***********************************************************************/
    private final JButton closeObjBtn = new JButton("");

    /***********************************************************************
     * Inspects the selected object.
     ***********************************************************************/
    private final JButton inspectObjBtn = new JButton("");

    /***********************************************************************
     * Use the selected object or pulls up inventory.
     ***********************************************************************/
    private final JButton useObjBtn = new JButton("");

    /***********************************************************************
     * Takes the selected object.
     ***********************************************************************/
    private final JButton takeItemBtn = new JButton("");

    /***********************************************************************
     * Talks to the selected object.
     ***********************************************************************/
    private final JButton beginDialogueBtn = new JButton("");

    /***********************************************************************
     * Exits the game.
     ***********************************************************************/
    private final JButton confirmBtn = new JButton("CONFIRM");

    /***********************************************************************
     * Unpauses the game.
     ***********************************************************************/
    private final JButton cancelBtn = new JButton("CANCEL");

    /***********************************************************************
     * Transfers to the player to the next location.
     ***********************************************************************/
    private final JButton redArrowForwards = new JButton("");

    /***********************************************************************
     * Transfers to the player to the previous location.
     ***********************************************************************/
    private final JButton redArrowBackwards = new JButton("");

    /***********************************************************************
     * Uses the chosen item in the inventory.
     ***********************************************************************/
    private final JButton usableItem = new JButton("");

    /***********************************************************************
     * Selects the passenger quarters computer object.
     ***********************************************************************/
    private final JCheckBox passengerQuartersComputerCB = new JCheckBox();

    /***********************************************************************
     * Selects the passenger corridor keypad object.
     ***********************************************************************/
    private final JCheckBox passengerCorridorKeypadCB = new JCheckBox();

    private static final long serialVersionUID = 1L;

    /******************************************************************
     * Main method that runs the primaryGUI class.
     * @throws IOException Error for input/output violations.
     * @throws UnsupportedAudioFileException Error for wrong audio file.
     * @throws LineUnavailableException Error for unavailable dataline.
     ******************************************************************/
    PrimaryGUI() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        super("Soul Survivor");

        // An ArrayList for storing the items in the inventory.
        ArrayList<String> inventory = new ArrayList<>(10);

        // An ArrayList for storing the inventory use history of the player.
        ArrayList<String> useHistory = new ArrayList<>(10);

        // location object helps with bundling buttons with current room.
        Location currentLocation = new Location();
        currentLocation.setLocation("Main Menu");

        // soundPlayer object allows for non-looping sound effects to be played.
        Soundplayer audio = new Soundplayer();

        // Allows an audio clip to play in the background on the main menu.
        AudioInputStream musicMainMenu = AudioSystem.getAudioInputStream(new File("resources/music/Main Menu Theme.wav"));
        AudioFormat formatMusicMainMenu = musicMainMenu.getFormat();
        DataLine.Info infoMusicMainMenu = new DataLine.Info(Clip.class, formatMusicMainMenu);
        Clip audioClipMusicMainMenu = (Clip) AudioSystem.getLine(infoMusicMainMenu);

        // Opens and plays the assigned main menu audio clip.
        audioClipMusicMainMenu.open(musicMainMenu);
        audioClipMusicMainMenu.start();
        audioClipMusicMainMenu.loop(Clip.LOOP_CONTINUOUSLY);

        // Allows an audio clip to play in the background in the main game.
        AudioInputStream musicMainGame = AudioSystem.getAudioInputStream(new File("resources/music/Soul Survivor.wav"));
        AudioFormat formatMusicMainGame = musicMainGame.getFormat();
        DataLine.Info infoMusicMainGame = new DataLine.Info(Clip.class, formatMusicMainGame);
        Clip audioClipMusicMainGame = (Clip) AudioSystem.getLine(infoMusicMainGame);

        // Instantiates panels for the mainMenu.
        JLayeredPane backgroundPane = new JLayeredPane();
        JLayeredPane gameButtonsPane = new JLayeredPane();

        // Creates a layout manager to handle the organization.
        OverlayLayout overlayBackgroundPanel = new OverlayLayout(backgroundPane);

        // Sets the layouts for the panels.
        backgroundPane.setLayout(overlayBackgroundPanel);
        gameButtonsPane.setLayout(null);

        // Creates an initial background for the GUI using the assigned photo.
        JLabel mainMenu = new JLabel("");
        mainMenu.setMaximumSize(new Dimension(1920, 1080));
        mainMenu.setIcon(new ImageIcon("resources/ui/Main Menu.jpg"));
        backgroundPane.add(mainMenu);

        // ---------- Inventory Item Button ------------ //

        // Creates and aligns the Passenger Quarters computer checkbox.
        usableItem.setOpaque(true);
        usableItem.setContentAreaFilled(true);
        usableItem.setBorderPainted(false);

        usableItem.setFont(new Font("Dialog", Font.BOLD, 20));
        usableItem.setForeground(Color.white);
        usableItem.setBackground(Color.black);

        // Adds the Passenger Quarters computer checkbox.
        gameButtonsPane.add(usableItem);

        // ---------- Arrow Buttons for Exploration ------------ //

        // Creates and aligns the forwards arrow button.
        redArrowForwards.setOpaque(true);
        redArrowForwards.setContentAreaFilled(false);
        redArrowForwards.setBorderPainted(false);

        // Adds the forward arrow button.
        gameButtonsPane.add(redArrowForwards);

        // Creates and aligns the backward arrow button.
        redArrowBackwards.setOpaque(true);
        redArrowBackwards.setContentAreaFilled(false);
        redArrowBackwards.setBorderPainted(false);

        // Adds the backward arrow button.
        gameButtonsPane.add(redArrowBackwards);

        // ---------- Main Game Message ------------ //

        // Creates and aligns the game message.
        notification.setOpaque(false);
        notification.setFont(new Font("Dialog", Font.BOLD, 20));
        notification.setForeground(Color.black);

        gameButtonsPane.add(notification);
        notification.setHorizontalAlignment(SwingConstants.CENTER);
        notification.setVerticalAlignment(SwingConstants.CENTER);
        notification.setBounds(131, 867, 1169, 40);
        notification.setVisible(true);

        // ---------- Object Selection Message ------------ //

        // Creates and aligns the object selection message.
        selection.setOpaque(false);
        selection.setFont(new Font("Dialog", Font.BOLD, 20));
        selection.setForeground(Color.red);
        selection.setBackground(Color.black);

        gameButtonsPane.add(selection);
        selection.setHorizontalAlignment(SwingConstants.CENTER);
        selection.setVerticalAlignment(SwingConstants.CENTER);
        selection.setBounds(42, 40, 300, 40);

        // ---------- Pause Menu Message ------------ //

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
        pauseMessage.setBounds(1400, 875, 405, 40);
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
            if (startBtn == beginGame.getSource()) {
                // Main Game Mode is activated.
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

                redArrowForwards.setVisible(true);

                // Changes the background image to the game screen.
                mainMenu.setIcon(new ImageIcon("resources/ui/introduction/Intro Sequence 1.jpg"));

                // Ends the main menu music.
                audioClipMusicMainMenu.stop();

                // Opens and plays the assigned main menu audio clip.
                try {
                    audioClipMusicMainGame.open(musicMainGame);
                } catch (LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }

                audioClipMusicMainGame.start();
                audioClipMusicMainGame.loop(Clip.LOOP_CONTINUOUSLY);

                // Changes the intro sequence frame with each press of the enter key.
                KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
                Action enterAction = new AbstractAction() {
                    // Initializes the intro sequence frame count.
                    private int counter = 1;

                    public void actionPerformed(final ActionEvent event) {
                        // Increments through each intro sequence frame.
                        if (!confirmBtn.isVisible() && !cancelBtn.isVisible()) {
                            counter++;
                            mainMenu.setIcon(new ImageIcon("resources/ui/introduction/Intro Sequence " + counter + ".jpg"));
                        }

                        // Stops incrementing and begins actual game with first location.
                        if (counter > 5) {
                            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKey, null);

                            mainMenu.setIcon(new ImageIcon("resources/ui/locations/Passenger Quarters.jpg"));
                            currentLocation.setLocation("Passenger Quarters");

                            passengerQuartersComputerCB.setVisible(true);
                        }

                        // ---------- Traveling Between Locations ------------ //

                        switch (currentLocation.getLocation()) {
                            case "Passenger Quarters":
                                // Places the redArrowForwards on the Passenger Quarters screen.
                                redArrowForwards.setBounds(550, 700, 125, 125);

                                // Checkboxes that must appear in Passenger Quarters.
                                passengerQuartersComputerCB.setBounds(1000, 525, 250, 125);

                                // Checkboxes that must disappear in Passenger Quarters.
                                passengerCorridorKeypadCB.setVisible(false);

                                // Creates a mouseListener on the redArrowForwards at the Passenger Quarters.
                                redArrowForwards.addMouseListener(new MouseAdapter() {
                                    // If mouseEntered, display the Passenger Corridor location.
                                    @Override
                                    public void mouseEntered(final MouseEvent e) {
                                        if (currentLocation.getLocation().equals("Passenger Quarters") && gameState == 1) {
                                            notification.setText("TRAVEL TO PASSENGER CORRIDOR");
                                            notification.setForeground(Color.black);
                                            notification.setVisible(true);
                                        }
                                    }

                                    // If mousePressed while object is selected, deny the player's move.
                                    @Override
                                    public void mousePressed(final MouseEvent e) {
                                        if (currentLocation.getLocation().equals("Passenger Quarters") && gameState == 2) {
                                            notification.setText("UNSELECT THE OBJECT TO PROCEED");
                                            notification.setForeground(Color.red);
                                            notification.setVisible(true);

                                            audio.playSound("resources/sounds/Selection is Denied.wav");
                                        }
                                    }

                                    // If mouseExited, set the game message to disappear.
                                    @Override
                                    public void mouseExited(final MouseEvent e) {
                                        if (!redArrowForwards.isSelected()) {
                                            notification.setVisible(false);
                                        }
                                    }
                                });

                            case "Passenger Corridor":
                                // Action listener for allowing the player to proceed forward.
                                redArrowForwards.addActionListener(changeLocationForwards -> {
                                    // Places the traversal arrows on the Passenger Corridor screen.
                                    redArrowForwards.setBounds(600, 520, 125, 125);
                                    redArrowBackwards.setBounds(600, 700, 125, 125);
                                    redArrowBackwards.setVisible(true);

                                    // Resets the selected object that the player chose.
                                    passengerQuartersComputerCB.setSelected(false);
                                    notification.setText("");
                                    clickCount = 0;

                                    // If the player has not used their keycard, notify the door is locked.
                                    if (useHistory.size() == 0 && currentLocation.getLocation().equals("Passenger Corridor")) {
                                        notification.setText("THE DOOR IS LOCKED");
                                        notification.setForeground(Color.red);

                                        audio.playSound("resources/sounds/Selection is Denied.wav");
                                    }

                                    // If the player has used their keycard, notify the door is unlocked.
                                    if (useHistory.size() >= 1 && currentLocation.getLocation().equals("Passenger Corridor")) {
                                        notification.setText("THE DOOR IS UNLOCKED");
                                        notification.setForeground(new Color(0, 153, 0));
                                    }

                                    // Player's current location is now the Passenger Corridor.
                                    mainMenu.setIcon(new ImageIcon("resources/ui/locations/Passenger Corridor.jpg"));
                                    currentLocation.setLocation("Passenger Corridor");

                                    // Traveling from Passenger Corridor to Passenger Quarters.
                                    if (currentLocation.getLocation().equals("Passenger Corridor")) {
                                        redArrowForwards.setBounds(600, 520, 125, 125);
                                        redArrowBackwards.setBounds(600, 700, 125, 125);

                                        // Checkbox that must appear in Passenger Corridor.
                                        passengerCorridorKeypadCB.setBounds(750, 382, 20, 30);
                                        passengerCorridorKeypadCB.setVisible(true);

                                        // Checkbox that must disappear in Passenger Corridor.
                                        passengerQuartersComputerCB.setVisible(false);

                                        // Creates a mouseListener on the redArrowForwards at the Passenger Corridor.
                                        redArrowForwards.addMouseListener(new MouseAdapter() {
                                            // If mouseEntered, display the Main Lobby location.
                                            @Override
                                            public void mouseEntered(final MouseEvent e) {
                                                if (currentLocation.getLocation().equals("Passenger Corridor") && gameState == 1) {
                                                    notification.setText("TRAVEL TO MAIN LOBBY");
                                                    notification.setForeground(Color.black);
                                                    notification.setVisible(true);
                                                }
                                            }

                                            // If mousePressed while object is selected, deny the player's move.
                                            @Override
                                            public void mousePressed(final MouseEvent e) {
                                                if (currentLocation.getLocation().equals("Passenger Corridor") && gameState == 2) {
                                                    notification.setText("UNSELECT THE OBJECT TO PROCEED");
                                                    notification.setForeground(Color.red);
                                                    notification.setVisible(true);

                                                    audio.playSound("resources/sounds/Selection is Denied.wav");
                                                }
                                            }

                                            // If mouseExited, set the game message to disappear.
                                            @Override
                                            public void mouseExited(final MouseEvent e) {
                                                if (!redArrowForwards.isSelected()) {
                                                    notification.setVisible(false);
                                                }
                                            }
                                        });

                                        // Creates a mouseListener on the redArrowBackwards at the Passenger Corridor.
                                        redArrowBackwards.addMouseListener(new MouseAdapter() {
                                            // If mouseEntered, display the Passenger Quarters location.
                                            @Override
                                            public void mouseEntered(final MouseEvent e) {
                                                if (currentLocation.getLocation().equals("Passenger Corridor") && gameState == 1) {
                                                    notification.setText("TRAVEL TO PASSENGER QUARTERS");
                                                    notification.setForeground(Color.black);
                                                    notification.setVisible(true);
                                                }
                                            }

                                            // If mousePressed while object is selected, deny the player's move.
                                            @Override
                                            public void mousePressed(final MouseEvent e) {
                                                if (currentLocation.getLocation().equals("Passenger Corridor") && gameState == 2) {
                                                    notification.setText("UNSELECT THE OBJECT TO PROCEED");
                                                    notification.setForeground(Color.red);
                                                    notification.setVisible(true);

                                                    audio.playSound("resources/sounds/Selection is Denied.wav");
                                                }
                                            }

                                            // If mouseExited, set the game message to disappear.
                                            @Override
                                            public void mouseExited(final MouseEvent e) {
                                                if (!redArrowBackwards.isSelected()) {
                                                    notification.setVisible(false);
                                                }
                                            }
                                        });

                                        // Action listener for allowing the player to proceed backward.
                                        redArrowBackwards.addActionListener(changeLocationBackwards -> {
                                            redArrowForwards.setBounds(550, 700, 125, 125);
                                            redArrowBackwards.setVisible(false);

                                            // Checkboxes that must appear in Passenger Quarters.
                                            passengerQuartersComputerCB.setVisible(true);

                                            // Checkboxes that must disappear in Passenger Quarters.
                                            passengerCorridorKeypadCB.setVisible(false);

                                            // Resets the selected object that the player chose.
                                            passengerQuartersComputerCB.setSelected(false);
                                            notification.setText("");

                                            // Player's current location is now the Passenger Quarters.
                                            mainMenu.setIcon(new ImageIcon("resources/ui/locations/Passenger Quarters.jpg"));
                                            currentLocation.setLocation("Passenger Quarters");
                                        });
                                    }
                                });
                            default:
                                // Blank switch case to fulfill Sun Checks Checkstyle standards.
                        }
                    }
                };

                // Searches for the enter key input that is pressed by the player.
                getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKey, "ENTER");
                getRootPane().getActionMap().put("ENTER", enterAction);

                // Plays a sound effect when button is clicked.
                audio.playSound("resources/sounds/Click Choice on Main Menu.wav");
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
            if (loadGame.getSource() == loadBtn) {
                audio.playSound("resources/sounds/Click Choice on Main Menu.wav");
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
            if (optionMenu.getSource() == optionBtn) {
                audio.playSound("resources/sounds/Click Choice on Main Menu.wav");
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
            if (credits.getSource() == secretBtn) {
                audio.playSound("resources/sounds/Click Choice on Main Menu.wav");
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
        openObjBtn.setBounds(131, 925, 175, 130);

        // Plays a sound effect when button is clicked.
        openObjBtn.addActionListener(openAction -> {
            if (gameState == 1 && openAction.getSource() == openObjBtn) {
                notification.setText("SELECT AN OBJECT TO OPEN");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                audio.playSound("resources/sounds/Click Action Button.wav");
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
        closeObjBtn.setBounds(332, 925, 175, 130);

        // Plays a sound effect when button is clicked.
        closeObjBtn.addActionListener(closeAction -> {
            if (gameState == 1 && closeAction.getSource() == closeObjBtn) {
                notification.setText("SELECT AN OBJECT TO CLOSE");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                audio.playSound("resources/sounds/Click Action Button.wav");
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
        inspectObjBtn.setBounds(532, 924, 175, 130);

        // Plays a sound effect when button is clicked.
        inspectObjBtn.addActionListener(inspectAction -> {
            if (gameState == 1 && inspectAction.getSource() == inspectObjBtn) {
                notification.setText("SELECT AN OBJECT TO INSPECT");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                audio.playSound("resources/sounds/Click Action Button.wav");
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
        useObjBtn.setBounds(730, 924, 175, 130);


        // Plays a sound effect when button is clicked.
        useObjBtn.addActionListener(useAction -> {
            if (gameState == 1 && useAction.getSource() == useObjBtn) {
                notification.setText("SELECT AN OBJECT TO USE");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                audio.playSound("resources/sounds/Click Action Button.wav");
            }

            // Counts when the use button is clicked when an object is selected.
            if (gameState == 2 && useObjBtn == useAction.getSource()) {
                clickCount++;
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
        takeItemBtn.setBounds(927, 923, 175, 130);

        // Plays a sound effect when button is clicked.
        takeItemBtn.addActionListener(takeAction -> {
            if (gameState == 1 && takeAction.getSource() == takeItemBtn) {
                notification.setText("SELECT AN OBJECT TO TAKE");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                audio.playSound("resources/sounds/Click Action Button.wav");
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
        beginDialogueBtn.setBounds(1124, 923, 175, 130);

        // Plays a sound effect when button is clicked.
        beginDialogueBtn.addActionListener(dialogueAction -> {
            if (gameState == 1 && dialogueAction.getSource() == beginDialogueBtn) {
                notification.setText("SELECT AN OBJECT TO BEGIN DIALOGUE");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                audio.playSound("resources/sounds/Click Action Button.wav");
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
        exitBtn.setBounds(28, 950, 80, 80);

        // The functionality of the exit button, brings up pause menu.
        exitBtn.addActionListener(exitMenuDisplay -> {
            // Pause menu will appear.
            confirmBtn.setVisible(true);
            cancelBtn.setVisible(true);
            pauseMessage.setVisible(true);

            // Action buttons will disappear.
            exitBtn.setVisible(false);
            openObjBtn.setVisible(false);
            closeObjBtn.setVisible(false);
            inspectObjBtn.setVisible(false);
            useObjBtn.setVisible(false);
            takeItemBtn.setVisible(false);
            beginDialogueBtn.setVisible(false);

            // Traversal arrows will disappear.
            redArrowForwards.setVisible(false);
            redArrowBackwards.setVisible(false);

            // Selectable objects will disappear.
            passengerQuartersComputerCB.setVisible(false);
            passengerCorridorKeypadCB.setVisible(false);

            // Inventory will deactivate.
            usableItem.setEnabled(false);

            // Pauses the main menu music.
            clipTimeMainMenu = audioClipMusicMainMenu.getMicrosecondPosition();
            audioClipMusicMainMenu.stop();

            // Pauses the main game music.
            clipTimeMainGame = audioClipMusicMainGame.getMicrosecondPosition();
            audioClipMusicMainGame.stop();

            // Plays a sound effect when button is clicked.
            audio.playSound("resources/sounds/Pause Menu.wav");
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
        confirmBtn.setFont(new Font("Dialog", Font.BOLD, 20));

        // Adds the confirm button.
        gameButtonsPane.add(confirmBtn);
        confirmBtn.setBounds(1400, 925, 175, 100);

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
        cancelBtn.setFont(new Font("Dialog", Font.BOLD, 20));

        // Adds the cancel button.
        gameButtonsPane.add(cancelBtn);
        cancelBtn.setBounds(1630, 925, 175, 100);

        // The functionality of the cancel button, resumes game.
        cancelBtn.addActionListener(resumeGame -> {
            confirmBtn.setVisible(false);
            cancelBtn.setVisible(false);
            pauseMessage.setVisible(false);

            // If on the main menu, the main menu song will resume.
            if (gameState == 0) {
                // Resumes the main menu music.
                audioClipMusicMainMenu.setMicrosecondPosition(clipTimeMainMenu);
                audioClipMusicMainMenu.start();
            }

            // If in the main game, the main game song will resume.
            if (gameState > 0) {
                // Resumes the main game music.
                audioClipMusicMainGame.setMicrosecondPosition(clipTimeMainGame);
                audioClipMusicMainGame.start();
            }

            // Main menu buttons will reappear.
            if (gameState == 0) {
                startBtn.setVisible(true);
                loadBtn.setVisible(true);
                optionBtn.setVisible(true);
                secretBtn.setVisible(true);
            }

            // Action buttons, traversal arrows and inventory will reactivate.
            if (gameState == 1 || gameState == 2) {
                exitBtn.setVisible(true);
                openObjBtn.setVisible(true);
                closeObjBtn.setVisible(true);
                inspectObjBtn.setVisible(true);
                useObjBtn.setVisible(true);
                takeItemBtn.setVisible(true);
                beginDialogueBtn.setVisible(true);

                redArrowForwards.setVisible(true);
                redArrowBackwards.setVisible(true);

                // If the use button was last used before pausing, keep inventory active.
                if (notification.getText().equals("WHAT ITEM WOULD YOU LIKE TO USE?")) {
                    usableItem.setEnabled(true);
                }

                // If the Passenger Quarters Computer was last selected, keep selected.
                if (currentLocation.getLocation().equals("Passenger Quarters")) {
                    passengerQuartersComputerCB.setVisible(true);
                }

                // If the Passenger Corridor Keypad was last selected, keep selected.
                if (currentLocation.getLocation().equals("Passenger Corridor")) {
                    passengerCorridorKeypadCB.setVisible(true);
                }
            }
        });

        // ---------- Keyboard Interactions ------------ //

        // Displays the exit game button options.
        KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(final ActionEvent event) {
                // Pause menu will appear.
                confirmBtn.setVisible(true);
                cancelBtn.setVisible(true);
                pauseMessage.setVisible(true);

                // Pauses the main menu music.
                clipTimeMainMenu = audioClipMusicMainMenu.getMicrosecondPosition();
                audioClipMusicMainMenu.stop();

                // Pauses the main game music.
                clipTimeMainGame = audioClipMusicMainGame.getMicrosecondPosition();
                audioClipMusicMainGame.stop();

                // Main menu buttons will disappear.
                if (gameState == 0) {
                    startBtn.setVisible(false);
                    loadBtn.setVisible(false);
                    optionBtn.setVisible(false);
                    secretBtn.setVisible(false);
                }

                // Main game features will disappear.
                if (gameState == 1 || gameState == 2) {
                    // Action buttons will disappear.
                    exitBtn.setVisible(false);
                    openObjBtn.setVisible(false);
                    closeObjBtn.setVisible(false);
                    inspectObjBtn.setVisible(false);
                    useObjBtn.setVisible(false);
                    takeItemBtn.setVisible(false);
                    beginDialogueBtn.setVisible(false);

                    // Traversal arrows will disappear.
                    redArrowForwards.setVisible(false);
                    redArrowBackwards.setVisible(false);

                    // Selectable objects will disappear.
                    passengerQuartersComputerCB.setVisible(false);
                    passengerCorridorKeypadCB.setVisible(false);

                    // Inventory will deactivate.
                    usableItem.setEnabled(false);
                }

                // Plays a sound effect when button is clicked.
                audio.playSound("resources/sounds/Pause Menu.wav");
            }
        };

        // Searches for the escape key input that is pressed by the player.
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKey, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);

        // ---------- Passenger Quarters Computer CheckBox ------------ //

        // Creates and aligns the Passenger Quarters computer checkbox.
        passengerQuartersComputerCB.setOpaque(true);
        passengerQuartersComputerCB.setContentAreaFilled(false);
        passengerQuartersComputerCB.setBorderPainted(false);

        // Adds the Passenger Quarters computer checkbox.
        gameButtonsPane.add(passengerQuartersComputerCB);

        // ---------- Passenger Corridor Keypad CheckBox ------------ //

        // Creates and aligns the Passenger Quarters computer checkbox.
        passengerCorridorKeypadCB.setOpaque(true);
        passengerCorridorKeypadCB.setContentAreaFilled(false);
        passengerCorridorKeypadCB.setBorderPainted(false);

        // Adds the Passenger Quarters computer checkbox.
        gameButtonsPane.add(passengerCorridorKeypadCB);

        // ---------- Action Button Functions for Interactive Objects ------------ //

        // MouseListener interactions with the Passenger Quarters Computer.
        passengerQuartersComputerCB.addMouseListener(new MouseAdapter() {
            // If mouseEntered, display the selection message.
            @Override
            public void mouseEntered(final MouseEvent e) {
                if (!passengerQuartersComputerCB.isSelected()) {
                    notification.setText("DO YOU WANT TO SELECT THE COMPUTER?");
                    notification.setForeground(Color.black);
                    notification.setVisible(true);
                }
            }

            // If mouseExited, set the game message to disappear.
            @Override
            public void mouseExited(final MouseEvent e) {
                if (!passengerQuartersComputerCB.isSelected()) {
                    notification.setVisible(false);
                }
            }

            // If mousePressed, Selection Mode is activated.
            @Override
            public void mousePressed(final MouseEvent e) {
                // Selection Mode is activated.
                gameState = 2;

                // Selection labels appear.
                selection.setText("COMPUTER");
                selection.setOpaque(true);
                selection.setVisible(true);

                // Selected Computer game message appears.
                notification.setText("THE COMPUTER IS SELECTED");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                // Deactivates traversal between rooms.
                redArrowForwards.setEnabled(false);
            }
        });

        // Action Button interactions with the Passenger Quarters Computer.
        passengerQuartersComputerCB.addActionListener(message -> {
            if (passengerQuartersComputerCB.isSelected() && gameState == 2 && currentLocation.getLocation().equals("Passenger Quarters")) {

                // Open action button when Passenger Quarters Computer is selected.
                openObjBtn.addActionListener(openComputer -> {
                    // Inventory deactivates.
                    usableItem.setEnabled(false);

                    // Prevents the player from opening the computer.
                    if (passengerQuartersComputerCB.isSelected() && openObjBtn == openComputer.getSource()) {
                        notification.setText("THE COMPUTER CANNOT BE OPENED");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });

                // Close action button when Passenger Quarters Computer is selected.
                closeObjBtn.addActionListener(closeComputer -> {
                    // Inventory deactivates.
                    usableItem.setEnabled(false);

                    // Prevents the player from closing the computer.
                    if (passengerQuartersComputerCB.isSelected() && closeObjBtn == closeComputer.getSource()) {
                        notification.setText("THE COMPUTER CANNOT BE CLOSED");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });

                // Inspect action button when Passenger Quarters Computer is selected.
                inspectObjBtn.addActionListener(inspectComputer -> {
                    // Inventory deactivates.
                    usableItem.setEnabled(false);

                    // Displays the main character's current thoughts initially.
                    if (passengerQuartersComputerCB.isSelected() && inspectObjBtn == inspectComputer.getSource()) {
                        notification.setText("PLAYER: THE COMPUTER SYSTEM HAS IMPORTANT INFORMATION ON IT");
                        notification.setForeground(Color.black);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Click Action Button.wav");
                    }

                    // Displays the main character's current thoughts after acquiring the keycard.
                    if (inventory.size() >= 1) {
                        if (passengerQuartersComputerCB.isSelected() && inspectObjBtn == inspectComputer.getSource() && inventory.get(0).equals("ACCESS CODE")) {
                            notification.setText("PLAYER: I ALREADY HAVE WHAT I NEED");
                            notification.setForeground(Color.black);
                            notification.setVisible(true);

                            audio.playSound("resources/sounds/Click Action Button.wav");
                        }
                    }

                    // Displays the main character's current thoughts after using the keycard.
                    if (useHistory.size() >= 1) {
                        if (passengerQuartersComputerCB.isSelected() && inspectObjBtn == inspectComputer.getSource()) {
                            notification.setText("PLAYER: THERE IS NOTHING ELSE HERE FOR ME");
                            notification.setForeground(Color.black);
                            notification.setVisible(true);

                            audio.playSound("resources/sounds/Click Action Button.wav");
                        }
                    }
                });

                // Use action button when Passenger Quarters Computer is selected.
                useObjBtn.addActionListener(useComputer -> {
                    if (clickCount == 0) {
                        // Using the computer for the first time grants the player a keycard.
                        if (passengerQuartersComputerCB.isSelected() && useObjBtn == useComputer.getSource() && inventory.size() == 0 && useHistory.size() == 0) {
                            notification.setText("YOU USE THE COMPUTER AND FIND AN ACCESS CODE FOR THE HALLWAY");
                            notification.setForeground(new Color(0, 153, 0));
                            notification.setVisible(true);

                            // Keycard is added to the inventory.
                            inventory.add(0, "ACCESS CODE");

                            // Inventory button is added to the main game.
                            usableItem.setBounds(1400, 100, 410, 40);
                            usableItem.setText(inventory.get(0));
                            usableItem.setVisible(true);
                            usableItem.setEnabled(false);

                            audio.playSound("resources/sounds/Selection is Approved.wav");
                        }
                    }

                    if (clickCount >= 1) {
                        if (inventory.size() == 0) {
                            // Using the computer with an empty inventory will be denied.
                            if (passengerQuartersComputerCB.isSelected() && useObjBtn == useComputer.getSource()) {
                                notification.setText("YOUR INVENTORY IS EMPTY");
                                notification.setForeground(Color.red);
                                notification.setVisible(true);

                                audio.playSound("resources/sounds/Selection is Denied.wav");
                            }
                        }

                        if (inventory.size() >= 1) {
                            // Using the computer with a usable inventory.
                            if (passengerQuartersComputerCB.isSelected() && useObjBtn == useComputer.getSource() && inventory.get(0).equals("ACCESS CODE")) {
                                notification.setText("WHAT ITEM WOULD YOU LIKE TO USE?");
                                notification.setForeground(Color.black);
                                notification.setVisible(true);

                                // Inventory activates.
                                usableItem.setEnabled(true);

                                audio.playSound("resources/sounds/Click Action Button.wav");

                                usableItem.addActionListener(denyKeycard -> {
                                    // Using the keycard on the computer.
                                    if (currentLocation.getLocation().equals("Passenger Quarters") && usableItem.getText().equals("ACCESS CODE")) {
                                        // Main Game Mode is activated.
                                        gameState = 1;

                                        // Player's action is denied.
                                        notification.setText("THE KEYCARD DOES NOTHING");
                                        notification.setForeground(Color.red);
                                        notification.setVisible(true);

                                        // Traversal arrows are reactivated.
                                        redArrowForwards.setEnabled(true);
                                        redArrowBackwards.setEnabled(true);

                                        // Selection Mode is deactivated.
                                        passengerQuartersComputerCB.setSelected(false);
                                        selection.setOpaque(false);
                                        selection.setVisible(false);
                                        usableItem.setEnabled(false);

                                        audio.playSound("resources/sounds/Selection is Denied.wav");
                                    }
                                });
                            }
                        }
                    }
                });

                // Take action button when Passenger Quarters Computer is selected.
                takeItemBtn.addActionListener(takeComputer -> {
                    // Inventory deactivates.
                    usableItem.setEnabled(false);

                    // Prevents the player from taking the computer.
                    if (passengerQuartersComputerCB.isSelected() && takeItemBtn == takeComputer.getSource()) {
                        notification.setText("THE COMPUTER IS CONNECTED TO THE DESK AND CANNOT BE PICKED UP");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });

                // Talk action button when Passenger Quarters Computer is selected.
                beginDialogueBtn.addActionListener(talkToComputer -> {
                    // Inventory deactivates.
                    usableItem.setEnabled(false);

                    // Notifies the player that talking to the computer is pointless.
                    if (passengerQuartersComputerCB.isSelected() && beginDialogueBtn == talkToComputer.getSource()) {
                        notification.setText("YOU MUST BE BORED IF YOU ARE TRYING TO TALK TO A COMPUTER");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });
            } else {
                // Main Game Mode is activated.
                gameState = 1;

                // Selection Mode is deactivated.
                notification.setVisible(false);
                selection.setOpaque(false);
                selection.setVisible(false);
                usableItem.setEnabled(false);

                // Traversal arrows are activated.
                redArrowForwards.setEnabled(true);
            }
        });

        // ---------- Actions for Passenger Corridor Keypad ------------ //

        // MouseListener interactions with the Passenger Corridor Keypad.
        passengerCorridorKeypadCB.addMouseListener(new MouseAdapter() {
            // If mouseEntered, display the selection message.
            @Override
            public void mouseEntered(final MouseEvent e) {
                if (!passengerCorridorKeypadCB.isSelected()) {
                    notification.setText("DO YOU WANT TO SELECT THE KEYPAD?");
                    notification.setForeground(Color.black);
                    notification.setVisible(true);
                }
            }

            // If mouseExited, set the game message to disappear.
            @Override
            public void mouseExited(final MouseEvent e) {
                if (!passengerCorridorKeypadCB.isSelected()) {
                    notification.setVisible(false);
                }
            }

            // If mousePressed, Selection Mode is Activated.
            @Override
            public void mousePressed(final MouseEvent e) {
                // Selection Mode is activated.
                gameState = 2;

                // Selection labels appear.
                selection.setText("KEYPAD");
                selection.setOpaque(true);
                selection.setVisible(true);

                // Selected Keypad game message appears.
                notification.setText("THE KEYPAD IS SELECTED");
                notification.setForeground(Color.black);
                notification.setVisible(true);

                // Deactivates traversal between rooms.
                redArrowForwards.setEnabled(false);
                redArrowBackwards.setEnabled(false);
            }
        });

        // Action Button interactions with the Passenger Corridor Keypad.
        passengerCorridorKeypadCB.addActionListener(message -> {
            if (passengerCorridorKeypadCB.isSelected() && gameState == 2 && currentLocation.getLocation().equals("Passenger Corridor")) {

                // Open action button when Passenger Corridor Keypad is selected.
                openObjBtn.addActionListener(openKeypad -> {
                    // Prevents the player from opening the keypad.
                    if (passengerCorridorKeypadCB.isSelected() && openObjBtn == openKeypad.getSource()) {
                        notification.setText("THE KEYPAD CANNOT BE OPENED");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });

                // Close action button when Passenger Corridor Keypad is selected.
                closeObjBtn.addActionListener(closeKeypad -> {
                    // Prevents the player from closing the keypad.
                    if (passengerCorridorKeypadCB.isSelected() && closeObjBtn == closeKeypad.getSource()) {
                        notification.setText("THE KEYPAD CANNOT BE CLOSED");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });

                // Inspect action button when Passenger Corridor Keypad is selected.
                inspectObjBtn.addActionListener(inspectKeypad -> {
                    // Displays the main character's current thoughts initially.
                    if (passengerCorridorKeypadCB.isSelected() && inspectObjBtn == inspectKeypad.getSource() && inventory.size() == 0 && useHistory.size() == 0) {
                        notification.setText("PLAYER: I NEED TO FIND THE ACCESS CODE TO OPEN THIS DOOR");
                        notification.setForeground(Color.black);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Click Action Button.wav");
                    }

                    // Displays the main character's current thoughts while the keycard is in the inventory.
                    if (inventory.size() >= 1) {
                        if (passengerCorridorKeypadCB.isSelected() && inspectObjBtn == inspectKeypad.getSource() && inventory.get(0).equals("ACCESS CODE")) {
                            notification.setText("PLAYER: MAYBE I SHOULD USE THE ACCESS CODE?");
                            notification.setForeground(Color.black);
                            notification.setVisible(true);

                            audio.playSound("resources/sounds/Click Action Button.wav");
                        }
                    }

                    // Displays the main character's current thoughts after using the keycard on the door.
                    if (passengerCorridorKeypadCB.isSelected() && inspectObjBtn == inspectKeypad.getSource() && inventory.size() == 0 && useHistory.size() >= 1) {
                        notification.setText("PLAYER: I'VE ALREADY UNLOCKED THE DOOR");
                        notification.setForeground(Color.black);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Click Action Button.wav");
                    }
                });

                // Use action button when Passenger Corridor Keypad is selected.
                useObjBtn.addActionListener(useKeypad -> {
                    if (inventory.size() == 0) {
                        // Using the keypad with an empty inventory will be denied.
                        if (passengerCorridorKeypadCB.isSelected() && useObjBtn == useKeypad.getSource()) {
                            notification.setText("YOUR INVENTORY IS EMPTY");
                            notification.setForeground(Color.red);
                            notification.setVisible(true);

                            audio.playSound("resources/sounds/Selection is Denied.wav");
                        }
                    }

                    if (inventory.size() >= 1) {
                        // Using the computer with a usable inventory.
                        if (passengerCorridorKeypadCB.isSelected() && useObjBtn == useKeypad.getSource() && inventory.get(0).equals("ACCESS CODE")) {
                            notification.setText("WHAT ITEM WOULD YOU LIKE TO USE?");
                            notification.setForeground(Color.black);
                            notification.setVisible(true);

                            // Inventory activates.
                            usableItem.setEnabled(true);

                            audio.playSound("resources/sounds/Click Action Button.wav");

                            usableItem.addActionListener(unlockDoor -> {
                                // Using the keycard on the computer.
                                if (currentLocation.getLocation().equals("Passenger Corridor") && usableItem.getText().equals("ACCESS CODE") && useHistory.size() == 0) {
                                    // Keycard is removed from the inventory.
                                    inventory.remove(0);

                                    // Main Game Mode is activated.
                                    gameState = 1;

                                    // Approves the player's actions.
                                    notification.setText("THE DOOR IS UNLOCKED");
                                    notification.setForeground(new Color(0, 153, 0));
                                    notification.setVisible(true);

                                    // Unlocking the door with the keycard is added to the record.
                                    useHistory.add("THE DOOR IS UNLOCKED");

                                    // Traversals arrows are reactivated.
                                    redArrowForwards.setEnabled(true);
                                    redArrowBackwards.setEnabled(true);

                                    // Selection Mode is deactivated.
                                    passengerCorridorKeypadCB.setSelected(false);
                                    selection.setOpaque(false);
                                    selection.setVisible(false);
                                    usableItem.setVisible(false);

                                    audio.playSound("resources/sounds/Selection is Approved.wav");
                                }
                            });
                        }
                    }
                });

                // Take action button when Passenger Corridor Keypad is selected.
                takeItemBtn.addActionListener(takeKeypad -> {
                    // Prevents the player from taking the keypad.
                    if (passengerCorridorKeypadCB.isSelected() && takeItemBtn == takeKeypad.getSource()) {
                        notification.setText("THE KEYPAD IS ATTACHED TO THE WALL AND CANNOT BE PICKED UP");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });

                // Talk action button when Passenger Corridor Keypad is selected.
                beginDialogueBtn.addActionListener(talkToKeypad -> {
                    // Notifies the player that talking to the keypad is pointless.
                    if (passengerCorridorKeypadCB.isSelected() && beginDialogueBtn == talkToKeypad.getSource()) {
                        notification.setText("SERIOUSLY, YOU ARE TRYING TO TALK TO A KEYPAD?");
                        notification.setForeground(Color.red);
                        notification.setVisible(true);

                        audio.playSound("resources/sounds/Selection is Denied.wav");
                    }
                });
            } else {
                // Main Game Mode is activated.
                clickCount = 0;
                gameState = 1;

                // Selection Mode is deactivated.
                notification.setVisible(false);
                selection.setOpaque(false);
                selection.setVisible(false);
                usableItem.setEnabled(false);

                // Traversal arrows are reactivated.
                redArrowForwards.setEnabled(true);
                redArrowBackwards.setEnabled(true);
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
    }
}
