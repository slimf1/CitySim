package com.isima.sma.utils;

import com.isima.sma.time.Clock;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClockTest {

    @Test
    public void testIncrClock(){
        Clock clock = new Clock();
        int oldValue = clock.getTime();
        clock.incrementTime(10);
        assertEquals(oldValue + 10, clock.getTime());
    }

    @Test
    public void testSetClock(){
        Clock clock = new Clock();
        int newValue = 3;
        clock.setTime(3);
        assertEquals(newValue, clock.getTime());
    }

    @Test
    public void formatTime() {
        assertEquals("00:00", Clock.formatTime(0));
        assertEquals("12:00", Clock.formatTime(Clock.TICK_MAX / 2));
        assertEquals("08:00", Clock.formatTime(Clock.TICK_MAX / 3));
        assertEquals("06:00", Clock.formatTime(Clock.TICK_MAX / 4));
        assertEquals("00:40", Clock.formatTime(Clock.TICK_MAX / 36));
        assertEquals("00:04", Clock.formatTime(1));
    }
}
