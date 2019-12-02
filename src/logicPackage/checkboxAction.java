package logicPackage;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;

/***********************************************************************
 * The checkboxAction class helps with tracking when a checkbox
 * on the location image is selected.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (11/29/19)
 ***********************************************************************/
public class checkboxAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;

	public checkboxAction(String text) {
		super(text);
	}

	public void actionPerformed(ActionEvent event) {
		JCheckBox checkBox = (JCheckBox) event.getSource();
		if(checkBox.isSelected()) {
			System.out.println("Item is selected.");
		}

		else {
			System.out.println("Item is unselected.");
		}
	}
}
