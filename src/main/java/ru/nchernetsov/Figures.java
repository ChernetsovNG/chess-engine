package ru.nchernetsov;

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
}
