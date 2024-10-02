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

    public Aventurier avance() {
        Position destination = new Position(position.getHor(), position.getVer());
        switch (orientation) {
            case N -> destination.setVer(destination.getVer() - 1);
            case E -> destination.setHor(destination.getHor() + 1);
            case S -> destination.setVer(destination.getVer() + 1);
            case O -> destination.setHor(destination.getHor() - 1);
        }
        if (isValide(destination, carte)) {
            position = destination;
            collecte();
        }
        return this;
    }

    public Aventurier droite() {
        switch (orientation) {
            case N -> orientation = Orientation.E;
            case E -> orientation = Orientation.S;
            case S -> orientation = Orientation.O;
            case O -> orientation = Orientation.N;
        }
        return this;
    }

    public Aventurier gauche() {
        switch (orientation) {
            case N -> orientation = Orientation.O;
            case O -> orientation = Orientation.S;
            case S -> orientation = Orientation.E;
            case E -> orientation = Orientation.N;
        }
        return this;
    }

    public Aventurier collecte() {
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
        if (dest.getHor() < 0 || dest.getHor() >= carte.getLargeur()) {
            return false;
        }
        if (dest.getVer() < 0 || dest.getVer() >= carte.getHauteur()) {
            return false;
        }
        // L'aventurier ne peut aller sur une position occupée par une montagne ou un
        // autre aventurier.
        return carte.getMontagnes().stream()
                .noneMatch(m -> m.getPosition().equals(dest))
                &&
                carte.getAventuriers().stream()
                        .noneMatch(a -> a.getPosition().equals(dest));
    }

    public String toString() {
        return Type.A + " - " + nom + " - " + position.getHor() + " - " + position.getVer() + " - " +
                orientation + " - " + nbTresors + "\n";
    }

}
