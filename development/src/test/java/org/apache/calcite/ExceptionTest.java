package org.apache.calcite;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ExceptionTest {
  
  protected static Connection connection;
  
  @BeforeClass
  public static void beforeClass() {
    connection =
        TnpmConnectionFactory.getCalciteLocalConnection(null);
  }
  
  @AfterClass
  public static void afterClass() throws Exception {
    connection.close();
  }
  
  public static void analyzeResult(Map<String, Integer> result) {
    if(result.size() == 0) {
      System.out.println("Result is empty.");
    } else {
      for(Map.Entry<String, Integer> entry : result.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
    }
  }
  
  public static void checkForUnsupportedOperationException(
      Map<String, Integer> result) {
    assertEquals(null, result.get("java.lang.UnsupportedOperationException"));
  }
  
  public static Map<String, Integer> consolidateMethodExceptions(Object object,
      Method[] methods, Map<String, Object> parameterValuePool) {
    Map<String, Integer> result = new HashMap<String, Integer>();
    for(int i = 0; i < methods.length; i++) {
      try {
        Class<?>[] parameterTypes = methods[i].getParameterTypes();
        Object[] arguements = new Object[] {};
        if(parameterTypes.length > 0) {
          arguements = new Object[parameterTypes.length];
          for(int j = 0; j < arguements.length; j++) {
            String parameterTypeName = parameterTypes[j].getName();
            arguements[j] =
                retrieveParameterValue(parameterValuePool, parameterTypeName);
          }
        }
        methods[i].invoke(object, arguements);
      } catch (IllegalArgumentException ex) {
        updateExceptionCount(result, ex.getClass().getName());
        System.out.println(methods[i] + "is throwing the IllegalArgumentException.");
        ex.printStackTrace();
      } catch (InvocationTargetException ex) {
        Throwable throwable = ex.getCause();
        String exceptionCauseClassName = throwable.getClass().getName();
        if(exceptionCauseClassName
            .equals("java.lang.UnsupportedOperationException")) {
          updateExceptionCount(result, exceptionCauseClassName);
          throwable.printStackTrace();
        } 
      } catch (Exception ex) {
        updateExceptionCount(result, ex.getClass().getName());
        ex.printStackTrace();
      }
    }
    return result;
  } 
  
  private static Map<String, Integer> updateExceptionCount(
      Map<String, Integer> result, String exceptionClassName) {
    Integer count = result.get(exceptionClassName);
    if(count == null) {
      result.put(exceptionClassName, 1);
    } else {
      result.put(exceptionClassName, count + 1);
    }
    return result;
  }
  
  private static Object retrieveParameterValue(Map<String, Object> map, 
      String parameterTypeName) {
    return 
        map.get(parameterTypeName) == null ? 
            new Object() : map.get(parameterTypeName);
  }
  
}
