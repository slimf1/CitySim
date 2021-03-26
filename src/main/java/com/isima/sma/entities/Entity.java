package com.isima.sma.entities;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 7808675342380405440L;

    private int x;
    private int y;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public String conRepresentation() {
        return "E";
    }

    public Paint fxRepresentation() {
        return Color.BLACK;
    }
}
