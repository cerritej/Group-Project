package pack1;
import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JButton startBtn;
	JButton loadBtn;
	JButton optnBtn;
	JButton secretBtn;
	GridBagConstraints gbc = new GridBagConstraints();

	public MainFrame() {
		
		super("Soul Survivor");
		
		setLayout(new GridBagLayout());
		
	
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
		
		startBtn = new JButton("");
		add(startBtn);
		
		loadBtn = new JButton("");
		add(loadBtn);
		
		optnBtn = new JButton("");
		add(optnBtn);
		
		optnBtn = new JButton("");
		add(secretBtn);
		
	}
}
