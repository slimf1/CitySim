package com.isima.sma;

import com.isima.sma.gui.FxUserInterface;
import com.isima.sma.utils.MTRandom;
import javafx.application.Application;

public class JavaFxApplication {
    public static void main(String[] args) {
        MTRandom.getInstance().setSeed(1);
        Application.launch(FxUserInterface.class, args);
    }
}
