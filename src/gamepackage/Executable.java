package gamepackage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import java.io.IOException;

/***********************************************************************
 * The Executable class contains the main method which allows the
 * application to be executed.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson)
 * @version (10 / 25 / 19)
 ***********************************************************************/
public final class Executable {
    private Executable() {
        // Blank constructor to fulfill Sun Checks Checkstyle standards.
    }
    /******************************************************************
     * Main method that runs the primaryGUI class.
     * @param args The arguments for the main method.
     ******************************************************************/
     public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new PrimaryGUI();
            } catch (IOException e) {
                System.out.println("Error with playing sound file.");
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                System.out.println("Error with playing sound file.");
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                System.out.println("Error with playing sound file.");
                e.printStackTrace();
            }
        });
    }
}
