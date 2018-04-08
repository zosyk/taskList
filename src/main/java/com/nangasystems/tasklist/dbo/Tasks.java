package com.nangasystems.tasklist.dbo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
public class Tasks {

    private List<Task> tasks;

    public Tasks() {}

    public Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @XmlElement(name = "task")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}