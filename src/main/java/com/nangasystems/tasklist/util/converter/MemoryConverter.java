package com.nangasystems.tasklist.util.converter;

import static com.nangasystems.tasklist.util.MemoryBlock.*;
import static java.lang.String.format;

public class MemoryConverter {

    private static final String MEMORY_FORMAT = "%.2f %s";

    public static String convert(Long value) {
        if (value == 0) return "-";

        if (value >= MB.getValue() && value < GB.getValue()) {
            return format(MEMORY_FORMAT, 1.f * value / MB.getValue(), MB.name());
        } else if (value >= GB.getValue()) {
            return format(MEMORY_FORMAT, 1.f * value / GB.getValue(), GB.name());
        } else {
            return format("%d %s", value, KB.name());
        }
    }
}
