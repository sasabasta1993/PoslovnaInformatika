package projekat.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;


import util.ColumnList;




public class AnalitikaIzvodaStandardForm   extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"ID racuna", "Broj izvoda", "Broj stavke", "Oznaka vrste", "Naziv vrste", "Šifra mesta", "Naziv mesta", "ID valute", "Naziv valute", 
			"Dužnik", "Svrha placanja", "Poverilac", "Datum naloga", "Datum valute", "Racun dužnika",
			"Model zaduženja", "Poziv na broj zaduženja", "Racun poverioca", "Model odobrenja",
			"Poziv na broj odobrenja", "Hitno", "Iznos", "Tip greške", "Status" }, 0, "Analitika izvoda",
			
			" SELECT analitika_izvoda.id_racuna, analitika_izvoda.dsr_izvod, asi_brojstavke, analitika_izvoda.vpl_oznaka, vrste_placanja.vpl_naziv, "
			
			+ "  analitika_izvoda.nm_sifra, naseljeno_mesto.nm_naziv,  "
			+ " analitika_izvoda.va_id, valute.va_naziv_val, asi_duznik, asi_svrha, asi_poverilac, asi_datpri, asi_datval, asi_racduz,"
			+ " asi_modzad, asi_pbzad, asi_racpov, asi_mododob, asi_pbodo, asi_hitno, asi_iznos,"
			+ " asi_tipgreske, asi_status FROM analitika_izvoda "
			+ " JOIN"
			+ " naseljeno_mesto ON analitika_izvoda.nm_sifra = naseljeno_mesto.nm_sifra JOIN"
			+ " valute ON analitika_izvoda.va_id = valute.va_id JOIN"
			+ " vrste_placanja ON analitika_izvoda.vpl_oznaka = vrste_placanja.vpl_oznaka WHERE", "ORDER BY analitika_izvoda.id_racuna, analitika_izvoda.dsr_izvod"
					+ ", asi_brojstavke", new Integer [] {0,1,2});

	
	
	
	private String sqlQuery = 			"SELECT analitika_izvoda.id_racuna, analitika_izvoda.dsr_izvod, asi_brojstavke, analitika_izvoda.vpl_oznaka, vrste_placanja.vpl_naziv, "
			
			+ "  analitika_izvoda.nm_sifra, naseljeno_mesto.nm_naziv,  "
			+ " analitika_izvoda.va_id, valute.va_naziv_val, asi_duznik, asi_svrha, asi_poverilac, asi_datpri, asi_datval, asi_racduz,"
			+ " asi_modzad, asi_pbzad, asi_racpov, asi_mododob, asi_pbodo, asi_hitno, asi_iznos,"
			+ " asi_tipgreske, asi_status FROM analitika_izvoda "
			+ " JOIN"
			+ " naseljeno_mesto ON analitika_izvoda.nm_sifra = naseljeno_mesto.nm_sifra JOIN"
			+ " valute ON analitika_izvoda.va_id = valute.va_id JOIN"
			+ " vrste_placanja ON analitika_izvoda.vpl_oznaka = vrste_placanja.vpl_oznaka"+ "  ORDER BY analitika_izvoda.id_racuna, analitika_izvoda.dsr_izvod"
					+ ", asi_brojstavke";
			
			
	private static String insertSql = "INSERT INTO analitika_izvoda (id_racuna, dsr_izvod, asi_brojstavke, vpl_oznaka, nm_sifra,"
			+ " va_id, asi_duznik, asi_svrha, asi_poverilac, asi_datpri, asi_datval, asi_racduz,"
			+ "asi_modzad, asi_pbzad, asi_racpov, asi_mododob, asi_pbodo, asi_hitno, asi_iznos,"
			+ "asi_tipgreske, asi_status) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String deleteSql = "DELETE FROM analitika_izvoda WHERE  asi_brojstavke=?";//id_racuna=? or dsr_izvod=? or
	private static String updateSql = "UPDATE analitika_izvoda SET id_racuna=?, dsr_izvod=?, asi_brojstavke=?,"
			+ " vpl_oznaka=?, nm_sifra=?,"
			+ " va_id=?, asi_duznik=?, asi_svrha=?, asi_poverilac=?, asi_datpri=?, asi_datval=?, asi_racduz=?,"
			+ " asi_modzad=?, asi_pbzad=?, asi_racpov=?, asi_mododob=?, asi_pbodo=?, asi_hitno=?, asi_iznos=?,"
			+ " asi_tipgreske=?, asi_status=?";
	private static String searchSql = "SELECT * FROM analitika_izvoda JOIN dnevno_stanje_racuna on analitika_izvoda.dsr_izvod = dnevno_stanje_racuna.dsr_izvod JOIN"
			+ " naseljeno_mesto on analitika_izvoda.nm_sifra = naseljeno_mesto.nm_sifra JOIN"
			+ " valute on analitika_izvoda.va_id = valute.va_id JOIN"
			+ " vrste_placanja on analitika_izvoda.vpl_oznaka = vrste_placanja.vpl_oznaka WHERE id_racuna like ?, dnevno_stanje_racuna.dsr_izvod like ?,"
			+ " asi_brojstavke like ?, vrste_placanja.vpl_oznaka like ?, naseljeno_mesto.nm_sifra like ?,"
			+ " valute.va_id like ?, asi_duznik like ?, asi_svrha like ?, asi_poverilac like ?, asi_datpri like ?,"
			+ " asi_datval like ?, asi_racduz like ?,"
			+ " asi_modzad like ?, asi_pbzad like ?, asi_racpov like ?, asi_mododob like ?, asi_pbodo like ?,"
			+ " asi_hitno like ?, asi_iznos like ?,"
			+ " asi_tipgreske like ?, asi_status like ?";
	public AnalitikaIzvodaStandardForm(ColumnList colList, AbstractForm parent) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql,
				new ArrayList<ArrayList<String>>(Arrays.asList(
						new ArrayList<String>(Arrays.asList(new String[]{"stavka_clearing_rtgs.id_racuna",
								"stavka_clearing_rtgs.dsr_izvod", "stavka_clearing_rtgs.asi_brojstavke"})),
						new ArrayList<String>(Arrays.asList(new String[]{"analitika_preseka.id_racuna",
								"analitika_preseka.dsr_izvod", "analitika_preseka.asi_brojstavke"})))),
				new ArrayList<ArrayList<Integer>>(Arrays.asList(
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0,1,2})),
						new ArrayList<Integer>(Arrays.asList(new Integer[]{0,1,2})))),	
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.StavkaClearingRtgsStandardForm",
						"projekat.forms.AnalitikaPresekaStandardForm"})),
				new ArrayList<String>(Arrays.asList(new String[]{"Stavka clearinga/rtgs", "Analitika preseka"})),
				new ArrayList<String>(Arrays.asList(new String[]{"projekat.forms.DnevnoStanjeRacunaStandardForm",
				"projekat.forms.VrstaPlacanjaStandardForm", "projekat.forms.NaseljenaMestaStandardForm", "projekat.forms.ValutaStandardForm"})), new ArrayList<Integer>(Arrays.asList(new Integer[]{1,3,4,6})));
		setSize(1920, 800);
		setLocationRelativeTo(parent);
		this.parentForm = parent;
		ArrayList<String> tipovi = new ArrayList<String>();
		
		for(int i = 0; i < 1; i++)
			tipovi.add("text");
		
		setTitle("Analitika izvoda");
	
		HashMap<String, ArrayList<String>> listaComboVrednosti = new HashMap<String, ArrayList<String>>();
		ArrayList<String> lista = new ArrayList<String>();
		
		lista.add("ID racuna");
		lista.add("Broj izvoda,int");
		tipovi.add(1, "doubleZoom");
		lista.add("Broj stavke,int");
		tipovi.add(2, "text");
		lista.add("Oznaka vrste");
		tipovi.add("zoom");
		lista.add("naziv vrste placanja");
		tipovi.add("lookup")  ;
		
		lista.add("Šifra mesta,int");
		tipovi.add("zoom");
		lista.add("naziv mesta");
		tipovi.add("lookup")  ;
		
		
		
		lista.add("ID valute,int");
		tipovi.add("zoom");
		lista.add("naziv valute");
		tipovi.add("lookup")  ;
		
		
		
		lista.add("Dužnik");
		lista.add("Svrha placanja");
		lista.add("Poverilac");
		lista.add("Datum naloga,date"); //datum tip
		lista.add("Datum valute,date"); //datum tip
		lista.add("Racun duznika");
		lista.add("Model zaduzenja,int");
		lista.add("Poziv na broj zaduženja");
		lista.add("Racun poverioca");
		lista.add("Model odobrenja,int");
		lista.add("Poziv na broj odobrenja");
		lista.add("Hitno, bool");
		lista.add("iznos");
		lista.add("tip greske,int");
		lista.add("Status");
		
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		tipovi.add( "text");
		
		tipovi.add("check");
		//lista.add("Iznos,double");
		//lista.add("Tip greške,int");
		
		for(int i = 18; i < 20; i++)
			tipovi.add("text");
		
		//lista.add("Status");
		listaComboVrednosti.put("Status", new ArrayList<String>(Arrays.asList(new String[]{"p", "n"})));
		tipovi.add( "combo");
		
		
		panel(lista.size(), lista, tipovi, listaComboVrednosti);
	

		initTable(tableModel, sqlQuery, colList);
		JButton btnKliring = new JButton("Clearing");
		dataPanel.add(btnKliring);
		btnKliring.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//app.MainFrame.getInstance().clearing();
				
			}});
	
		
		blokiranjeKljuceva();
		
	}
 /*
	@Override
	public void addRow(String insertSql) {

		int brojacTf = 0; //U NAZIVE TEXTFIELDA DODATI I TIPOVE, ZAOBICI LOOKUP POLJA (moze mozda ako je disabled??)
    	ArrayList<Object> listaZaSQL = new ArrayList<Object>();
    	
    	for (int i=0; i<dataPanel.getComponentCount(); ++i)
    	{
    		if (dataPanel.getComponent(i) instanceof JTextField)
    		{
    			if(((JTextField)dataPanel.getComponent(i)).isEditable()){ //zaobilazak lookup polja (iskusno, by banez :D)
    				brojacTf++;
    				String tfName = ((JTextField)dataPanel.getComponent(i)).getName();
    				if(tfName.contains(",")){ //provera da li su tekstualna polja numericka
    					String [] nameParts = tfName.split(",");
    					if(nameParts[1].equals("int")){
    						//Integer numericVal = Integer.parseInt(((JTextField)dataPanel.getComponent(i)).getText().trim());
    						BigDecimal numericVal = new BigDecimal(((JTextField)dataPanel.getComponent(i)).getText().trim());
    						listaZaSQL.add(numericVal);
    					} else if (nameParts[1].equals("double")){
    					//	Double numericVal = Double.parseDouble(((JTextField)dataPanel.getComponent(i)).getText().trim());
    						BigDecimal numericVal = new BigDecimal(((JTextField)dataPanel.getComponent(i)).getText().trim());
    						listaZaSQL.add(numericVal);
    					} else if (nameParts[1].equals("date")){
    						String pattern = "yyyy-MM-dd";
    					    SimpleDateFormat format = new SimpleDateFormat(pattern);
    					    java.util.Date parsed = null;
    					    try {
    					      //(((JTextField)dataPanel.getComponent(i)).getText().trim());
    					      parsed = format.parse(((JTextField)dataPanel.getComponent(i)).getText().trim());
    					      java.sql.Date date = new java.sql.Date(parsed.getTime());
    					      listaZaSQL.add(date);
    					    } catch (ParseException e) {
    					      e.printStackTrace();
    					    }
    						
    					}
    					else if (nameParts[1].equals("racun") || nameParts[1].equals("broj"))
    					{
    						listaZaSQL.add(((JTextField)dataPanel.getComponent(i)).getText().toString());
    					}
    					else if (nameParts[1].equals("bool"))
    					{
    						listaZaSQL.add(((JCheckBox)dataPanel.getComponent(i)).isSelected());
    					}
    				} else {
    					listaZaSQL.add(((JTextField)dataPanel.getComponent(i)).getText().toString());
    				}
        			
    			}
    			
    		} else if (dataPanel.getComponent(i) instanceof JCheckBox)
    		{
    			brojacTf++;
    			listaZaSQL.add(((JCheckBox)dataPanel.getComponent(i)).isSelected());
    		} else if (dataPanel.getComponent(i) instanceof JComboBox<?>){
    			brojacTf++;
    			listaZaSQL.add(((JComboBox<String>)dataPanel.getComponent(i)).getSelectedItem().toString());
    		}
    	}
    	
		Object [] nizZaSQL = new Object [brojacTf];
		
		for (int j=0; j<brojacTf; ++j)
		{
			if(listaZaSQL.get(j) instanceof String)
				nizZaSQL[j] = listaZaSQL.get(j).toString().trim();
			else if(listaZaSQL.get(j) instanceof Boolean)
				nizZaSQL[j] = (Boolean)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof BigDecimal)
				nizZaSQL[j] = (BigDecimal)listaZaSQL.get(j);
		//	else if(listaZaSQL.get(j) instanceof Double)
			//	nizZaSQL[j] = (Double)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof Date)
				nizZaSQL[j] = (Date)listaZaSQL.get(j);
		}
		
	
	//	"Broj raèuna", "Broj izvoda", "Broj stavke", "Oznaka vrste", "Šifra mesta", "Šifra valute", 
	//	"Dužnik", "Svrha plaæanja", "Poverilac", "Datum naloga", "Datum valute", "Raèun dužnika",
	//	"Model zaduženja", "Poziv na broj zaduženja", "Raèun poverioca", "Model odobrenja",
	//	"Poziv na broj odobrenja", "Hitno", "Iznos", "Tip greške", "Status"
		
		
		if(nizZaSQL[0].toString().substring(0, 3).equals("215")){
			dodavanjeAnalitikeIzvoda((String)nizZaSQL[0], null, null, (String)nizZaSQL[3], Long.parseLong(String.valueOf(nizZaSQL[4])), (String)nizZaSQL[5], (String)nizZaSQL[6], (String)nizZaSQL[7], (String)nizZaSQL[8], (java.sql.Date)nizZaSQL[9], (java.sql.Date)nizZaSQL[10], (String)nizZaSQL[11], (BigDecimal)(nizZaSQL[12]), (String)nizZaSQL[13], (String)nizZaSQL[14], (BigDecimal)nizZaSQL[15], (String)nizZaSQL[16], (Boolean)nizZaSQL[17], (BigDecimal)nizZaSQL[18], (BigDecimal)nizZaSQL[19], (String)nizZaSQL[20], true);
		}
		if(nizZaSQL[14].toString().substring(0, 3).equals("215")){
			dodavanjeAnalitikeIzvoda((String)nizZaSQL[14], null, null, (String)nizZaSQL[3], Long.parseLong(String.valueOf(nizZaSQL[4])), (String)nizZaSQL[5], (String)nizZaSQL[6], (String)nizZaSQL[7], (String)nizZaSQL[8], (java.sql.Date)nizZaSQL[9], (java.sql.Date)nizZaSQL[10], (String)nizZaSQL[11], (BigDecimal)(nizZaSQL[12]), (String)nizZaSQL[13], (String)nizZaSQL[14], (BigDecimal)nizZaSQL[15], (String)nizZaSQL[16], (Boolean)nizZaSQL[17], (BigDecimal)nizZaSQL[18], (BigDecimal)nizZaSQL[19], (String)nizZaSQL[20], false);

		}
		
	}	
	*/
	
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
			int brojKolone = 2;
			if(i == 1)
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
