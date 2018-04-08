package com.nangasystems.tasklist.service;

import com.nangasystems.tasklist.dbo.ComparableTask;
import com.nangasystems.tasklist.util.CompareStatus;
import com.nangasystems.tasklist.util.exporter.Exporter;
import com.nangasystems.tasklist.util.importer.Importer;
import com.nangasystems.tasklist.util.parser.TaskParser;
import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.util.executor.CmdExecutor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nangasystems.tasklist.util.CompareStatus.*;


@Service
public class DefaultTaskListService implements TaskListService {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultTaskListService.class);

    private final static String TASKLIST_COMMAND = "chcp.com 65001 && tasklist /fo csv";// 65001 - UTF-8 code

    @Autowired
    private CmdExecutor cmdExecutor;

    @Autowired
    private TaskParser taskParser;

    @Autowired
    private Exporter exporter;

    @Autowired
    private Importer importer;

    public ObservableList<Task> getTasks() {

        List<String> lines = cmdExecutor.execute(TASKLIST_COMMAND);

        return taskParser.parse(lines);
    }

    @Override
    public ObservableList<Task> getGroupedTasks() {
        return getTasks().stream()
                .collect(
                        Collectors.groupingBy(
                                Task::getName,
                                Collectors.summingLong(Task::getMemory)))
                .entrySet().stream()
                .map(entry -> new Task(entry.getKey(), "", entry.getValue()))
                .sorted()
                .collect(Collectors.toCollection(
                        FXCollections::observableArrayList));
    }

    @Override
    public ObservableList<Task> importTasks(File file) throws Exception {
        return importer.importTasks(file);
    }

    @Override
    public ObservableList<ComparableTask> getComparableTasks(ObservableList<Task> dumpedTasks) {
        ObservableList<Task> runningTasks = getGroupedTasks();
        ObservableList<ComparableTask> comparableTasks = FXCollections.observableArrayList();

        Map<String, Task> dumpedMap = dumpedTasks.stream()
                .collect(
                        Collectors.toMap(Task::getName, Function.identity()));

        for(Task task: runningTasks) {
            CompareStatus status = UNCHANGED;
            Task dumpedTask = dumpedMap.get(task.getName());
            if(dumpedTask!=null) {
                if(dumpedTask.getMemory() != task.getMemory()) {
                    status = CHANGED;
                }
                dumpedMap.remove(task.getName());
            } else {
                dumpedTask = Task.empty();
                status = ADDED;
            }
            comparableTasks.add(new ComparableTask(task, status, dumpedTask));
        }

        dumpedMap.values().stream()
                .forEach(task ->
                        comparableTasks.add(
                                new ComparableTask(Task.empty(), REMOVED, task)));

        return comparableTasks;
    }

    public void export(List<Task> tasks, File exportFile) {
        try {
            exporter.export(tasks, exportFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
