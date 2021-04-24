package com.isima.sma.time;

/**
 * Partie d'une journée
 * @author Barthélemy J.
 */
public enum TimeOfDay {
    DAWN(4, 7),
    MORNING(7, 12),
    MIDDAY(12, 14),
    AFTERNOON(14, 18),
    DUSK(18, 21),
    NIGHT(21, 4);

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
