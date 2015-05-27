package org.apache.calcite.jdbc;

import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeFactory.FieldInfoBuilder;
import org.junit.Test;

public class DatabaseMetadataTest {
  @Test
  public void Test1() throws Exception {
    CalciteConnectionHelper calciteConnectionHelper =
        new CalciteConnectionHelper();
    CalciteConnectionImpl calciteConnectionImpl =
        (CalciteConnectionImpl)
          calciteConnectionHelper.getCalciteConnectionImpl();
    
    JavaTypeFactory typeFactory = calciteConnectionImpl.typeFactory;
    
    FieldInfoBuilder fieldInfoBuilder = typeFactory.builder();
    for (int i = 0; i < fieldInfoBuilder.getFieldCount(); i++) {
      System.out.println(
          fieldInfoBuilder.getFieldName(i) + "|_|" + 
          fieldInfoBuilder.getFieldName(i));
    }
  }
}
