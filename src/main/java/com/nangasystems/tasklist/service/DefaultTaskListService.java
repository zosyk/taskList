package com.nangasystems.tasklist.service;

import com.nangasystems.tasklist.dbo.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultTaskListService implements TaskListService {

    public ObservableList<Task> getTasks() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        tasks.add(new Task("process1,", 1000, 1000));
        tasks.add(new Task("process1,", 1000, 1000));
        tasks.add(new Task("process1,", 1000, 1000));
        tasks.add(new Task("process1,", 1000, 1000));
        tasks.add(new Task("process1,", 1000, 1000));

        return tasks;
    }
}
