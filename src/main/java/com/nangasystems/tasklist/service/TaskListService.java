package com.nangasystems.tasklist.service;

import com.nangasystems.tasklist.dbo.Task;
import javafx.collections.ObservableList;

public interface TaskListService {

    ObservableList<Task> getTasks();
}
