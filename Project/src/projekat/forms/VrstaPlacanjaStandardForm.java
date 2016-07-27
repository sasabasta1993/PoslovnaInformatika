package projekat.forms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JTextField;

import util.ColumnList;



public class VrstaPlacanjaStandardForm extends AbstractForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"Oznaka", "Naziv"}, 0, "Vrsta placanja", "SELECT vpl_oznaka, vpl_naziv FROM vrste_placanja WHERE" //RtgsClearingStavka
			, "ORDER BY vpl_oznaka", new Integer [] {0});
	private String sqlQuery = "SELECT vpl_oznaka, vpl_naziv FROM vrste_placanja ORDER BY vpl_oznaka";
	private static String insertSql = "INSERT INTO vrste_placanja (vpl_oznaka, vpl_naziv) VALUES (? ,?)";
	private static String deleteSql = "DELETE FROM vrste_placanja WHERE vpl_oznaka=?";
	private static String updateSql = "UPDATE vrste_placanja SET vpl_oznaka=?, vpl_naziv=? where vpl_oznaka=?";
	private static String searchSql = "SELECT * FROM vrste_placanja WHERE vpl_oznaka like ? and vpl_naziv like ?";

	public VrstaPlacanjaStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql,
				new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[]{"analitika_izvoda.vpl_oznaka"})))),
				new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))),	
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.AnalitikaIzvodaStandardForm"})),
				new                      ArrayList<String>(Arrays.asList(new String[]{"Analitika izvoda"})), null, new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));
		setTitle("Vrste placanja");
		this.indexImena.add(1);
		this.parentForm = parentForm;
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		for(int i = 0; i < 2; i++)
			tipovi.add("text");
	
		lista.add("Oznaka vrste,int");
		lista.add("Naziv vrste placanja");
		panel(lista.size(), lista, tipovi, null);
	
		initTable(tableModel, sqlQuery, null);
		
		blokiranjeKljuceva();
		
		

		final JLabel lblSifra = new JLabel("Oznaka vrste mora imati najvise 3 karaktera.");
		lblSifra.setForeground(Color.RED);
		lblSifra.setVisible(false);
		dataPanel.add(lblSifra, "pushx", 3);
		




dataPanel.getComponent(1).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
					if (tekstPolja.length() > 3) {
			
						lblSifra.setVisible(true);
		}
		
		else if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(0)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(1)).setToolTipText("Niste uneli oznaku vrste");
			lblSifra.setVisible(false);
		}
		else {
			
			lblSifra.setVisible(false);
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(0)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(1)).setToolTipText(null);
	}
});




		
		dataPanel.getComponent(3).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(3)).getText();
				if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(2)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(3)).setToolTipText("Morate uneti naziv vrste placanja!");
	
				
				
				
				
				
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(2)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(3)).setToolTipText(null);
			}
		});
	}
	
	@Override
	public boolean cekiranje()
	{
		boolean sveOk = true;
		for (int i=0; i<dataPanel.getComponentCount(); ++i)
		{
			if (dataPanel.getComponent(i) instanceof JTextField)
			{
				if (((JTextField) dataPanel.getComponent(i)).isEditable())
				{
				if (((JTextField) dataPanel.getComponent(i)).getText().equals(""))
				{
					System.out.println("usao" + i);
					((JLabel)dataPanel.getComponent(i-1)).setForeground(Color.RED);
					sveOk = false;
				}
				
				}
			}
		}
		
		
		
		String tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
		if (tekstPolja.length() != 3 && tekstPolja.length() > 0)
		{
			//dataPanel.add(labelaIkonica, 7);
			((JLabel)dataPanel.getComponent(0)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(1)).setToolTipText("Sifra valute je od tacno 3 karaktera!");
			sveOk = false;
		}
		
		
		if (sveOk)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkup(){
		// analitika izvoda
		for (int i = 0; i < listaKlasa.size(); ++i) {
			Class<?> class1 = null;
			String imeKlase = listaKlasa.get(i);
			try {
				class1 = Class.forName(imeKlase);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Constructor<?> constructor = null;
			try {
				constructor = class1.getConstructor(util.ColumnList.class,
						projekat.forms.AbstractForm.class);
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Object instance = null;
			try {
				instance = constructor.newInstance(null, null);
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AbstractForm form = (AbstractForm) instance;
			int brojKolone = 3;

			for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
				if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
						form.tblGrid.getValueAt(j, brojKolone)))
					return false;

			}
		}
		return true;
	}
	
}
