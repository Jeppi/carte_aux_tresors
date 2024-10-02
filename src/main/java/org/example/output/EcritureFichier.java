package org.example.output;

import lombok.Data;
import org.example.model.Carte;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Data
public class EcritureFichier {

    private final Carte carte;

    public EcritureFichier(Carte carte) {
        this.carte = carte;
    }

    public void ecritResultat() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resultat.txt"));
            writer.write(carte.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
