package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.stream.Collectors;

import static com.nangasystems.tasklist.util.converter.MemoryConverter.convert;

public class TaskListController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskListController.class);

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
    private void refreshTasks() {
        ObservableList<Task> tasks = taskListService.getTasks();
        if(groupCheckBox.isSelected()) {
            taskTable.setItems(tasks);
            groupTasks();
        } else {
            refreshTasks(tasks);
        }
    }

    @FXML
    private void groupTasks() { //todo move to service
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

    @FXML
    private void exportToXml() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                taskListService.export(taskTable.getItems(), file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOG.error("Unable to exporter to a null file");
        }
    }

    private void refreshTasks(ObservableList<Task> tasks) {
        taskTable.setItems(tasks);
        tasksCount.setText(String.valueOf(tasks.size()));
    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
