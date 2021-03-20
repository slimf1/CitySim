package com.isima.sma.entities;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Entity {
    public String conRepresentation() {
        return "E";
    }

    public Paint fxRepresentation() {
        return Color.BLACK;
    }
}
