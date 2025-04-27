import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mhk.Task;
import org.mhk.TaskManager;
import org.mhk.enums.State;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    static TaskManager taskManager;

    @BeforeAll
    static void setUp()
    {
        taskManager = new TaskManager(List.of(
                new Task("Buy milk", "You need to buy milk"),
                new Task("Buy Cheese", "You need to buy cheese"),
                new Task("Buy bread", "You need to buy bread"),
                new Task("Buy egg", "You need to buy egg")
        ));
    }

    @BeforeEach
    void InitATask() {

    }

    @AfterEach
    void clear()
    {
        //taskManager.flushTaskList();
    }

    static Stream<Task> provideTasks() {
        return taskManager.getTaskList().stream();
    }

    /**
     * Test function which asserts that tasks have a title
     */
    @ParameterizedTest
    @MethodSource("provideTasks")
    void taskShouldStoreTitle(Task task) {
        assertNotNull(task.getTitle());
    }

    /**
     * Test function which asserts that tasks have a title
     */
    @ParameterizedTest
    @MethodSource("provideTasks")
    void taskShouldStoreDescription(Task task) {
        assertNotNull(task.getDescription());
    }

    /**
     * Initial state of a task should be NEW
     */
    @ParameterizedTest
    @MethodSource("provideTasks")
    void taskInitialStateShouldBeNew(Task task) {
        assertEquals(State.NEW, task.getState());
    }

    /**
     * List all tasks with their state
     */
    @ParameterizedTest
    @MethodSource("provideTasks")
    void listTaskWithState(Task task) {
        assertNotNull(task.getState());
    }

    /**
    * Tasks should have non null ID
    */
    @ParameterizedTest
    @MethodSource("provideTasks")
    void taskShouldHaveNonNullId(Task task) {
        assertNotNull(task.getId(), "Task ID should not be null");
    }

    /**
     * Tasks should have unique ID
     */
    @Test
    void taskIdsShouldBeUnique() {
        List<Task> tasks = taskManager.getTaskList();
        Set<String> ids = tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toSet());

        assertEquals(tasks.size(), ids.size(), "Task IDs should be unique");
    }

    /**
     * ID should be final
     */
    @Test
    void taskIdShouldRemainTheSame() {
        Task task = new Task("Sample", "Sample description");
        String originalId = task.getId();

        // Simulate changes
        task.setTitle("Updated title");
        task.setDescription("Updated description");

        assertEquals(originalId, task.getId(), "Task ID should not change after creation");
    }
}
