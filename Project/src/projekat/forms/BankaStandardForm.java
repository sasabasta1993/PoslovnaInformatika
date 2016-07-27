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



public class BankaStandardForm  extends AbstractForm {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean sveOk = true;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"Id banke", "Šifra mesta", "Naziv mesta", "Sifra banke", "PIB", "Naziv banke", "Adresa banke", "Email banke", "Web banke", "Telefon banke", "Fax banke", "Banka" }, 0, "Banke",
			"SELECT IDBANKE, banka.NM_SIFRA, naseljeno_mesto.nm_naziv, sifra_banke, pib_ba, naziv_ba, adresa_ba,"
			+ " e_mail_ba, web_ba, telefon_ba, fax_ba, banka FROM banka JOIN naseljeno_mesto ON banka.nm_sifra = naseljeno_mesto.nm_sifra WHERE", "ORDER BY IDBANKE", new Integer [] {0});

	private String sqlQuery = "SELECT IDBANKE, banka.NM_SIFRA, naseljeno_mesto.nm_naziv, sifra_banke, pib_ba, naziv_ba, adresa_ba,"
			+ " e_mail_ba, web_ba, telefon_ba, fax_ba, banka FROM banka JOIN naseljeno_mesto ON banka.nm_sifra = naseljeno_mesto.nm_sifra ORDER BY IDBANKE";
	private static String insertSql = "INSERT INTO banka (IDBANKE, NM_SIFRA,sifra_banke, pib_ba, naziv_ba, adresa_ba,"
			+ "e_mail_ba, web_ba, telefon_ba, fax_ba, banka) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM banka WHERE IDBANKE=?";
	private static String updateSql = "UPDATE banka SET IDBANKE=?, NM_SIFRA=?, sifra_banke=?, pib_ba=?, naziv_ba=?, adresa_ba=?,"
			+ " e_mail_ba=?, web_ba=?, telefon_ba=?, fax_ba=?, banka=? where IDBANKE=?";
	private static String searchSql = "SELECT IDBANKE, banka.NM_SIFRA, naseljeno_mesto.nm_naziv,sifra_banke, pib_ba, naziv_ba, adresa_ba,"
			+ " e_mail_ba, web_ba, telefon_ba, fax_ba, banka FROM banka JOIN naseljeno_mesto ON banka.nm_sifra = naseljeno_mesto.nm_sifra WHERE IDBANKE like ? or naseljeno_mesto.NM_SIFRA like ? or "
			+ " sifra_banke like ? or pib_ba like ? or naziv_ba like ? or adresa_ba like ? or e_mail_ba like ? or web_ba like ? or telefon_ba like ? or fax_ba like ? or banka like ?";
	
	
	
	
	
	
	public BankaStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, 
		new ArrayList<ArrayList<String>>(Arrays.asList(
				new ArrayList<String>(Arrays.asList(new String[]{"kursna_lista.idbanke"})), // kursna_lista.ba_idbanke"
				new ArrayList<String>(Arrays.asList(new String[]{"racuni_pravnih_lica.idbanke"})))), //racuni_pravnih_lica.ba_idbanke
		new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
				new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))),	
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.KursnaListaStandardForm", 
				"projekat.forms.RacuniPravnihLicaStandardForm"})),
		new ArrayList<String>(Arrays.asList(new String[]{"Kursna lista", "Racuni"})),
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.NaseljenaMestaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));

		setTitle("Banke");
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		this.indexImena.add(4);
		this.parentForm = parentForm;
		//for(int i = 0; i < 9; i++)
			tipovi.add(0,"text");
		
		lista.add("ID banke,int");
		lista.add("Šifra mesta");
		tipovi.add(1, "zoom");
		lista.add("Naziv mesta");
		tipovi.add("lookup");
		lista.add("Sifra banke ");
		lista.add("Pib");
		lista.add("Naziv");
		lista.add("Adresa");
		lista.add("Email");
		lista.add("Web");
		lista.add("Telefon");
		lista.add("Fax");
		for(int i = 2; i < 10; i++)
			tipovi.add("text");
		
		lista.add("Banka?");
		tipovi.add("check");
		panel(lista.size(), lista, tipovi, null);
		initTable(tableModel, sqlQuery, null);
		
		blokiranjeKljuceva();
		
dataPanel.getComponent(1).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
				
				 if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(0)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(1)).setToolTipText("Morate uneti id banke!");
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
					((JTextField) dataPanel.getComponent(3)).setToolTipText("Morate uneti sifru mesta banke!");
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
				if (tekstPolja.length() > 3)
				{
					//dataPanel.add(labelaIkonica, 7);
					((JLabel)dataPanel.getComponent(6)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(7)).setToolTipText("Sifra banke je od najvise  3 karaktera!");
				}
				else if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(6)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(7)).setToolTipText("Morate uneti sifru banke!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(6)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(7)).setToolTipText(null);
			}
		});
	
	

		dataPanel.getComponent(9).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(9)).getText();
				if (tekstPolja.length() > 10) {
					
					((JLabel)dataPanel.getComponent(8)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(9)).setToolTipText("Pib banke je od najvise 10 karaktera!");
					
				}
				
				
				else if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(10)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(11)).setToolTipText("Morate uneti pib banke!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(8)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(9)).setToolTipText(null);
			}
		});

		
		
		dataPanel.getComponent(11).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(11)).getText();
				if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(10)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(11)).setToolTipText("Morate uneti naziv banke!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(10)).setForeground(Color.BLACK);
				    ((JTextField) dataPanel.getComponent(11)).setToolTipText(null);
			}
		});		
		
		
		
		
		
	dataPanel.getComponent(13).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(13)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(12)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(13)).setToolTipText("Morate uneti adresu banke!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(12)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(13)).setToolTipText(null);
	}
});

dataPanel.getComponent(15).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(15)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(14)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(15)).setToolTipText("Morate uneti email banke!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(14)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(15)).setToolTipText(null);
	}
});

dataPanel.getComponent(17).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(17)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(16)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(17)).setToolTipText("Morate uneti web adresu banke!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(16)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(17)).setToolTipText(null);
	}
});

dataPanel.getComponent(19).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(19)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(18)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(19)).setToolTipText("Morate uneti telefon banke!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(18)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(19)).setToolTipText(null);
	}
});
		
dataPanel.getComponent(21).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(21)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(20)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(21)).setToolTipText("Morate uneti fax banke!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(20)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(21)).setToolTipText(null);
	}
});
		
	}
	
	

	@Override
	public boolean cekiranje()
	{
		/*boolean sveOk1 = false;
		boolean sveOk2 = false;*/
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
		
		String tekstPolja = ((JTextField) dataPanel.getComponent(7)).getText();
		if (tekstPolja.length() != 8)
		{
			//dataPanel.add(labelaIkonica, 7);
			((JLabel)dataPanel.getComponent(6)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(7)).setToolTipText("Swift kod banke je od tacno 8 cifara!");
			sveOk = false;
		}
		
		tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
		if (tekstPolja.length() != 3 && tekstPolja.length() > 0)
		{
			//dataPanel.add(labelaIkonica, 7);
			((JLabel)dataPanel.getComponent(0)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(1)).setToolTipText("Sifra banke je od tacno 3 karaktera!");
			sveOk = false;
		}
		
		
		if (sveOk)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkup(){
		// Kursna lista, racuni pravnih lica 
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
				brojKolone = 1;
			
			for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
				if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
						form.tblGrid.getValueAt(j, brojKolone)))
					return false;
			}
			}
		
		return true;
	}
	
	
	
	
}
