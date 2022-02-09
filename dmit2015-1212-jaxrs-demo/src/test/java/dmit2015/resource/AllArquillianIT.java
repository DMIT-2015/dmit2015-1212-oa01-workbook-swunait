package dmit2015.resource;

import dmit2015.repository.TodoItemRepositoryIT;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("All Arquillian Integration Tests")
@SelectClasses({TodoItemRepositoryIT.class, TodoItemResourceArquillianRestAssuredIT.class})
public class AllArquillianIT {

    // the class remains completely empty,
    // being used only as a holder for the above annotations

}
