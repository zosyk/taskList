package com.nangasystems.tasklist;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskListApplication extends Application{

    private TaskListService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initSpringContext();

        TableView<Task> taskTable;

        primaryStage.setTitle("Task list");

        //name column
        TableColumn<Task, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //pID column
        TableColumn<Task, Integer> pIDColumn = new TableColumn<>("pID");
        pIDColumn.setMinWidth(200);
//        pIDColumn.setCellValueFactory(new PropertyValueFactory<>("pID"));

        //usedMemory column
        TableColumn<Task, Integer> usedMemoryColumn = new TableColumn<>("usedMemory");
        usedMemoryColumn.setMinWidth(200);
//        usedMemoryColumn.setCellValueFactory(new PropertyValueFactory<>("usedMemory"));


        taskTable = new TableView<>();

        ObservableList<Task> tasks = service.getTasks();
        taskTable.setItems(tasks);
        taskTable.getColumns().addAll(nameColumn, pIDColumn, usedMemoryColumn);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(taskTable);

        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }

    private void initSpringContext() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        service = applicationContext.getBean(TaskListService.class);
    }
}
