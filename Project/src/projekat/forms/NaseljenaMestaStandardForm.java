package projekat.forms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JTextField;


import util.ColumnList;
import database.DBConnection;





public class NaseljenaMestaStandardForm extends AbstractForm  {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"Šifra mesta", "Šifra države", "Naziv države", "Naziv mesta", "PTT oznaka" }, 0, "Naseljena mesta",
			"SELECT nm_sifra, naseljeno_mesto.dr_sifra, drzava.dr_naziv, nm_naziv, nm_pttoznaka FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra WHERE ", " ORDER BY naseljeno_mesto.nm_sifra", new Integer [] {0});
	private String sqlQuery = "SELECT nm_sifra, naseljeno_mesto.dr_sifra, dr_naziv, nm_naziv, nm_pttoznaka FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra ORDER BY naseljeno_mesto.nm_sifra";
	private static String insertSql = "INSERT INTO naseljeno_mesto (nm_sifra, dr_sifra, nm_naziv, nm_pttoznaka) VALUES (? ,?, ?, ?)";
	private static String deleteSql = "DELETE FROM naseljeno_mesto WHERE nm_sifra=?";
	private static String updateSql = "UPDATE naseljeno_mesto SET nm_sifra=?, dr_sifra=?, nm_naziv=?, nm_pttoznaka=? where nm_sifra=?";
	private static String searchSql = "SELECT nm_sifra, naseljeno_mesto.dr_sifra, drzava.dr_naziv, nm_naziv, nm_pttoznaka"
			+ " FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra WHERE nm_sifra like ? or drzava.dr_sifra like ?  or drzava.dr_naziv like ? or nm_naziv like ? or nm_pttoznaka like ?";	


	
	public NaseljenaMestaStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, 
				new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[]{"klijent.nm_sifra"})),
						new ArrayList<String>(Arrays.asList(new String[]{"analitika_izvoda.nm_sifra"})),
						new ArrayList<String>(Arrays.asList(new String[]{"banka.nm_sifra"})))),
				new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))),	
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.KlijentStandardForm", 
						"projekat.forms.AnalitikaIzvodaStandardForm", "projekat.forms.BankaStandardForm"})),
				new ArrayList<String>(Arrays.asList(new String[]{"Klijent", "Analitika izvoda", "Banka"})),
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.DrzaveStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));

		// TODO Auto-generated constructor stub
		setTitle("Naseljena mesta");
		this.parentForm = parentForm;
		this.indexImena.add(3);
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		//for(int i = 0; i < 4; i++)
			tipovi.add("text");
		
		lista.add("Šifra mesta,int");
		lista.add("Šifra države");
		tipovi.add(1, "zoom");
		lista.add("Naziv države");
		tipovi.add(2, "lookup");
		lista.add("Naziv mesta");
		lista.add("PTT oznaka");
		for(int i = 3; i < 5; i++)
			tipovi.add("text");
		
		panel(5, lista, tipovi, null);
		initTable(tableModel, sqlQuery, colList);

		blokiranjeKljuceva();
		
dataPanel.getComponent(1).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
				if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(0)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(1)).setToolTipText("Niste uneli sifru mesta!");
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
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(3)).getText().trim();
				if (tekstPolja.equals(""))
				{
					//dataPanel.add(labelaIkonica, 7);
					((JLabel)dataPanel.getComponent(2)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(3)).setToolTipText("Niste odabrali drzavu!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(2)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(3)).setToolTipText(null);
			}
		});
		
		//dodavanje keyListenera za zoom // mozda treba promeniti
		final AbstractForm activeForm = this;
		sifreTextFields.get(0).addKeyListener(new KeyAdapter() {
					String otkucanaSifra = "";
	                public void keyTyped(KeyEvent e){
	                	if(e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
	                		otkucanaSifra += e.getKeyChar();
	                    String stmt = "SELECT drzava.dr_naziv FROM drzava WHERE dr_sifra = ?";
	                    ResultSet rs = null;
	                    PreparedStatement preparedStmt = null;
	                    try {
							preparedStmt = DBConnection.getConnection().prepareStatement(stmt);
							preparedStmt.setString(1, otkucanaSifra);
							rs = preparedStmt.executeQuery();
						   
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                    
	                    String nazivDrzave = "";
	                    try {
							if(rs.next()){
								nazivDrzave = rs.getString("dr_naziv");
								preparedStmt.close();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                    
	                    if(nazivDrzave != ""){
	                    //	activeForm.getLookupTextFields().get(0).setText(nazivDrzave);
	                    	otkucanaSifra = "";
	                    }
	                   // else
	                    //	activeForm.getLookupTextFields().get(0).setText("");
	                }
	            });		
dataPanel.getComponent(7).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(7)).getText();
				if (tekstPolja.equals(""))
				{
					//dataPanel.add(labelaIkonica, 7);
					((JLabel)dataPanel.getComponent(6)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(7)).setToolTipText("Niste uneli nazvi mesta!");
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
				if (tekstPolja.equals(""))
				{
					//dataPanel.add(labelaIkonica, 7);
					((JLabel)dataPanel.getComponent(8)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(9)).setToolTipText("Niste uneli ptt oznaku!");
				}
				else
				{
					if (tekstPolja.length() > 12)
					{
						((JLabel)dataPanel.getComponent(8)).setForeground(Color.RED);
						((JTextField) dataPanel.getComponent(9)).setToolTipText("Ptt oznaka sadrzi najvise 12 karaktera!");
					}
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(8)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(9)).setToolTipText(null);
			}
		});
	
	}



	@Override
	public boolean checkup() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean cekiranje() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
