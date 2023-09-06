package com.iam.serviceacquisition.common.enums;

public enum TeamSize {
    SIZE_3_TO_5(1, 3, 5),
    SIZE_6_TO_10(2, 6, 10),
    SIZE_11_TO_15(3, 11, 15);

    private final int id;
    private final long min;
    private final long max;

    TeamSize(int id, long min, long max) {
        this.id = id;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
