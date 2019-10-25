package gamePackage;
import javax.swing.*;

public class executable {
	public static void main(String[]args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new primaryGUI();
			}
		});
	}
}
