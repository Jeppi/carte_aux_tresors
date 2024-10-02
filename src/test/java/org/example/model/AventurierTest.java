package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AventurierTest {
    Aventurier aventurier;
    Carte carte = new Carte(3, 4);

    @Test
    void rotation() {
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "D", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.E, aventurier.getOrientation());
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "DD", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.S, aventurier.getOrientation());
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "DDD", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.O, aventurier.getOrientation());
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "DDDD", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.N, aventurier.getOrientation());

        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "G", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.O, aventurier.getOrientation());
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "GG", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.S, aventurier.getOrientation());
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "GGG", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.E, aventurier.getOrientation());
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "GGGG", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(Orientation.N, aventurier.getOrientation());
    }

    // On teste la direction et les limites pour ne pas sortir de la carte
    @Test
    void deplacement_limites_carte() {
        // Ici les montagnes n'interférent pas avec les déplacements choisis
        carte.ajouteMontagne(new Montagne(2, 2));
        carte.ajouteMontagne(new Montagne(0, 0));

        // Orientation Nord on part de (1,1)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "A", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(1, 0), aventurier.getPosition());
        // On avance de deux pas alors que la carte nous permet d'avancer que d'un pas
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "AA", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(1, 0), aventurier.getPosition());

        // Orientation Sud, on part de (1,1)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.S, "A", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(1, 2), aventurier.getPosition());
        // On ne peut pas dépasser le bord de la carte
        aventurier = new Aventurier("Lara", 1, 1, Orientation.S, "AAAAAA", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(1, 3), aventurier.getPosition());

        // Orientation Ouest, on part de (1,1)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.O, "A", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(0, 1), aventurier.getPosition());
        // On ne peut pas dépasser le bord de la carte
        aventurier = new Aventurier("Lara", 1, 1, Orientation.O, "AAAAAA", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(0, 1), aventurier.getPosition());

        // Orientation Est, on part de (1,1)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.E, "A", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(2, 1), aventurier.getPosition());
        // On ne peut pas dépasser le bord de la carte
        aventurier = new Aventurier("Lara", 1, 1, Orientation.E, "AAAAAA", carte);
        aventurier.executeParcours();
        Assertions.assertEquals(new Position(2, 1), aventurier.getPosition());

    }

    // On teste les montagnes comme obstacle : cases (0,0) et (2,2)
    @Test
    public void deplacement_limites_montagne() {

        carte.ajouteMontagne(new Montagne(2, 2));
        carte.ajouteMontagne(new Montagne(0, 0));

        // en arrivant par le Nord de (1,1) vers (2,2)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.E, "ADA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué au nord de la montagne en (2,1)
        Assertions.assertEquals(new Position(2, 1), aventurier.getPosition());

        // en arrivant par l'Ouest de (1,1) vers (2,2)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.S, "AGA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué à l'Ouest de la montagne en (1, 2)
        Assertions.assertEquals(new Position(1, 2), aventurier.getPosition());

        // en arrivant par l'Est de (1,1) vers (0,0)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "AGA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué à l'Est de la montagne en (1,0)
        Assertions.assertEquals(new Position(1, 0), aventurier.getPosition());

        // en arrivant par le Sud de (1,1) vers (0, 0)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.O, "ADA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué au Sud de la montagne en (0, 1)
        Assertions.assertEquals(new Position(0, 1), aventurier.getPosition());

    }

    // On teste les autres aventuriers comme obstacle : cases (0,0) et (2,2)
    @Test
    public void deplacement_limites_autre_aventurier() {

        carte.ajouteAventurier(new Aventurier("John", 2, 2, Orientation.S, "AGA", carte));
        carte.ajouteAventurier(new Aventurier("Jenna", 0, 0, Orientation.S, "AGA", carte));

        // en arrivant par le Nord de (1,1) vers (2,2)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.E, "ADA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué au nord de l'aventurier en (2,1)
        Assertions.assertEquals(new Position(2, 1), aventurier.getPosition());

        // en arrivant par l'Ouest de (1,1) vers (2,2)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.S, "AGA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué à l'Ouest de l'aventurier en (1, 2)
        Assertions.assertEquals(new Position(1, 2), aventurier.getPosition());

        // en arrivant par l'Est de (1,1) vers (0,0)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.N, "AGA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué à l'Est de l'aventurier en (1,0)
        Assertions.assertEquals(new Position(1, 0), aventurier.getPosition());

        // en arrivant par le Sud de (1,1) vers (0, 0)
        aventurier = new Aventurier("Lara", 1, 1, Orientation.O, "ADA", carte);
        aventurier.executeParcours();
        // on reste bien bloqué au Sud de l'aventurier en (0, 1)
        Assertions.assertEquals(new Position(0, 1), aventurier.getPosition());

    }

    @Test
    public void collecteTresors() {
        Tresor tresor1 = new Tresor(1, 2, 3);
        Tresor tresor2 = new Tresor(1, 1, 1);
        carte.ajouteTresor(tresor1);
        carte.ajouteTresor(tresor2);

        // L'aventurier ne peut prendre qu'un trésor à la fois il doit quitter la case puis revenir
        // pour en reprendre un autre

        // 1 - il arrive à la case tourne sur place et ressort de la case, il ne doit avoir pris qu'un
        // seul trésor
        aventurier = new Aventurier("Lara", 0, 2, Orientation.E, "ADGA", carte);
        aventurier.executeParcours();
        // L'aventurier a rammassé un trésor, il en reste 2 en (1,2)
        Assertions.assertEquals(1, aventurier.getNbTresors());
        Assertions.assertEquals(2, tresor1.getQuantite());
        Assertions.assertEquals(1, tresor2.getQuantite());

        // 2 - il passe par les deux trésors, il ne reste plus de tresor2 dans la liste et il reste
        // un trésor1.
        aventurier = new Aventurier("Jeff", 0, 0, Orientation.E, "ADAA", carte);
        aventurier.executeParcours();
        // L'aventurier a rammassé deux trésors, il en reste 1 en (1,2) et 0 en (1,1)
        Assertions.assertEquals(2, aventurier.getNbTresors());
        Assertions.assertEquals(1, carte.getTresors().size());
        Assertions.assertEquals(1, tresor1.getQuantite());
    }
}
