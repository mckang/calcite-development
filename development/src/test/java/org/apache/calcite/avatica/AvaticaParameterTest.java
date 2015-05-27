package org.apache.calcite.avatica;

import java.util.Random;

import org.apache.calcite.BaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AvaticaParameterTest extends BaseTest {
  @Test public void setDouble() {
    Object slots[] = new Object[1];
    Random rand = new Random();
    AvaticaParameter avp =
        new AvaticaParameter(false, -1, -1, -1, "DUMMY", "DUMMY", "DUMMY");
    // avp.setDouble(slots, 0, rand.nextDouble());
    showAttribute("slots[0]", slots[0].getClass().getName());
    assertEquals("java.lang.Double", slots[0].getClass().getName());
  }
}
