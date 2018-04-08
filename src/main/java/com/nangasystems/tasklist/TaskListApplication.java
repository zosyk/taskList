package com.nangasystems.tasklist;

import com.nangasystems.tasklist.controller.TaskListController;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TaskListApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(TaskListApplication.class);

    private ApplicationContext applicationContext;
    private BorderPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initSpringContext();
        primaryStage.setTitle("Task List");

        try {
            initRootLayout(primaryStage);
            initTaskListLayout();

            primaryStage.show();
        } catch (IOException e) {
            LOG.error("Unable to load root layout. Error message: {}", e.getMessage());
        }
    }

    private void initTaskListLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("taskList.fxml"));
        VBox taskListView = fxmlLoader.load();
        TaskListController taskListController = fxmlLoader.getController();
        taskListController.setTaskListService(applicationContext.getBean(TaskListService.class));
        taskListController.initTaskTable();

        root.setCenter(taskListView);
    }

    private void initRootLayout(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("root.fxml"));
        root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
    }

    private void initSpringContext() {
        applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
    }
}
