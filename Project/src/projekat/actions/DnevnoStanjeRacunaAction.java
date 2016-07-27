package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.DnevnoStanjeRacunaStandardForm;



public class DnevnoStanjeRacunaAction extends AbstractAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DnevnoStanjeRacunaAction() {
		KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Dnevno stanje");
		putValue(NAME, "Dnevno stanje");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		DnevnoStanjeRacunaStandardForm form;
		try {
			form = new DnevnoStanjeRacunaStandardForm(null, null);
			form.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
