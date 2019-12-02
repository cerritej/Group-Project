package logicPackage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;


public class CheckboxAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;


	public CheckboxAction(String text) {
		super(text);
	}
	
	
	public void actionPerformed(ActionEvent event) {
		JCheckBox checkBox = (JCheckBox) event.getSource();
		if(checkBox.isSelected()) {
			System.out.println("Item is selected");
		}
		else {
			System.out.println("Item is unselected");
		}
	}

}
