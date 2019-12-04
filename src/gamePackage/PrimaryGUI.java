package gamePackage;

import logicPackage.Location;
import logicPackage.Soundplayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/***********************************************************************
 * The PrimaryGUI class creates the components and general functions
 * that can be displayed when the GUI is executed.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (10 / 25 / 19)
 ***********************************************************************/

class PrimaryGUI extends JFrame {
    /* A dynamic value to help specify gameState conditions. */
    private int gameState = 0;

    /* JLabel for displaying game messages for the player. */
    private JLabel notification = new JLabel();
    private JLabel selection = new JLabel();

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

    /* Buttons for changing the location of the player */
    private JButton redArrowForwards = new JButton("");
    private JButton redArrowBackwards = new JButton("");

    /* Checkbox for Passenger Quarters Computer */
    private JCheckBox passengerQuartersComputerCB = new JCheckBox();

    private static final long serialVersionUID = 1L;

    /******************************************************************
     * Main method that runs the primaryGUI class.
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     ******************************************************************/
    PrimaryGUI() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        super("Soul Survivor");

        // An ArrayList for storing the items in the inventory.
        ArrayList<String> inventory = new ArrayList<String>(10);

        // location object helps with bundling buttons with current room.
        Location currentLocation = new Location();
        currentLocation.setLocation("Main Menu");

        // soundPlayer object allows for sound effects and music to be played.
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
        mainMenu.setIcon(new ImageIcon("resources/ui/Main Menu.jpg"));
        backgroundPane.add(mainMenu);

        // ---------- Passenger Quarters Computer CheckBox ------------ //

        // Creates and aligns the Passenger Quarters computer checkbox.
        passengerQuartersComputerCB.setOpaque(true);
        passengerQuartersComputerCB.setContentAreaFilled(false);
        passengerQuartersComputerCB.setBorderPainted(false);

        // Adds the Passenger Quarters computer checkbox.
        gameButtonsPane.add(passengerQuartersComputerCB);

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
            if (beginGame.getSource() == startBtn) {
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

                redArrowForwards.setVisible(true);

                // Changes the background image to the game screen.
                mainMenu.setIcon(new ImageIcon("resources/ui/introduction/Intro Sequence 1.jpg"));

                // Ends the main menu music.
                audioClipMusicMainMenu.stop();

                // Assigns an audio file to an AudioInputStream.
                AudioInputStream musicMainGame = null;
                try {
                    musicMainGame = AudioSystem.getAudioInputStream(new File("resources/music/Soul Survivor.wav"));
                } catch (UnsupportedAudioFileException | IOException e) {
                    System.out.println("Error with AudioInputStream file.");
                    e.printStackTrace();
                }

                // Gets information about the music track that has been assigned.
                AudioFormat formatMainGame = musicMainGame.getFormat();
                DataLine.Info infoMainGame = new DataLine.Info(Clip.class, formatMainGame);
                Clip audioClipMainGame = null;
                try {
                    audioClipMainGame = (Clip) AudioSystem.getLine(infoMainGame);
                } catch (LineUnavailableException e) {
                    System.out.println("Error with getting DataLine information of file.");
                    e.printStackTrace();
                }

                // Opens the updated music track for the game screen.
                try {
                    audioClipMainGame.open(musicMainGame);
                } catch (LineUnavailableException | IOException e) {
                    System.out.println("Error with opening the file.");
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
                        if (!confirmBtn.isVisible() && !cancelBtn.isVisible()) {
                            counter++;
                            mainMenu.setIcon(new ImageIcon("resources/ui/introduction/Intro Sequence " + counter + ".jpg"));
                        }

                        // Stops incrementing and begins actual game with first location.
                        if (counter > 5) {
                            mainMenu.setIcon(new ImageIcon("resources/ui/locations/Passenger Quarters.jpg"));
                            currentLocation.setLocation("Passenger Quarters");
                        }

                        // ---------- Locations ------------ //

                        // Traveling from Passenger Quarters to Passenger Corridor.
                        if (currentLocation.getLocation().equals("Passenger Quarters")) {
                            redArrowForwards.setBounds(550, 700, 125, 125);
                            passengerQuartersComputerCB.setBounds(1000, 525, 250, 125);

                            redArrowForwards.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    if (currentLocation.getLocation().equals("Passenger Quarters")) {
                                        notification.setText("TRAVEL TO PASSENGER CORRIDOR");
                                        notification.setForeground(Color.black);
                                        notification.setVisible(true);
                                    }
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    if (!redArrowForwards.isSelected()) {
                                        notification.setVisible(false);
                                    }
                                }
                            });

                            passengerQuartersComputerCB.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    if (!passengerQuartersComputerCB.isSelected()) {
                                        notification.setText("DO YOU WANT TO SELECT THE COMPUTER?");
                                        notification.setForeground(Color.black);
                                        notification.setVisible(true);
                                    }
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    if (!passengerQuartersComputerCB.isSelected()) {
                                        notification.setVisible(false);
                                    }
                                }
                            });

                            passengerQuartersComputerCB.addActionListener(message -> {
                                if (passengerQuartersComputerCB.isSelected()) {
                                    gameState = 2;

                                    selection.setText("COMPUTER");
                                    selection.setOpaque(true);
                                    selection.setVisible(true);

                                    notification.setText("THE COMPUTER IS SELECTED");
                                    notification.setForeground(Color.black);
                                    notification.setVisible(true);

                                    redArrowForwards.setVisible(false);

                                    openObjBtn.addActionListener(openComputer -> {
                                        if(passengerQuartersComputerCB.isSelected() && openObjBtn == openComputer.getSource()) {
                                            notification.setText("THE COMPUTER CANNOT BE OPENED");
                                            notification.setForeground(Color.red);
                                            notification.setVisible(true);

                                            audio.playSound("resources/sounds/Selection is Denied.wav");
                                        }
                                    });

                                    closeObjBtn.addActionListener(openComputer -> {
                                        if(passengerQuartersComputerCB.isSelected() && closeObjBtn == openComputer.getSource()) {
                                            notification.setText("THE COMPUTER CANNOT BE CLOSED");
                                            notification.setForeground(Color.red);
                                            notification.setVisible(true);

                                            audio.playSound("resources/sounds/Selection is Denied.wav");
                                        }
                                    });

                                    inspectObjBtn.addActionListener(openComputer -> {
                                        if(passengerQuartersComputerCB.isSelected() && inspectObjBtn == openComputer.getSource()) {
                                            notification.setText("PLAYER: THE GOVERNMENT WAS NICE ENOUGH TO PROVIDE EACH PASSENGER THEIR OWN COMPUTER");
                                            notification.setForeground(Color.black);
                                            notification.setVisible(true);
                                        }
                                    });

                                    useObjBtn.addActionListener(openComputer -> {
                                        int clickCount = 0;
                                        if(passengerQuartersComputerCB.isSelected() && useObjBtn == openComputer.getSource()) {
                                            notification.setText("YOU USE THE COMPUTER AND FIND AN ACCESS CODE FOR THE HALLWAY");
                                            notification.setForeground(Color.green);
                                            notification.setVisible(true);

                                            inventory.add("Access Code");
                                            clickCount++;

                                            audio.playSound("resources/sounds/Selection is Approved.wav");
                                        }

//                                        if(clickCount >= 1 && inventory.get(0).equals("Access Code")) {
//                                            notification.setText("YOU ALREADY HAVE WHAT YOU NEEDED");
//                                            notification.setForeground(Color.BLACK);
//                                            notification.setVisible(true);
//                                        }
                                    });

                                    takeItemBtn.addActionListener(openComputer -> {
                                        if(passengerQuartersComputerCB.isSelected() && takeItemBtn == openComputer.getSource()) {
                                            notification.setText("THE COMPUTER IS CONNECTED TO THE DESK AND CANNOT BE PICKED UP");
                                            notification.setForeground(Color.red);
                                            notification.setVisible(true);

                                            audio.playSound("resources/sounds/Selection is Denied.wav");
                                        }
                                    });

                                    beginDialogueBtn.addActionListener(openComputer -> {
                                        if(passengerQuartersComputerCB.isSelected() && beginDialogueBtn == openComputer.getSource()) {
                                            notification.setText("YOU MUST BE BORED IF YOU ARE TRYING TO TALK TO A COMPUTER");
                                            notification.setForeground(Color.red);
                                            notification.setVisible(true);

                                            audio.playSound("resources/sounds/Selection is Denied.wav");
                                        }
                                    });
                                } else {
                                    notification.setVisible(false);
                                    selection.setOpaque(false);
                                    selection.setVisible(false);

                                    redArrowForwards.setVisible(true);
                                }
                            });

                            // Action listener for allowing the player to proceed forward.
                            redArrowForwards.addActionListener(changeLocationForwards -> {
                                redArrowForwards.setBounds(600, 520, 125, 125);
                                redArrowBackwards.setBounds(600, 700, 125, 125);
                                redArrowBackwards.setVisible(true);

                                // Resets the selected object that the player chose.
                                passengerQuartersComputerCB.setSelected(false);
                                notification.setText("");

                                notification.setText("THE DOOR IS LOCKED");
                                notification.setForeground(Color.red);

                                if(currentLocation.getLocation().equals("Passenger Corridor")) {
                                    audio.playSound("resources/sounds/Selection is Denied.wav");
                                }

                                // Player's current location is now the Passenger Corridor.
                                mainMenu.setIcon(new ImageIcon("resources/ui/locations/Passenger Corridor.jpg"));
                                currentLocation.setLocation("Passenger Corridor");

                                // Traveling from Passenger Corridor to Passenger Quarters.
                                if (currentLocation.getLocation().equals("Passenger Corridor")) {
                                    redArrowForwards.setBounds(600, 520, 125, 125);
                                    redArrowBackwards.setBounds(600, 700, 125, 125);

                                    // Checkboxes that must disappear in Passenger Corridor.
                                    passengerQuartersComputerCB.setVisible(false);

                                    redArrowForwards.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseEntered(MouseEvent e) {
                                            if (currentLocation.getLocation().equals("Passenger Corridor")) {
                                                notification.setText("TRAVEL TO MAIN LOBBY");
                                                notification.setForeground(Color.black);
                                                notification.setVisible(true);
                                            }
                                        }

                                        @Override
                                        public void mouseExited(MouseEvent e) {
                                            if (!redArrowForwards.isSelected()) {
                                                notification.setVisible(false);
                                            }
                                        }
                                    });

                                    redArrowBackwards.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseEntered(MouseEvent e) {
                                            if (currentLocation.getLocation().equals("Passenger Corridor")) {
                                                notification.setText("TRAVEL TO PASSENGER QUARTERS");
                                                notification.setForeground(Color.black);
                                                notification.setVisible(true);
                                            }
                                        }

                                        @Override
                                        public void mouseExited(MouseEvent e) {
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

                                        // Resets the selected object that the player chose.
                                        passengerQuartersComputerCB.setSelected(false);
                                        notification.setText("");

                                        // Player's current location is now the Passenger Quarters.
                                        mainMenu.setIcon(new ImageIcon("resources/ui/locations/Passenger Quarters.jpg"));
                                        currentLocation.setLocation("Passenger Quarters");
                                    });
                                }
                            });
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
            if (!(gameState == 2) && openAction.getSource() == openObjBtn) {
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
            if (!(gameState == 2) && closeAction.getSource() == closeObjBtn) {
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
            if (!(gameState == 2) && inspectAction.getSource() == inspectObjBtn) {
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
            if (!(gameState == 2) && useAction.getSource() == useObjBtn) {
                audio.playSound("resources/sounds/Click Action Button.wav");
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
            if (!(gameState == 2) && takeAction.getSource() == takeItemBtn) {
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
            if (!(gameState == 2) && dialogueAction.getSource() == beginDialogueBtn) {
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

            redArrowForwards.setVisible(false);
            redArrowBackwards.setVisible(false);

            passengerQuartersComputerCB.setVisible(false);

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
        confirmBtn.setFont(new Font("Dialog", Font.PLAIN, 20));

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
        cancelBtn.setFont(new Font("Dialog", Font.PLAIN, 20));

        // Adds the cancel button.
        gameButtonsPane.add(cancelBtn);
        cancelBtn.setBounds(1630, 925, 175, 100);

        // The functionality of the cancel button, resumes game.
        cancelBtn.addActionListener(resumeGame -> {
            confirmBtn.setVisible(false);
            cancelBtn.setVisible(false);
            pauseMessage.setVisible(false);

            // Performs the correct cancel button operations for main menu.
            if (gameState == 0) {
                startBtn.setVisible(true);
                loadBtn.setVisible(true);
                optionBtn.setVisible(true);
                secretBtn.setVisible(true);
            }

            // Performs the correct cancel button operations for game screen.
            if (gameState == 1) {
                exitBtn.setVisible(true);
                openObjBtn.setVisible(true);
                closeObjBtn.setVisible(true);
                inspectObjBtn.setVisible(true);
                useObjBtn.setVisible(true);
                takeItemBtn.setVisible(true);
                beginDialogueBtn.setVisible(true);

                redArrowForwards.setVisible(true);
                redArrowBackwards.setVisible(true);

                passengerQuartersComputerCB.setVisible(true);
            }
        });

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

                    redArrowForwards.setVisible(false);
                    redArrowBackwards.setVisible(false);

                    passengerQuartersComputerCB.setVisible(false);
                }

                // Plays a sound effect when button is clicked.
                audio.playSound("resources/sounds/Pause Menu.wav");
            }
        };

        // Searches for the escape key input that is pressed by the player.
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKey, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);

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