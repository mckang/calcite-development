package org.apache.calcite.prepare;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import org.apache.calcite.adapter.enumerable.EnumerableInterpreterRule;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.jdbc.CalcitePrepare.CalciteSignature;
import org.apache.calcite.jdbc.CalcitePrepare.Context;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.linq4j.function.Function1;
import org.apache.calcite.plan.Contexts;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelOptCostFactory;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.sql.SqlOperatorTable;
import org.apache.calcite.sql.validate.SqlValidator;

public class VolcanoTest {
  private CalcitePrepareImpl calcitePrepareImpl = new CalcitePrepareImpl();
  private Context context = null;
  
  public VolcanoTest() {
  }
  
  public VolcanoTest(Context context) {
    this.context = context;
  }
  
  public RelOptPlanner getCustomPlanner(List<RelOptRule> rules) {
    RelOptCostFactory costFactory = null;
    org.apache.calcite.plan.Context ctxt = Contexts.of(context.config());
    final VolcanoPlanner planner =
        new VolcanoPlanner(costFactory, ctxt);
    
    planner.addRelTraitDef(ConventionTraitDef.INSTANCE); // Important
    
    for(RelOptRule rule: rules) {
      planner.addRule(rule);
    }
    
    return planner;
  }
  
  public RelOptPlanner getCustomPlanner() {
    return getCustomPlanner(Collections.<RelOptRule>emptyList());
  }
  
  public List<Function1<Context, RelOptPlanner>> getPlannerFactories() {    
    return calcitePrepareImpl.createPlannerFactories();
  }
  
  public SqlValidator getSqlValidator(SqlOperatorTable opTab,
      CalciteCatalogReader catalogReader, JavaTypeFactory typeFactory) {
    return new CalciteSqlValidator(opTab, catalogReader, typeFactory);
  }
  
  public <T> CalciteSignature<T> invokePrepare2_(String sql,
      Queryable<T> queryable, Type elementType, int maxRowCount,
      CalciteCatalogReader catalogReader, RelOptPlanner planner) {
    return calcitePrepareImpl.prepare2_(context, sql, queryable, elementType,
        maxRowCount, catalogReader, planner);
  }
}
