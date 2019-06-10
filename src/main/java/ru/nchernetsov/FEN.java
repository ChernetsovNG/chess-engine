package ru.nchernetsov;

import java.util.Arrays;
import java.util.List;

import static ru.nchernetsov.Position.figuresToCodes;

/*
 * Класс, представляющий нотацию Форсайта–Эдвардса (англ. Forsyth–Edwards Notation, FEN) —
 * стандартная нотация записи шахматных диаграмм
 *
 * Запись FEN описывает позицию на шахматной доске в виде строки ASCII символов
 *
 * Поля записи:
 *
 * 1. Положение фигур со стороны белых. Позиция описывается цифрами и буквами по горизонталям сверху вниз,
 *    начиная с восьмой горизонтали и заканчивая первой. Расположение фигур на горизонтали записывается
 *    слева направо, данные каждой горизонтали разделяются косой чертой /
 *    Белые фигуры обозначаются заглавными буквами. K, Q, R, B, N, P —
 *    соответственно белые король, ферзь, ладья, слон, конь, пешка
 *    k, q, r, b, n, p — соответственно чёрные король, ферзь, ладья, слон, конь, пешка
 *    Обозначения фигур взяты из англоязычного варианта алгебраической нотации
 *    Цифра задаёт количество пустых полей на горизонтали, счёт ведётся либо от левого края доски,
 *    либо после фигуры (8 означает пустую горизонталь)
 *
 * 2. Активная сторона: w — следующий ход принадлежит белым, b — следующий ход чёрных
 *
 * 3. Возможность рокировки. k — в сторону королевского фланга (короткая), q — в сторону ферзевого фланга (длинная)
 *    Заглавными указываются белые. Невозможность рокировки обозначается «-»
 *
 * 4. Возможность взятия пешки на проходе. Указывается проходимое поле, иначе «-»
 *
 * 5. Счётчик полуходов. Число полуходов, прошедших с последнего хода пешки или взятия фигуры
 *    Используется для определения применения правила 50 ходов
 *
 * 6. Номер хода. Любой позиции может быть присвоено любое неотрицательное значение (по умолчанию 1),
 *    счётчик увеличивается на 1 после каждого хода чёрных
 *
 */
public class FEN {

    private final String fen;

    public FEN(String fen) {
        this.fen = fen;
    }

    public Position toPosition() {
        Position position = new Position();

        // Делим строку по пробелам - должно быть 6 элементов
        String[] splitByWhitespace = fen.split("\\s");
        if (splitByWhitespace.length != 6) {
            throw new IllegalStateException("Fen split by whitespace length not equal 6");
        }

        String horizontals = splitByWhitespace[0];
        String whoseMove = splitByWhitespace[1];
        String castlings = splitByWhitespace[2];
        String aisleTaking = splitByWhitespace[3];
        String halfMovesCount = splitByWhitespace[4];
        String moveNumber = splitByWhitespace[5];

        // Заполняем доску фигурами
        int[][] board = createBoard(horizontals);
        position.setBoard(board);

        // Ход белых?
        boolean isWhiteMove = calculateWhoseMove(whoseMove);
        position.setWhiteMove(isWhiteMove);

        // Возможность рокировок
        setCastlingsPossibility(position, castlings);

        // Клетка для взятия на проходе
        setAisleTakingSquare(position, aisleTaking);

        int halfMovesCountInt = Integer.parseInt(halfMovesCount);
        position.setHalfMovesCount(halfMovesCountInt);

        int moveNumberInt = Integer.parseInt(moveNumber);
        position.setMoveNumber(moveNumberInt);

        return position;
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

    public static int[] horizontalRepresentationToBoardRow(String horizontalRepresentation) {
        String[] split = horizontalRepresentation.split("\\|");
        String[] boardRowElements = split[1].trim().split("\\s");

        int[] result = new int[8];
        for (int i = 0; i < boardRowElements.length; i++) {
            String element = boardRowElements[i];
            Integer code = figuresToCodes.get(element);
            result[i] = code;
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

    private int[][] createBoard(String horizontals) {
        // Горизонтали разбиваем по разделителю
        String[] horizontalsArray = horizontals.split(HORIZONTAL_DELIMITER);
        if (horizontalsArray.length != 8) {
            throw new IllegalStateException("Horizontals array length not equal 8");
        }

        int[][] board = new int[8][];

        for (int i = 0; i < 8; i++) {
            String horizontal = horizontalsArray[i];
            String horizontalRepresentation = horizontalRepresentation(8 - i, horizontal);
            int[] boardRow = horizontalRepresentationToBoardRow(horizontalRepresentation);
            board[7 - i] = boardRow;
        }

        return board;
    }

    private boolean calculateWhoseMove(String whoseMove) {
        if (whoseMove.equals("w")) {
            return true;
        } else if (whoseMove.equals("b")) {
            return false;
        }
        throw new IllegalStateException("whoseMove = " + whoseMove + " Must be w or b");
    }

    private void setCastlingsPossibility(Position position, String castlings) {
        if (castlings.contains("K")) {
            position.setWhiteShortCastlingPossible(true);
        } else {
            position.setWhiteShortCastlingPossible(false);
        }

        if (castlings.contains("Q")) {
            position.setWhiteLongCastlingPossible(true);
        } else {
            position.setWhiteLongCastlingPossible(false);
        }

        if (castlings.contains("k")) {
            position.setBlackShortCastlingPossible(true);
        } else {
            position.setBlackShortCastlingPossible(false);
        }

        if (castlings.contains("q")) {
            position.setBlackLongCastlingPossible(true);
        } else {
            position.setBlackLongCastlingPossible(false);
        }
    }

    private void setAisleTakingSquare(Position position, String aisleTaking) {
        if (!aisleTaking.equals("-")) {
            char[] chars = aisleTaking.toCharArray();
            char letter = chars[0];
            char number = chars[1];

            int horizontal = Integer.parseInt(String.valueOf(number));

            int vertical = -1;
            if (letter == 'a') {
                vertical = 1;
            } else if (letter == 'b') {
                vertical = 2;
            } else if (letter == 'c') {
                vertical = 3;
            } else if (letter == 'd') {
                vertical = 4;
            } else if (letter == 'e') {
                vertical = 5;
            } else if (letter == 'f') {
                vertical = 6;
            } else if (letter == 'g') {
                vertical = 7;
            } else if (letter == 'h') {
                vertical = 8;
            }
            int[] aisleTakingSquare = new int[]{horizontal - 1, vertical - 1};
            position.setAisleTakingSquare(aisleTakingSquare);
        } else {
            position.setAisleTakingSquare(new int[]{-1, -1});
        }
    }

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
    public static final String HORIZONTAL_DELIMITER = "/";

    public static final String EMPTY = ".";

    private static final List<String> figures = Arrays.asList(
            "k", "K", "q", "Q", "r", "R", "b", "B", "n", "N", "p", "P");
}
