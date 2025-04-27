import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Task Suite")
@SelectClasses({
        TaskTest.class,
        TaskManagerTest.class
})
class TaskSuite {
}