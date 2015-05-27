package org.apache.calcite.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDbTest {
  @Test
  public void Test1() {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    DB db = mongoClient.getDB("test");
    DBCollection coll = db.getCollection("zips");
    DBCursor cursor = coll.find();
    int count = cursor.itcount();
    assertTrue(count > 0);
  }
}
