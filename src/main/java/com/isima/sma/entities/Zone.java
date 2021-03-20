package com.isima.sma.entities;

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
        String zoneTypeName = zoneType.toString();
        return zoneTypeName.charAt(0) + zoneTypeName.substring(1).toLowerCase();
    }
}
