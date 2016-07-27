package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.AnalitikaIzvodaStandardForm;



public class AnalitikaIzvodaAction extends AbstractAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnalitikaIzvodaAction() {
		KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Analitika izvoda");
		putValue(NAME, "Analitika izvoda");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		AnalitikaIzvodaStandardForm form;
		try {
			form = new AnalitikaIzvodaStandardForm(null, null);

			form.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
