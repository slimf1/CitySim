package com.isima.sma;

import com.isima.sma.ui.FxUserInterface;
import com.isima.sma.utils.MTRandom;
import javafx.application.Application;

/**
 * Programme principal pour l'application graphique
 */
public class JavaFxApplication {
    public static void main(String[] args) {
        MTRandom.getInstance().setSeed(1);
        Application.launch(FxUserInterface.class, args);
    }
}
