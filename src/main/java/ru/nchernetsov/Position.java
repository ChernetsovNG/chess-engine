package ru.nchernetsov;

import java.util.Arrays;

public class Position {

    /**
     * Представление доски (8 горизонталей)
     */
    private final char[][] board;

    /**
     * Ход белых - true, ход белых - false
     */
    private boolean isWhiteMove;

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
    private int moveNumber;

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

    /**
     * Изменяем очерёдность хода и увеличиваем счётчик ходов
     */
    public void incrementMove() {
        if (isWhiteMove) {
            isWhiteMove = false;
        } else {
            isWhiteMove = true;
            moveNumber++;
        }
    }

    public Position copy() {
        return new Position(Arrays.copyOf(this.board, this.board.length),
                this.isWhiteMove, this.whiteShortCastlingPossible, this.blackShortCastlingPossible,
                this.whiteLongCastlingPossible, this.blackLongCastlingPossible,
                Arrays.copyOf(this.aisleTakingSquare, this.aisleTakingSquare.length),
                this.halfMovesCount, this.moveNumber);
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
