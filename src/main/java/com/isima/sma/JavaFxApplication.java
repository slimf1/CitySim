package com.isima.sma;

import com.isima.sma.ui.FxUserInterface;
import com.isima.sma.utils.MTRandom;
import javafx.application.Application;

public class JavaFxApplication {
    public static void main(String[] args) {
        Application.launch(FxUserInterface.class, args);
        MTRandom.getInstance().setSeed(1);
    }
}
