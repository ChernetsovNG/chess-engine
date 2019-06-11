package ru.nchernetsov;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        FEN fen = new FEN(inputString);
        Position position = fen.toPosition();
        FEN newFen = position.toFEN();
        System.out.println(newFen.getFen());
    }
}

class FEN {

    private final String fen;

    public FEN(String fen) {
        this.fen = fen;
    }

    public Position toPosition() {
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
        char[][] board = createBoard(horizontals);

        // Ход белых?
        boolean isWhiteMove = calculateWhoseMove(whoseMove);

        // Возможность рокировок
        boolean whiteShortCastlingPossible = getWhiteShortCastlingPossible(castlings);
        boolean whiteLongCastlingPossible = getWhiteLongCastlingPossible(castlings);
        boolean blackShortCastlingPossible = getBlackShortCastlingPossible(castlings);
        boolean blackLongCastlingPossible = getBlackLongCastlingPossible(castlings);

        // Клетка для взятия на проходе
        int[] aisleTakingSquare = getAisleTakingSquare(aisleTaking);

        int halfMovesCountInt = Integer.parseInt(halfMovesCount);

        int moveNumberInt = Integer.parseInt(moveNumber);

        return new Position(board, isWhiteMove, whiteShortCastlingPossible, blackShortCastlingPossible,
                whiteLongCastlingPossible, blackLongCastlingPossible, aisleTakingSquare,
                halfMovesCountInt, moveNumberInt);
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

    private char[][] createBoard(String horizontals) {
        // Горизонтали разбиваем по разделителю
        String[] horizontalsArray = horizontals.split(HORIZONTAL_DELIMITER);
        if (horizontalsArray.length != 8) {
            throw new IllegalStateException("Horizontals array length not equal 8");
        }

        char[][] board = new char[8][];

        for (int i = 0; i < 8; i++) {
            String horizontal = horizontalsArray[i];
            String horizontalRepresentation = horizontalRepresentation(8 - i, horizontal);
            char[] boardRow = horizontalRepresentationToBoardRow(horizontalRepresentation);
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

    private boolean getWhiteShortCastlingPossible(String castlings) {
        return castlings.contains("K");
    }

    private boolean getWhiteLongCastlingPossible(String castlings) {
        return castlings.contains("Q");
    }

    private boolean getBlackShortCastlingPossible(String castlings) {
        return castlings.contains("k");
    }

    private boolean getBlackLongCastlingPossible(String castlings) {
        return castlings.contains("q");
    }

    private int[] getAisleTakingSquare(String aisleTaking) {
        if (aisleTaking.equals("-")) {
            return new int[]{-1, -1};
        }
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
        return new int[]{horizontal - 1, vertical - 1};
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

class Position {

    /**
     * Представление доски (8 горизонталей)
     */
    private final char[][] board;

    /**
     * Ход белых - true, ход белых - false
     */
    private final boolean isWhiteMove;

    /**
     * Возможность рокировки в короткую и длинную сторону
     */
    private final boolean whiteShortCastlingPossible;
    private final boolean blackShortCastlingPossible;
    private final boolean whiteLongCastlingPossible;
    private final boolean blackLongCastlingPossible;

    /**
     * Поле для возможного взятия на проходе
     */
    private final int[] aisleTakingSquare;

    /**
     * Число полуходов, прошедших с последнего хода пешки или взятия фигуры
     */
    private final int halfMovesCount;

    /**
     * Номер хода (счётчик увеличивается на 1 после каждого хода чёрных)
     */
    private final int moveNumber;

    public Position(char[][] board, boolean isWhiteMove, boolean whiteShortCastlingPossible,
                    boolean blackShortCastlingPossible, boolean whiteLongCastlingPossible,
                    boolean blackLongCastlingPossible, int[] aisleTakingSquare, int halfMovesCount, int moveNumber) {
        this.board = board;
        this.isWhiteMove = isWhiteMove;
        this.whiteShortCastlingPossible = whiteShortCastlingPossible;
        this.blackShortCastlingPossible = blackShortCastlingPossible;
        this.whiteLongCastlingPossible = whiteLongCastlingPossible;
        this.blackLongCastlingPossible = blackLongCastlingPossible;
        this.aisleTakingSquare = aisleTakingSquare;
        this.halfMovesCount = halfMovesCount;
        this.moveNumber = moveNumber;
    }

    public FEN toFEN() {
        StringBuilder sb = new StringBuilder();

        // Заполняем фигуры
        for (int i = board.length - 1; i >= 0; i--) {
            char[] horizontal = board[i];
            String rowFenString = horizontalToFENString(horizontal);
            sb.append(rowFenString);
            if (i > 0) {
                sb.append(FEN.HORIZONTAL_DELIMITER);
            }
        }
        sb.append(" ");

        // Заполняем очерёдность хода
        if (isWhiteMove) {
            sb.append("w");
        } else {
            sb.append("b");
        }
        sb.append(" ");

        // Заполняем возможность рокировок
        if (whiteShortCastlingPossible) {
            sb.append("K");
        }
        if (whiteLongCastlingPossible) {
            sb.append("Q");
        }
        if (blackShortCastlingPossible) {
            sb.append("k");
        }
        if (blackLongCastlingPossible) {
            sb.append("q");
        }
        if (!whiteShortCastlingPossible && !whiteLongCastlingPossible &&
                !blackShortCastlingPossible && !blackLongCastlingPossible) {
            sb.append("-");
        }
        sb.append(" ");

        // Заполняем возможность взятия на проходе
        if (aisleTakingSquare[0] == -1 && aisleTakingSquare[1] == -1) {
            sb.append("-");
        } else {
            int horizontal = aisleTakingSquare[0] + 1;
            int vertical = aisleTakingSquare[1] + 1;
            if (vertical == 1) {
                sb.append("a");
            } else if (vertical == 2) {
                sb.append("b");
            } else if (vertical == 3) {
                sb.append("c");
            } else if (vertical == 4) {
                sb.append("d");
            } else if (vertical == 5) {
                sb.append("e");
            } else if (vertical == 6) {
                sb.append("f");
            } else if (vertical == 7) {
                sb.append("g");
            } else if (vertical == 8) {
                sb.append("h");
            }
            sb.append(horizontal);
        }
        sb.append(" ");

        // заполняем номер полухода
        sb.append(halfMovesCount);
        sb.append(" ");

        // заполняем номер хода
        sb.append(moveNumber);

        return new FEN(sb.toString());
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

    public char[][] getBoard() {
        return board;
    }

    public boolean isWhiteMove() {
        return isWhiteMove;
    }

    public boolean isWhiteShortCastlingPossible() {
        return whiteShortCastlingPossible;
    }

    public boolean isBlackShortCastlingPossible() {
        return blackShortCastlingPossible;
    }

    public boolean isWhiteLongCastlingPossible() {
        return whiteLongCastlingPossible;
    }

    public boolean isBlackLongCastlingPossible() {
        return blackLongCastlingPossible;
    }

    public int[] getAisleTakingSquare() {
        return aisleTakingSquare;
    }

    public int getHalfMovesCount() {
        return halfMovesCount;
    }

    public int getMoveNumber() {
        return moveNumber;
    }
}
