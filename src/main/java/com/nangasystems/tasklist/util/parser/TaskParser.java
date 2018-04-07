package com.nangasystems.tasklist.util.parser;

import com.nangasystems.tasklist.dbo.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskParser {
    public ObservableList<Task> parse(List<String> lines) {
        List<Task> tasks = lines.stream()
                .skip(4)
                .map(line -> line
                        .replace("\"", "")
                        .replace(" K", "")
                        .split(","))
                .map(array ->
                        new Task(
                                array[0],
                                Long.valueOf(array[1]),
                                Long.valueOf(array[4].replaceAll("(?!\\d).", ""))))
                .sorted()
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(tasks);
    }
}
