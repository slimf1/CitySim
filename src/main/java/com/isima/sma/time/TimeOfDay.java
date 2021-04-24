package com.isima.sma.time;


public enum TimeOfDay {
    NIGHT(25, 4),
    DAWN(4, 6),
    MORNING(6, 12),
    MIDDAY(12, 17),
    AFTERNOON(17, 22),
    DUSK(22, 25);


    private int lower;
    private int upper;

    TimeOfDay(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }
}
