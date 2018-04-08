package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.ComparableTask;
import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import com.nangasystems.tasklist.util.CompareStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.File;
import java.util.List;

import static com.nangasystems.tasklist.util.CompareStatus.ADDED;
import static com.nangasystems.tasklist.util.CompareStatus.CHANGED;
import static com.nangasystems.tasklist.util.CompareStatus.REMOVED;
import static com.nangasystems.tasklist.util.converter.MemoryConverter.convert;
import static java.util.Arrays.asList;


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
        initColumnValues();
        initCellFactory();

        compareTaskTable.setItems(comparableTasks);
    }

    private void initCellFactory() {
        leftName.setCellFactory(buildHighlightFactory(asList(ADDED)));
        leftMemory.setCellFactory(buildHighlightFactory(asList(ADDED, CHANGED)));
        rightName.setCellFactory(buildHighlightFactory(asList(REMOVED)));
        rightMemory.setCellFactory(buildHighlightFactory(asList(REMOVED, CHANGED)));
    }

    private Callback<TableColumn<ComparableTask, String>, TableCell<ComparableTask, String>> buildHighlightFactory(List<CompareStatus> allowedStatuses) {
        return new Callback<TableColumn<ComparableTask, String>, TableCell<ComparableTask, String>>() {
            @Override
            public TableCell<ComparableTask, String> call(TableColumn<ComparableTask, String> soCalledFriendStringTableColumn) {
                return new TableCell<ComparableTask, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        TableRow<ComparableTask> tableRow = getTableRow();
                        ComparableTask task = tableRow.getItem();

                        setStyle("");

                        if(task == null || empty) return;

                        setText(item);

                        CompareStatus status = task.getStatus();

                        if(!allowedStatuses.contains(status)) return;

                        switch (status) {
                            case CHANGED:
                                setStyle("-fx-background-color: greenyellow; -fx-opacity: 0.5");
                                break;
                            case ADDED:
                                setStyle("-fx-background-color: yellowgreen");
                                break;
                            case REMOVED:
                                setStyle("-fx-background-color: #ff4c3c");
                                break;
                            default:
                                setStyle("");
                        }
                    }
                };
            }
        };
    }

    private void initColumnValues() {
        leftName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRunning().getName()));
        leftMemory.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getRunning().getMemory())));
        status.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus().name()));
        rightName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDumped().getName()));
        rightMemory.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getDumped().getMemory())));
    }

    public void setDumpedTaskFile(File dumpedTaskFile) {
        this.dumpedTaskFile = dumpedTaskFile;
    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
