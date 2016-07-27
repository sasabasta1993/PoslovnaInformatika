package projekat.forms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import util.ColumnList;



public class AnalitikaPresekaStandardForm  extends AbstractForm{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"Id klijenta", "ID racuna", "Datum", "Presek", "Redni broj promene", "ID racuna 2",
			"Broj izvoda", "Broj stavke"}, 0, "Analitika preseka",
			"SELECT id_klijenta, pre_id_racuna, bnp_datum, bnp_presek, apr_rbr,"
			+ " id_racuna, dsr_izvod, asi_brojstavke FROM analitika_preseka WHERE", "ORDER BY"
					+ " analitika_preseka.id_klijenta, analitika_preseka.pre_id_racuna,"
					+ " analitika_preseka.bnp_datum, analitika_preseka.bnp_presek, analitika_preseka.apr_rbr", new Integer [] {0,1,2,3,4});
	private String sqlQuery = "SELECT id_klijenta, pre_id_racuna, bnp_datum, bnp_presek, apr_rbr,"
			+ " id_racuna, dsr_izvod, asi_brojstavke FROM analitika_preseka"+ " ORDER BY"
					+ " analitika_preseka.id_klijenta, analitika_preseka.pre_id_racuna," 
					+ " analitika_preseka.bnp_datum, analitika_preseka.bnp_presek, analitika_preseka.apr_rbr";
	private static String insertSql = "INSERT INTO analitika_preseka (id_klijenta, pre_id_racuna, bnp_datum,"
			+ " bnp_presek, apr_rbr, id_racuna, dsr_izvod, asi_brojstavke) VALUES (? ,?, ?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM analitika_preseka WHERE id_klijenta =?, pre_id_racuna =?"
			+ ", bnp_datum =?, bnp_presek =?, apr_rbr =?";
	private static String updateSql = "UPDATE analitika_preseka SET id_klijenta =?, pre_id_racuna=?,"
			+ " bnp_datum =?, bnp_presek =?, apr_rbr =?, id_racuna =?, dsr_izvod =?, asi_brojstavke =?"
			+ " where id_klijenta=?, pre_id_racuna =?, bnp_datum =?, bnp_presek =?, apr_rbr =?";
	private static String searchSql = "SELECT * FROM     analitika_preseka WHERE id_klijenta like ?"
			+ "or pre_id_racuna like ? or bnp_datum like ? or bnp_presek like ? or apr_rbr like ? or"
			+ " id_racuna like ? or dsr_izvod like ? or asi_brojstavke like ?";

	
	public AnalitikaPresekaStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, null, null, null, null, 
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.PresekStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{2,4,6,8,11})));

		setTitle("Analitika preseka");
		this.parentForm = parentForm;
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		for(int i = 0; i < 3; i++)
			tipovi.add("text");
		
		lista.add("ID klijenta,int");
		lista.add("Broj racuna");
		lista.add("Datum,date");

		lista.add("Broj Preseka,int");
		tipovi.add(3, "doubleZoom");
		lista.add("Redni broj promene,int");
		lista.add("Izvod,int");
		lista.add("Broj stavke,int");
		
		for(int i = 4; i < 7; i++)
			tipovi.add("text");
		panel(7, lista, tipovi, null);

		
		btnNextForm.setEnabled(false);
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
