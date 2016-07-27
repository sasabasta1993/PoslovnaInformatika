package projekat.forms;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;
import database.DBConnection;

public class GenericTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String tableName = "";
	private String whereSql;
	private String orderSql;
	private Integer [] listaIndeksaClear;
	
	
	public GenericTableModel(Object[] colNames, int rowCount, String tableName, String whereSql, String orderSql, Integer [] listaIndeksaClear) {
		super(colNames, rowCount);
		this.tableName = tableName;
		this.whereSql = whereSql;
		this.orderSql = orderSql;
		this.listaIndeksaClear = listaIndeksaClear;
	}
	
	public void open(String sqlQuery) throws SQLException {
	    fillData(sqlQuery);
	  }
	
	public void refresh() throws SQLException{
		String sqlPart1 = whereSql.substring(0, whereSql.indexOf("WHERE"));
		fillData(sqlPart1 + orderSql);
		
		
	}
	
	
	public void openAsChildForm(String where) throws SQLException{
		
		String sql = "";
		
		sql = whereSql + where + orderSql;
		fillData(sql);
	}
	
	
	public void deleteRow(int index, String deleteSql) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(deleteSql);
	    
	    for (int i=0; i<listaIndeksaClear.length; ++i)
	    {
	    	String kljuc = (String)getValueAt(index, listaIndeksaClear[i]);
		    stmt.setString(i+1, kljuc); //proveriti da li ce raditi za sve, mozda ce morati setInt, itd..
	    }
	    //stmt.setString(1, kljuc);
	    //Brisanje iz baze 
	    int rowsAffected = stmt.executeUpdate();
	    stmt.close();
	    DBConnection.getConnection().commit();
	    if (rowsAffected > 0) {
	    
	      removeRow(index);
		  fireTableDataChanged();
	    }
	  }
	
	
	public void updateRow(Object [] nizZaSQL, String kljuc, int index, String updateSql) throws SQLException {
		
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(updateSql);

		for (int i=0; i<nizZaSQL.length; ++i){
		    	if(nizZaSQL[i] instanceof String)
		    		stmt.setString(i+1, nizZaSQL[i].toString());
		    	else if(nizZaSQL[i] instanceof Boolean)
		    		stmt.setBoolean(i+1, (Boolean) nizZaSQL[i]);
		    	else if(nizZaSQL[i] instanceof Integer)
		    		stmt.setInt(i+1, (Integer) nizZaSQL[i]);
		    	else if(nizZaSQL[i] instanceof Double)
		    		stmt.setDouble(i+1, (Double) nizZaSQL[i]);
		    	
		 }
			
		
	    stmt.setString(nizZaSQL.length+1, kljuc);
	    
	    int rowsAffected = stmt.executeUpdate();
	    stmt.close();
	    DBConnection.getConnection().commit();
	    
	    if (rowsAffected > 0) {
		      /*setValueAt(sviTf[0], index, 0);
		      setValueAt(sviTf[1], index, 1);*/
	    	
	    	for (int i=0; i<nizZaSQL.length; ++i)
			{
	    		setValueAt(nizZaSQL[i], index, i);
			}
	    	
			  fireTableDataChanged();
		    }
	    
	}
	
	public void findRow(Object [] nizZaSQL, String searchSql) throws SQLException
	{
		
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(searchSql);
		for (int i=0; i<nizZaSQL.length; ++i)
	    {
	    	if(nizZaSQL[i] instanceof String)
	    		stmt.setString(i+1, '%' +nizZaSQL[i].toString() + '%');
	    	else if(nizZaSQL[i] instanceof Boolean)
	    		stmt.setBoolean(i+1, (Boolean) nizZaSQL[i]);
	    	else if(nizZaSQL[i] instanceof Integer)
	    		stmt.setInt(i+1, (Integer) nizZaSQL[i]);
	    	else if(nizZaSQL[i] instanceof Double)
	    		stmt.setDouble(i+1, (Double) nizZaSQL[i]);
	    	
	    }

		ResultSet rs = stmt.executeQuery();
		setRowCount(0);
		
		while (rs.next())
		{
			Vector<Object> row = new Vector<Object>();
			
			for (int i=0; i<nizZaSQL.length; ++i)
			{
				if(nizZaSQL[i] instanceof String)
					row.add(rs.getString(i+1));
		    	else if(nizZaSQL[i] instanceof Boolean)
		    		row.add(rs.getBoolean(i+1));
		    	else if(nizZaSQL[i] instanceof Integer)
		    		row.add(rs.getInt(i+1));
		    	else if(nizZaSQL[i] instanceof Double)
		    		row.add(rs.getDouble(i+1));
			}
			addRow(row);
		}
		
	    stmt.close();
	    DBConnection.getConnection().commit();
	    
	  
		
	}
	
	
	public int insertRow(Object [] nizZaSQL, String insertSql) throws SQLException {
	    int retVal = 0;
	    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(insertSql);

	    for (int i=0; i<nizZaSQL.length; ++i)
	    {
	    	if(nizZaSQL[i] instanceof String)
	    		stmt.setString(i+1, nizZaSQL[i].toString());
	    	else if(nizZaSQL[i] instanceof Boolean)
	    		stmt.setBoolean(i+1, (Boolean) nizZaSQL[i]);
	    	else if(nizZaSQL[i] instanceof Integer)
	    		stmt.setInt(i+1, (Integer) nizZaSQL[i]);
	    	else if(nizZaSQL[i] instanceof Double)
	    		stmt.setDouble(i+1, (Double) nizZaSQL[i]);
	    	else if(nizZaSQL[i] instanceof Date)
	    		stmt.setDate(i+1, (java.sql.Date) nizZaSQL[i]);
	    }
	    int rowsAffected = stmt.executeUpdate();
	    stmt.close();
	    //Unos sloga u bazu
	    DBConnection.getConnection().commit();
	    if (rowsAffected > 0) {
	      // i unos u TableModel  
	      retVal = sortedInsert(nizZaSQL);
	      fireTableDataChanged();
	    }
	    return retVal;
	  }
	
	
	private int sortedInsert(Object [] nizZaSQL) { 
	    int left = 0;
	    int right = getRowCount() - 1;   
	    int mid = (left + right) / 2;
	    boolean areEqual = false;
	    
	    while (left <= right ) {      
	      mid = (left + right) / 2;
	      
	      if(nizZaSQL[0] instanceof String){
				String aSifra = (String) getValueAt(mid, 0);

				if (SortUtils.getLatCyrCollator().compare((String) nizZaSQL[0],
						aSifra) > 0)
					left = mid + 1;
				else if (SortUtils.getLatCyrCollator().compare(
						(String) nizZaSQL[0], aSifra) < 0)
					right = mid - 1;
				else
					// ako su jednaki: to ne moze da se desi ako tabela ima
					// primarni kljuc
					areEqual = true;
			} else if (nizZaSQL[0] instanceof Integer){
				Integer aSifra = Integer.parseInt(getValueAt(mid, 0).toString());

				if ((Integer) nizZaSQL[0] > aSifra)
					left = mid + 1;
				else if ((Integer) nizZaSQL[0] < aSifra)
					right = mid - 1;
				else
					// ako su jednaki: to ne moze da se desi ako tabela ima
					// primarni kljuc
					areEqual = true;
			}
	      
	      if(areEqual)
	    	  break;
	    }
	    insertRow(left, nizZaSQL);
	    return left;
	  }
	

	
	private void fillData(String sqlQuery) throws SQLException {
		setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sqlQuery);
	    while (rset.next()) {
	      
	      String [] kolone = new String [rset.getMetaData().getColumnCount()];
	      for (int i=0; i<rset.getMetaData().getColumnCount(); ++i)
	      {
	    	  kolone[i] = rset.getString(rset.getMetaData().getColumnName(i+1));
	    	  
	      }
	      addRow(kolone);
	    }
	    rset.close();
	    stmt.close();
	    fireTableDataChanged();    
	  }  
	
	
	
}
