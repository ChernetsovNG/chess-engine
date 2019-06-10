import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        FEN fen = new FEN(inputString);
        String[] representation = fen.toRepresentation();
        for (String string : representation) {
            System.out.println(string);
        }
    }
}

class FEN {

    private final String fen;

    public FEN(String fen) {
        this.fen = fen;
    }

    /**
     * Вывод шахматной диаграммы в текстовом ASCII формате по образцу
     * 11 строчек по 21 символу на каждом
     */
    public String[] toRepresentation() {
        // Делим строку по пробелам - должно быть 6 элементов
        String[] splitByWhitespace = fen.split("\\s");
        if (splitByWhitespace.length != 6) {
            throw new IllegalStateException("Fen split by whitespace length not equal 6");
        }

        String horizontals = splitByWhitespace[0];
        String whoseMove = splitByWhitespace[1];
        String castlings = splitByWhitespace[2];
        String aisleTaking = splitByWhitespace[3];
        String unknown = splitByWhitespace[4];
        String moveNumber = splitByWhitespace[5];

        // Горизонтали разбиваем по разделителю
        String[] horizontalsArray = horizontals.split(HORIZONTAL_DELIMITER);
        if (horizontalsArray.length != 8) {
            throw new IllegalStateException("Horizontals array length not equal 8");
        }

        // Первая, последняя и предпоследняя строки всегда одинаковые
        String[] result = new String[11];

        result[0] = FIRST_ROW;

        for (int i = 0; i < 8; i++) {
            String horizontal = horizontalsArray[i];
            result[i + 1] = horizontalRepresentation(8 - i, horizontal);
        }

        result[9] = PENULTIMATE_ROW;
        result[10] = LAST_ROW;

        return result;
    }

    public String getFen() {
        return fen;
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
                    elements[index++] = EMPTY;
                }
            } else if (chString.contains("2")) {
                for (int i = 0; i < 2; i++) {
                    elements[index++] = EMPTY;
                }
            } else if (chString.contains("3")) {
                for (int i = 0; i < 3; i++) {
                    elements[index++] = EMPTY;
                }
            } else if (chString.contains("4")) {
                for (int i = 0; i < 4; i++) {
                    elements[index++] = EMPTY;
                }
            } else if (chString.contains("5")) {
                for (int i = 0; i < 5; i++) {
                    elements[index++] = EMPTY;
                }
            } else if (chString.contains("6")) {
                for (int i = 0; i < 6; i++) {
                    elements[index++] = EMPTY;
                }
            } else if (chString.contains("7")) {
                for (int i = 0; i < 7; i++) {
                    elements[index++] = EMPTY;
                }
            } else if (chString.contains("8")) {
                for (int i = 0; i < 8; i++) {
                    elements[index++] = EMPTY;
                }
            } else if (figures.contains(chString.toLowerCase())) {
                elements[index++] = chString;
            }
        }

        return String.format(HORIZONTAL_TEMPLATE, horizontalNumber,
                elements[0], elements[1], elements[2], elements[3],
                elements[4], elements[5], elements[6], elements[7]);
    }

    // PRIVATE section

    // const

    private static final String FIRST_ROW = "  +-----------------+";

    private static final String PENULTIMATE_ROW = "  +-----------------+";

    private static final String LAST_ROW = "    a b c d e f g h  ";

    /**
     * Шаблон обычной горизонтали доски
     */
    private static final String HORIZONTAL_TEMPLATE = "%d | %s %s %s %s %s %s %s %s |";

    /**
     * Разделитель записи для горизонталей
     */
    private static final String HORIZONTAL_DELIMITER = "/";

    private static final String EMPTY = ".";

    private static final List<String> figures = Arrays.asList(
            "k", "K", "q", "Q", "r", "R", "b", "B", "n", "N", "p", "P");
}
