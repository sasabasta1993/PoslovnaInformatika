package projekat.forms;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.ColumnList;
import database.DBConnection;



public class RacuniPravnihLicaStandardForm extends AbstractForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean datumOk = true;
	boolean racunOk = true;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"Broj računa", "Datum otvaranja", "Vazeci", "ID racuna", "ID banke", "Naziv banke", "ID klijenta","Ime klijenta", "ID valute", "Naziv valute"}
	, 0, "Računi pravnih lica", "SELECT bar_racun, bar_datotv, vazeci, id_racuna, racuni_pravnih_lica.idbanke, banka.naziv_ba, racuni_pravnih_lica.id_klijenta, klijent.ime, racuni_pravnih_lica.va_id, valute.va_naziv_val"
			+ " FROM racuni_pravnih_lica JOIN banka ON racuni_pravnih_lica.idbanke = banka.idbanke"
			+ "  JOIN valute ON racuni_pravnih_lica.va_id = valute.va_id JOIN klijent ON racuni_pravnih_lica.id_klijenta = klijent.id_klijenta WHERE",
			"ORDER BY id_racuna", new Integer [] {0});
	
	private String sqlQuery =  "SELECT bar_racun, bar_datotv, vazeci, id_racuna, racuni_pravnih_lica.idbanke, banka.naziv_ba, racuni_pravnih_lica.id_klijenta, klijent.ime, racuni_pravnih_lica.va_id, valute.va_naziv_val"
			+ " FROM racuni_pravnih_lica JOIN banka ON racuni_pravnih_lica.idbanke = banka.idbanke"
			+ "  JOIN valute ON racuni_pravnih_lica.va_id = valute.va_id JOIN klijent ON racuni_pravnih_lica.id_klijenta = klijent.id_klijenta " +  
			"ORDER BY id_racuna";
	private static String insertSql = "INSERT INTO racuni_pravnih_lica (bar_racun, bar_datotv, vazeci,"
			+ " id_racuna, idbanke, id_klijenta, va_id) VALUES (? ,?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM racuni_pravnih_lica WHERE  bar_racun=?";
	private static String updateSql = "UPDATE racuni_pravnih_lica SET bar_racun=?, bar_datotv=?, vazeci=?,"
			+ " id_racuna=?, idbanke=?, id_klijenta=?, va_id=? where bar_racun =?";
	private static String searchSql = "SELECT bar_racun, bar_datotv, vazeci, id_racuna, racuni_pravnih_lica.idbanke, banka.naziv_ba, racuni_pravnih_lica.id_klijenta, klijent.ime, racuni_pravnih_lica.va_id"
			+ " FROM racuni_pravnih_lica JOIN banka ON racuni_pravnih_lica.idbanke = banka.idbanke"
			+ "  JOIN valute ON racuni_pravnih_lica.va_id = valute.va_id JOIN klijent ON racuni_pravnih_lica.id_klijenta = klijent.id_klijenta WHERE bar_racun like ? or"
			+ " bar_datotv like ? or vazeci like ? or id_racuna like ? or idbanke like ?"
			+ " or id_klijenta like ? or va_id like ?";
	
	public RacuniPravnihLicaStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
	
		super(insertSql, deleteSql, updateSql, searchSql,
				new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[]         {"prenos_izvoda___presek.id_racuna"})), //ra_br_racun
						new ArrayList<String>(Arrays.asList(new String[]{"ukidanje.id_racuna"})),
						new ArrayList<String>(Arrays.asList(new String[]{"dnevno_stanje_racuna.id_racuna"})))),   //ra_br_racun
				new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))), 
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.PresekStandardForm",
						"projekat.forms.UkidanjeStandardForm", "projekat.forms.DnevnoStanjeRacunaStandardForm"})),
				new ArrayList<String>(Arrays.asList(new String[]{"Presek racuna", "Ukidanje racuna", "Dnevno stanje racuna"})),
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.BankaStandardForm",
						"projekat.forms.KlijentStandardForm", "projekat.forms.ValutaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));
		// TODO Auto-generated constructor stub
		setTitle("Računi pravnih lica");
		this.parentForm = parentForm;
		HashMap<String, ArrayList<String>> listaComboVrednosti = new HashMap<String, ArrayList<String>>();
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		tipovi.add("text");
		
		lista.add("Broj računa");
		lista.add("datum otvaranja");
		tipovi.add("text");
		lista.add("Vazeci");
		tipovi.add("check");
		
		
		
		lista.add("ID racuna,int");
		tipovi.add("text");
		
		lista.add("ID banke");
		tipovi.add("zoom");
		lista.add("Naziv banke");
		tipovi.add("lookup");
		lista.add("ID klijenta");
		tipovi.add("zoom");
		lista.add("Naziv klijenta");
		tipovi.add("lookup");
		lista.add("ID valute");
		tipovi.add("zoom");
		lista.add("Naziv valute");
		tipovi.add("lookup");
		
		
		
		
		
		
		
		panel(lista.size(), lista, tipovi, listaComboVrednosti);
		
		initTable(tableModel, sqlQuery, colList);

		blokiranjeKljuceva();
		
		
		JButton btnUkidanjeRacuna = new JButton("Ukidanje racuna");

		dataPanel.add(btnUkidanjeRacuna);
		
	/*	btnUkidanjeRacuna.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GenericTableModel gtm = (GenericTableModel) tblGrid.getModel();
				int index = tblGrid.getSelectedRow();
				String ra_status = (String)gtm.getValueAt(index, 7);
				
				if (index == -1) // Ako nema selektovanog reda (tabela prazna)
				{
					JOptionPane.showMessageDialog(
							RacuniPravnihLicaStandardForm.this,
							"Niste selektovali nijedan racun",
							"", JOptionPane.WARNING_MESSAGE);
					return;
				}else if(ra_status.equals("N")){
						JOptionPane.showMessageDialog(RacuniPravnihLicaStandardForm.this, "Selektovani racun je vec deaktiviran");
				}
				
				else{
				
				gtm = (GenericTableModel) tblGrid.getModel();

				
				UkidanjeRacunaParametriForm form = new UkidanjeRacunaParametriForm();
				

			form.setVisible(true);
				
				if (form.getPotvrdjeno()) {
				
				String brojRacunaNasljednika = form.getBrojRacuna();
				String imePoverioca = form.getImePoverioca();
				java.sql.Date danasnjiSQL = new java.sql.Date(new Date().getTime());
				
				String ra_br_racun = (String) gtm.getValueAt(index, 0);
				BigDecimal iznos = null;
				String kl_naziv = "";
				
			//	ra_br_racun, racuni_pravnih_lica.ba_idbanke, banka.ba_naziv, racuni_pravnih_lica.kl_idklijent, klijent.kl_naziv, racuni_pravnih_lica.va_sifra, valute.va_naziv, ra_bar_datotv, ra_status "
				
				
				try {
					PreparedStatement stIznos = DBConnection.getConnection().prepareStatement("SELECT dsr_izvod, dsr_novostanje FROM dnevno_stanje_racuna WHERE "
							+ "ra_br_racun like ? ORDER BY dsr_izvod DESC", PreparedStatement.RETURN_GENERATED_KEYS);
					
					//stIznos.setString(1, ra_br_racun);
					
					
					
					
					ResultSet rsIznos = stIznos.executeQuery();
					
					
					while(rsIznos.next()){
						iznos = rsIznos.getBigDecimal("dsr_novostanje");
					break;
					}
					
					System.out.println(iznos);
					
					stIznos.close();
					
					
					String duznikQuery = "SELECT klijent.kl_naziv, klijent.kl_fizicko_lice, klijent.kl_ime, klijent.kl_prezime "
							+ "FROM racuni_pravnih_lica JOIN klijent ON racuni_pravnih_lica.kl_idklijent = klijent.kl_idklijent "
							+ "AND racuni_pravnih_lica.ra_br_racun like ?";
					
					PreparedStatement stDuznik = DBConnection.getConnection().prepareStatement(duznikQuery);
					stDuznik.setString(1,ra_br_racun);
					
					ResultSet rsDuznik =  stDuznik.executeQuery();
					
					while(rsDuznik.next()){
						
						String naziv = rsDuznik.getString(1);
						Boolean fizickoLice = rsDuznik.getBoolean(2);
						String ime = rsDuznik.getString(3);
						String prezime = rsDuznik.getString(4);
						
						if(fizickoLice){
							kl_naziv = naziv;
						}else{
							kl_naziv = ime+ " "+ prezime;
						}
						break;
					}
					
	
					dodavanjeAnalitikeIzvoda(ra_br_racun, null, null, "KES", new Long(10), "DIN", kl_naziv, "Ukidanje racuna",
							imePoverioca, danasnjiSQL, danasnjiSQL, ra_br_racun, new BigDecimal(00), "00", brojRacunaNasljednika, new BigDecimal(00), 
							"00", false, iznos, new BigDecimal(1), "E", true);
					

					
					if(brojRacunaNasljednika.substring(0, 3).equals("215")){// racun poverioca je iz nase banke
						
						dodavanjeAnalitikeIzvoda(brojRacunaNasljednika, null, null, "KES", new Long(10), "DIN", kl_naziv, "Prebacivanje sredstava sa ukinutog racuna",
								imePoverioca, danasnjiSQL, danasnjiSQL, ra_br_racun, new BigDecimal(00), "00", brojRacunaNasljednika, new BigDecimal(00), 
								"00", false, iznos, new BigDecimal(1), "E", false);
						
					}else{
						//rtgs
					}
					
					
					
					PreparedStatement stStatus = DBConnection.getConnection().prepareStatement("UPDATE racuni_pravnih_lica SET ra_status=? WHERE ra_br_racun like ?");
					stStatus.setString(1, "N");
					stStatus.setString(2, ra_br_racun);
					stStatus.executeUpdate();
					
					DBConnection.getConnection().commit();
					
					stStatus.close();
					PreparedStatement stUkidanje = DBConnection.getConnection().prepareStatement("INSERT INTO ukidanje (ra_br_racun, uk_datukidanja, uk_naracun) VALUES (? ,?, ?)");
					stUkidanje.setString(1, ra_br_racun);
					stUkidanje.setDate(2, danasnjiSQL);
					stUkidanje.setString(3, brojRacunaNasljednika);
					
					stUkidanje.execute();
					
					DBConnection.getConnection().commit();
					stUkidanje.close();
					
					
					JOptionPane.showMessageDialog(RacuniPravnihLicaStandardForm.this, "Racun je deaktiviran");
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				}
				}
			}
			
		});*/

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

		
		
		if (sveOk && datumOk && racunOk)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkup(){
		// presek, ukidanje, dnevno stanje racuna 
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
			int brojKolone = 2;
			if(i == 1 || i == 2)
				brojKolone = 0;

			for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
				if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
						form.tblGrid.getValueAt(j, brojKolone)))
					return false;

			}
		}
		return true;
	}
	
	
	
}
