package projekat.form.actions;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import projekat.forms.DrzaveStandardForm;
import projekat.forms.NaseljenaMestaStandardForm;
import util.ColumnList;


public class ZoomFormAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog parentForm;
	private JDialog childForm;
	
	public ZoomFormAction(JDialog parentForm, JDialog childForm) {
		putValue(SHORT_DESCRIPTION, "Zoom");
		putValue(NAME, "...");
		this.parentForm = parentForm;
		this.childForm = childForm;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(parentForm instanceof NaseljenaMestaStandardForm){
			DrzaveStandardForm form = null;
			try {
				form = new DrzaveStandardForm(null,null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			form.setLocationRelativeTo(parentForm);
			form.setVisible(true);
			
		//	ColumnList cl = form.getColumns();
			
			//((NaseljenaMestaStandardForm)parentForm).setColumns(cl);
			
		}
		
		
	}
	
}
