package com.isima.sma;

import com.isima.sma.ui.ConsoleUserInterface;
import com.isima.sma.utils.MTRandom;

/**
 * Programme principal pour la version console
 */
public class ConsoleApplication {
    public static void main(String[] args) {
        ConsoleUserInterface ui = new ConsoleUserInterface();
        MTRandom.getInstance().setSeed(1);
        ui.start();
    }
}
