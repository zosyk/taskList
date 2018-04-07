package com.nangasystems.tasklist.util;

public enum MemoryBlock {

    KB(1),
    MB(1024),
    GB(1024 * 1024);

    private long value;

    MemoryBlock(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}