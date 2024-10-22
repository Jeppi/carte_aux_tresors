package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CarteTest {

    @Test
    void testIntegration_aventurier_unique() {
        Carte carte = new Carte(3, 4);
        carte.ajouteMontagne(new Montagne(new Position(1, 0)));
        carte.ajouteMontagne(new Montagne(new Position(2, 1)));
        // trésor 1
        carte.ajouteTresor(new Tresor(0, 3, 2));
        // trésor 2
        carte.ajouteTresor(new Tresor(1, 3, 3));
        Aventurier lara = new Aventurier("Lara", 1, 1, Orientation.S, "AADADAGGA", carte);
        carte.ajouteAventurier(lara);
        // positions de départ sur la carte
        //   -    M    -
        //   -  L(S)   M
        //   -    -    -
        // T(2) T(3)   -

        carte.avanceAventuriers();

        // aventurier : pos, nbTrésor
        // Lara : (1,1),0 -> (1,2), 0 -> (1,3), 1 -> (0,3), 2 -> (0,2), 2 -> (0,3), 3
        // trésor 1 : 2 -> 1 -> 0
        // trésor 2 : 3 -> 2

        // positions d'arrivée sur la carte
        //   -    M    -
        //   -    -    M
        //   -    -    -
        // L(S)  T(2)  -

        Assertions.assertEquals(new Position(0, 3), lara.getPosition());
        Assertions.assertEquals(1, carte.getTresors().size());
        // Il reste un unique trésor avec une quantité à 2
        Assertions.assertEquals(2, carte.getTresors().get(0).getQuantite());
        Assertions.assertEquals(3, lara.getNbTresors());
    }

    @Test
    void testIntegration_deux_aventuriers() {
        Carte carte = new Carte(3, 4);
        carte.ajouteMontagne(new Montagne(new Position(1, 0)));
        carte.ajouteMontagne(new Montagne(new Position(2, 1)));
        carte.ajouteTresor(new Tresor(0, 3, 2));
        carte.ajouteTresor(new Tresor(1, 3, 3));
        Aventurier lara = new Aventurier("Lara", 1, 1, Orientation.S, "AAADADAGGA", carte);
        Aventurier john = new Aventurier("John", 1, 2, Orientation.E, "ADADAAA", carte);
        carte.ajouteAventurier(lara);
        carte.ajouteAventurier(john);

        // positions de départ sur la carte
        //   -    M    -
        //   -  L(S)   M
        //   -  J(E)   -
        // T(2) T(3)   -

        // Lara : (1,1),S,0 -> (1,1),S,0 -> (1,2),S,0 -> (1,3),S,1 -> (1,3),Ouest,1 -> (0,3),Ouest, 2 -> (0,3),N, 2      -> (0,2),N,2   -> (0,2),Ouest, 2 -> (O,2),S, 2 -> (0,2),S,2
        // John : (1,2),E,0 -> (2,2),E,0 -> (2,2),S,0 -> (2,3),S,0 -> (2,3),Ouest,0 -> (1,3),Ouest, 1 -> (1,3),Ouest, 1  -> (0,3),Ouest,2

        // trésor 1 : 2 -> 1 -> 0
        // trésor 2 : 3 -> 2 -> 1

        // positions d'arrivée sur la carte
        //   -    M    -
        //   -    -    M
        // L(S)   -    -
        // J(O) T(1)   -

        carte.avanceAventuriers();

        Assertions.assertEquals(new Position(0, 2), lara.getPosition());
        Assertions.assertEquals(2, lara.getNbTresors());
        Assertions.assertEquals(new Position(0, 3), john.getPosition());
        Assertions.assertEquals(2, john.getNbTresors());

        Assertions.assertEquals(1, carte.getTresors().size());
        // Il reste un unique trésor avec une quantité à un
        Assertions.assertEquals(1, carte.getTresors().get(0).getQuantite());
    }
}
