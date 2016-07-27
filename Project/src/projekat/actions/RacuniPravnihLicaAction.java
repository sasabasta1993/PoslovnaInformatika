package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.RacuniPravnihLicaStandardForm;



public class RacuniPravnihLicaAction   extends AbstractAction{
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RacuniPravnihLicaAction() {
		KeyStroke ctrlMKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK);
		putValue(ACCELERATOR_KEY,ctrlMKeyStroke);
		putValue(SHORT_DESCRIPTION, "Računi pravnih lica");
		putValue(NAME, "Računi pravnih lica");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		RacuniPravnihLicaStandardForm form = null;
		try {
			form = new RacuniPravnihLicaStandardForm(null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}	

}
