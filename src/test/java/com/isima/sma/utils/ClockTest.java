package com.isima.sma.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClockTest {



    @Test
    public void testIncrClock(){
        int oldValue = Clock.getInstance().getTime();
        Clock.getInstance().incrementTime(10);
        assertEquals(oldValue + 10, Clock.getInstance().getTime());
    }

    @Test
    public void testSetClock(){
        int newValue = 3;
        Clock.getInstance().setTime(3);
        assertEquals(newValue, Clock.getInstance().getTime());
    }
}
