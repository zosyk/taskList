package com.nangasystems.tasklist.dbo;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"name", "memory"})
public class Task implements Comparable<Task> {

    private String name;
    private long memory;
    private String id;

    public Task() {}

    public Task(String name, String id, long memory) {
        this.name = name;
        this.id = id;
        this.memory = memory;
    }

    public Task(String name, long memory) {
        this.name = name;
        this.memory = memory;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "memory")
    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (memory != task.memory) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        return id != null ? id.equals(task.id) : task.id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (int) (memory ^ (memory >>> 32));
        return result;
    }

    @Override
    public int compareTo(Task o) {
        return (int)(o.memory - this.memory);
    }

    public static Task empty() {
        return new Task("-", 0);
    }
}
