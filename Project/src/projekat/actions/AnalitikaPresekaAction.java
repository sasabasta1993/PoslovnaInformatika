package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.AnalitikaPresekaStandardForm;



public class AnalitikaPresekaAction  extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnalitikaPresekaAction() {
		//KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_, ActionEvent.CTRL_MASK);
		//putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Analitika preseka");
		putValue(NAME, "Analitika preseka");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		AnalitikaPresekaStandardForm form;
		try {
			form = new AnalitikaPresekaStandardForm(null, null);

			form.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
