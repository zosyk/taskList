package com.nangasystems.tasklist.util.exporter;

import com.nangasystems.tasklist.dbo.Task;

import java.io.File;
import java.util.List;

public interface Exporter {

    void export(List<Task> tasks, File exportFile) throws Exception;
}
