package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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
    private TableColumn<Task, String> processIDColumn;

    @FXML
    private TableColumn<Task, String> memoryColumn;

    @FXML
    private Label tasksCount;

    @FXML
    private CheckBox groupCheckBox;

    public void initTaskTable() {
        refreshTasks(taskListService.getTasks());

        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        processIDColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        memoryColumn.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getMemory())));

    }

    @FXML
    private void refreshTasks() {
        ObservableList<Task> tasks = taskListService.getTasks();
        if (groupCheckBox.isSelected()) {
            taskTable.setItems(tasks);
            groupTasks();
        } else {
            refreshTasks(tasks);
        }
    }

    @FXML
    private void groupTasks() {
        ObservableList<Task> tasks;
        if (groupCheckBox.isSelected()) {
            tasks = taskListService.getGroupedTasks();
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
