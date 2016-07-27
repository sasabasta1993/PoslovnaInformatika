package projekat.forms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


import util.ColumnList;



public class UkidanjeStandardForm extends AbstractForm {

	
	private GenericTableModel tableModel = new GenericTableModel(new String[] {
			"ID racuna", "ID ukidanja", "Datum ukidanja", "Prenosi se na racun" }, 0, "Ukidanje racuna", 
			"SELECT id_racuna, id_ukidanja, uk_datukidanja, uk_naracun FROM ukidanje WHERE", "ORDER BY id_racuna", new Integer [] {0});
	private String sqlQuery = "SELECT id_racuna, id_ukidanja, uk_datukidanja, uk_naracun FROM ukidanje ORDER BY id_racuna";
	private static String insertSql = "INSERT INTO ukidanje (id_racuna, id_ukidanja, uk_datukidanja, uk_naracun) VALUES (? ,?, ?, ?)";
	private static String deleteSql = "DELETE FROM ukidanje WHERE id_racuna=? "; //id_racuna
	private static String updateSql = "UPDATE ukidanje SET id_racuna=?, id_ukidanja=?, uk_datukidanja=?, uk_naracun=? where id_racuna=? ";//id_racuna
	private static String searchSql = "SELECT * FROM ukidanje WHERE id_racuna like ? or id_ukidanja like ? or uk_datukidanja like ? or uk_naracun like ?";

	public UkidanjeStandardForm(ColumnList colList, AbstractForm parentForm) throws SQLException {
		super(insertSql, deleteSql, updateSql, searchSql, null, null, null, null, null, new ArrayList<Integer>(Arrays.asList(new Integer[]{1})));
		setTitle("Ukidanje racuna");
		
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> tipovi = new ArrayList<String>();
		btnNextForm.setEnabled(false);
		for(int i = 0; i < 4; i++)
			tipovi.add("text");
		
		lista.add("ID racuna,racun");
		lista.add("ID ukidanja,int");
		lista.add("Datum ukidanja");
		lista.add("Sredstva se prenose na racun");
		panel(lista.size(), lista, tipovi, null);
	
		initTable(tableModel, sqlQuery, colList);
		
		blokiranjeKljuceva();
		
		/*btnCommit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (mode == MODE_ADD)
				{
					//addRow(insertSql);
					//da provjerim da li ima taj racun u banci?
					//((JTextField)dataPanel.getComponent(3)).setText(colList.getColumn(0).getValue().toString());
					
					int brojacTf = 0; //U NAZIVE TEXTFIELDA DODATI I TIPOVE, ZAOBICI LOOKUP POLJA (moze mozda ako je disabled??)
			    	ArrayList<Object> listaZaSQL = new ArrayList<Object>();
			    	
			    	for (int i=0; i<dataPanel.getComponentCount(); ++i)
			    	{
			    		if (dataPanel.getComponent(i) instanceof JTextField)
			    		{
			    			if(((JTextField)dataPanel.getComponent(i)).isEditable()){ //zaobilazak lookup polja
			    				brojacTf++;
			    				String tfName = ((JTextField)dataPanel.getComponent(i)).getName();
			    				if(tfName.contains(",")){ //provera da li su tekstualna polja numericka
			    					String [] nameParts = tfName.split(",");
			    					if(nameParts[1].equals("int")){
			    						Integer numericVal = Integer.parseInt(((JTextField)dataPanel.getComponent(i)).getText().trim());
			    						listaZaSQL.add(numericVal);
			    					} else if (nameParts[1].equals("double")){
			    						Double numericVal = Double.parseDouble(((JTextField)dataPanel.getComponent(i)).getText().trim());
			    						listaZaSQL.add(numericVal);
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
						else if(listaZaSQL.get(j) instanceof Integer)
							nizZaSQL[j] = (Integer)listaZaSQL.get(j);
						else if(listaZaSQL.get(j) instanceof Double)
							nizZaSQL[j] = (Double)listaZaSQL.get(j);
					}
					
					
					
					Statement stmt;
					try {
						stmt = DBConnection.getConnection().createStatement();
						ResultSet rset = stmt.executeQuery("SELECT kl_idklijent FROM racuni_pravnih_lica WHERE ra_br_racun ='"+nizZaSQL[0]+"';");
						
						
						
						
						if (!rset.isBeforeFirst() ){
							
							
							stmt = DBConnection.getConnection().createStatement();
							stmt.executeUpdate("DELETE FROM ukidanje WHERE ra_br_racun ='"+nizZaSQL[0]+"';");
							stmt.close();
							    //Unos sloga u bazu
							DBConnection.getConnection().commit();
							GenericTableModel gtm = (GenericTableModel)tblGrid.getModel();
							gtm.fireTableDataChanged();
							
							JOptionPane.showMessageDialog(UkidanjeStandardForm.this, "Ne postoji korisnik sa tim brojem racuna","Greska", JOptionPane.ERROR_MESSAGE);
						
						}
						
						
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(UkidanjeStandardForm.this, e.getMessage(),"Greska", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				    
				
				}
				
			}
			
		});*/
		
		
		
	}
	
	private static final long serialVersionUID = -3430576818222914953L;
	
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
