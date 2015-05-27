package org.apache.calcite;

import java.io.Reader;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class HtmlParser {  
  public static HTMLDocument run(Reader in) throws Exception {
    HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
    HTMLDocument htmlDoc = (HTMLDocument) htmlEditorKit.createDefaultDocument();
    htmlEditorKit.read(in, htmlDoc, 0);
    return htmlDoc;
  }
}
