package org.example.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Aventurier {
    private final String nom;
    private Position position;
    private Orientation orientation;
    private String parcours;
    private int nbTresors = 0;
    private final Carte carte;

    public Aventurier(String nom, int x, int y, Orientation orientation, String parcours, Carte carte) {
        Position pos = new Position(x, y);
        if (isValide(pos, carte)) {
            this.nom = nom;
            this.position = pos;
            this.orientation = orientation;
            this.parcours = parcours;
            this.carte = carte;
            // On peut actionner la collecte du trésor si l'aventurier démarre sur une case "Trésor"
        } else {
            throw new IllegalArgumentException("Cette position sur la carte n'est pas occupable par un aventurier");
        }
    }

    public Aventurier executeActionSuivante() {
        if (!parcours.isEmpty()) {
            switch (Deplacement.valueOf(parcours.substring(0, 1))) {
                case A -> avance();
                case D -> droite();
                case G -> gauche();
            }
            parcours = parcours.substring(1);
        }
        return this;
    }

    public Aventurier executeParcours() {
        while (!parcours.isEmpty()) {
            executeActionSuivante();
        }
        return this;
    }

    private Aventurier avance() {
        Position destination = new Position(position.hor(), position.ver());
        switch (orientation) {
            case N -> destination = new Position(position.hor(), position.ver() - 1);
            case E -> destination = new Position(position.hor() + 1, position.ver());
            case S -> destination = new Position(position.hor(), position.ver() + 1);
            case O -> destination = new Position(position.hor() - 1, position.ver());
        }
        if (isValide(destination, carte)) {
            position = destination;
            collecte();
        }
        return this;
    }

    private Aventurier droite() {
        switch (orientation) {
            case N -> orientation = Orientation.E;
            case E -> orientation = Orientation.S;
            case S -> orientation = Orientation.O;
            case O -> orientation = Orientation.N;
        }
        return this;
    }

    private Aventurier gauche() {
        switch (orientation) {
            case N -> orientation = Orientation.O;
            case O -> orientation = Orientation.S;
            case S -> orientation = Orientation.E;
            case E -> orientation = Orientation.N;
        }
        return this;
    }

    private Aventurier collecte() {
        List<Tresor> tresorsCopy = new ArrayList<>(carte.getTresors());
        for (Tresor tresor : tresorsCopy) {
            if (position.equals(tresor.getPos())) {
                carte.decompteTresor(tresor);
                nbTresors += 1;
            }
        }
        return this;
    }

    private static boolean isValide(Position dest, Carte carte) {
        if (dest.hor() < 0 || dest.hor() >= carte.getLargeur()) {
            return false;
        }
        if (dest.ver() < 0 || dest.ver() >= carte.getHauteur()) {
            return false;
        }
        // L'aventurier ne peut aller sur une position occupée par une montagne ou un
        // autre aventurier.
        return carte.getMontagnes().stream()
                .noneMatch(m -> m.position().equals(dest))
                &&
                carte.getAventuriers().stream()
                        .noneMatch(a -> a.getPosition().equals(dest));
    }

    public String toString() {
        return Type.A + " - " + nom + " - " + position.hor() + " - " + position.ver() + " - " +
                orientation + " - " + nbTresors + "\n";
    }

}
