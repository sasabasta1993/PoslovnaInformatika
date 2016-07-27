package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.VrstaPlacanjaStandardForm;



public class VrstaPlacanjaAction extends AbstractAction {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VrstaPlacanjaAction() {
		KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Vrsta placanja");
		putValue(NAME, "Vrsta placanja");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		VrstaPlacanjaStandardForm form;
		try {
			form = new VrstaPlacanjaStandardForm(null, null);
			form.setVisible(true);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
