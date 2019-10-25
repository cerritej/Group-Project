package pack1;
import javax.swing.*;
import java.awt.*;

public class primaryGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public primaryGUI() {
		super("Soul Survivor");

		JPanel panel = new JPanel() {
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};

		LayoutManager overlay = new OverlayLayout(panel);
		panel.setLayout(overlay);

		JButton startBtn = new JButton("");
		startBtn.setMaximumSize(new Dimension(25, 25));
		startBtn.setAlignmentX(CENTER_ALIGNMENT);
		startBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(startBtn);

		JButton loadBtn = new JButton("");
		loadBtn.setMaximumSize(new Dimension(50, 50));
		loadBtn.setAlignmentX(CENTER_ALIGNMENT);
		loadBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(loadBtn);

		JButton optnBtn = new JButton("");
		optnBtn.setMaximumSize(new Dimension(100, 100));
		optnBtn.setAlignmentX(CENTER_ALIGNMENT);
		optnBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(optnBtn);

		JButton secretBtn = new JButton("");
		secretBtn.setMaximumSize(new Dimension(200, 200));
		secretBtn.setAlignmentX(CENTER_ALIGNMENT);
		secretBtn.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(secretBtn);

		JLabel mainMenu = new JLabel("");
		mainMenu.setMaximumSize(new Dimension(1920, 1080));
		mainMenu.setAlignmentX(CENTER_ALIGNMENT);
		mainMenu.setAlignmentY(CENTER_ALIGNMENT);
		mainMenu.setIcon(new ImageIcon("resources\\ui\\Main Menu.jpg"));
		panel.add(mainMenu);

		add(panel, BorderLayout.CENTER);

		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
	}
}
