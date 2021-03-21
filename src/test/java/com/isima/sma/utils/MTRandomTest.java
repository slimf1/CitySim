package com.isima.sma.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MTRandomTest {

    @Test
    public void testSeeding() {
        MTRandom.getInstance().setSeed(1);
        int[] sample = new int[10];
        for(int i = 0; i < 10; ++i)
            sample[i] = MTRandom.getInstance().nextInt();

        MTRandom.getInstance().setSeed(1);
        int[] secondSample = new int[10];
        for(int i = 0; i < 10; ++i)
            secondSample[i] = MTRandom.getInstance().nextInt();

        assertArrayEquals(sample, secondSample);

        MTRandom.getInstance().setSeed(1);
    }
}