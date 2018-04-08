package com.nangasystems.tasklist.service;

import com.nangasystems.tasklist.util.exporter.Exporter;
import com.nangasystems.tasklist.util.parser.TaskParser;
import com.nangasystems.tasklist.dbo.Task;
import com.nangasystems.tasklist.util.executor.CmdExecutor;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;


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

    public ObservableList<Task> getTasks() {

        List<String> lines = cmdExecutor.execute(TASKLIST_COMMAND);

        return taskParser.parse(lines);
    }

    public void export(List<Task> tasks, File exportFile) throws Exception {
        exporter.export(tasks, exportFile);
    }
}
