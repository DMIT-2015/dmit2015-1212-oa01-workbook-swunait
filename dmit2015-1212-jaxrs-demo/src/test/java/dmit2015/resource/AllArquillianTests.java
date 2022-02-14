package dmit2015.resource;

import dmit2015.repository.TodoItemRepositoryIT;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

//@RunWith(JUnitPlatform.class)
@Suite
@SuiteDisplayName("All Arquillian Integration Tests")
@SelectClasses({TodoItemRepositoryIT.class, TodoItemResourceArquillianRestAssuredIT.class})
public class AllArquillianTests {

    // the class remains completely empty,
    // being used only as a holder for the above annotations

}
