package org.apache.calcite.jdbc;

import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.calcite.BaseTest;
import org.apache.calcite.avatica.AvaticaParameter;
import org.apache.calcite.avatica.AvaticaPreparedStatement;
import org.apache.calcite.avatica.Meta;
import org.apache.calcite.linq4j.tree.Primitive;

public class TypeConversion extends BaseTest {
  public static void main(String[] args) throws Exception {
    CalciteMetaImpl meta = CalciteMetaImplTest.createMeta();
    Meta.ConnectionHandle ch = CalciteMetaImplTest.createConnectionHandle(meta);
    String sql = "select * from depts where 1 = ? and 2 = ? and 3 = ? and 4 = ? and 5 = ? and 6 = ?";
    
    Meta.StatementHandle h =
        CalciteMetaImplTest.createStatementHandle(meta, ch, sql);
    List<AvaticaParameter> avps = h.signature.parameters;
    for(int i = 0; i < avps.size(); i++) {
      showAttribute("typeName", avps.get(i).typeName);
    }
    
    AvaticaPreparedStatement preparedStatement =
        meta.getConnection().getFactory().newPreparedStatement(
            meta.getConnection(), h, h.signature, 
            ResultSet.FETCH_FORWARD, ResultSet.TYPE_SCROLL_INSENSITIVE, 
            Statement.CLOSE_ALL_RESULTS);
    
    Random rand = new Random();
    List<Object> parameterValues = new ArrayList<Object>();
    parameterValues.add((java.lang.Number) rand.nextInt());
    parameterValues.add((java.lang.Number)rand.nextLong());
    parameterValues.add((java.lang.Number)rand.nextFloat());
    parameterValues.add((java.lang.Number) rand.nextDouble());
    parameterValues.add((java.lang.Number) 
        (short) rand.nextInt(Short.MAX_VALUE));
    for(int i = 0; i < parameterValues.size(); i++) {
      showAttribute("[" + i + "]", parameterValues.get(i));
    }
    
    showAttribute("Primitive.INT", Primitive.INT);
    showAttribute("Primitive.INT", Primitive.LONG);
    showAttribute("Primitive.INT", Primitive.FLOAT);
    showAttribute("Primitive.INT", Primitive.DOUBLE);
    showAttribute("Primitive.INT", Primitive.SHORT);
    
    preparedStatement.setInt(1, rand.nextInt());
    preparedStatement.setLong(2, rand.nextLong());
    preparedStatement.setFloat(3, rand.nextFloat());
    preparedStatement.setDouble(4, rand.nextDouble());
    preparedStatement.setShort(5, (short) rand.nextInt(Short.MAX_VALUE));
    preparedStatement.setString(6, "tnpm-cloud");
    
    ParameterMetaData parameterMetaData =
        preparedStatement.getParameterMetaData();
    for(int i = 1; i <= parameterMetaData.getParameterCount(); i++) {
      showAttribute("Class name for [" + i + "]",
          parameterMetaData.getParameterClassName(i));
      showAttribute("Type name for [" + i + "]",
          parameterMetaData.getParameterTypeName(i));
    }
    
  }
}
