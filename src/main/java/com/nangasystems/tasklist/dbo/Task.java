package com.nangasystems.tasklist.dbo;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {

    private SimpleStringProperty name;
    private SimpleLongProperty processID;
    private SimpleLongProperty usedMemory;

    public Task(String name, long processID, long usedMemory) {
        this.name = new SimpleStringProperty(name);
        this.processID = new SimpleLongProperty(processID);
        this.usedMemory = new SimpleLongProperty(usedMemory);
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleLongProperty getProcessID() {
        return processID;
    }

    public SimpleLongProperty getUsedMemory() {
        return usedMemory;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public Long getProcessIDValue() {
        return processID.getValue();
    }

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
}
