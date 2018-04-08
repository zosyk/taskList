package com.nangasystems.tasklist.service;

import com.nangasystems.tasklist.dbo.ComparableTask;
import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.util.executor.CmdExecutor;
import com.nangasystems.tasklist.util.parser.TaskParser;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.nangasystems.tasklist.util.CompareStatus.*;
import static javafx.collections.FXCollections.observableArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTaskListServiceTest {

    private DefaultTaskListService service = new DefaultTaskListService();

    @Mock
    private CmdExecutor executor;

    @Mock
    private TaskParser parser;

    @Before
    public void setUp() {
        when(parser.parse(any())).thenReturn(getMockTaskList());

        service.setCmdExecutor(executor);
        service.setTaskParser(parser);
    }

    @Test
    public void returnsAllTasks() {
        ObservableList<Task> tasks = service.getTasks();

        assertThat(tasks.size()).isEqualTo(2);
    }

    @Test
    public void returnsGroupedTasks() {
        ObservableList<Task> groupedTasks = service.getGroupedTasks();

        assertThat(groupedTasks.size()).isEqualTo(1);
    }

    @Test
    public void returnsGroupedTasksWithAggregatedMemory() {
        ObservableList<Task> groupedTasks = service.getGroupedTasks();

        assertThat(groupedTasks.get(0).getMemory()).isEqualTo(600);
    }

    @Test
    public void returnsComparableTasks() {
        ObservableList<Task> dumpedTasks = observableArrayList(new Task("idea.exe", 1000));
        ObservableList<ComparableTask> comparableTasks = service.getComparableTasks(dumpedTasks);

        assertThat(comparableTasks.size()).isEqualTo(2);
    }

    @Test
    public void returnsComparableTaskWithAddedStatus() {
        ObservableList<ComparableTask> comparableTasks = service.getComparableTasks(observableArrayList());

        ComparableTask comparableTask = comparableTasks.get(0);

        assertThat(comparableTask.getStatus()).isEqualTo(ADDED);
    }

    @Test
    public void returnsComparableTaskWithChangedStatus() {
        ObservableList<ComparableTask> comparableTasks = service.getComparableTasks(observableArrayList(new Task("java.exe", 500)));

        ComparableTask comparableTask = comparableTasks.get(0);

        assertThat(comparableTask.getStatus()).isEqualTo(CHANGED);
    }

    @Test
    public void returnsComparableTaskWithUnChangedStatus() {
        ObservableList<ComparableTask> comparableTasks = service.getComparableTasks(observableArrayList(new Task("java.exe", 600)));

        ComparableTask comparableTask = comparableTasks.get(0);

        assertThat(comparableTask.getStatus()).isEqualTo(UNCHANGED);
    }

    @Test
    public void returnsComparableTaskWithRemovedStatus() {
        when(parser.parse(any())).thenReturn(observableArrayList());

        ObservableList<ComparableTask> comparableTasks = service.getComparableTasks(observableArrayList(new Task("java.exe", 600)));

        ComparableTask comparableTask = comparableTasks.get(0);

        assertThat(comparableTask.getStatus()).isEqualTo(REMOVED);
    }

    private ObservableList<Task> getMockTaskList() {
        ObservableList<Task> tasks = observableArrayList();
        tasks.add(new Task("java.exe", "4024", 100));
        tasks.add(new Task("java.exe", "9024", 500));

        return tasks;
    }
}