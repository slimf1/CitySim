package com.isima.sma.utils;

public class Clock {

    public static final int TICK_MAX = 300;

    private int time;
    private static final Clock instance = new Clock();

    private Clock(){
        time = 0;
    }

    public static Clock getInstance(){
        return instance;
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
