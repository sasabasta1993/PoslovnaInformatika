package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.BankaStandardForm;



public class BankaAction extends AbstractAction    {

	public BankaAction() {
		KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Države");
		putValue(NAME, "Države");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		BankaStandardForm form;
		try {
			form = new BankaStandardForm(null, null);
			form.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
