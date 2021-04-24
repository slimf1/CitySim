package com.isima.sma.utils;

import java.util.Objects;

/**
 * Représente un couple d'objets
 * quelconques
 * @param <T1> Le type du premier élément
 * @param <T2> Le type du second élément
 */
public final class Pair<T1, T2> {

    private final T1 first; // Le premier élément
    private final T2 second; // Le second élément

    /**
     * Constructeur d'un couple
     * @param first Le premier élément du couple
     * @param second Le second élément du couple
     */
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return Le premier élément du couple
     */
    public final T1 getFirst() {
        return first;
    }

    /**
     * @return Le second élément du couple
     */
    public final T2 getSecond() {
        return second;
    }

    /**
     * @return La signature d'un couple de valeurs
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    /**
     * Teste l'égalité entre deux couples
     * @param obj Le second couple
     * @return {@code true} si les deux couples sont
     * égaux, {@code false} sinon
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }

    /**
     * @return La représentation en chaîne du couple
     */
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

}
