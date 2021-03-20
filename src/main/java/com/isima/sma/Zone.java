package com.isima.sma;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Zone extends Entity {

    private ZoneType zoneType;

    public Zone(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    public Zone(String zoneTypeName) {
        this(ZoneType.valueOf(zoneTypeName));
    }

    @Override
    public Paint fxRepresentation() {
        return zoneType.getPaint();
    }

    @Override
    public String toString() {
        return zoneType.toString();
    }
}
