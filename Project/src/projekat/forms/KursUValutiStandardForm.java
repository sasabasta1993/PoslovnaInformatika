package projekat.forms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JTextField;

import util.ColumnList;



public class KursUValutiStandardForm  extends AbstractForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GenericTableModel tableModel = new GenericTableModel(new String[] { //sifra kursa????
			"ID banke", "ID kursne liste", "Broj kursne liste", "Redni broj", "ID valute 1", "Naziv valute 1", "ID valute 2", "Naziv valute 2", "Kupovni kurs", "Srednji kurs", "Prodajni kurs"}, 0, "Kurs u valuti",
			 "SELECT kurs_u_valuti.idbanke, kurs_u_valuti.kl_id, kursna_lista.kl_broj, kls_rbr, kurs_u_valuti.va_id, v1.va_naziv_val, kurs_u_valuti.val_va_id, v2.va_naziv_val, kls_kupovni, kls_srednji, kls_prodajni FROM kurs_u_valuti "
					+ " JOIN valute v1 ON kurs_u_valuti.va_id = v1.va_id JOIN valute v2 ON kurs_u_valuti.val_va_id = v2.va_id JOIN kursna_lista ON kurs_u_valuti.kl_id = kursna_lista.kl_id WHERE ",// bez v2.val_va_id
			"ORDER BY idbanke, kls_rbr", new Integer [] {0,1,2});
	
	
	// kursuvaluti.idbanke????
	private String sqlQuery = "SELECT kurs_u_valuti.idbanke, kurs_u_valuti.kl_id, kursna_lista.kl_broj, kls_rbr, kurs_u_valuti.va_id, v1.va_naziv_val, kurs_u_valuti.val_va_id,v2.va_naziv_val, kls_kupovni, kls_srednji, kls_prodajni FROM kurs_u_valuti "
			+ " JOIN valute v1 ON kurs_u_valuti.va_id = v1.va_id JOIN valute v2 ON kurs_u_valuti.val_va_id = v2.va_id JOIN kursna_lista ON kurs_u_valuti.kl_id = kursna_lista.kl_id "+
			"ORDER BY idbanke, kls_rbr";
	private static String insertSql = "INSERT INTO kurs_u_valuti (idbanke, kl_id, kls_rbr, va_id, val_va_id, kls_kupovni, kls_srednji, kls_prodajni) VALUES (? ,?, ?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM kurs_u_valuti WHERE kls_rbr=?";
	private static String updateSql = "UPDATE kurs_u_valuti SET idbanke=?, kl_id=?, kls_rbr=?, va_id=?, val_va_id=?, kls_kupovni=?, kls_srednji=?, kls_prodajni=? where idbanke=? and kl_id=? and kls_rbr=?"; // ba_idbanke ??? kursnalistastandardform
	private static String searchSql = "SELECT * FROM kurs_u_valuti WHERE idbanke like ? or kl_id like ? or kls_rbr like ? or va_id like ? or val_va_id like ? or kls_kupovni like ? or kls_srednji like ? or kls_prodajni like ?";
	
	public KursUValutiStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, 
				/*new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[]{"presek.kl_idklijent"})),
						new ArrayList<String>(Arrays.asList(new String[]{"racuni_pravnih_lica.kl_idklijent"}))))*/null,
				/*new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})),
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0}))))*/null,	
				/*new ArrayList<String>(Arrays.asList(new String[]{"projekat.gui.form.PresekStandardForm", 
						"projekat.gui.form.RacuniPravnihLicaStandardForm"}))*/null,
				/*new ArrayList<String>(Arrays.asList(new String[]{"Presek", "Racuni"}))*/null,
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.KursnaListaStandardForm", 
				"projekat.forms.ValutaStandardForm", "projekat.forms.ValutaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1,3,6})));

		setTitle("Kurs u valuti");
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		
		tipovi.add("text");
		
		btnNextForm.setEnabled(false);
		
		lista.add("ID banke");
		//////////////////////////////////
		lista.add("ID kursne liste");
		tipovi.add("zoom");
		
		//////////////////////////////
		lista.add("Broj kursne liste");
		tipovi.add("lookup");
		////////////////////////////////////
		lista.add("Redni broj kursa,int");
		tipovi.add("text");
		////////////////////////////////
		lista.add("ID valute 1,int");
		tipovi.add("zoom");
		lista.add("Naziv valute 1");
		tipovi.add("lookup");
		lista.add("ID valute 2,int");
		tipovi.add("zoom");
		
		lista.add("Naziv valute 2");
		
		tipovi.add("lookup");
		lista.add("Kupovni kurs");
		lista.add("Srednji kurs");
		lista.add("Prodajni kurs");
		
		for(int i = 4; i < 7; i++)
			tipovi.add("text");
		panel(lista.size(), lista, tipovi, null);
		
		initTable(tableModel, sqlQuery, colList);
		
		blokiranjeKljuceva();
/*		
dataPanel.getComponent(5).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(6)).getText();
				if (tekstPolja.length() > 2 && tekstPolja.length() > 0)
				{
					//dataPanel.add(labelaIkonica, 7);
					((JLabel)dataPanel.getComponent(5)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(6)).setToolTipText("Redni broj je manji od 2 karaktera!");
				}
				else if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(5)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(6)).setToolTipText("Morate uneti redni broj");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(0)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(1)).setToolTipText(null);
			}
		});
		
		dataPanel.getComponent(8).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(8)).getText();
				if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(7)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(8)).setToolTipText("Morate uneti naziv vrste placanja!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(7)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(8)).setToolTipText(null);
			}
		});
		
dataPanel.getComponent(12).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(12)).getText();
				if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(11)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(12)).setToolTipText("Morate uneti valute!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(11)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(12)).setToolTipText(null);
			}
		});

dataPanel.getComponent(16).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(16)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(15)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(16)).setToolTipText("Morate uneti naziv valute!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(15)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(16)).setToolTipText(null);
	}
});

dataPanel.getComponent(18).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(18)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(17)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(18)).setToolTipText("Morate uneti naziv vrste placanja!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(17)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(18)).setToolTipText(null);
	}
});

dataPanel.getComponent(20).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(20)).getText();
		if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(19)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(20)).setToolTipText("Morate uneti naziv vrste placanja!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(19)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(20)).setToolTipText(null);
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

		String tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
		if (tekstPolja.length() > 2 && tekstPolja.length() > 0)
		{
			//dataPanel.add(labelaIkonica, 7);
			((JLabel)dataPanel.getComponent(5)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(6)).setToolTipText("Redni broj mora biti manji od 2 karaktera!");
			sveOk = false;
		}
		
		
		if (sveOk)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkup(){
		return true;
	}



}
