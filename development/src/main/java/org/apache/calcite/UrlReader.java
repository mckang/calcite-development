package org.apache.calcite;

import java.io.InputStreamReader;
import java.net.URL;

public class UrlReader {  
  public static InputStreamReader run(String url) throws Exception {
    URL pageUrl = new URL(url);    
    InputStreamReader inputStreamReader =
        new InputStreamReader(pageUrl.openStream());
    return inputStreamReader;
  }
}
