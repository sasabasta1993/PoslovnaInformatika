package projekat.forms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.ColumnList;



public class KlijentStandardForm   extends AbstractForm {

	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		boolean proveraPib = true;
		boolean proveraJmbg = false;
		private GenericTableModel tableModel = new GenericTableModel(new String[] {
				"Id", "Šifra delatnosti","Naziv delatnosti" , "Sifra mesta", "Naziv mesta", "PIB", "JMBG", "Fizicko lice", "Ime", "Prezime", "Naziv klijenta", "Adresa",
				"Email", "Web", "Telefon", "Fax" }, 0,"Klijent",
				"SELECT id_klijenta, klijent.sifra_delatnosti, sifrarnik_delatnosti.naziv_delatnosti, klijent.nm_sifra, naseljeno_mesto.nm_naziv, PR_pib, jmbg, fizicko_lice, ime,"
				+ "prezime, pr_naziv, pr_adresa, pr_email, pr_web, pr_telefon, pr_fax "
				+ "FROM klijent JOIN sifrarnik_delatnosti ON klijent.sifra_delatnosti = sifrarnik_delatnosti.sifra_delatnosti   JOIN"
				
				+ " naseljeno_mesto ON klijent.nm_sifra = naseljeno_mesto.nm_sifra WHERE", "ORDER BY id_klijenta", new Integer [] {0});
		
		private String sqlQuery =    "SELECT id_klijenta, klijent.sifra_delatnosti, sifrarnik_delatnosti.naziv_delatnosti, klijent.nm_sifra, naseljeno_mesto.nm_naziv, PR_pib, jmbg, fizicko_lice, ime,"
				+ "prezime, pr_naziv, pr_adresa, pr_email, pr_web, pr_telefon, pr_fax "
				+ "FROM klijent JOIN sifrarnik_delatnosti ON klijent.sifra_delatnosti = sifrarnik_delatnosti.sifra_delatnosti   JOIN"
				+ " naseljeno_mesto ON klijent.nm_sifra = naseljeno_mesto.nm_sifra ORDER BY id_klijenta"    ;
		private static String insertSql = "INSERT INTO klijent (id_klijenta, sifra_delatnosti, nm_sifra, PR_pib,"
				+ " jmbg, fizicko_lice, ime, prezime, pr_naziv, pr_adresa, pr_email, pr_web, pr_telefon, pr_fax) VALUES (?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		private static String deleteSql = "DELETE FROM klijent WHERE id_klijenta=?";
		private static String updateSql = "UPDATE klijent SET id_klijenta=?, sifra_delatnosti=?, nm_sifra=?, PR_pib=?, jmbg=?, fizicko_lice=?, ime=?, prezime=?, pr_naziv=?, pr_adresa=?, pr_email=?, pr_web=?, pr_telefon=?, pr_fax=? where id_klijenta=?";
		private static String searchSql = "SELECT id_klijenta, klijent.sifra_delatnosti, sifrarnik_delatnosti.naziv_delatnosti, klijent.nm_sifra, naseljeno_mesto.nm_naziv, PR_pib,"
				+ "jmbg, fizicko_lice, ime, prezime, pr_naziv, pr_adresa,"
				+ "pr_email, pr_web, pr_telefon, pr_fax FROM klijent "
				
				+ "JOIN klijent ON klijent.sifra_delatnosti = sifrarnik_delatnosti.sifra_delatnosti   JOIN"
				+ " naseljeno_mesto ON klijent.nm_sifra = naseljeno_mesto.nm_sifra WHERE id_klijenta like ? or sifra_delatnosti like ?"
				+ "or nm_sifra like ? or PR_pib like ? or jmbg like ? or fizicko_lice like ? or ime like ? or prezime like ? or pr_naziv like ? or pr_adresa like ? or pr_email like ? or pr_web like ? or pr_telefon like ? or pr_fax  like ?";
		
		public KlijentStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
			super(insertSql, deleteSql, updateSql, searchSql,
					new ArrayList<ArrayList<String>>(Arrays.asList(
							new ArrayList<String>(Arrays.asList(new String[]{"prenos_izvoda___presek.id_klijenta"})), //presek.kl_idklijent
							new ArrayList<String>(Arrays.asList(new String[]{"racuni_pravnih_lica.id_klijenta"})))),
					new ArrayList<ArrayList<Integer>>(Arrays.asList(
							new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
							new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))),	
					new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.PresekStandardForm", 
							"projekat.forms.RacuniPravnihLicaStandardForm"})),
					new ArrayList<String>(Arrays.asList(new String[]{"Presek", "Racuni"})),
					new ArrayList<String>(Arrays.asList(new String[]{
					"projekat.forms.SifrarnikDelatnostiStandardForm", "projekat.forms.NaseljenaMestaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));

			setTitle("Klijenti");
			this.indexImena.add(10);
			this.parentForm = parentForm;
			ArrayList<String> lista = new ArrayList<String>();
			ArrayList<String> tipovi = new ArrayList<String>();
			
			tipovi.add(0,"text");
			
			lista.add("ID,int");
			lista.add("Šifra delatnosti");
			lista.add("Šifra mesta,int");
			
			for(int i = 1; i < 3; i++)
				tipovi.add("zoom");
			
			lista.add("naziv delatnosti");
			lista.add("naziv mesta");
			tipovi.add("lookup");
			tipovi.add("lookup");
			
			lista.add("Pib");
			tipovi.add("text");
			lista.add("Jmbg");
			tipovi.add("text");
			lista.add("Fizicko lice");
			
			tipovi.add("check");
			
			lista.add("Ime");
			lista.add("Prezime");
			lista.add("Naziv klijenta");
			lista.add("Adresa");
			lista.add("Email");
			lista.add("Web");
			lista.add("Telefon,int");
			lista.add("Fax,broj");
			
			for(int i = 6; i < 14; i++)
				tipovi.add("text");
			
			panel(lista.size(), lista, tipovi, null);
			initTable(tableModel, sqlQuery, colList);
			
			blokiranjeKljuceva();
			
	dataPanel.getComponent(13).setEnabled(false);
			
			
		
			
			
			
			dataPanel.getComponent(23).addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
				
					String tekstPolja = ((JTextField) dataPanel.getComponent(23)).getText();
					
						if (tekstPolja.equals(""))
						{
							((JLabel)dataPanel.getComponent(22)).setForeground(Color.RED);
							((JTextField) dataPanel.getComponent(23)).setToolTipText("Morate uneti adresu!");
							//validacija = false;
						}
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					((JLabel)dataPanel.getComponent(22)).setForeground(Color.BLACK);
					((JTextField) dataPanel.getComponent(23)).setToolTipText(null);
				}
			});
			
			
			
			
			
			dataPanel.getComponent(29).addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
				
					String tekstPolja = ((JTextField) dataPanel.getComponent(29)).getText();
					
						if (tekstPolja.equals(""))
						{
							((JLabel)dataPanel.getComponent(28)).setForeground(Color.RED);
							((JTextField) dataPanel.getComponent(29)).setToolTipText("Morate uneti telefon!");
							//validacija = false;
						}
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					((JLabel)dataPanel.getComponent(28)).setForeground(Color.BLACK);
					((JTextField) dataPanel.getComponent(29)).setToolTipText(null);
				}
			});
			
		}

		@Override
		public boolean cekiranje()
		{
			boolean sveOk = true;
			if (((JTextField)dataPanel.getComponent(23)).getText().equals(""))
			{
				((JLabel)dataPanel.getComponent(22)).setForeground(Color.RED);
				((JTextField)dataPanel.getComponent(23)).setToolTipText("Niste uneli adresu!");
				sveOk = false;
			}
			if (((JTextField)dataPanel.getComponent(29)).getText().equals(""))
			{
				((JLabel)dataPanel.getComponent(28)).setForeground(Color.RED);
				((JTextField)dataPanel.getComponent(29)).setToolTipText("Niste uneli telefon!");
				sveOk = false;
			}
			
			if (proveraPib)
			{
				if (((JTextField)dataPanel.getComponent(11)).getText().equals(""))
				{
					((JLabel)dataPanel.getComponent(10)).setForeground(Color.RED);
					((JTextField)dataPanel.getComponent(11)).setToolTipText("Morate uneti pib!");
					((JLabel)dataPanel.getComponent(12)).setForeground(Color.BLACK);
					sveOk = false;
				}
				else
				{
					String tekstPolja = ((JTextField) dataPanel.getComponent(11)).getText();
					if (tekstPolja.length() != 11 && tekstPolja.length() > 0)
					{
						if (proveraPib)
						{
							((JLabel)dataPanel.getComponent(10)).setForeground(Color.RED);
							((JTextField) dataPanel.getComponent(11)).setToolTipText("Pib je broj od tacno 11 cifara!");
							sveOk = false;
						}
					}
				}
			}
			
			if (proveraJmbg)
			{
				if (((JTextField)dataPanel.getComponent(13)).getText().equals(""))
				{
					((JLabel)dataPanel.getComponent(12)).setForeground(Color.RED);
					((JTextField)dataPanel.getComponent(13)).setToolTipText("Morate uneti jmbg!");
					((JLabel)dataPanel.getComponent(10)).setForeground(Color.BLACK);
					sveOk = false;
				}
				else
				{
					String tekstPolja = ((JTextField) dataPanel.getComponent(13)).getText();
					
					if (tekstPolja.length() != 13 && tekstPolja.length() > 0)
					{
						if (proveraJmbg)
						{
							((JLabel)dataPanel.getComponent(12)).setForeground(Color.RED);
							((JTextField) dataPanel.getComponent(13)).setToolTipText("Jmbg je broj od tacno 11 cifara!");
							sveOk = false;
						}
					}
				}
			}
			
			
			if (sveOk == true)
				return true;
			else
				return false;
				
		}
		
		@Override
		public boolean checkup(){
			// Presek, racuni 
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
				int brojKolone = 1;
				if (i == 0)
					brojKolone = 0;
				else if (i == 1)
					brojKolone = 3;

				for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
					if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
							form.tblGrid.getValueAt(j, brojKolone)))
						return false;
					
				}
			}
			return true;
		}
	
	
	
	}
	

