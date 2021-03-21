package com.isima.sma.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZoneTest {

    @Test
    public void fxRepresentation() {
    }

    @Test
    public void conRepresentation() {
        assertEquals(new Zone("INDUSTRIAL", 0, 0).conRepresentation(), "I");
        assertEquals(new Zone("COMMERCIAL", 0, 0).conRepresentation(), "C");
        assertEquals(new Zone("OFFICE", 0, 0).conRepresentation(), "O");
        assertEquals(new Zone("RESIDENTIAL", 0, 0).conRepresentation(), "R");
    }

    @Test
    public void testToString() {
        assertEquals(new Zone("OFFICE", 0, 0).toString(), "Office");
        assertEquals(new Zone("RESIDENTIAL", 0, 0).toString(), "Residential");
        assertEquals(new Zone(ZoneType.INDUSTRIAL, 0, 0).toString(), "Industrial");
        assertEquals(new Zone(ZoneType.COMMERCIAL, 0, 0).toString(), "Commercial");
    }
}