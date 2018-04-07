package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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


    public void initTaskTable() {
        ObservableList<Task> tasks = taskListService.getTasks();

        nameColumn.setCellValueFactory(param -> param.getValue().getName());
        processIDColumn.setCellValueFactory(param -> param.getValue().getProcessID());
        memoryColumn.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getUsedMemoryValue())));

        taskTable.setItems(tasks);
    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
