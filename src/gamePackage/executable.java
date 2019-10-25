package gamePackage;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class executable {
	public static void main(String[]args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					new primaryGUI();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
