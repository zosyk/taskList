package com.nangasystems.tasklist;

import com.nangasystems.tasklist.controller.TaskListController;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TaskListApplication extends Application{

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initSpringContext();
        primaryStage.setTitle("Task List");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("tableView.fxml"));
        VBox tableView = fxmlLoader.load();
        TaskListController taskListController = fxmlLoader.getController();
        taskListController.setTaskListService(applicationContext.getBean(TaskListService.class));
        taskListController.initTaskTable();

        primaryStage.setScene(new Scene(tableView));
        primaryStage.show();
    }

    private void initSpringContext() {
        applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
    }
}
