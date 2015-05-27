package org.apache.calcite;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

public class EventListenerTest {
  public static void main(String[] args) throws Exception {
    MyClass myClass = new MyClass();
    myClass.addEventListener(new MyEventListener() {

      @Override
      public void myEventOccured(MyEvent event) {
        System.out.println(this.hashCode() + ": " + event);
      }
      
    });
    
    System.out.println("Sleep for 3 seconds ...");
    Thread.sleep(3000);
    
    myClass.fireEvent(new MyEvent("Hello World ?"));
  }
}

interface MyEventListener extends EventListener {
  public void myEventOccured(MyEvent event);
}

class MyEvent extends EventObject {

  private static final long serialVersionUID = 221808202292737340L;

  public MyEvent(Object source) {
    super(source);
  }
}

class MyClass {
  List<MyEventListener> eventListeners = new ArrayList<MyEventListener>();
  
  public void addEventListener(MyEventListener eventListener) {
    eventListeners.add(eventListener);
  }
  
  public void removeEventListener(MyEventListener eventListener) {
    eventListeners.remove(eventListener);
  }
  
  public void fireEvent(MyEvent event) {
    for(int i = 0; i < eventListeners.size(); i++) {
      eventListeners.get(i).myEventOccured(event);
    }
  }
}
