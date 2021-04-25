package com.isima.sma.time;

import java.io.Serializable;

/**
 * Horloge pour la gestion du temps
 * dans l'application.
 * @author Barthélemy J.
 */
public class Clock implements Serializable {

    /**
     * Le nombre de ticks total dans une journée
     */
    public static final int TICK_MAX = 360;

    private static final long serialVersionUID = 2574525790696639327L;

    /**
     * Le nombre de ticks effectif
     */
    private int time;

    /**
     * Constructeur d'une horloge
     */
    public Clock(){
        time = 0;
    }

    /**
     * Conversion du nombre de ticks en une
     * chaîne représentant l'heure de la journée
     * en fonction du nombre de ticks maximum
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

    /**
     * Incrémente le nombre de ticks
     * @param incr Le nombre de ticks à incrémenter
     */
    public void incrementTime(int incr){
        int t = getTime() + incr;
        if(t >= TICK_MAX){
            t -= TICK_MAX;
        }
        setTime(t);
    }

    /**
     * Getter pour le nombre de ticks
     * @return Le nombre de ticks
     */
    public int getTime(){
        return this.time;
    }

    /**
     * Setter pour le nombre de ticks
     * @param t Le nouveau nombre de ticks
     */
    public void setTime(int t){
        this.time = t;
    }
}
