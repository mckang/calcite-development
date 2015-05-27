package org.apache.calcite.linq4j;

import org.apache.calcite.linq4j.function.Function0;
import org.apache.calcite.linq4j.function.Function1;
import org.apache.calcite.linq4j.function.Function2;
import org.apache.calcite.linq4j.function.Predicate1;
import org.junit.Test;
import org.junit.BeforeClass;

public class Linq4jTest {
  
  protected static Enumerable<Fruit> fruitEnumerable;
  
  @BeforeClass
  public static void beforeClass() {
    fruitEnumerable = Linq4j.asEnumerable(Store.fruits);
  }
  
  @Test
  public void Test1() {      
    //select shelfId, count(*) from Store.fruits where (name = "Apple" or name != null) and quantity >= 0 group by shelfId
    fruitEnumerable
      .where(new Predicate1<Fruit>(){
        @Override public boolean apply(Fruit v0) {
          return (v0.name == "Apple" || v0.name != null) && v0.quantity >= 0; // Operator OR + AND
        }})
      .orderBy(new Function1<Fruit, String>(){
        public String apply(Fruit a0) {
          return a0.name;
        }
      })
      .groupBy(
          new Function1<Fruit, Integer>() {
            @Override public Integer apply(Fruit a0) {
              return a0.shelfId;
            }
          },
          new Function0<Integer>() {
            @Override public Integer apply() {
              return 0;
            }
          },
          new Function2<Integer, Fruit, Integer>() {
            @Override public Integer apply(Integer v0, Fruit v1) {
              return ++v0; // COUNT
            }
          },
          new Function2<Integer, Integer, Object[]>() {
            @Override public Object[] apply(Integer v0, Integer v1) {
              return new Object[] { v0, v1 };
            }
          }
      ).foreach(new Function1<Object[], Void>() {
        @Override public Void apply(Object[] a0) {
          debug(a0[0] + "|_|" + a0[1]);
          return null;
        }
      });
  }
  
  @Test
  public void Test2() {
    //select shelfId, max(quantity) from store.fruits group by shelfId
    fruitEnumerable
      .groupBy(
            new Function1<Fruit, Integer>() {
              @Override public Integer apply(Fruit a0) {
                return a0.shelfId;
              }
            },
            new Function0<Integer>() {
              @Override public Integer apply() {
                return 0;
              }
            },
            new Function2<Integer, Fruit, Integer>() {
              @Override public Integer apply(Integer v0, Fruit v1) {
                if (v1.quantity >= v0) { // MAX
                  return v1.quantity;
                } else {
                  return v0;
                }
              }
            },
            new Function2<Integer, Integer, Object[]>() {
              @Override public Object[] apply(Integer v0, Integer v1) {
                return new Object[] { v0, v1 };
              }
            }
          ).foreach(new Function1<Object[], Void>() {
            @Override public Void apply(Object[] a0) {
              debug(a0[0] + "|_|" + a0[1]);
              return null;
            }

          });
  }
  
  private void debug(Object obj) {
    System.out.println("[DEBUG]: " + obj);
  }
}

class Store {
  private static final int LOCAL_SHELF = 1;
  private static final int INTER_SHELF = 2;
  
  public static Fruit[] fruits = new Fruit[] {
    new Fruit("Orange", 6, INTER_SHELF),
    new Fruit("Apple", 1, INTER_SHELF),
    new Fruit("Grape", 3, INTER_SHELF),
    new Fruit("Watermelon", 0, LOCAL_SHELF),
    new Fruit("Pear", 1, LOCAL_SHELF),
    new Fruit("Guava", 2, LOCAL_SHELF),
    new Fruit("Papaya", 4, LOCAL_SHELF),
  };
}

class Fruit {
  String name;
  int quantity;
  int shelfId;
  
  public Fruit(String name, int quantity, int shelfId) {
    this.name = name;
    this.quantity = quantity;
    this.shelfId = shelfId;
  }
}
