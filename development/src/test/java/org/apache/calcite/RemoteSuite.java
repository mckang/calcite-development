package org.apache.calcite;

import org.apache.calcite.avatica.AvaticaPreparedStatementTest;
import org.apache.calcite.avatica.AvaticaStatementTest;
import org.apache.calcite.squirrel.SquirrelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AvaticaStatementTest.class,
    AvaticaPreparedStatementTest.class,
    SquirrelTest.class
})

public class RemoteSuite {

}
