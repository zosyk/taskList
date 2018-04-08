package com.nangasystems.tasklist.controller;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MenuBarController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskListController.class);

    private TaskListService taskListService;

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
            try {
                ObservableList<Task> importedTasks = taskListService.importTasks(file);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("compareTaskLists.fxml"));
                VBox vBox = fxmlLoader.load();

                Stage window = new Stage();
//
                window.initModality(Modality.APPLICATION_MODAL);
//
//        VBox vBox = new VBox(10);
//
                Scene scene = new Scene(vBox);

                window.setScene(scene);
                window.show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOG.error("Unable to import. File is null!");//todo think about error dialog
        }

    }

    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }
}
