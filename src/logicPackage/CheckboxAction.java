package logicPackage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;


public class CheckboxAction extends AbstractAction{
	public CheckboxAction(String text) {
		super(text);
	}
	
	
	public void actionPerformed(ActionEvent event) {
		JCheckBox checkBox = (JCheckBox) event.getSource();
		if(checkBox.isSelected()) {
			System.out.println("Checked");
		}
		else {
			System.out.println("Unchecked");
		}
	}

}
