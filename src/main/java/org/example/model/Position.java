package org.example.model;

public record Position(int hor, int ver) {

    public String toString() {
        return hor + " - " + ver;
    }

}
