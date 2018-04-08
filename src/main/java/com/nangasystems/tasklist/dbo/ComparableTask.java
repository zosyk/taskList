package com.nangasystems.tasklist.dbo;

import com.nangasystems.tasklist.util.CompareStatus;

import java.util.Optional;

public class ComparableTask implements Comparable<ComparableTask> {
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

    public Task getDumped() {
        return dumped;
    }

    public CompareStatus getStatus() {
        return status;
    }

    @Override
    public int compareTo(ComparableTask comparableTask) {
        long thisRunning = Optional.ofNullable(running).orElseGet(Task::empty).getMemory();
        long thisDumped = Optional.ofNullable(dumped).orElseGet(Task::empty).getMemory();

        long thisMaxMemory = Math.max(thisRunning, thisDumped);

        long thatRunning = Optional.ofNullable(comparableTask.getRunning()).orElseGet(Task::empty).getMemory();
        long thatDumped = Optional.ofNullable(comparableTask.getDumped()).orElseGet(Task::empty).getMemory();

        long thatMaxMemory = Math.max(thatRunning, thatDumped);

        return (int) (thatMaxMemory - thisMaxMemory);
    }
}
