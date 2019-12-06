package gamePackage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import java.io.IOException;

/***********************************************************************
 * The Executable class contains the main method which allows the
 * application to be executed.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (10 / 25 / 19)
 ***********************************************************************/
public class Executable {
    /******************************************************************
     * Main method that runs the primaryGUI class.
     * @param args The arguments for the main method.
     ******************************************************************/
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new PrimaryGUI();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                System.out.println("Error with playing sound file.");
                e.printStackTrace();
            }
        });
    }
}
