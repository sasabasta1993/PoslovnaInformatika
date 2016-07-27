package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.PresekStandardForm;



public class PresekAction extends AbstractAction {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PresekAction() {
		KeyStroke ctrlMKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlMKeyStroke);
		putValue(SHORT_DESCRIPTION, "Preseci izvoda");
		putValue(NAME, "Preseci izvoda");
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		PresekStandardForm form = null;
		try {
			form = new PresekStandardForm(null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}

}
