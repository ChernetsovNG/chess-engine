package ru.nchernetsov;

import java.util.HashMap;
import java.util.Map;

public class Position {

    /**
     * Представление доски (8 горизонталей)
     */
    private int[][] board = new int[8][8];

    /**
     * Ход белых - true, ход белых - false
     */
    private boolean isWhiteMove = true;

    /**
     * Возможность рокировки в короткую и длинную сторону
     */
    private boolean whiteShortCastlingPossible = true;
    private boolean blackShortCastlingPossible = true;
    private boolean whiteLongCastlingPossible = true;
    private boolean blackLongCastlingPossible = true;

    /**
     * Поле для возможного взятия на проходе
     */
    private int[] aisleTakingSquare = {-1, -1};

    /**
     * Число полуходов, прошедших с последнего хода пешки или взятия фигуры
     */
    private int halfMovesCount = 0;

    /**
     * Номер хода (счётчик увеличивается на 1 после каждого хода чёрных)
     */
    private int moveNumber = 1;

    public FEN toFEN() {
        String fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        StringBuilder sb = new StringBuilder();

        // Заполняем фигуры
        for (int i = board.length - 1; i >= 0; i--) {
            int[] horizontal = board[i];
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

    public static String horizontalToFENString(int[] horizontal) {
        StringBuilder sb = new StringBuilder();
        int emptyCounter = 0;
        for (int i = 0; i < 8; i++) {
            int element = horizontal[i];
            String figure = codesToFigures.get(element);
            if (!figure.equals(FEN.EMPTY)) {
                sb.append(figure);
            } else {
                emptyCounter++;
                if (i < 7) {
                    int nextElement = horizontal[i + 1];
                    String nextFigure = codesToFigures.get(nextElement);
                    if (!nextFigure.equals(FEN.EMPTY)) {
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

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean isWhiteMove() {
        return isWhiteMove;
    }

    public void setWhiteMove(boolean whiteMove) {
        isWhiteMove = whiteMove;
    }

    public boolean isWhiteShortCastlingPossible() {
        return whiteShortCastlingPossible;
    }

    public void setWhiteShortCastlingPossible(boolean whiteShortCastlingPossible) {
        this.whiteShortCastlingPossible = whiteShortCastlingPossible;
    }

    public boolean isBlackShortCastlingPossible() {
        return blackShortCastlingPossible;
    }

    public void setBlackShortCastlingPossible(boolean blackShortCastlingPossible) {
        this.blackShortCastlingPossible = blackShortCastlingPossible;
    }

    public boolean isWhiteLongCastlingPossible() {
        return whiteLongCastlingPossible;
    }

    public void setWhiteLongCastlingPossible(boolean whiteLongCastlingPossible) {
        this.whiteLongCastlingPossible = whiteLongCastlingPossible;
    }

    public boolean isBlackLongCastlingPossible() {
        return blackLongCastlingPossible;
    }

    public void setBlackLongCastlingPossible(boolean blackLongCastlingPossible) {
        this.blackLongCastlingPossible = blackLongCastlingPossible;
    }

    public int[] getAisleTakingSquare() {
        return aisleTakingSquare;
    }

    public void setAisleTakingSquare(int[] aisleTakingSquare) {
        this.aisleTakingSquare = aisleTakingSquare;
    }

    public int getHalfMovesCount() {
        return halfMovesCount;
    }

    public void setHalfMovesCount(int halfMovesCount) {
        this.halfMovesCount = halfMovesCount;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    // представления фигур
    public static final Map<String, Integer> figuresToCodes = new HashMap<>();
    public static final Map<Integer, String> codesToFigures = new HashMap<>();

    static {
        // фигуры белых
        figuresToCodes.put("K", 11);
        figuresToCodes.put("Q", 12);
        figuresToCodes.put("R", 13);
        figuresToCodes.put("B", 14);
        figuresToCodes.put("N", 15);
        figuresToCodes.put("P", 16);

        // фигуры черных
        figuresToCodes.put("k", 21);
        figuresToCodes.put("q", 22);
        figuresToCodes.put("r", 23);
        figuresToCodes.put("b", 24);
        figuresToCodes.put("n", 25);
        figuresToCodes.put("p", 26);

        figuresToCodes.put(".", 0);

        figuresToCodes.forEach((figure, code) -> codesToFigures.put(code, figure));
    }

    // PRIVATE section
}
