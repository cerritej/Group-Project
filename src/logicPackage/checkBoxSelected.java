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
public class checkBoxSelected extends AbstractAction{
	
	private static final long serialVersionUID = 1L;
	public int status = 0;

	public checkBoxSelected(String text) {
		super(text);
	}
	
	public int getStatus() {
		return status; 
	}

	public void actionPerformed(ActionEvent event) {
		JCheckBox checkBox = (JCheckBox) event.getSource();
		if(checkBox.isSelected()) {
			status = 1;
		}

		else {
			status = 0;
		}
	}
}
