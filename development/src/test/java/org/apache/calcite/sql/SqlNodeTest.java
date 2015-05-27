package org.apache.calcite.sql;

import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.util.SqlString;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlNodeTest {
  @Test public void Test1() throws Exception {
    String sql = "SELECT a FROM tbl";
    SqlParser sqlParser = SqlParser.create(sql);
    SqlNode sqlNode = sqlParser.parseStmt();
    SqlString sqlString = sqlNode.toSqlString(SqlDialect.CALCITE);
    System.out.println("sqlString: " + sqlString.getSql());
    
    SqlParserPos POS = new SqlParserPos(0, 0);
    SqlNodeList keywordList = new SqlNodeList(POS);
    
    // Retrieve relevant projections
    SqlNodeList selectionList = ((SqlSelect) sqlNode).getSelectList();
    SqlNodeList newSelectionList = new SqlNodeList(POS);
    newSelectionList.add(selectionList.get(0));
    
    // Retrieve table
    SqlNode table = ((SqlSelect) sqlNode).getFrom();
    
    // Make Reference Sql
    SqlNode whereClause = null;
    SqlNodeList groupByList = null;
    SqlNode having = null;
    SqlNodeList windowDecls = null;
    SqlNodeList orderByList = null;
    SqlNode offset = null;
    SqlNode fetch = null;
    
    SqlSelect refSelection = new SqlSelect(
          POS,
          keywordList,
          newSelectionList,
          table,
          whereClause,
          groupByList,
          having,
          windowDecls,
          orderByList,
          offset,
          fetch
        );
    
    SqlString sqlString2 = refSelection.toSqlString(SqlDialect.CALCITE);
    System.out.println("sqlString2: " + sqlString2.getSql());
    
    assertTrue(sqlString.equals(sqlString2));
    
    // Make Where clause
    SqlNode[] whereOperands = {selectionList.get(0), (SqlNode) refSelection.clone()};
    SqlNode where = new SqlBasicCall(SqlStdOperatorTable.EQUALS, whereOperands, POS);
 
    SqlSelect refSelection2 = (SqlSelect) refSelection.clone();
    refSelection2.setWhere(where);
    
    SqlString sqlString3 = refSelection2.toSqlString(SqlDialect.CALCITE);
    System.out.println("sqlString3: " + sqlString3.getSql());
  }
}
