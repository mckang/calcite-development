package org.apache.calcite.test;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.jdbc.CalciteConnectionHelper;
import org.apache.calcite.jdbc.CalcitePrepare;
import org.apache.calcite.jdbc.CalcitePrepare.Context;
import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.apache.calcite.linq4j.function.Function1;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.prepare.VolcanoTest;
import org.junit.Test;

public class SqlToRelConverterTest extends SqlToRelTestBase {
  @Test public void test1() throws Exception {      
    //String sql = "SELECT c1 FROM \"tjoin1\" WHERE c2 = (SELECT rnum FROM \"tjoin2\" where c2 = (SELECT rnum from \"tjoin3\"))";
    //String sql = "select * from \"tjoin2\" where c1 = (select c1 from \"tjoin1\" where rnum >= (Select rnum from \"tjoin1\" where rnum=0))";
    //String sql = "select * from \"tjoin2\" where c1 = (select c1 from \"tjoin1\" where rnum = (Select rnum from \"tjoin1\" where rnum=0))";
    //String sql = "select * from \"tjoin1\" where c1 = (select c2 from \"tjoin2\")";
    //String sql = "SELECT * FROM \"dept\" WHERE DEPTNO = (SELECT DEPTNO FROM \"emp\" WHERE ENAME=(SELECT ENAME FROM \"emp\" WHERE JOB='PRESIDENT'))";
    //String sql = "SELECT * FROM \"dept\" WHERE DEPTNO = (SELECT DEPTNO FROM \"emp\")";
    String sql = "SELECT sum(deptno) FROM \"dept\" where deptno > 0";
    
    CalciteConnectionHelper calciteConnectionHelper =
        new CalciteConnectionHelper(TnpmConnectionFactory.jdbcModel);
    CalcitePrepare.Context context = calciteConnectionHelper.getContext();
    
    final JavaTypeFactory typeFactory = context.getTypeFactory();
    CalciteCatalogReader catalogReader =
        new CalciteCatalogReader(
            context.getRootSchema(),
            context.config().caseSensitive(),
            context.getDefaultSchemaPath(),
            typeFactory);
    
    VolcanoTest volcanoTest = new VolcanoTest(context);
    List<Function1<Context, RelOptPlanner>> plannerFactories =
        volcanoTest.getPlannerFactories();

    RelOptPlanner relOptPlanner =
        plannerFactories.get(0).apply(context);
    
    int maxRowCount = -1;
    Type type = Object[].class;
    
    volcanoTest.invokePrepare2_(sql, null, type, maxRowCount,
        catalogReader, relOptPlanner);
  }
}
