package logicPackage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/***********************************************************************
 * The Soundplayer class allows all non-looping sound effects to be
 * played while the user interacts with the GUI.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (11 / 29 / 19)
 ***********************************************************************/
public class Soundplayer {
    /******************************************************************
     * The method for playing non-looping sound effects.
     * @param soundName The name of the sound file being passed.
     ******************************************************************/
    public void playSound(final String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound file.");
            ex.printStackTrace();
        }
    }
}
