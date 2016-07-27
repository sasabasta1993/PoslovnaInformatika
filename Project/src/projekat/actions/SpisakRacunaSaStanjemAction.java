package projekat.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import database.DBConnection;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import projekat.forms.SifrarnikDelatnostiStandardForm;

public class SpisakRacunaSaStanjemAction extends AbstractAction{
	
	public SpisakRacunaSaStanjemAction() {
		//KeyStroke precica = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK);
		//putValue(ACCELERATOR_KEY,precica);
		putValue(SHORT_DESCRIPTION, "Spisak racuna sa stanjem");
		putValue(NAME, "Spisak racuna sa stanjem");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	
		try {
	          System.out.println(getClass().getResource("/tempReport.jasper"));
		  JasperPrint jp = JasperFillManager.fillReport(
		  getClass().getResource("/tempReport.jasper").openStream(),
		  null, DBConnection.getConnection());
		  JasperViewer.viewReport(jp, false);

		} catch (Exception ex) {
		  ex.printStackTrace();
		}
		
	}

}
