package projekat.forms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JTextField;

import util.ColumnList;

public class KursnaListaStandardForm  extends AbstractForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean datumOk = true;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"ID banke", "Naziv banke", "ID kursne liste", "Broj kursne liste", "Primenjuje se od", "Datum"}, 0, "Kursna lista",
			"SELECT kursna_lista.idbanke, banka.naziv_ba, kl_id, kl_broj, kl_datpr, datum FROM kursna_lista JOIN banka on"
			+ " kursna_lista.idbanke = banka.idbanke WHERE ", " ORDER BY kursna_lista.idbanke, datum", new Integer [] {0,2});
	
	private String sqlQuery = "SELECT kursna_lista.idbanke, banka.naziv_ba, kl_id, kl_broj, kl_datpr, datum FROM kursna_lista JOIN banka on"
			+ " kursna_lista.idbanke = banka.idbanke ORDER BY kursna_lista.idbanke, datum";
	private static String insertSql = "INSERT INTO kursna_lista (idbanke, kl_id, kl_broj, kl_datpr, datum) VALUES (? ,?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM kursna_lista WHERE  idbanke=? and kl_id=?";		        //idbanke=? and datum
	private static String updateSql = "UPDATE kursna_lista SET idbanke =?, kl_id =?, kl_broj =?, kl_datpr =?, datum =? where  idbanke=? ";
	private static String searchSql = "SELECT kursna_lista.idbanke, banka.naziv_ba, kl_id, kl_broj, kl_datpr, datum FROM kursna_lista JOIN banka ON"
			+ " kursna_lista.idbanke = banka.idbanke WHERE kursna_lista.idbanke like ? or kl_id like ? or kl_broj like ? or kl_datpr like ? or datum like ?";
			

	
	public KursnaListaStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, 
				new ArrayList<ArrayList<String>>(Arrays.asList(
				new ArrayList<String>(Arrays.asList(new String[]{"kurs_u_valuti.idbanke", "kurs_u_valuti.kl_id"})))), //ba_idbanke ??
		new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<Integer>(Arrays.asList(new Integer[]{0,2})))),	
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.KursUValutiStandardForm"})),
		new ArrayList<String>(Arrays.asList(new String[]{"Kurs u valuti"})),
		new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.BankaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1,5})));

		setTitle("Kursna lista");
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		
		tipovi.add(0, "zoom");
		lista.add("ID banke,int");
		lista.add("naziv banke");
		tipovi.add("lookup");
		lista.add("ID kursne liste,int");
		tipovi.add("text");
		
		lista.add("Broj kursne liste,int");
		
		lista.add("Primenjuje se od");
		lista.add("Datum");

		for(int i = 1; i < 4; i++)
			tipovi.add("text");
		
		panel(lista.size(), lista, tipovi, null);
		
		initTable(tableModel, sqlQuery, colList);
		blokiranjeKljuceva();
		
		
		
		final JLabel lblSifra = new JLabel("Broj kursne liste mora imati najvise 3 karaktera.");
		lblSifra.setForeground(Color.RED);
		lblSifra.setVisible(false);
		//dataPanel.add(lblSifra, "pushx", 6);
				
		
		
dataPanel.getComponent(1).addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			
				String tekstPolja = ((JTextField) dataPanel.getComponent(1)).getText();
				if (tekstPolja.equals(""))
				{
					((JLabel)dataPanel.getComponent(0)).setForeground(Color.RED);
					((JTextField) dataPanel.getComponent(1)).setToolTipText("Morate uneti ID banke!");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				((JLabel)dataPanel.getComponent(0)).setForeground(Color.BLACK);
				((JTextField) dataPanel.getComponent(1)).setToolTipText(null);
			}
		});
		





dataPanel.getComponent(5).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(5)).getText();
		
		 if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(4)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(5)).setToolTipText("Morate uneti ID kursne liste!");
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(4)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(5)).setToolTipText(null);
	}
});	


dataPanel.getComponent(7).addFocusListener(new FocusListener() {
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	
		String tekstPolja = ((JTextField) dataPanel.getComponent(7)).getText();
					if (tekstPolja.length() > 3) {
			
						lblSifra.setVisible(true);
		}
		
		else if (tekstPolja.equals(""))
		{
			((JLabel)dataPanel.getComponent(6)).setForeground(Color.RED);
			((JTextField) dataPanel.getComponent(7)).setToolTipText("Niste uneli broj kursne liste");
			lblSifra.setVisible(false);
		}
		else {
			
			lblSifra.setVisible(false);
		}
	}
	
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		((JLabel)dataPanel.getComponent(6)).setForeground(Color.BLACK);
		((JTextField) dataPanel.getComponent(7)).setToolTipText(null);
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
		

		
		if (sveOk && datumOk)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkup(){
		// kurs u valuti
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

			for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
				if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
						form.tblGrid.getValueAt(j, brojKolone)))
					return false;

			}
		}
		return true;
	}

}
