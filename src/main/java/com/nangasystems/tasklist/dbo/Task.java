package com.nangasystems.tasklist.dbo;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Task implements Comparable<Task> {

    private SimpleStringProperty name;
    private SimpleStringProperty processID;
    private SimpleLongProperty usedMemory;

    public Task() {}

    public Task(String name, String processID, long usedMemory) {
        this.name = new SimpleStringProperty(name);
        this.processID = new SimpleStringProperty(processID);
        this.usedMemory = new SimpleLongProperty(usedMemory);
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleStringProperty getProcessID() {
        return processID;
    }

    public SimpleLongProperty getUsedMemory() {
        return usedMemory;
    }

    @XmlElement(name = "name")
    public String getNameValue() {
        return name.getValue();
    }

    public String getProcessIDValue() {
        return processID.getValue();
    }

    @XmlElement(name = "memory")
    public Long getUsedMemoryValue() {
        return usedMemory.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (getNameValue() != null ? !getNameValue().equals(task.getNameValue()) : task.getNameValue() != null) return false;
        if (getProcessIDValue() != null ? !getProcessIDValue().equals(task.getProcessIDValue()) : task.getProcessIDValue() != null) return false;
        return getUsedMemoryValue() != null ? getUsedMemoryValue().equals(task.getUsedMemoryValue()) : task.getUsedMemoryValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getNameValue() != null ? getNameValue().hashCode() : 0;
        result = 31 * result + (getProcessIDValue() != null ? getProcessIDValue().hashCode() : 0);
        result = 31 * result + (getUsedMemoryValue() != null ? getUsedMemoryValue().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name=" + name.getValue() +
                ", processID=" + processID.getValue() +
                ", usedMemory=" + usedMemory.getValue() +
                '}';
    }

    @Override
    public int compareTo(Task o) {
        return (int)(o.getUsedMemoryValue() - this.getUsedMemoryValue());
    }
}
