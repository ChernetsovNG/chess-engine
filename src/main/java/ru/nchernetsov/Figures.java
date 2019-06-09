package ru.nchernetsov;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Figures {

    KING("k"),

    QUEEN("q"),

    ROOK("r"),

    BISHOP("b"),

    KNIGHT("n"),

    PAWN("p");

    private final String notation;

    Figures(String notation) {
        this.notation = notation;
    }

    public String getNotation() {
        return notation;
    }

    public static List<String> notations = Arrays.stream(values())
        .map(Figures::getNotation)
        .collect(Collectors.toList());
}
