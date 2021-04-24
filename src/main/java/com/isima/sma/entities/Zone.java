package com.isima.sma.entities;

import javafx.scene.paint.Paint;

/**
 * Zone de la ville
 * @author Slimane F.
 */
public class Zone extends Entity {

    private ZoneType zoneType; // Type de la zone

    /**
     * Constructeur d'une zone
     * @param zoneType Type de la zone
     * @param x Abscisse de la zone
     * @param y Ordonnée de la zone
     */
    public Zone(ZoneType zoneType, int x, int y) {
        super(x, y);
        this.zoneType = zoneType;
    }

    /**
     * Constructeur d'une zone
     * @param zoneTypeName Nom du type de la zone
     * @param x Abscisse de la zone
     * @param y Ordonnée de la zone
     */
    public Zone(String zoneTypeName, int x, int y) {
        this(ZoneType.valueOf(zoneTypeName), x, y);
    }

    /**
     * Type d'une zone
     * @return Le type d'une zone
     */
    public final ZoneType getZoneType() {
        return zoneType;
    }

    /**
     * Représentation graphique de la
     * zone. Se base sur son type.
     * @return La représentation graphique de
     * la zone
     */
    @Override
    public Paint fxRepresentation() {
        return zoneType.getPaint();
    }

    /**
     * Représentation console d'une zone.
     * Définie comme la première lettre du
     * nom de son type.
     * @return La représentation console d'une
     * zone
     */
    @Override
    public String conRepresentation() {
        return zoneType.toString().substring(0, 1);
    }

    /**
     * Chaîne représentant la zone
     * @return Une chaîne représentant la zone
     */
    @Override
    public String toString() {
        String zoneTypeName = zoneType.toString();
        return zoneTypeName.charAt(0) + zoneTypeName.substring(1).toLowerCase();
    }
}
