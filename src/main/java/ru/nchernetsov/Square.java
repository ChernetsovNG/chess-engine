package ru.nchernetsov;

import java.util.Objects;

public class Square {

    private int verticalIndex;

    private int horizontalIndex;

    private String notation;

    public Square(String notation) {
        this.notation = notation;
        verticalIndex = calcVerticalIndex(notation);
        horizontalIndex = calcHorizontalIndex(notation);
    }

    public Square(int horizontal, int vertical) {
        if (horizontal < 0 || horizontal > 7) {
            throw new IllegalArgumentException("horizontal must be 0 ... 7");
        }
        if (vertical < 0 || vertical > 7) {
            throw new IllegalArgumentException("vertical must be 0 ... 7");
        }
        this.horizontalIndex = horizontal;
        this.verticalIndex = vertical;
        String horizontalStr = calcHorizontalStr(horizontal);
        String verticalStr = calcVerticalStr(vertical);
        this.notation = verticalStr + horizontalStr;
    }

    private int calcVerticalIndex(String notation) {
        char vertical = notation.toCharArray()[0];
        if (vertical == 'a') {
            return 0;
        } else if (vertical == 'b') {
            return 1;
        } else if (vertical == 'c') {
            return 2;
        } else if (vertical == 'd') {
            return 3;
        } else if (vertical == 'e') {
            return 4;
        } else if (vertical == 'f') {
            return 5;
        } else if (vertical == 'g') {
            return 6;
        } else if (vertical == 'h') {
            return 7;
        }
        throw new IllegalArgumentException("Cannot calc vertical for symbol = " + vertical);
    }

    private int calcHorizontalIndex(String notation) {
        char horizontal = notation.toCharArray()[1];
        if (horizontal == '1') {
            return 0;
        } else if (horizontal == '2') {
            return 1;
        } else if (horizontal == '3') {
            return 2;
        } else if (horizontal == '4') {
            return 3;
        } else if (horizontal == '5') {
            return 4;
        } else if (horizontal == '6') {
            return 5;
        } else if (horizontal == '7') {
            return 6;
        } else if (horizontal == '8') {
            return 7;
        }
        throw new IllegalArgumentException("Cannot calc horizontal for symbol = " + horizontal);
    }

    private String calcHorizontalStr(int horizontal) {
        if (horizontal < 0 || horizontal > 7) {
            throw new IllegalArgumentException("horizontal must be 0 ... 7");
        }
        return "" + (horizontal + 1);
    }

    private String calcVerticalStr(int vertical) {
        if (vertical < 0 || vertical > 7) {
            throw new IllegalArgumentException("vertical must be 0 ... 7");
        }
        if (vertical == 0) {
            return "a";
        } else if (vertical == 1) {
            return "b";
        } else if (vertical == 2) {
            return "c";
        } else if (vertical == 3) {
            return "d";
        } else if (vertical == 4) {
            return "e";
        } else if (vertical == 5) {
            return "f";
        } else if (vertical == 6) {
            return "g";
        } else {
            return "h";
        }
    }

    public int getVerticalIndex() {
        return verticalIndex;
    }

    public int getHorizontalIndex() {
        return horizontalIndex;
    }

    public String getNotation() {
        return notation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return verticalIndex == square.verticalIndex &&
                horizontalIndex == square.horizontalIndex &&
                Objects.equals(notation, square.notation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verticalIndex, horizontalIndex, notation);
    }
}
