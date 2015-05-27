package org.apache.calcite.avatica;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.calcite.ExceptionTest;
import org.apache.calcite.Reflection;
import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AvaticaStatementExceptionTest extends ExceptionTest {
  @Test
  public void testStatementException() throws Exception {
    Method[] methods = Reflection.constructMethod("java.sql.Statement");
    Statement statement = connection.createStatement();
    
    Map<String, Integer> result =
        consolidateMethodExceptions(statement, methods,
            createParameterValuePool());
    
    checkForUnsupportedOperationException(result);
  }
  
  private static Map<String, Object> createParameterValuePool() {
    Map<String, Object> parameterValuePool = new HashMap<String, Object>();
    parameterValuePool.put("java.lang.String", "values(1, 2)");
    parameterValuePool.put("int", 0);
    parameterValuePool.put("boolean", false);
    parameterValuePool.put("[I", new int[] {});
    parameterValuePool.put("[Ljava.lang.String;", new String[] {});
    
    return parameterValuePool;
  }
  
  /***************************************************************************/
  
  private static void codeThatWorks(String className, Statement statement)
      throws Exception {
    Class<?> clazz = Class.forName(className);
    Method m = clazz.getMethod("executeQuery", new Class[] {  String.class });
    Object ret = m.invoke(statement, new Object[] { "values (1, 2)" });
    System.out.println(ret.getClass().getName());
  }
  
  private static void codeThatWorks1(String className, Statement statement)
      throws Exception {
    Class<?> clazz = Class.forName(className);
    Method m = clazz.getMethod("setMaxFieldSize", new Class[] {  int.class });
    Object ret = m.invoke(statement, new Object[] { 0 });
    System.out.println(ret.getClass().getName());
  }
  
  private static void codeThatWorks2(String className, Statement statement)
      throws Exception {
    Class<?> clazz = Class.forName(className);
    Method m = clazz.getMethod("executeUpdate", new Class[] { String.class, String[].class });
    Object ret = m.invoke(statement, new Object[] { "values(1, 2)", new String[] {} });
    System.out.println(ret.getClass().getName());
  }
}
