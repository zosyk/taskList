package com.nangasystems.tasklist.util.importer;

import com.nangasystems.tasklist.dbo.Task;
import javafx.collections.ObservableList;

import java.io.File;

public interface Importer {

    ObservableList<Task> importTasks(File file) throws Exception;
}
