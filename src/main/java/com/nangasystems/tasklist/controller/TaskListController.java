package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static com.nangasystems.tasklist.util.converter.MemoryConverter.convert;

public class TaskListController {

    private TaskListService taskListService;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> nameColumn;

    @FXML
    private TableColumn<Task, Number> processIDColumn;

    @FXML
    private TableColumn<Task, String> memoryColumn;

    @FXML
    private Label tasksCount;

    public void initTaskTable() {
        refreshTasks();

        nameColumn.setCellValueFactory(param -> param.getValue().getName());
        processIDColumn.setCellValueFactory(param -> param.getValue().getProcessID());
        memoryColumn.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getUsedMemoryValue())));

    }

    public void refreshTasks() {
        ObservableList<Task> tasks = taskListService.getTasks();
        taskTable.setItems(tasks);
        tasksCount.setText(String.valueOf(tasks.size()));
    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
