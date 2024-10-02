package org.example.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Carte {
    final private int largeur;
    final private int hauteur;

    final private List<Tresor> tresors = new ArrayList<>();
    final private List<Montagne> montagnes = new ArrayList<>();
    final private List<Aventurier> aventuriers = new ArrayList<>();

    // Taille minimum de la carte 1 x 1
    final static int TAILLE_MINIMUM = 1;

    public Carte(int largeur, int hauteur) {
        if (largeur < TAILLE_MINIMUM || hauteur < TAILLE_MINIMUM) {
            throw new IllegalArgumentException("les dimensions d'une carte doivents être supérieures à 1");
        }
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public void decompteTresor(Tresor tresor) {
        if (tresor.getQuantite() > 1) {
            tresor.decremente();
        } else {
            tresors.remove(tresor);
        }
    }

    public Carte ajouteMontagne(Montagne montagne) {
        montagnes.add(montagne);
        return this;
    }

    public Carte ajouteTresor(Tresor tresor) {
        tresors.add(tresor);
        return this;
    }

    public Carte ajouteAventurier(Aventurier aventurier) {
        aventuriers.add(aventurier);
        return this;
    }

    public Carte avanceAventuriers() {
        boolean poursuivre = false;
        List<Aventurier> aventuriersCopy = new ArrayList<>(aventuriers);
        for (Aventurier aventurier : aventuriersCopy) {
            aventurier.executeActionSuivante();
            // On continue tant qu'un aventurier a un parcours à exécuter
            poursuivre |= !aventurier.getParcours().isEmpty();
        }
        if (poursuivre) {
            avanceAventuriers();
        }
        return this;
    }

    @Override
    public String toString() {
        String affichage = Type.C + " - " + largeur + " - " + hauteur + "\n";
        for (Montagne m : montagnes) {
            affichage += m.toString();
        }
        if (!tresors.isEmpty()) {
            affichage += "# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}\n";
        }
        for (Tresor t : tresors) {
            affichage += t.toString();
        }
        if (!aventuriers.isEmpty()) {
            affichage += "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} " +
                    "- {Axe vertical} - {Orientation} - {Nb. trésors ramassés}\n";
        }
        for (Aventurier a : aventuriers) {
            affichage += a.toString();
        }
        return affichage;
    }
}
