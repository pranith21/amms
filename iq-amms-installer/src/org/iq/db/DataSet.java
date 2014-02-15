package org.iq.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.iq.util.string.StringUtil;

/**
 * @author Sam
 * 
 */
public class DataSet {

  private static final String DATE_TYPE = "DATE";
  private static final String TIMESTAMP_TYPE = "TIMESTAMP";
  private String clientLocale;
  private ArrayList<Column> columns;
  private ArrayList<HashMap<String, Object>> rows;

  /**
   * 
   * 
   */
  public DataSet() {
    columns = new ArrayList<Column>();
    rows = new ArrayList<HashMap<String, Object>>();
  }

  /**
   * @return the clientLocale
   */
  public String getClientLocale() {
    return clientLocale;
  }

  /**
   * @param clientLocale the clientLocale to set
   */
  public void setClientLocale(final String clientLocale) {
    this.clientLocale = clientLocale;
  }

  /**
   * 
   * @param rs
   * @throws SQLException
   */
  public DataSet(ResultSet rs) throws SQLException {
    columns = new ArrayList<Column>();
    rows = new ArrayList<HashMap<String, Object>>();

    if (rs != null) {
      ResultSetMetaData metadata;
      metadata = rs.getMetaData();
      for (int i = 1; i <= metadata.getColumnCount(); i++) {
        addColumn(metadata.getColumnLabel(i), metadata.getColumnTypeName(i));
      }
      while (rs.next()) {
        HashMap<String, Object> row = new HashMap<String, Object>();
        for (int i = 0; i < columns.size(); i++) {
          Column c = columns.get(i);
          Object value = null;
          if (c.columnType.equals(DATE_TYPE)
              || c.columnType.equals(TIMESTAMP_TYPE)) {
            value = rs.getTimestamp(c.columnName);
          }
          else {
            value = rs.getObject(c.columnName);
          }
          row.put(c.columnName, value);
        }
        rows.add(row);
      }
    }
  }

  /**
   * 
   * @author Sam
   * 
   */

  class Column {
    /**
     * column name
     */
    public String columnName;
    /**
     * column type
     */
    public String columnType;

    /**
     * 
     */
    public Column() {

    }

    /**
     * @param columnName
     * @param columnType
     */
    public Column(String columnName, String columnType) {
      this.columnName = columnName;
      this.columnType = columnType;
    }

  }

  /**
   * 
   * @param columnName
   * @param columnType
   */
  private void addColumn(String columnName, String columnType) {
    Column c = new Column(columnName, columnType);
    columns.add(c);
  }

  /**
   * 
   * @return row size
   */
  public int getRowCount() {
    return rows.size();

  }

  /**
   * 
   * @return column size
   */
  public int getColumnCount() {
    return columns.size();
  }

  /**
   * 
   * @param columnIndex
   * @return column name
   */
  public String getColumnName(int columnIndex) {
    return columns.get(columnIndex).columnName;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public Object getValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    HashMap<String, Object> row = rows.get(rowIndex);
    return row.get(columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public Object getValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    return row.get(columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public Date getDateValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    java.sql.Timestamp dt = (java.sql.Timestamp)row.get(columnName);
    Date retVal = null;
    if (dt != null) {
      retVal = new Date(dt.getTime());
    }
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public Date getDateValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getDateValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public int getIntValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    if (row.get(columnName)!=null) {
      return (Integer)row.get(columnName);
    }
    return 0;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public int getIntValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getIntValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public long getLongValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    BigDecimal val = (BigDecimal)row.get(columnName);
    long retVal = 0;
    if (val != null) {
      retVal = val.longValue();
    }
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public long getLongValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getLongValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public float getFloatValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    BigDecimal val = (BigDecimal)row.get(columnName);
    float retVal = 0.0f;
    if (val != null) {
      retVal = val.floatValue();
    }
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public float getFloatValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getFloatValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public double getDoubleValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    BigDecimal val = (BigDecimal)row.get(columnName);
    double retVal = 0.0;
    if (val != null) {
      retVal = val.doubleValue();
    }
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public double getDoubleValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getDoubleValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public String getStringValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    Object val = row.get(columnName);
    String retVal = (String)val;
    /*String retVal = null;
    if (val != null) {
      retVal = AmspUtil.convertString(val.toString(), clientLocale);
    }*/
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public String getStringValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getStringValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return retVal
   */
  public BigDecimal getBigDecimalValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    BigDecimal val = (BigDecimal)row.get(columnName);
    BigDecimal retVal = null;
    if (val != null) {
      retVal = val;
    }
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return BigDecimal values
   */
  public BigDecimal getBigDecimalValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getBigDecimalValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return boolean
   */
  public boolean getBooleanValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    BigDecimal val = (BigDecimal)row.get(columnName);
    
    boolean retVal = false;
    if (val != null) {
    	if (val.intValue() == 0){
    		retVal = false;
    	}else{
    		retVal = true;
    	}    
    }    
    return retVal;

  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return boolean 
   */
  public boolean getBooleanValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getBooleanValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return retVal
   */
  public short getShortValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    BigDecimal val = (BigDecimal)row.get(columnName);
    short retVal = 0;
    if (val != null) {
      retVal = val.shortValue();
    }
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return BigDecimal values
   */
  public short getShortValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getShortValue(rowIndex, columnName);
  }

  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return retVal
   */
  public Timestamp getTimeStampValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);
    java.sql.Timestamp timeStamp = (java.sql.Timestamp)row.get(columnName);
    Timestamp retVal = null;
    if (timeStamp != null) {
      retVal = new Timestamp(timeStamp.getTime());
    }
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return retVal
   */
  public Timestamp getTimeStampValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getTimeStampValue(rowIndex, columnName);
  }
  
  /**
   * 
   * @param rowIndex
   * @param columnName
   * @return values
   */
  public String getClobValue(int rowIndex, String columnName) {
    HashMap<String, Object> row = rows.get(rowIndex);    
    Object resultObj = row.get(columnName);
    StringBuffer ruleBuffer = new StringBuffer();
    String ruleStr = null;
    String retVal = null;

    try{
      if(resultObj instanceof Clob){
        Clob clobData = (Clob) resultObj;
        BufferedReader bufferRead;
        bufferRead = new BufferedReader(clobData.getCharacterStream());

        while ((ruleStr=bufferRead.readLine()) != null){
          ruleBuffer.append(/*AmspUtil.convertString(*/ruleStr/*,clientLocale)*/);
        }
        retVal = ruleBuffer.toString();
      } else {
        retVal = (String) resultObj;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }   
    return retVal;
  }

  /**
   * 
   * @param rowIndex
   * @param columnIndex
   * @return values
   */
  public String getClobValue(int rowIndex, int columnIndex) {
    String columnName = columns.get(columnIndex).columnName;
    return getClobValue(rowIndex, columnName);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    int i = 1;
    for (HashMap<String, Object> row : rows) {
      buf.append("Row #"+i+++":");
      for (Column c : columns) {
        buf.append(c.columnName);
        buf.append(" = ");
        buf.append(StringUtil.getStringValue(row.get(c.columnName)));
        buf.append(" ; ");
      }
      buf.append(StringUtil.lineSeparator);
    }
    return buf.toString();
  }
}