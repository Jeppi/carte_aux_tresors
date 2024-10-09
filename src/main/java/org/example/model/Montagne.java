package org.example.model;

import lombok.Data;

@Data
public class Montagne {
    private final Position position;

    public Montagne(int hor, int ver) {
        position = new Position(hor, ver);
    }

    public String toString() {
        return Type.M + " - " + position.hor() + " - " + position.ver() + "\n";
    }
}
