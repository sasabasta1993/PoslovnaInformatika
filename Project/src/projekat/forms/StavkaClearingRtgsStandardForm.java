package projekat.forms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


import util.ColumnList;

;

public class StavkaClearingRtgsStandardForm  extends AbstractForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"ID naloga za placanje", "ID poruke clearing-a",  
			"ID racuna", "Broj izvoda", "Broj stavke" }, 0, "RtgsClearingStavka",
			"SELECT id_naloga_za_placanje,id_poruke_cl, "
			+ "id_racuna, dsr_izvod, asi_brojstavke FROM stavka_clearing_rtgs   WHERE",
			" ORDER BY id_naloga_za_placanje", new Integer [] {0});

	private String sqlQuery = "SELECT id_naloga_za_placanje,id_poruke_cl, "
			+ "id_racuna, dsr_izvod, asi_brojstavke FROM stavka_clearing_rtgs  " +
			  
			"   ORDER BY id_naloga_za_placanje";
	private static String insertSql = "INSERT INTO stavka_clearing_rtgs (id_naloga_za_placanje, id_poruke_cl, "
			+ "id_racuna, dsr_izvod, asi_brojstavke) VALUES (? ,?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM stavka_clearing_rtgs WHERE id_naloga_za_placanje=?";
	private static String updateSql = "UPDATE stavka_clearing_rtgs SET id_naloga_za_placanje =?"
			+ ", id_poruke_cl =?, id_racuna =?, dsr_izvod =?, asi_brojstavke =? where id_naloga_za_placanje=?";
	private static String searchSql = "SELECT * FROM stavka_clearing_rtgs WHERE id_naloga_za_placanje like ? or"
			+ " id_poruke_cl like ? or id_racuna like?  or dsr_izvod like ? or asi_brojstavke like ?";
	
	
	
	
	public StavkaClearingRtgsStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, null, null, null, null, new ArrayList<String>(Arrays.asList(new String[]{
				"projekat.forms.ZaglavljeClearingRtgsStandardForm", "projekat.forms.AnalitikaIzvodaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));
		setTitle("Stavka clearinga");

		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		tipovi.add("text,zoom");
		
		lista.add("ID naloga za plaćanje");
		lista.add("ID poruke clearing-a");

		tipovi.add("doubleZoom");
		
		lista.add("ID računa");
		lista.add("Broj izvoda,int");
		lista.add("Broj stavke,int");
		
		tipovi.add("text,zoom");
		tipovi.add("text,zoom");
		tipovi.add("doubleZoom");
		btnNextForm.setEnabled(false);
		panel(lista.size(), lista, tipovi, null);
	
		initTable(tableModel, sqlQuery, colList);
		
		blokiranjeKljuceva();
	}
	

	@Override
	public boolean cekiranje()
	{
		return true;
	}
	
	@Override
	public boolean checkup(){
		return true;
	}

}
