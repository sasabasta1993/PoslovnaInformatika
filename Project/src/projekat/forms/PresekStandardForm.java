package projekat.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.ColumnList;
import database.DBConnection;



public class PresekStandardForm  extends AbstractForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"ID klijenta", "Ime klijenta", "ID racuna", "Datum otvaranje racuna", "Datum naloga", "Broj preseka", "ID racuna 2", "Broj izvoda", "Datum prometa", "Broj promena u korist", "Ukupno u korist", "Broj promena na teret", "Ukupno na teret", "Broj pogrešnih stavki u korist", "Broj pogrešnih stavki na teret", "Status naloga" }, 0, "Presek izvoda",
			"  SELECT prenos_izvoda___presek.id_klijenta, klijent.ime, prenos_izvoda___presek.id_racuna, racuni_pravnih_lica.bar_datotv, bnp_datum, bnp_presek, dne_id_racuna, prenos_izvoda___presek.dsr_izvod, dnevno_stanje_racuna.dsr_datum, bnp_brukorist, bnp_u_korist, bnp_brnateret, bnp_ukteret, bnp_brpogk, bnp_brpogt, bnp_status FROM  " +
			 " prenos_izvoda___presek  JOIN klijent ON prenos_izvoda___presek.id_klijenta = klijent.id_klijenta  JOIN racuni_pravnih_lica ON prenos_izvoda___presek.id_racuna = racuni_pravnih_lica.id_racuna"  +
			"  JOIN dnevno_stanje_racuna ON prenos_izvoda___presek.dsr_izvod = dnevno_stanje_racuna.dsr_izvod WHERE",
			"ORDER BY prenos_izvoda___presek.id_klijenta, id_racuna, bnp_datum, bnp_presek", new Integer [] {0,2,3,4});
	
	private String sqlQuery = "SELECT prenos_izvoda___presek.id_klijenta, klijent.ime, prenos_izvoda___presek.id_racuna, racuni_pravnih_lica.bar_datotv, bnp_datum, bnp_presek, dne_id_racuna, prenos_izvoda___presek.dsr_izvod, dnevno_stanje_racuna.dsr_datum, bnp_brukorist, bnp_u_korist, bnp_brnateret, bnp_ukteret, bnp_brpogk, bnp_brpogt, bnp_status FROM  " +
			 " prenos_izvoda___presek  JOIN klijent ON prenos_izvoda___presek.id_klijenta = klijent.id_klijenta  JOIN racuni_pravnih_lica ON prenos_izvoda___presek.id_racuna = racuni_pravnih_lica.id_racuna"  +
			"  JOIN dnevno_stanje_racuna ON prenos_izvoda___presek.dsr_izvod = dnevno_stanje_racuna.dsr_izvod ORDER BY prenos_izvoda___presek.id_klijenta, id_racuna, bnp_datum, bnp_presek";		
	private static String insertSql = "INSERT INTO prenos_izvoda___presek (id_klijenta, id_racuna, bnp_datum, bnp_presek, dne_id_racuna, dsr_izvod, bnp_brukorist, bnp_u_korist, bnp_brnateret, bnp_ukteret, bnp_brpogk, bnp_brpogt, bnp_status) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM prenos_izvoda___presek WHERE id_klijenta=?, id_racuna=?, bnp_datum=?, bnp_presek=?";
	private static String updateSql = "UPDATE prenos_izvoda___presek SET id_klijenta=?, id_racuna=?, bnp_datum=?, bnp_presek=?, dne_id_racuna=?, dsr_izvod=?, bnp_brukorist=?, bnp_u_korist=?, bnp_brnateret=?, bnp_ukteret=?, bnp_brpogk=?, bnp_brpogt=?, bnp_status=? WHERE id_klijenta=?, id_racuna=?, bnp_datum=?, bnp_presek=?";
	private static String searchSql = "SELECT * FROM prenos_izvoda___presek WHERE id_klijenta like ?, id_racuna like ?, bnp_datum like ?, bnp_presek like ?, dne_id_racuna like ?, dsr_izvod like ?, bnp_brukorist like ?, bnp_u_korist like ?, bnp_brnateret like ?, bnp_ukteret like ?, bnp_brpogk like ?, bnp_brpogt like ?, bnp_status like ?";
	
	public PresekStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, 
				new ArrayList<ArrayList<String>>(Arrays.asList(
				new ArrayList<String>(Arrays.asList(new String[]{"analitika_preseka.id_klijenta", "analitika_preseka.id_racuna",
						"analitika_preseka.bnp_datum", "analitika_preseka.bnp_presek"})))),
		new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<Integer>(Arrays.asList(new Integer[]{0,2,3,4})))),	
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.AnalitikaPresekaStandardForm"})),
		new ArrayList<String>(Arrays.asList(new String[]{"Analitika preseka"})),
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.KlijentStandardForm", "projekat.forms.RacuniPravnihLicaStandardForm", "projekat.forms.DnevnoStanjeRacunaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1,5,9,11})));
		// TODO Auto-generated constructor stub
		setTitle("Prenos izvoda-presek");
		this.parentForm = parentForm;
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		
		
		lista.add("ID klijenta,int");
		tipovi.add("zoom");
		 lista.add("Naziv klijeta");
		 tipovi.add("lookup");
		
		
		lista.add("ID računa,int");
		tipovi.add("zoom");
		 lista.add("Datum otvaranja racuna");
		 tipovi.add("lookup");
		
		

		lista.add("Datum naloga,date");
		lista.add("Broj preseka,int");
		tipovi.add("text");
		tipovi.add("text");
		//HIDDEN POLJE, dupli broj racuna
		lista.add("Dne_id racuna,int");	
		tipovi.add("text,zoom");
		
		
		lista.add("Broj izvoda,int"); //tipovi.add("doubleZoom");
		tipovi.add("doubleZoom");
		//lista.add("Datum prometa");
		//tipovi.add("lookup");
		
		lista.add("Broj promena u korist,int");
		lista.add("Ukupno u korist,double");
		lista.add("Broj promena na teret,int");
		lista.add("Ukupno na teret,double");
		lista.add("Broj pogrešnih stavki u korist,int");
		lista.add("Broj pogrešnih stavki na teret,int");
		lista.add("Status naloga");
		
		for(int i = 6; i < 13; i++)
			tipovi.add("text");
		
		panel(lista.size(), lista, tipovi, null);
		
		initTable(tableModel, sqlQuery, colList);
		
		JButton btnExport = new JButton("Export preseka");
		dataPanel.add(btnExport);
	/*	btnExport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BigDecimal idKlijenta = null;
				String brRacuna = null;
				java.sql.Date datum = null;
				BigDecimal brPreseka = null;
				boolean prazno = false;
				if(dataPanel.getComponent(1) instanceof JTextField){
					if(((JTextField)dataPanel.getComponent(1)).getText() == null ||
					((JTextField)dataPanel.getComponent(1)).getText().equals("")){
						prazno = true;
					}else{
					idKlijenta = BigDecimal.valueOf(Double.parseDouble(((JTextField)dataPanel.getComponent(1)).getText()));
					System.out.println("id : "+idKlijenta);
					}
				}
				
				if(dataPanel.getComponent(4) instanceof JTextField){
					if(((JTextField)dataPanel.getComponent(4)).getText() == null ||
							((JTextField)dataPanel.getComponent(4)).getText().equals("")){
								prazno = true;
					}else{
						
					brRacuna = ((JTextField)dataPanel.getComponent(4)).getText();
					System.out.println(" racun "+brRacuna);
					}
				}
				
				if(dataPanel.getComponent(7) instanceof JTextField){
					if(((JTextField)dataPanel.getComponent(7)).getText() == null ||
							((JTextField)dataPanel.getComponent(7)).getText().equals("")){
								prazno = true;
					}else{
						
					SimpleDateFormat pattern = new SimpleDateFormat(
							"yyyy-MM-dd");

					java.util.Date temp = null;
					try {
						temp = (java.util.Date) pattern.parse(((JTextField)dataPanel.getComponent(7)).getText().toString());
						datum =new java.sql.Date(temp.getTime());
							} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						
					System.out.println("datum "+datum.toString());
				}
				}
				
				if(	dataPanel.getComponent(9) instanceof JTextField){
					if(((JTextField)dataPanel.getComponent(9)).getText() == null ||
							((JTextField)dataPanel.getComponent(9)).getText().equals("")){
								prazno = true;
					}else{
						
					brPreseka = BigDecimal.valueOf(Double.parseDouble(((JTextField)dataPanel.getComponent(9)).getText()));
					System.out.println("presjek "+brPreseka);
					}
				}
				if(prazno){
					 JOptionPane.showMessageDialog(PresekStandardForm.this, "Niste selektovali presek."
					    		, "Upozorenje " + "", JOptionPane.WARNING_MESSAGE);
					 return;
				}
				Presek p = new Presek();
				
				p = MainFrame.getInstance().pronadjiPresek(idKlijenta, brRacuna, datum, brPreseka);
				if(p == null){
					 JOptionPane.showMessageDialog(PresekStandardForm.this, "Presek nije pronadjen. Proverite da li ste selektovali presek."
					    		, "Greska " + "", JOptionPane.ERROR_MESSAGE);
					
				}else if(p.getPrStatus().equalsIgnoreCase("P")){
					 JOptionPane.showMessageDialog(PresekStandardForm.this, "Presek je vec poslat."
					    		, "Obavjestenje " + "", JOptionPane.INFORMATION_MESSAGE);
		
				}else{
					
				ExportPresekAction akcija = new ExportPresekAction(p);
				akcija.actionPerformed(e);
				izmjeniStanjePresjeka(p);
				try {
					tableModel.refresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}});*/


		blokiranjeKljuceva();
	}
	
	/*protected void izmjeniStanjePresjeka(Presek p) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement updatePresjeka =  DBConnection
					.getConnection()
					.prepareStatement("UPDATE PRESEK SET "
								+ "PR_STATUS ='P' where "
								+ "KL_IDKLIJENT = ? and RA_BR_RACUN like ? and PR_DATUM = ? and"
								+ " PR_BR_PRESEK = ? ", PreparedStatement.RETURN_GENERATED_KEYS);
			//updatePresjeka.setCha(1, "P");
			updatePresjeka.setBigDecimal(1,BigDecimal.valueOf(p.getKlIDKlijenta().doubleValue()));
			updatePresjeka.setString(2, p.getRaBrRacuna());
			java.sql.Date d = new java.sql.Date(p.getPrDatum().toGregorianCalendar().getTime().getTime());
			updatePresjeka.setDate(3,d);
			updatePresjeka.setBigDecimal(4, BigDecimal.valueOf(p.getPrBrPReseka().doubleValue()));
			updatePresjeka.executeUpdate();
			DBConnection.getConnection().commit();
			updatePresjeka.close();
			//refresh();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}*/

	@Override
	public boolean cekiranje()
	{
		return true;
	}
	
	@Override
	public boolean checkup(){
		// analitika preseka
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
				if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 4).equals(
						form.tblGrid.getValueAt(j, brojKolone)))
					return false;

			}
		}
		return true;
	}


}
