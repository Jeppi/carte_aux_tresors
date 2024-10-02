package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Position {

    private int hor;
    private int ver;

    public String toString() {
        return hor + " - " + ver;
    }

}
