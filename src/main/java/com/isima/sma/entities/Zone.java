package com.isima.sma.entities;

import javafx.scene.paint.Paint;

public class Zone extends Entity {

    private ZoneType zoneType;

    public Zone(ZoneType zoneType, int x, int y) {
        super(x, y);
        this.zoneType = zoneType;
    }

    public Zone(String zoneTypeName, int x, int y) {
        this(ZoneType.valueOf(zoneTypeName), x, y);
    }

    public final ZoneType getZoneType() {
        return zoneType;
    }

    @Override
    public Paint fxRepresentation() {
        return zoneType.getPaint();
    }

    @Override
    public String conRepresentation() {
        return zoneType.toString().substring(0, 1);
    }

    @Override
    public String toString() {
        String zoneTypeName = zoneType.toString();
        return zoneTypeName.charAt(0) + zoneTypeName.substring(1).toLowerCase();
    }
}
