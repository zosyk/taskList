package com.nangasystems.tasklist.dbo;

import java.util.Objects;

public class Task {

    private String name;
    private Long pID;
    private Integer usedMemory;

    public Task(String name, long pID, int usedMemory) {
        this.name = name;
        this.pID = pID;
        this.usedMemory = usedMemory;
    }

    public String getName() {
        return name;
    }

    public Long getpID() {
        return pID;
    }

    public Integer getUsedMemory() {
        return usedMemory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return pID == task.pID &&
                Objects.equals(name, task.name) &&
                Objects.equals(usedMemory, task.usedMemory);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, pID, usedMemory);
    }
}
