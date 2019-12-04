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
public class CheckboxSelected extends AbstractAction{
	/* A string to help identify what the interactive object is. */
	private String objectName;

	/* A dynamic value to help specify if the object is selected. */
	public int status = 0;

	private static final long serialVersionUID = 1L;

	public CheckboxSelected(String text) {
		super(text);
		objectName = text;
	}

	public int getStatus() {
		return status;
	}

	public String getName() {
		return objectName;
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