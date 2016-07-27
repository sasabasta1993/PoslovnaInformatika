package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import projekat.forms.DrzaveStandardForm;
import projekat.forms.SifrarnikDelatnostiStandardForm;

public class SifrarnikDelatnostiAction  extends AbstractAction{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SifrarnikDelatnostiAction() {
		KeyStroke precica = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK);
		putValue(ACCELERATOR_KEY,precica);
		putValue(SHORT_DESCRIPTION, "Sifrarnik delatnosti");
		putValue(NAME, "Sifrarnik delatnosti");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	
		 SifrarnikDelatnostiStandardForm form;
		try {
			form = new SifrarnikDelatnostiStandardForm(null,null);
			form.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
