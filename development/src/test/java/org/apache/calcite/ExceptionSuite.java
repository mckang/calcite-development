package org.apache.calcite;

import org.apache.calcite.avatica.AvaticaConnectionExceptionTest;
import org.apache.calcite.avatica.AvaticaPreparedStatementExceptionTest;
import org.apache.calcite.avatica.AvaticaStatementExceptionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AvaticaConnectionExceptionTest.class,
  AvaticaStatementExceptionTest.class,
  AvaticaPreparedStatementExceptionTest.class
})

public class ExceptionSuite {

}
