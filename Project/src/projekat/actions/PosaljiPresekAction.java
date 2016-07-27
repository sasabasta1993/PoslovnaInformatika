package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.PresekForm;



public class PosaljiPresekAction extends AbstractAction {
	
	public PosaljiPresekAction() {
		{
			KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);
			putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
			putValue(SHORT_DESCRIPTION, "Presek");
			putValue(NAME, "Posalji presek");
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		PresekForm form;
		form = new PresekForm();
		form.setVisible(true);
	
		
	}

}
