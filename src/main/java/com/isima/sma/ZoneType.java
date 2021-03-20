package com.isima.sma;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum ZoneType {
    RESIDENTIAL(Color.GREEN),
    COMMERCIAL(Color.BLUE),
    INDUSTRIAL(Color.YELLOW),
    OFFICE(Color.CYAN);

    private Paint paint;

    ZoneType(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }
}
