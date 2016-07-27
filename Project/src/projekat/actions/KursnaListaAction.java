package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.KursnaListaStandardForm;



public class KursnaListaAction  extends AbstractAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KursnaListaAction() {
		KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Kursna lista");
		putValue(NAME, "Kursna lista");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		KursnaListaStandardForm form;
		try {
			form = new KursnaListaStandardForm(null, null);
			form.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}


		














































																						
					


