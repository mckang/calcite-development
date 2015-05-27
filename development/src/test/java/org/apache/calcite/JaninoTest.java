package org.apache.calcite;

import java.io.StringReader;

import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IClassBodyEvaluator;
import org.codehaus.commons.compiler.ICompilerFactory;

public class JaninoTest {
  public static void main(String[] args) {
    JaninoTest janino = new JaninoTest();
    janino.run();
  }
  
  public void run() {
    try {
      ICompilerFactory compilerFactory =
          CompilerFactoryFactory.getDefaultCompilerFactory();
      IClassBodyEvaluator classBodyEvaluator =
          compilerFactory.newClassBodyEvaluator();
      classBodyEvaluator.setClassName("AnonymousClass");
      classBodyEvaluator.setImplementedInterfaces(new Class[] { IActor.class });
      ClassLoader cl = JaninoTest.class.getClassLoader();
      classBodyEvaluator.setParentClassLoader(cl);
      String concreteImplementation = ""
          + "public String feedback(String str) {"
          + "System.out.println(\"I will do this before returning !!!\");"
          + "return str;"
          + "}";
      IActor actor =
          (IActor) classBodyEvaluator
            .createInstance(new StringReader(concreteImplementation));
      System.out.println(actor.feedback("Hello World !!!"));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}




