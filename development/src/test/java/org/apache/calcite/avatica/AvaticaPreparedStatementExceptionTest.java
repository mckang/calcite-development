package org.apache.calcite.avatica;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.apache.calcite.ExceptionTest;
import org.apache.calcite.Reflection;
import org.junit.Test;

public class AvaticaPreparedStatementExceptionTest extends ExceptionTest {

  protected PreparedStatement preparedStatement;
  
  @Test
  public void testPreparedStatementExceptions() throws Exception {
    preparedStatement = connection.prepareStatement("values (1, 2)");
    
    Method[] methods = Reflection.constructMethod("java.sql.PreparedStatement");
    
    Map<String, Integer> result =
        consolidateMethodExceptions(preparedStatement, methods,
            createParameterValuePool());
    
    checkForUnsupportedOperationException(result);
    analyzeResult(result);
  }
  
  /*
   * 1. int 2. boolean 3. byte 4. short 5. long 6. float 7. double 8.
   * java.math.BigDecimal 9. java.lang.String 10. [B 11. java.sql.Date 12.
   * java.sql.Time 13. java.sql.Timestamp 14. java.io.InputStream 15.
   * java.lang.Object 16. java.io.Reader 17. java.sql.Ref 18. java.sql.Blob 19.
   * java.sql.Clob 20. java.sql.Array 21. java.util.Calendar 22. java.net.URL
   * 23. java.sql.RowId 24. java.sql.NClob 25. java.sql.SQLXML
   */
  private static Map<String, Object> createParameterValuePool() throws Exception {
    Random rand = new Random();
    java.util.Date date = new java.util.Date();
    Map<String, Object> parameterValuePool = new HashMap<String, Object>();
    parameterValuePool.put("int", 0);
    parameterValuePool.put("boolean", false);
    parameterValuePool.put("short", (short) 0);
    parameterValuePool.put("long", 0l);
    parameterValuePool.put("byte", Byte.parseByte("0"));
    parameterValuePool.put("float", (float) 0);
    parameterValuePool.put("double", 0.00);
    parameterValuePool.put("java.math.BigDecimal", new BigDecimal(0));
    parameterValuePool.put("java.lang.String", "values(1, 2)");
    parameterValuePool.put("[B", new byte[] {});
    parameterValuePool.put("java.sql.Date", new Date(date.getTime()));
    parameterValuePool.put("java.sql.Time", new Time(date.getTime()));
    parameterValuePool.put("java.sql.Timestamp", new Timestamp(date.getTime()));
    parameterValuePool.put("java.io.InputStream", new InputStream() {
      @Override
      public int read() throws IOException {
        return 0;
      }});
    parameterValuePool.put("java.lang.Object", new Object());
    parameterValuePool.put("java.io.Reader", new Reader(){
      @Override
      public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
      }
      @Override
      public void close() throws IOException {        
      }});
    parameterValuePool.put("java.sql.Ref", new Ref() {
      @Override
      public String getBaseTypeName() throws SQLException {
        return null;
      }
      @Override
      public Object getObject(Map<String, Class<?>> map) throws SQLException {
        return null;
      }
      @Override
      public Object getObject() throws SQLException {
        return null;
      }
      @Override
      public void setObject(Object value) throws SQLException {
      }});
    parameterValuePool.put("java.sql.Blob", new Blob() {
      @Override
      public long length() throws SQLException {
        return 0;
      }
      @Override
      public byte[] getBytes(long pos, int length) throws SQLException {
        return null;
      }
      @Override
      public InputStream getBinaryStream() throws SQLException {
        return null;
      }
      @Override
      public long position(byte[] pattern, long start) throws SQLException {
        return 0;
      }
      @Override
      public long position(Blob pattern, long start) throws SQLException {
        return 0;
      }
      @Override
      public int setBytes(long pos, byte[] bytes) throws SQLException {
        return 0;
      }

      @Override
      public int setBytes(long pos, byte[] bytes, int offset, int len)
          throws SQLException {
        // TODO Auto-generated method stub
        return 0;
      }
      @Override
      public OutputStream setBinaryStream(long pos) throws SQLException {
        return null;
      }
      @Override
      public void truncate(long len) throws SQLException {  
      }
      @Override
      public void free() throws SQLException {
      }
      @Override
      public InputStream getBinaryStream(long pos, long length)
          throws SQLException {
        return null;
      }});
    parameterValuePool.put("java.sql.Clob", new Clob() {
      @Override
      public long length() throws SQLException {
        return 0;
      }
      @Override
      public String getSubString(long pos, int length) throws SQLException {
        return null;
      }
      @Override
      public Reader getCharacterStream() throws SQLException {
        return null;
      }
      @Override
      public InputStream getAsciiStream() throws SQLException {
        return null;
      }
      @Override
      public long position(String searchstr, long start) throws SQLException {
        return 0;
      }
      @Override
      public long position(Clob searchstr, long start) throws SQLException {
        return 0;
      }
      @Override
      public int setString(long pos, String str) throws SQLException {
        return 0;
      }
      @Override
      public int setString(long pos, String str, int offset, int len)
          throws SQLException {
        return 0;
      }
      @Override
      public OutputStream setAsciiStream(long pos) throws SQLException {
        return null;
      }
      @Override
      public Writer setCharacterStream(long pos) throws SQLException {
        return null;
      }
      @Override
      public void truncate(long len) throws SQLException {
      }
      @Override
      public void free() throws SQLException {
      }
      @Override
      public Reader getCharacterStream(long pos, long length)
          throws SQLException {
        return null;
      }});
    parameterValuePool.put("java.sql.Array", new Array() {
      @Override
      public String getBaseTypeName() throws SQLException {
        return null;
      }
      @Override
      public int getBaseType() throws SQLException {
        return 0;
      }
      @Override
      public Object getArray() throws SQLException {
        return null;
      }
      @Override
      public Object getArray(Map<String, Class<?>> map) throws SQLException {
        return null;
      }
      @Override
      public Object getArray(long index, int count) throws SQLException {
        return null;
      }
      @Override
      public Object getArray(long index, int count, Map<String, Class<?>> map)
          throws SQLException {
        return null;
      }
      @Override
      public ResultSet getResultSet() throws SQLException {
        return null;
      }
      @Override
      public ResultSet getResultSet(Map<String, Class<?>> map)
          throws SQLException {
        return null;
      }
      @Override
      public ResultSet getResultSet(long index, int count) throws SQLException {
        return null;
      }
      @Override
      public ResultSet getResultSet(long index, int count,
          Map<String, Class<?>> map) throws SQLException {
        return null;
      }
      @Override
      public void free() throws SQLException {        
      }});
    parameterValuePool.put("java.util.Calendar", Calendar.getInstance());
    parameterValuePool.put("java.net.URL", new URL("http://www.google.com"));
    parameterValuePool.put("java.sql.RowId", new RowId() {
      @Override
      public byte[] getBytes() {
        return null;
      }});
    parameterValuePool.put("java.sql.NClob", new NClob() {
      @Override
      public long length() throws SQLException {
        return 0;
      }
      @Override
      public String getSubString(long pos, int length) throws SQLException {
        return null;
      }
      @Override
      public Reader getCharacterStream() throws SQLException {
        return null;
      }

      @Override
      public InputStream getAsciiStream() throws SQLException {
        // TODO Auto-generated method stub
        return null;
      }
      @Override
      public long position(String searchstr, long start) throws SQLException {
        return 0;
      }
      @Override
      public long position(Clob searchstr, long start) throws SQLException {
        return 0;
      }
      @Override
      public int setString(long pos, String str) throws SQLException {
        return 0;
      }
      @Override
      public int setString(long pos, String str, int offset, int len)
          throws SQLException {
        return 0;
      }
      @Override
      public OutputStream setAsciiStream(long pos) throws SQLException {
        return null;
      }
      @Override
      public Writer setCharacterStream(long pos) throws SQLException {
        return null;
      }
      @Override
      public void truncate(long len) throws SQLException { 
      }
      @Override
      public void free() throws SQLException {   
      }
      @Override
      public Reader getCharacterStream(long pos, long length)
          throws SQLException {
        return null;
      }});
    parameterValuePool.put("java.sql.SQLXML", new SQLXML() {
      @Override
      public void free() throws SQLException {
      }
      @Override
      public InputStream getBinaryStream() throws SQLException {
        return null;
      }
      @Override
      public OutputStream setBinaryStream() throws SQLException {
        return null;
      }
      @Override
      public Reader getCharacterStream() throws SQLException {
        return null;
      }
      @Override
      public Writer setCharacterStream() throws SQLException {
        return null;
      }
      @Override
      public String getString() throws SQLException {
        return null;
      }
      @Override
      public void setString(String value) throws SQLException {  
      }
      @Override
      public <T extends Source> T getSource(Class<T> sourceClass)
          throws SQLException {
        return null;
      }
      @Override
      public <T extends Result> T setResult(Class<T> resultClass)
          throws SQLException {
        return null;
      }});
        
    return parameterValuePool;
  }
}
