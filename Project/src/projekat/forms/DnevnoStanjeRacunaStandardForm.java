package projekat.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;


import util.ColumnList;
import database.DBConnection;





public class DnevnoStanjeRacunaStandardForm  extends AbstractForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericTableModel tableModel = new GenericTableModel(
			new String[] {                 "Id racuna", "Broj izvoda", "Datum prometa", "Prethodno stanje",
					"U korist", "Na teret", "Novo stanje" },
			0,
			"Dnevno stanje racuna",
			"SELECT dnevno_stanje_racuna.id_racuna, dsr_izvod, dsr_datum, dsr_prethodno, dsr_ukorist, dsr_nateret, dsr_novostanje"
					+ " FROM dnevno_stanje_racuna" + " WHERE",
			" ORDER BY dnevno_stanje_racuna.id_racuna, dsr_izvod",
			new Integer[] { 0, 1 });
	private String sqlQuery = "SELECT dnevno_stanje_racuna.id_racuna, dsr_izvod, dsr_datum, dsr_prethodno, dsr_ukorist,"
			+ " dsr_nateret, dsr_novostanje FROM dnevno_stanje_racuna ORDER BY dnevno_stanje_racuna.id_racuna, dsr_izvod";
	private static String insertSql = "INSERT INTO dnevno_stanje_racuna (id_racuna, dsr_izvod, dsr_datum, "
			+ "dsr_prethodno, dsr_ukorist, dsr_nateret, dsr_novostanje) VALUES (? ,?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM dnevno_stanje_racuna WHERE id_racuna=? and dsr_izvod=?";
	private static String updateSql = "UPDATE dnevno_stanje_racuna SET id_racuna=?, dsr_izvod=?, dsr_datum=?,"
			+ " dsr_prethodno=?, dsr_ukorist=?, dsr_nateret=?, dsr_novostanje=? WHERE id_racuna=? and dsr_izvod=?";
	private static String searchSql = "SELECT racuni_pravnih_lica.id_racuna, dsr_izvod, dsr_datum, dsr_prethodno, dsr_ukorist, dsr_nateret, dsr_novostanje"
			+ " FROM dnevno_stanje_racuna JOIN racuni_pravnih_lica on dnevno_stanje_racuna.id_racuna"
			+ " = racuni_pravnih_lica.id_racuna WHERE racuni_pravnih_lica.id_racuna like ? or dsr_izvod like ? or "
			+ "dsr_datum like ? or dsr_prethodno like ? or dsr_ukorist like ? or dsr_nateret like ? or dsr_novostanje like ?";

	
	
	
	
	public DnevnoStanjeRacunaStandardForm(ColumnList colList,
			AbstractForm parentForm) throws SQLException {
		super(
				insertSql,
				deleteSql,
				updateSql,
				searchSql,
				new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[] {
								"analitika_izvoda.id_racuna",//ra_br_racun
								"analitika_izvoda.dsr_izvod" })),
						new ArrayList<String>(Arrays.asList(new String[] {
								"prenos_izvoda___presek.id_racuna", "prenos_izvoda___presek.dsr_izvod" })))),
				new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[] { 0,
								2 })),
						new ArrayList<Integer>(Arrays.asList(new Integer[] { 0,
								2 })))),
				new ArrayList<String>(Arrays.asList(new String[] {
						"projekat.forms.AnalitikaIzvodaStandardForm",
						"projekat.forms.PresekStandardForm" })),
				new ArrayList<String>(Arrays.asList(new String[] {
						"Analitika izvoda", "Presek" })),
				new ArrayList<String>(
						Arrays.asList(new String[] { "projekat.forms.RacuniPravnihLicaStandardForm" })),
				new ArrayList<Integer>(Arrays.asList(new Integer[] { 1, 4 })));

		setTitle("Dnevno stanje racuna");
		this.indexImena.add(3);
		this.parentForm = parentForm;
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();

		lista.add("ID racuna");
		tipovi.add("zoom");

		for (int i = 1; i < 7; i++)
			tipovi.add("text");

		lista.add("Broj izvoda");
		lista.add("Datum prometa,date");
		lista.add("Prethodno stanje,double");
		lista.add("Promet u korist,double");
		lista.add("Promet na teret,double");
		lista.add("Novo stanje,double");
		panel(7, lista, tipovi, null);

		initTable(tableModel, sqlQuery, colList);

		blokiranjeKljuceva();

		JButton btnFormirajIzvod = new JButton("Formiraj presek");

		dataPanel.add(btnFormirajIzvod);

		/*btnFormirajIzvod.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tblGrid.getSelectedRow();
				if (index == -1) // Ako nema selektovanog reda (tabela prazna)
				{
					JOptionPane.showMessageDialog(
							DnevnoStanjeRacunaStandardForm.this,
							"Niste selektovali nijedno dnevno stanje racuna",
							"", JOptionPane.WARNING_MESSAGE);
					return;
				} else {

					GenericTableModel gtm = (GenericTableModel) tblGrid
							.getModel();

					// ra_br_racun, dsr_izvod, dsr_datum, dsr_prethodno,
					// dsr_ukorist, dsr_nateret, dsr_novostanje)

					String ra_br_racun = (String) gtm.getValueAt(index, 0);
					BigDecimal dsr_izvod = new BigDecimal((String) gtm
							.getValueAt(index, 1));
					BigDecimal kl_idklijent = null;
					
					
					SimpleDateFormat pattern = new SimpleDateFormat(
							"yyyy-MM-dd");

					Date datumNaloga = null;

					try {
						datumNaloga = (Date) pattern.parse((String) gtm
								.getValueAt(index, 2));

					} catch (ParseException e1) {
						e1.printStackTrace();
					}

					java.sql.Date pr_datum = new java.sql.Date(datumNaloga
							.getTime());

					try {

						PreparedStatement stBrojRacuna = DBConnection
								.getConnection()
								.prepareStatement(
										"SELECT kl_idklijent FROM racuni_pravnih_lica WHERE ra_br_racun like ?");

						stBrojRacuna.setString(1, ra_br_racun);

						ResultSet rsBrojRacuna = stBrojRacuna.executeQuery();

						while (rsBrojRacuna.next()) {
							kl_idklijent = rsBrojRacuna.getBigDecimal("kl_idklijent");
						}

						stBrojRacuna.close();
						
						
						///select ai_broj stavke da bih znala koliko imam analitika izvoda
						
						
						BigDecimal brAnalitika = new BigDecimal(0);
						PreparedStatement stBrAnalitika = DBConnection
								.getConnection()
								.prepareStatement("SELECT ai_brojstavke FROM analitika_izvoda WHERE dsr_izvod like ? and ra_br_racun like ? ORDER BY ai_brojstavke DESC");
						
						stBrAnalitika.setBigDecimal(1, dsr_izvod);
						stBrAnalitika.setString(2, ra_br_racun);
						
						ResultSet rsBrAnalitika = stBrAnalitika.executeQuery();
						
						rsBrAnalitika.next();
						brAnalitika = rsBrAnalitika.getBigDecimal("ai_brojstavke");
						
						if(!rsBrAnalitika.isBeforeFirst()){
						

						PreparedStatement stBrojStavke;

						stBrojStavke = DBConnection
								.getConnection()
								.prepareStatement(
										"SELECT ai_brojstavke, ai_racpov, ai_racduz, ai_iznos FROM analitika_izvoda "
												+ "WHERE dsr_izvod like ? and ra_br_racun like ?");

						stBrojStavke.setBigDecimal(1, dsr_izvod);
						stBrojStavke.setString(2, ra_br_racun);

						ResultSet rsBrojStavke = stBrojStavke.executeQuery();
						
						

						String insertAnalitika = "INSERT INTO analitika_preseka (kl_idklijent, pre_ra_br_racun, pr_datum,"
								+ " pr_br_presek, apr_rbr, ra_br_racun, dsr_izvod, ai_brojstavke) VALUES (? ,?, ?, ?, ?, ?, ?, ?)";

						PreparedStatement stAnalitikaPresjeka = DBConnection
								.getConnection().prepareStatement(
										insertAnalitika);

						int i = 1;
						int j = 1;
						int br_presjeka = 0;
						int uKorist = 0;
						int naTeret = 0;
						BigDecimal ukupnoUKorist = new BigDecimal(0);
						BigDecimal ukupnoNaTeret = new BigDecimal(0);
						
						int apr_rbr = 0;

						while (rsBrojStavke.next()) {
							
							apr_rbr = i%3;
							if(apr_rbr==0 ){
								apr_rbr = 3;
							}
							
							stAnalitikaPresjeka.setBigDecimal(1, kl_idklijent);
							stAnalitikaPresjeka.setString(2, ra_br_racun);
							stAnalitikaPresjeka.setDate(3, pr_datum);
							stAnalitikaPresjeka.setBigDecimal(4,
									new BigDecimal(j));
							br_presjeka = j;
							stAnalitikaPresjeka.setBigDecimal(5,
									new BigDecimal(apr_rbr));
							stAnalitikaPresjeka.setString(6, ra_br_racun);
							stAnalitikaPresjeka.setBigDecimal(7, dsr_izvod);
							BigDecimal ai_brojstavke = rsBrojStavke
									.getBigDecimal("ai_brojstavke");
							stAnalitikaPresjeka.setBigDecimal(8, ai_brojstavke);

							String ai_racduz = rsBrojStavke
									.getString("ai_racduz");
							BigDecimal ai_iznos = rsBrojStavke
									.getBigDecimal("ai_iznos");
							if (ai_racduz.equals(ra_br_racun)) {
								naTeret++;
								ukupnoNaTeret = ukupnoNaTeret.add(ai_iznos);
							} else {
								uKorist++;
								ukupnoUKorist = ukupnoUKorist.add(ai_iznos);
							}

							if (i % 3 == 0 || brAnalitika.compareTo(new BigDecimal(i))==0) {

								String insertPresjek = "INSERT INTO presek (kl_idklijent, ra_br_racun, pr_datum, pr_br_presek,"
										+ " dne_ra_br_racun, dsr_izvod, pr_brukorist, pr_uk_korist, pr_brnateret, pr_uk_teret, pr_brpogstk, "
										+ "pr_brpogstt, pr_status) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

								PreparedStatement stPresjek = DBConnection
										.getConnection().prepareStatement(
												insertPresjek);
								stPresjek.setBigDecimal(1, kl_idklijent);
								stPresjek.setString(2, ra_br_racun);
								stPresjek.setDate(3, pr_datum);
								stPresjek.setBigDecimal(4, new BigDecimal(
										br_presjeka));
								stPresjek.setString(5, ra_br_racun);
								stPresjek.setBigDecimal(6, dsr_izvod);
								stPresjek.setBigDecimal(7, new BigDecimal(
										uKorist));
								stPresjek.setBigDecimal(8, ukupnoUKorist);
								stPresjek.setBigDecimal(9, new BigDecimal(
										naTeret));
								stPresjek.setBigDecimal(10, ukupnoNaTeret);
								stPresjek.setBigDecimal(11, new BigDecimal(0));
								stPresjek.setBigDecimal(12, new BigDecimal(0));
								stPresjek.setString(13, "F");

								stPresjek.execute();
								DBConnection.getConnection().commit();

								j++;

								uKorist = 0;
								naTeret = 0;
								ukupnoUKorist = new BigDecimal(0);
								ukupnoNaTeret = new BigDecimal(0);
							}

							i++;

							stAnalitikaPresjeka.execute();
							DBConnection.getConnection().commit();

						}

						stBrojStavke.close();
						stAnalitikaPresjeka.close();
						
						JOptionPane.showMessageDialog(DnevnoStanjeRacunaStandardForm.this,"Izvod je formiran","", JOptionPane.INFORMATION_MESSAGE);
						
						
					}else{
						JOptionPane.showMessageDialog(DnevnoStanjeRacunaStandardForm.this,"Ne postoje analitike izvoda za ovo dnevno stanje raèuna, "
								+ "izvod se nece formirati.","", JOptionPane.INFORMATION_MESSAGE);

						
					}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				
				

			}
			
		});*/

	}
	
	

	@Override
	public boolean cekiranje()
	{
		return true;
	}
	
	@Override
	public boolean checkup(){
		// stavka clearing-a, analitika preseka
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
			int brojKolone = 0;
			if (i == 1)
				brojKolone = 2;

			for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
				if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
						form.tblGrid.getValueAt(j, brojKolone)))
					return false;

			}
		}
		return true;
	}

}
