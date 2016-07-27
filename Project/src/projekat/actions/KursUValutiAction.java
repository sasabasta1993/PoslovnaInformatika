package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.KursUValutiStandardForm;



public class KursUValutiAction  extends AbstractAction {
	
	
	
	
	public KursUValutiAction() {
		KeyStroke ctrlDKeyStroke	 = KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.SHIFT_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Kurs u valuti");
		putValue(NAME, "Kurs u valuti");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		KursUValutiStandardForm form;
		try {
			form = new KursUValutiStandardForm(null, null);
			form.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
