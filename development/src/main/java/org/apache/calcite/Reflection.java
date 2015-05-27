package org.apache.calcite;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Reflection {
  public static void main(String[] args) throws Exception {
    // inspect("java.sql.Connection");
    // inspect("java.sql.Statement");
    inspect("java.sql.PreparedStatement");
  }
  
  private static void inspect(String className) throws Exception {
    MethodSchema[] methodSchemas =
        constructMethodSchema(constructMethod(className));
    showAllMethodSchema(methodSchemas);
    showUniqueParameterType(methodSchemas);
  }
  
  private static void showAllMethodSchema(MethodSchema[] methodSchemas) {
    for(int i = 0; i < methodSchemas.length; i++) {
      System.out.println(methodSchemas[i]);
    }
  }
  
  public static Method[] constructMethod(String className) 
      throws Exception {
    Class<?> clazz = Class.forName(className);
    Method[] methods = clazz.getDeclaredMethods();
    return methods;
  }
  
  public static MethodSchema[] constructMethodSchema(Method[] methods)
      throws Exception {
    MethodSchema[] methodSchemas = new MethodSchema[methods.length];
    for(int i = 0; i < methods.length; i++) { 
      methodSchemas[i] = new MethodSchema(methods[i]);
    }
    return methodSchemas;
  }
  
  public static void showUniqueParameterType(MethodSchema[] methodSchemas) {
    ArrayList<String> uniqueTypes = new ArrayList<String>();
    for(int i = 0; i < methodSchemas.length; i++) {
      String[] parameterTypes = methodSchemas[i].getParameterTypes();
      for(int j = 0; j < parameterTypes.length; j++) {
        if(!uniqueTypes.contains(parameterTypes[j])) {
          uniqueTypes.add(parameterTypes[j]);
        }
      }
    }
    
    for(int i = 0; i < uniqueTypes.size(); i++) {
      System.out.println((i + 1) + ". " + uniqueTypes.get(i));
    }
  }
}
