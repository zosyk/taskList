package com.nangasystems.tasklist;

import com.nangasystems.tasklist.controller.ComparableTaskListController;
import com.nangasystems.tasklist.controller.MenuBarController;
import com.nangasystems.tasklist.controller.TaskListController;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;

public class TaskListApplication extends Application{

    private ApplicationContext applicationContext;
    private BorderPane root;
    private TaskListService taskListService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initSpringContext();
        initRootLayout(primaryStage);
        initTaskListLayout();

        primaryStage.show();
    }

    private void initTaskListLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/taskList.fxml"));
        VBox taskListBox = fxmlLoader.load();
        TaskListController taskListController = fxmlLoader.getController();
        taskListController.setTaskListService(taskListService);
        taskListController.initTaskTable();

        root.setCenter(taskListBox);
    }

    private void initRootLayout(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Task List");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/rootLayout.fxml"));
        root = fxmlLoader.load();
        MenuBarController menuBarController = fxmlLoader.getController();
        menuBarController.setTaskListService(taskListService);
        menuBarController.setApplication(this);

        primaryStage.setScene(new Scene(root));
    }

    private void initSpringContext() {
        applicationContext = new ClassPathXmlApplicationContext("spring/application-context.xml");
        taskListService = applicationContext.getBean(TaskListService.class);
    }

    public void openCompareTaskListsWindow(File file) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/compareTaskLists.fxml"));
        try {
            VBox vBox = fxmlLoader.load();
            ComparableTaskListController controller = fxmlLoader.getController();
            controller.setDumpedTaskFile(file);
            controller.setTaskListService(taskListService);
            controller.init();

            Stage window = new Stage();
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
