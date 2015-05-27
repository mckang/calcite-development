package org.apache.calcite;

public class BaseTest {
  
  public static void showAttribute(Object var, Object value) {
    System.out.println(var + ": " + value);
  }
  
  public static void showBorder(String name) {
    int totalLength = 100;
    int nameLength = name.length();
    int symbolLength = (totalLength - nameLength - 2) / 2;
    String symbolStream = "";
    for(int i = 1; i < symbolLength; i++) {
      symbolStream += "=";
    }
    System.out.println(symbolStream + " " + name + " " + symbolStream);
  }
  
}
