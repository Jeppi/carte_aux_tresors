package org.example;

import org.example.input.LectureFichier;
import org.example.model.Carte;
import org.example.output.EcritureFichier;

public class Main {
    public static void main(String[] args) {
        try {

            // Lecture des données d'entrée dans le fichier "carte.txt"
            LectureFichier lecture = new LectureFichier();
            lecture.lireFichier();
            Carte carte = lecture.getCarte();

            System.out.println(carte);

            // Parcours de la carte par les aventuriers
            carte.avanceAventuriers();

            System.out.println(carte);

            // Ecriture des données de sortie dans le fichier "resultat.txt"
            EcritureFichier ecriture = new EcritureFichier(carte);
            ecriture.ecritResultat();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
