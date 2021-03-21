package com.isima.sma.entities;

import static org.junit.Assert.*;

public class ZoneTypeTest {

    @org.junit.Test
    public void valueOf() {
        assertEquals(ZoneType.OFFICE, ZoneType.valueOf("OFFICE"));
    }

    @org.junit.Test
    public void testToString() {
        assertEquals("RESIDENTIAL", ZoneType.RESIDENTIAL.toString());
    }
}