package com.isima.sma;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Entity {
    public String conRepresentation() {
        return "E";
    }

    public Paint fxRepresentation() {
        return Color.BLACK;
    }

    public String name() {
        return "Default Entity";
    }
}
