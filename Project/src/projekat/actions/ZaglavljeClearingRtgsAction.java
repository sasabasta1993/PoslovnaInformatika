package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.ZaglavljeClearingRtgsStandardForm;



public class ZaglavljeClearingRtgsAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  ZaglavljeClearingRtgsAction() {
		KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Zaglavlje Clearing/RTGS");
		putValue(NAME, "Zaglavlje Clearing/RTGS");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ZaglavljeClearingRtgsStandardForm form;
		try {
			form = new ZaglavljeClearingRtgsStandardForm(null, null);
			form.setVisible(true);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

}
