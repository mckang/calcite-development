package org.apache.calcite;

import java.lang.reflect.Method;

public class MethodSchema {
  private String name;
  private String exceptionClassName;
  private int parametersCount;
  private String[] parameterTypes;
  
  public MethodSchema(Method method) {
    this.name = method.getName();
    
    Class<?>[] throwables = method.getExceptionTypes();
    if(throwables.length > 0) {
      this.exceptionClassName = throwables[0].getName();
    }
   
    Class<?>[] parameterTypes = method.getParameterTypes();
    this.parametersCount = parameterTypes.length;
    
    this.parameterTypes = new String[this.parametersCount];
    for(int i = 0; i < parameterTypes.length; i++) {
      this.parameterTypes[i] = parameterTypes[i].getName();
    }
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getExceptionClassName() {
    return this.exceptionClassName;
  }
  
  public int getParameterCount() {
    return this.parametersCount;
  }
  
  public String[] getParameterTypes() {
    return this.parameterTypes;
  }
  
  @Override
  public String toString() {
    String str = 
        "MethodName|_|" + this.name +
        "|_|ExceptionClassName|_|" + this.exceptionClassName +
        "|_|ParametersCount|_|" + this.parametersCount;
    
    for(int i = 0; i < this.parameterTypes.length; i++) {
      str +=
          "|_|Parameter" + (i + 1) +
          "|_|" + this.parameterTypes[i];
    }
    
    return str;   
  }
}
