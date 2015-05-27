package org.apache.calcite;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

public class JdbcMethodParser {
  public static void main(String[] args) throws Exception {  
    String url =
        "http://docs.oracle.com/javase/7/docs/api/java/sql/Statement.html";
    
    InputStreamReader htmlInputStream = UrlReader.run(url);
    HTMLDocument htmlDoc = HtmlParser.run(htmlInputStream);
    ArrayList<String> methodNames = getJdbcMethods(htmlDoc);
    
    Iterator<String> i = methodNames.iterator();
    while(i.hasNext()) {
      System.out.println(i.next());
    }
  }
  
  private static ArrayList<String> getJdbcMethods(HTMLDocument htmlDoc) {
    ArrayList<String> methodNames = new ArrayList<String>();
    HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.META);
    while(iterator.isValid()) {
      AttributeSet attributeSet = iterator.getAttributes();
      String name = attributeSet.getAttribute(HTML.Attribute.NAME).toString();
      String content;
      if(name.equals("keywords")) {
          content =
            attributeSet.getAttribute(HTML.Attribute.CONTENT).toString();
          
          if(content.contains("(")) { // qualify as method
            methodNames.add(content);
          }
      }       
      iterator.next();
    }
    
    return methodNames;
  }
}
