import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mhk.Task;
import org.mhk.TaskManager;
import org.mhk.enums.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskManagerTest {

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
    /**
     * Tasks' title should be modifiable
     */
    @ParameterizedTest
    @ValueSource(strings = {"Title a", "Title b", "Title c"})
    void shouldUpdateTaskTitle(String newTitle) {
        Task firstTask = taskManager.getTaskLocatedAt(0);
        String originalId = firstTask.getId();
        firstTask.setTitle(newTitle);
        assertEquals(newTitle, firstTask.getTitle());
        assertIdUnchanged(firstTask, originalId);
    }
    /**
     * Tasks' title shouldn't be empty
     */
    @Test
    void shouldNotAllowEmptyTitleUpdate(){
        Task task = taskManager.getTaskLocatedAt(0);
        assertThrows(IllegalArgumentException.class, () -> task.setTitle(""));
        assertThrows(IllegalArgumentException.class, () -> task.setTitle(null));
    }

    /**
     * Tasks' description should be modifiable
     */
    @ParameterizedTest
    @ValueSource(strings = {"desc a", "desc b", ""})
    void shouldUpdateTaskDescription(String desc) {
        Task firstTask = taskManager.getTaskLocatedAt(0);
        String originalId = firstTask.getId();
        firstTask.setDescription(desc);
        assertEquals(desc, firstTask.getDescription());
        assertIdUnchanged(firstTask, originalId);
    }

    /**
     * Tasks' state should be modifiable
     */
    @ParameterizedTest
    @MethodSource("listOfStates")
    void shouldUpdateTaskState(State state) {
        Task firstTask = taskManager.getTaskLocatedAt(0);
        String originalId = firstTask.getId();
        firstTask.setState(state);
        assertEquals(state, firstTask.getState());
        assertIdUnchanged(firstTask, originalId);
    }

    /**
     * ID's should never change no matter what the operation is
     */
    private void assertIdUnchanged(Task task, String originalId) {
        assertEquals(originalId, task.getId(), "Task ID should not change after modifications.");
    }



    static State[] listOfStates() {
        return State.values();
    }

}
