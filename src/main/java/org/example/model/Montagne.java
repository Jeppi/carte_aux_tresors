package org.example.model;

public record Montagne(Position position) {

    public String toString() {
        return Type.M + " - " + position.hor() + " - " + position.ver() + "\n";
    }
}
