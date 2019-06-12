package ru.nchernetsov;

import java.util.Arrays;
import java.util.List;

public final class Utils {

    /**
     * Разделитель записи для горизонталей
     */
    static final String HORIZONTAL_DELIMITER = "/";

    private static final List<String> figures = Arrays.asList(
            "k", "K", "q", "Q", "r", "R", "b", "B", "n", "N", "p", "P");

    /**
     * Шаблон представления горизонтали шахматной доски
     */
    private static final String HORIZONTAL_TEMPLATE = "%d | %s %s %s %s %s %s %s %s |";

    private Utils() {
    }

    public static String horizontalToFENString(char[] horizontal) {
        StringBuilder sb = new StringBuilder();
        int emptyCounter = 0;
        for (int i = 0; i < 8; i++) {
            char element = horizontal[i];
            if (element != '.') {
                sb.append(element);
            } else {
                emptyCounter++;
                if (i < 7) {
                    char nextElement = horizontal[i + 1];
                    if (nextElement != '.') {
                        sb.append(emptyCounter);
                        emptyCounter = 0;
                    }
                } else {
                    sb.append(emptyCounter);
                }
            }
        }
        return sb.toString();
    }

    public static char[] horizontalRepresentationToBoardRow(String horizontalRepresentation) {
        String[] split = horizontalRepresentation.split("\\|");
        String[] boardRowElements = split[1].trim().split("\\s");

        char[] result = new char[8];
        for (int i = 0; i < boardRowElements.length; i++) {
            String element = boardRowElements[i];
            result[i] = element.toCharArray()[0];
        }

        return result;
    }

    public static String horizontalRepresentation(int horizontalNumber, String horizontal) {
        String[] elements = new String[8];

        char[] chars = horizontal.toCharArray();

        int index = 0;
        for (char ch : chars) {
            if (index > 7) {
                break;
            }
            String chString = String.valueOf(ch);
            if (chString.contains("1")) {
                for (int i = 0; i < 1; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (chString.contains("2")) {
                for (int i = 0; i < 2; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (chString.contains("3")) {
                for (int i = 0; i < 3; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (chString.contains("4")) {
                for (int i = 0; i < 4; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (chString.contains("5")) {
                for (int i = 0; i < 5; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (chString.contains("6")) {
                for (int i = 0; i < 6; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (chString.contains("7")) {
                for (int i = 0; i < 7; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (chString.contains("8")) {
                for (int i = 0; i < 8; i++) {
                    elements[index++] = FEN.EMPTY;
                }
            } else if (figures.contains(chString.toLowerCase())) {
                elements[index++] = chString;
            }
        }

        return String.format(HORIZONTAL_TEMPLATE, horizontalNumber,
                elements[0], elements[1], elements[2], elements[3],
                elements[4], elements[5], elements[6], elements[7]);
    }
}
