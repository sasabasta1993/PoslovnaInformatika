package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.NaseljenaMestaStandardForm;



public class NaseljenaMestaAction  extends AbstractAction{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NaseljenaMestaAction() {
		KeyStroke ctrlMKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlMKeyStroke);
		putValue(SHORT_DESCRIPTION, "Naseljena mesta");
		putValue(NAME, "Naseljena mesta");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		NaseljenaMestaStandardForm form = null;
		try {
			form = new NaseljenaMestaStandardForm(null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}
	

}
