package projekat.forms;






import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import util.ColumnList;



public class ZaglavljeClearingRtgsStandardForm  extends AbstractForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"ID poruke clearing-a", "Vrsta", "SWIFT kod banke duznika", "SWIFT kod banke poverioca",
			 "Obracunski racun banke duznika","Obracunski racun banke poverioca", "Ukupan iznos",
			"Sifra valute","Datum valute", "Datum"}, 0, "ZaglavljeRtgsClearing", 
			"SELECT id_poruke_cl, vrsta_, swift_kod_banke_duznika_cl, swift_kod_banke_poverioca_cl, obracunski_racun_banke_duznika_cl, obracunski_racun_banke_poverioca_cl, "
			+ "ukupan_iznos, sifra_valute, datum_valute, datum FROM zaglavlje_clearing_rtgs WHERE", "ORDER BY id_poruke_cl", new Integer [] {0});
	private String sqlQuery = "SELECT id_poruke_cl, vrsta_, swift_kod_banke_duznika_cl, swift_kod_banke_poverioca_cl, obracunski_racun_banke_duznika_cl, obracunski_racun_banke_poverioca_cl, "
			+ "ukupan_iznos, sifra_valute, datum_valute, datum FROM zaglavlje_clearing_rtgs ORDER BY id_poruke_cl";
	private static String insertSql = "INSERT INTO zaglavlje_clearing_rtgs (id_poruke_cl, vrsta_, swift_kod_banke_duznika_cl, "
			+ "swift_kod_banke_poverioca_cl, obracunski_racun_banke_duznika_cl, obracunski_racun_banke_poverioca_cl, ukupan_iznos, sifra_valute, datum_valute, datum) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM zaglavlje_clearing_rtgs WHERE id_poruke_cl=?";
	private static String updateSql = "UPDATE zaglavlje_clearing_rtgs SET id_poruke_cl = ?, vrsta_= ?, swift_kod_banke_duznika_cl= ?, "
			+ "swift_kod_banke_poverioca_cl= ?, obracunski_racun_banke_duznika_cl= ?, obracunski_racun_banke_poverioca_cl= ?, ukupan_iznos= ?, sifra_valute= ?, datum_valute= ?, datum= ? where id_poruke_cl=?";
	private static String searchSql = "SELECT * FROM zaglavlje_clearing_rtgs WHERE id_poruke_cl like ? or vrsta_ like ? or swift_kod_banke_duznika_cl like ? or "
			+ "swift_kod_banke_poverioca_cl like ? or obracunski_racun_banke_duznika_cl like ? or obracunski_racun_banke_poverioca_cl like ? or ukupan_iznos like ? or sifra_valute like ? or datum_valute like ? or datum like ?";

	
	
	
	public ZaglavljeClearingRtgsStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql,
				new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[]{"stavka_clearing_rtgs.id_poruke_cl"})))),
				new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0})))),	
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.StavkaClearingRtgsStandardForm"})),
				new ArrayList<String>(Arrays.asList(new String[]{"Stavka clearing/RTGS"})), null, new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));
		setTitle("Zaglavlje Clearing/RTGS");
		this.parentForm = parentForm;
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		
		for(int i = 0; i < 10; i++)
			tipovi.add("text");
		
		lista.add("ID poruke");
		lista.add("Vrsta?,bool");
		lista.add("SWIFT kod banke duznika");
		lista.add("SWIFT kod banke poverioca");
		lista.add("Obracunski racun banke duznika");
		lista.add("Obracunski racun banke poverioca");
		lista.add("Ukupan iznos,double");
		lista.add("Sifra valute");
		lista.add("Datum valute");
		lista.add("Datum");
		
		tipovi.add(1, "check");
		
		panel(lista.size(), lista, tipovi, null);
	
		initTable(tableModel, sqlQuery, null);
	/*	JButton btnKliringRtgsExport = new JButton("Export");
		dataPanel.add(btnKliringRtgsExport);
		btnKliringRtgsExport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ExportClearingRtgsAction akcija = new ExportClearingRtgsAction();
				akcija.actionPerformed(e);
				
			}});
	*/
		blokiranjeKljuceva();
	}
	
	@Override
	public boolean cekiranje()
	{
		return true;
	}
	
	
	@Override
	public boolean checkup(){
		// analitika izvoda
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

			for (int j = 0; j < form.tblGrid.getRowCount(); ++j) {
				if (tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).equals(
						form.tblGrid.getValueAt(j, brojKolone)))
					return false;

			}
		}
		return true;
	}
	

}
