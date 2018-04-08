package com.nangasystems.tasklist.dbo;

import com.nangasystems.tasklist.util.CompareStatus;

public class ComparableTask {
    private Task running;
    private CompareStatus status;
    private Task dumped;

    public ComparableTask(Task running, CompareStatus status, Task dumped) {
        this.running = running;
        this.status = status;
        this.dumped = dumped;
    }

    public Task getRunning() {
        return running;
    }

    public void setRunning(Task running) {
        this.running = running;
    }

    public Task getDumped() {
        return dumped;
    }

    public void setDumped(Task dumped) {
        this.dumped = dumped;
    }

    public CompareStatus getStatus() {
        return status;
    }

    public void setStatus(CompareStatus status) {
        this.status = status;
    }
}
