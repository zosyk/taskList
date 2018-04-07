package com.nangasystems.tasklist;

import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.service.TaskListService;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static com.nangasystems.tasklist.util.converter.MemoryConverter.convert;

public class TaskListApplication extends Application{

    private TaskListService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initSpringContext();
        buildMainScreen(primaryStage);
    }

    private void buildMainScreen(Stage primaryStage) {
        primaryStage.setTitle("Task list");

        ObservableList<Task> tasks = service.getTasks();

        TableView<Task> taskTable = new TableView<>();
        taskTable.setItems(tasks);
        taskTable.getColumns().addAll(buildNameColumn(), buildProcessIDeColumn(), buildUsedMemoryColumn());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(taskTable);

        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }

    private TableColumn<Task, String> buildNameColumn() {
        TableColumn<Task, String> name = new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setCellValueFactory(param -> param.getValue().getName());

        return name;
    }

    private TableColumn<Task, Number> buildProcessIDeColumn() {
        TableColumn<Task, Number> processID = new TableColumn<>("Process ID");
        processID.setMinWidth(150);
        processID.setCellValueFactory(param -> param.getValue().getProcessID());

        return processID;
    }

    private TableColumn<Task, String> buildUsedMemoryColumn() {
        TableColumn<Task, String> usedMemory = new TableColumn<>("Used Memory");
        usedMemory.setMinWidth(150);
        usedMemory.setCellValueFactory(param -> new SimpleStringProperty(convert(param.getValue().getUsedMemoryValue())));

        return usedMemory;
    }

    private void initSpringContext() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        service = applicationContext.getBean(TaskListService.class);
    }
}
