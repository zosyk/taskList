package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.TaskListApplication;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MenuBarController {

    private static final Logger LOG = LoggerFactory.getLogger(MenuBarController.class);

    private TaskListService taskListService;
    private TaskListApplication application;

    @FXML
    private void exportToXml() {
        FileChooser fileChooser = getFileChooser();
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            taskListService.export(taskListService.getGroupedTasks(), file);
        } else {
            LOG.error("Unable to export. File is null!");
        }
    }

    private FileChooser getFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    @FXML
    private void importFromXml() {
        File file = getFileChooser().showOpenDialog(null);
        if (file != null) {
            application.compareTaskLists(file);
        } else {
            LOG.error("Unable to import. File is null!");//todo think about error dialog
        }
    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    public void setApplication(TaskListApplication application) {
        this.application = application;
    }
}
