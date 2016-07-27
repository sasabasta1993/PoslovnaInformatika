package projekat.forms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;


import javax.swing.JTextField;

import util.ColumnList;





public class DrzaveStandardForm  extends AbstractForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"Šifra", "Naziv" }, 0, "Drzave", "SELECT dr_sifra, dr_naziv FROM drzava WHERE ", "ORDER BY dr_sifra", new Integer [] {0});
	private String sqlQuery = "SELECT dr_sifra, dr_naziv FROM drzava ORDER BY dr_sifra";
	private static String insertSql = "INSERT INTO drzava (dr_sifra, dr_naziv) VALUES (? ,?)";
	private static String deleteSql = "DELETE FROM drzava WHERE dr_sifra=?";
	private static String updateSql = "UPDATE drzava SET dr_sifra=?, dr_naziv=? where dr_sifra=?";
	private static String searchSql = "SELECT * FROM drzava WHERE dr_sifra like ? or dr_naziv like ?";
	
	
	public DrzaveStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql,
				new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[]{"naseljeno_mesto.dr_sifra"})),
						new ArrayList<String>(Arrays.asList(new String[]{"valute.dr_sifra"})))),
				new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))),	
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.NaseljenaMestaStandardForm", 
						"projekat.forms.ValutaStandardForm"})),
				new ArrayList<String>(Arrays.asList(new String[]{"Naseljena mesta", "Valute"})), null, new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));
		setTitle("Države");
		this.parentForm = parentForm;
		this.indexImena.add(1);
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		ArrayList<Integer> listaIndeksaKljuceva = new ArrayList<Integer>();
		listaIndeksaKljuceva.add(1);
		
		for(int i = 0; i < 2; i++)
			tipovi.add("text");
		
		lista.add("Šifra,int");
		lista.add("Naziv");
		panel(lista.size(), lista, tipovi, null);

		initTable(tableModel, sqlQuery, null);
		blokiranjeKljuceva();
		
	
		final JLabel lblSifra = new JLabel("Šifra države mora imati najvise 3 karaktera.");
		lblSifra.setForeground(Color.RED);
		lblSifra.setVisible(false);
		dataPanel.add(lblSifra, "pushx", 2);
	
		
		
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
					((JTextField) dataPanel.getComponent(1)).setToolTipText("Niste uneli sifru drzave!");
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
		
		
		
		
		
		
		
		
	}
	
	
	@Override
	public boolean cekiranje()
	{
		return true;																	
	}
	
	@Override
	public boolean checkup(){
		//instancirati forme valuta i naseljena mesta preko refleksije
		for(int i = 0; i < listaKlasa.size(); ++i){
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
			
			for(int j = 0; j < form.tblGrid.getRowCount(); ++j){
				if(tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(form.tblGrid.getValueAt(j, 1)))
					return false;
			}
		}
		return true;
	}
	
	
	
	
}
