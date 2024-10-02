package org.example.model;

import lombok.Data;

@Data
public class Tresor {
    private final Position pos;
    private int quantite;

    public Tresor(int hor, int ver, int quantite) {
        if (quantite > 0) {
            pos = new Position(hor, ver);
            this.quantite = quantite;
        } else {
            throw new IllegalArgumentException("Un trésor ne peut avoir une quantité inférieure à 1");
        }
    }

    public void decremente() {
        if (quantite > 1) {
            quantite -= 1;
        } else {
            throw new IllegalArgumentException("Un trésor ne peut avoir une quantité inférieure à 1");
        }
    }

    @Override
    public String toString() {
        return Type.T + " - " + pos.toString() + " - " + quantite + "\n";
    }
}
