package com.isima.sma.entities;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

/**
 * Entité quelconque de la simulation
 * @author Slimane F.
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 7808675342380405440L;

    /**
     * L'abscisse du point de l'entité
     */
    private final int x;
    /**
     * L'ordonnée du point de l'entité
     */
    private final int y;

    /**
     * Constructeur d'une entité
     * @param x L'abscisse du point de l'entité
     * @param y L'ordonnée du point de l'entité
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Abscisse de l'entité
     * @return L'indice de la colonne de l'entité
     * dans la matrice
     */
    public final int getX() {
        return x;
    }

    /**
     * Ordonnée de l'entité
     * @return L'indice de la ligne de l'entité
     * dans la matrice
     */
    public final int getY() {
        return y;
    }

    /**
     * Indique si une entité est un
     * type de route
     * @return {@code true} si l'entité est une route
     * {@code false} sinon
     */
    public boolean isRoad() {
        return false;
    }

    /**
     * Représentation console de l'entité
     * @return Chaîne représentant l'entité
     * dans un affichage console
     */
    public String conRepresentation() {
        return "E";
    }

    /**
     * Réprésentation graphique de l'entité
     * @return Peinture représentant l'entité
     * dans un affichage Fx.
     */
    public Paint fxRepresentation() {
        return Color.BLACK;
    }
}
