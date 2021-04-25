package com.isima.sma.time;

/**
 * Partie d'une journée
 * @author Barthélemy J.
 */
public enum TimeOfDay {
    NIGHT(25, 4),
    DAWN(4, 6),
    MORNING(6, 12),
    MIDDAY(12, 17),
    AFTERNOON(17, 22),
    DUSK(22, 25);

    /**
     * Borne inférieure de l'heure de la période
     */
    private int lower;
    /**
     * Borne supérieure de l'heure de la période
     */
    private int upper;

    TimeOfDay(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * @return La borne inférieure de l'heure
     * de la période
     */
    public int getLower() {
        return lower;
    }

    /**
     * @return La borne supérieure de l'heure
     * de la période
     */
    public int getUpper() {
        return upper;
    }
}
