package logicPackage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/***********************************************************************
 * The soundPlayer class allows sound effects to be played while the
 * user interacts with the GUI.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (11 / 29 / 19)
 ***********************************************************************/
public class Soundplayer {
    public void playSound(String sound) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
