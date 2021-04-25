package com.isima.sma.entities;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Le type d'une zone de la ville
 * @author Slimane F.
 */
public enum ZoneType {
    RESIDENTIAL(Color.GREEN),
    COMMERCIAL(Color.BLUE),
    INDUSTRIAL(Color.YELLOW),
    OFFICE(Color.CYAN);

    /**
     * Représentation graphique du type
     */
    private Paint paint;

    ZoneType(Paint paint) {
        this.paint = paint;
    }

    /**
     * Retourne la représentation graphique du type
     * @return La représentation graphique du type
     */
    public Paint getPaint() {
        return paint;
    }
}
