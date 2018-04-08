package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.ComparableTask;
import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;

import static com.nangasystems.tasklist.util.converter.MemoryConverter.convert;


public class ComparableTaskListController {

    private TaskListService taskListService;
    private File dumpedTaskFile;

    @FXML
    private TableView compareTaskTable;
    @FXML
    private TableColumn<ComparableTask, String> leftName;
    @FXML
    private TableColumn<ComparableTask, String> leftMemory;
    @FXML
    private TableColumn<ComparableTask, String> status;
    @FXML
    private TableColumn<ComparableTask, String> rightName;
    @FXML
    private TableColumn<ComparableTask, String> rightMemory;


    public void init() throws Exception {
        ObservableList<Task> dumpedTasks = taskListService.importTasks(dumpedTaskFile);
        ObservableList<ComparableTask> comparableTasks = taskListService.getComparableTasks(dumpedTasks);

        initTable(comparableTasks);
    }

    private void initTable(ObservableList<ComparableTask> comparableTasks) {
        leftName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRunning().getName()));
        leftMemory.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getRunning().getMemory())));
        status.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus().name()));
        rightName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDumped().getName()));
        rightMemory.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getDumped().getMemory())));

        compareTaskTable.setItems(comparableTasks);
    }

    public void setDumpedTaskFile(File dumpedTaskFile) {
        this.dumpedTaskFile = dumpedTaskFile;
    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
