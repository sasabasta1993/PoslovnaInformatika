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



public class ValutaStandardForm  extends AbstractForm{

	private static final long serialVersionUID = 1L;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"ID valute", "Sifra drzave", "Naziv drzave", "Naziv valute", "Domilicina ", "Zvanicna sifra" }, 0, "Valuta",
			"SELECT va_id, valute.dr_sifra, drzava.dr_naziv, va_naziv_val, va_domicilna, zvanicna_sifra FROM valute JOIN drzava ON valute.dr_sifra = drzava.dr_sifra WHERE ", " ORDER BY va_id", new Integer [] {0});
	private String sqlQuery = "SELECT va_id, valute.dr_sifra, drzava.dr_naziv, va_naziv_val, va_domicilna, zvanicna_sifra FROM valute JOIN drzava ON valute.dr_sifra = drzava.dr_sifra ORDER BY va_id";
	private static String insertSql = "INSERT INTO valute (va_id, dr_sifra, va_naziv_val, va_domicilna, zvanicna_sifra) VALUES (? ,?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM valute WHERE va_id=?";
	private static String updateSql = "UPDATE valute SET va_id=?, dr_sifra=?, va_naziv_val=?, va_domicilna=?, zvanicna_sifra=? where va_id=?";
	private static String searchSql = "SELECT va_id, valute.dr_sifra, drzava.dr_naziv, va_naziv_val, va_domicilna, zvanicna_sifra FROM valute JOIN drzava ON valute.dr_sifra = drzava.dr_sifra WHERE va_id like ? or drzava.dr_sifra like ? or"
			+ " va_naziv_val like ? or id_domicilna like ? or zvanicna_sifra like ?";

	public ValutaStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, new ArrayList<ArrayList<String>>(Arrays.asList(
				new ArrayList<String>(Arrays.asList(new String[]{"kurs_u_valuti.va_id"})), //kursuvaluti, racun, analitika izvoda
				new ArrayList<String>(Arrays.asList(new String[]{"racuni_pravnih_lica.va_id"})),
				new ArrayList<String>(Arrays.asList(new String[]{"analitika_izvoda.va_id"})))),
		new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
				new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
				new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))),	
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.KursUValutiStandardForm", 
				"projekat.forms.RacuniPravnihLicaStandardForm", "projekat.forms.AnalitikaIzvodaStandardForm"})),
		new ArrayList<String>(Arrays.asList(new String[]{"Kurs u valuti", "Racuni pravnih lica", "Analitika izvoda"})),
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.DrzaveStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));
		setTitle("Valute");
		this.indexImena.add(3);
		this.parentForm = parentForm;
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		tipovi.add(0,"text");
		lista.add("ID valute,int");
		lista.add("Sifra drzave");
		tipovi.add("zoom");
		lista.add("Naziv drzave");
		tipovi.add("lookup");
		lista.add("Naziv valute");
		tipovi.add("text");
		lista.add("Domicilna");
		tipovi.add("check");
		lista.add("Zvanicna sifra");
		tipovi.add("text");
		
		panel(lista.size(), lista, tipovi, null);
		initTable(tableModel, sqlQuery, colList);
		
		blokiranjeKljuceva();
		
		
		
		final JLabel lblSifra = new JLabel("Zvanicna sifra mora imati najvise 3 karaktera.");
		lblSifra.setForeground(Color.RED);
		lblSifra.setVisible(false);
		dataPanel.add(lblSifra, "pushx", 12);
		
		
		
		
dataPanel.getComponent(1).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
				
				 if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(0)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(1)).setToolTipText("Morate uneti ID valute!");
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
					((JTextField) dataPanel.getComponent(3)).setToolTipText(" Unesite sifru drzave!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(2)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(3)).setToolTipText(null);
			}
		});
		
		dataPanel.getComponent(7).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(7)).getText();
				if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(6)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(7)).setToolTipText("Unesite naziv valute!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(6)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(7)).setToolTipText(null);
			}
		});
		
		
		
		
dataPanel.getComponent(11).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(11)).getText();
							if (tekstPolja.length() > 3) {
					
								lblSifra.setVisible(true);
				}
				
				else if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(10)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(11)).setToolTipText("Niste uneli zvanicnu sifru");
					lblSifra.setVisible(false);
				}
				else {
					
					lblSifra.setVisible(false);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(10)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(11)).setToolTipText(null);
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
				//	System.out.println("usao" + i);
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
		// Kurs u valuti, racuni, analitika izvoda
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
			int brojKolone1 = 1;
			int brojKolone2 = 5;
			if (i == 0)
				brojKolone1 = 3;
			else if (i == 1)
				brojKolone1 = 4;
			else if (i == 2)
				brojKolone1 = 7;

			if(i == 0){
				for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
					if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
							form.tblGrid.getValueAt(j, brojKolone1)) || tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
									form.tblGrid.getValueAt(j, brojKolone2)))
						return false;
				}
			} else {
				for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
					if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
							form.tblGrid.getValueAt(j, brojKolone1)))
						return false;
				}
			}
		}
		return true;
	}

	
	
}
