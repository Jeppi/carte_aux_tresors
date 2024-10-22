package org.example.input;

import lombok.Data;
import org.example.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.example.model.Type.C;

@Data
public class LectureFichier {

    private Carte carte;

    public void lireFichier() throws Exception {
        List<String> lignes;

        try {
            lignes = Files.readAllLines(Paths.get("carte.txt"));
        } catch (IOException e) {
            throw new IOException("Le fichier carte.txt doit être placé à la racine du programme.");
        }

        for (String ligne : lignes) {
            // On ignore les lignes commentées
            if (!ligne.trim().startsWith("#")) {
                String[] strings = ligne.trim().split("-");
                // Création de la carte
                if (carte == null) {
                    if (C.equals(Type.valueOf(ParseString(strings[0])))) {
                        carte = new Carte(parseNum(strings[1]), parseNum(strings[2]));
                    } else {
                        throw new Exception("Le fichier doit commencer par renseigner les dimensions de la carte");
                    }
                } else {
                    // Complétion de la carte
                    switch (Type.valueOf(strings[0].strip())) {
                        case M ->
                                carte.ajouteMontagne(new Montagne(new Position(parseNum(strings[1]), parseNum(strings[2]))));
                        case T ->
                                carte.ajouteTresor(new Tresor(parseNum(strings[1]), parseNum(strings[2]), parseNum(strings[3])));
                        case A ->
                                carte.ajouteAventurier(new Aventurier(ParseString(strings[1]), parseNum(strings[2]), parseNum(strings[3]), Orientation.valueOf(ParseString(strings[4])), ParseString(strings[5]), carte));
                    }
                }
            }
        }
    }

    private static int parseNum(String input) {
        return Integer.parseInt(input.strip());
    }

    private static String ParseString(String input) {
        return input.strip();
    }

}
