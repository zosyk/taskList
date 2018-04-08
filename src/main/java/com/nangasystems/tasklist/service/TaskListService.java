package com.nangasystems.tasklist.service;

import com.nangasystems.tasklist.dbo.ComparableTask;
import com.nangasystems.tasklist.dbo.Task;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public interface TaskListService {

    ObservableList<Task> getTasks();

    void export(List<Task> tasks, File exportFile);

    ObservableList<Task> getGroupedTasks();

    ObservableList<Task> importTasks(File file) throws Exception;

    ObservableList<ComparableTask> getComparableTasks(ObservableList<Task> dumpedTasks);
}
