import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mhk.Task;
import org.mhk.TaskManager;
import org.mhk.enums.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    static TaskManager taskManager;

    @BeforeEach
    void setUp()
    {
        taskManager = new TaskManager(List.of(
                new Task("Buy milk", "You need to buy milk"),
                new Task("Buy Cheese", "You need to buy cheese"),
                new Task("Buy bread", "You need to buy bread"),
                new Task("Buy egg", "You need to buy egg")
        ));
    }
    /**
     * Tasks' title should be modifiable
     */
    @ParameterizedTest
    @ValueSource(strings = {"Title a", "Title b", "Title c"})
    void shouldUpdateTaskTitle(String newTitle) {
        Task firstTask = taskManager.getTaskByIndex(0);
        String originalId = firstTask.getId();
        taskManager.updateTaskTitleByIndex(0, newTitle);
        assertEquals(newTitle, taskManager.getTaskByIndex(0).getTitle());
        assertIdUnchanged(firstTask, originalId);
    }
    /**
     * Tasks' title shouldn't be empty
     */
    @Test
    void shouldNotAllowEmptyTitleUpdate(){
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskTitleByIndex(0, ""));
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTaskTitleByIndex(0, null));
    }

    /**
     * Tasks' description should be modifiable
     */
    @ParameterizedTest
    @ValueSource(strings = {"desc a", "desc b", ""})
    void shouldUpdateTaskDescription(String desc) {
        Task firstTask = taskManager.getTaskByIndex(0);
        String originalId = firstTask.getId();
        taskManager.updateTaskDescriptionByIndex(0, desc);
        assertEquals(desc, taskManager.getTaskByIndex(0).getDescription());
        assertIdUnchanged(firstTask, originalId);
    }

    /**
     * Tasks' state should be modifiable
     */
    @ParameterizedTest
    @MethodSource("listOfStates")
    void shouldUpdateTaskState(State state) {
        Task firstTask = taskManager.getTaskByIndex(0);
        String originalId = firstTask.getId();
        taskManager.updateTaskStateByIndex(0, state);
        assertEquals(state, taskManager.getTaskByIndex(0).getState());
        assertIdUnchanged(firstTask, originalId);
    }

    /**
     * ID's should never change no matter what the operation is
     */
    private void assertIdUnchanged(Task task, String originalId) {
        assertEquals(originalId, task.getId(), "Task ID should not change after modifications.");
    }

    /**
     * task manager should be able to batch delete
     */
    @Test
    void shouldRemoveAllTasks(){
        taskManager.flushTaskList();
        assertTrue(taskManager.getTaskList().isEmpty());
    }



    static State[] listOfStates() {
        return State.values();
    }

}
