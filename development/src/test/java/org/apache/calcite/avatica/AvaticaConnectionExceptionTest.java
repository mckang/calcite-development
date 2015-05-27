package org.apache.calcite.avatica;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.calcite.ExceptionTest;
import org.apache.calcite.Reflection;
import org.junit.Ignore;
import org.junit.Test;

public class AvaticaConnectionExceptionTest extends ExceptionTest {
  
  @Test
  public void testConnectionExceptions() throws Exception {
    Method[] methods = Reflection.constructMethod("java.sql.Connection");
    
    Map<String, Integer> result =
        consolidateMethodExceptions(connection, methods,
            createParameterValuePool());
    
    checkForUnsupportedOperationException(result);
  }
  
  @Ignore @Test
  public void testConnectionExceptions1() throws Exception {
    codeThatWorks("java.sql.Connection", connection);
  }
  
  private static void codeThatWorks(String className, Connection connection)
      throws Exception {
    Class<?> clazz = Class.forName(className);
    Method m = clazz.getMethod("abort", new Class[] {  Executor.class });
    Object ret = m.invoke(connection, new Object[] { new Executor() {
      @Override
      public void execute(Runnable command) {
      }} });
    System.out.println(ret.getClass().getName());
  }
  
  /*
   * 1. java.lang.String 2. boolean 3. int 4. java.util.Map 5.
   * java.sql.Savepoint 6. [I 7. [Ljava.lang.String; 8. java.util.Properties 9.
   * [Ljava.lang.Object; 10. java.util.concurrent.Executor
   */
  private static Map<String, Object> createParameterValuePool() {
    Map<String, Object> parameterValuePool = new HashMap<String, Object>();
    parameterValuePool.put("java.lang.String", "values(1, 2)");
    parameterValuePool.put("boolean", false);
    parameterValuePool.put("int", 0);
    parameterValuePool.put("java.util.Map", new HashMap());
    parameterValuePool.put("java.sql.Savepoint", new Savepoint(){
      @Override
      public int getSavepointId() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override
      public String getSavepointName() throws SQLException {
        return null;
      }});    
    parameterValuePool.put("[I", new int[] {});
    parameterValuePool.put("[Ljava.lang.String;", new String[] {});
    parameterValuePool.put("java.util.Properties", new Properties());
    parameterValuePool.put("[Ljava.lang.Object;", new Object[] {});
    parameterValuePool.put("java.util.concurrent.Executor", new Executor() {
      @Override
      public void execute(Runnable command) {
      }});
    
    return parameterValuePool;
  }
}
