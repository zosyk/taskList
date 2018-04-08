package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.stream.Collectors;

import static com.nangasystems.tasklist.util.converter.MemoryConverter.convert;

public class TaskListController {

    private TaskListService taskListService;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> nameColumn;

    @FXML
    private TableColumn<Task, String> processIDColumn;

    @FXML
    private TableColumn<Task, String> memoryColumn;

    @FXML
    private Label tasksCount;

    @FXML
    private CheckBox groupCheckBox;

    public void initTaskTable() {
        refreshTasks(taskListService.getTasks());

        nameColumn.setCellValueFactory(param -> param.getValue().getName());
        processIDColumn.setCellValueFactory(param -> param.getValue().getProcessID());
        memoryColumn.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getUsedMemoryValue())));

    }

    @FXML
    public void refreshTasks() {
        ObservableList<Task> tasks = taskListService.getTasks();
        if(groupCheckBox.isSelected()) {
            taskTable.setItems(tasks);
            groupTasks();
        } else {
            refreshTasks(tasks);
        }
    }

    @FXML
    public void groupTasks() {
        ObservableList<Task> tasks;
        if (groupCheckBox.isSelected()) {
            tasks = taskTable.getItems().stream()
                    .collect(
                            Collectors.groupingBy(
                                    Task::getNameValue,
                                    Collectors.summingLong(Task::getUsedMemoryValue)))
                    .entrySet().stream()
                    .map(entry -> new Task(entry.getKey(), "", entry.getValue()))
                    .sorted()
                    .collect(Collectors.toCollection(
                            FXCollections::observableArrayList));
        } else {
            tasks = taskListService.getTasks();
        }
        refreshTasks(tasks);
    }

    private void refreshTasks(ObservableList<Task> tasks) {
        taskTable.setItems(tasks);
        tasksCount.setText(String.valueOf(tasks.size()));
    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
