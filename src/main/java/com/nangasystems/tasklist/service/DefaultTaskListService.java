package com.nangasystems.tasklist.service;

import com.nangasystems.tasklist.dbo.ComparableTask;
import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.util.CompareStatus;
import com.nangasystems.tasklist.util.executor.CmdExecutor;
import com.nangasystems.tasklist.util.exporter.Exporter;
import com.nangasystems.tasklist.util.importer.Importer;
import com.nangasystems.tasklist.util.parser.TaskParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nangasystems.tasklist.util.CompareStatus.*;


@Service
public class DefaultTaskListService implements TaskListService {

    private final static String TASKLIST_COMMAND = "chcp.com 65001 && tasklist /fo csv";// 65001 - UTF-8 code

    private CmdExecutor cmdExecutor;
    private TaskParser taskParser;
    private Exporter exporter;
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

        return comparableTasks.sorted();
    }

    public void export(List<Task> tasks, File exportFile) {
        try {
            exporter.export(tasks, exportFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    @Required
    public void setCmdExecutor(CmdExecutor cmdExecutor) {
        this.cmdExecutor = cmdExecutor;
    }

    @Autowired
    @Required
    public void setTaskParser(TaskParser taskParser) {
        this.taskParser = taskParser;
    }

    @Autowired
    @Required
    public void setExporter(Exporter exporter) {
        this.exporter = exporter;
    }

    @Autowired
    @Required
    public void setImporter(Importer importer) {
        this.importer = importer;
    }
}
