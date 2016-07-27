package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.StavkaClearingRtgsStandardForm;



public class StavkaClearingRtgsAction extends AbstractAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  StavkaClearingRtgsAction() {
		KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Stavka Clearing/RTGS");
		putValue(NAME, "Stavka Clearing/RTGS");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		StavkaClearingRtgsStandardForm form;
		try {
			form = new StavkaClearingRtgsStandardForm(null, null);
			form.setVisible(true);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
