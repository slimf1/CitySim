package com.isima.sma.utils;

public class Clock {

    public static final int TICK_MAX = 360;

    private int time;
    private static final Clock instance = new Clock();

    private Clock(){
        time = 0;
    }

    public static Clock getInstance(){
        return instance;
    }

    /**
     * Conversion du nombre de ticks en une
     * chaîne représentant l'heure de la journée
     * en fonction du nombre de ticks maximum
     * @author Slimane F.
     * @param ticks Le nombre de ticks
     * @return Une chaîne représentant l'heure courante
     * en fonction du nombre de ticks maximum
     */
    public static String formatTime(int ticks) {
        int totalMinutes = ticks * 24 * 60 / TICK_MAX;
        int hour = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hour, minutes);
    }

    public void incrementTime(int incr){
        int t = getTime() + incr;
        if(t >= TICK_MAX){
            t -= TICK_MAX;
        }
        setTime(t);
    }

    public int getTime(){
        return this.time;
    }

    void setTime(int t){
        this.time = t;
    }
}
