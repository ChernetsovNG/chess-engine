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
    private final Square aisleTakingSquare;

    /**
     * Число полуходов, прошедших с последнего хода пешки или взятия фигуры
     */
    private int halfMovesCount;

    /**
     * Номер хода (счётчик увеличивается на 1 после каждого хода чёрных)
     */
    private int moveNumber;

    public Position(char[][] board, boolean isWhiteMove, boolean whiteShortCastlingPossible,
                    boolean blackShortCastlingPossible, boolean whiteLongCastlingPossible,
                    boolean blackLongCastlingPossible, Square aisleTakingSquare, int halfMovesCount, int moveNumber) {
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
        if (aisleTakingSquare == null) {
            sb.append("-");
        } else {
            sb.append(aisleTakingSquare.getNotation());
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
     * Выполнить простой ход
     */
    public Position move(String move) {
        String fromSquareStr = move.substring(move.length() - 4, move.length() - 2);
        String toSquareStr = move.substring(move.length() - 2);

        Square fromSquare = new Square(fromSquareStr);
        Square toSquare = new Square(toSquareStr);

        Position positionCopy = copy();

        positionCopy.incrementMove();
        positionCopy.incrementHalfMove(move);

        char fromSquareElement = positionCopy.boardElement(fromSquare);
        positionCopy.setBoardElement(toSquare, fromSquareElement);
        positionCopy.setBoardElement(fromSquare, '.');

        return positionCopy;
    }

    /**
     * Выполнить превращение пешки
     */
    public Position pawnTransformation(String move) {
        // ход вида b2a1q
        String fromSquareStr = move.substring(0, 2);
        String toSquareStr = move.substring(2, 4);
        String transformToStr = move.substring(4, 5);

        Square fromSquare = new Square(fromSquareStr);
        Square toSquare = new Square(toSquareStr);
        char transformTo = transformToStr.toCharArray()[0];

        Position positionCopy = copy();

        positionCopy.incrementMove();
        positionCopy.incrementHalfMove(move);

        positionCopy.setBoardElement(toSquare, transformTo);
        positionCopy.setBoardElement(fromSquare, '.');

        return positionCopy;
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

    /**
     * Счётчик полуходов
     * Счётчик сбрасывается, если было взятие или ход пешкой
     * В остальных случаях счётчик увеличивается на 1 после каждого полухода
     */
    public void incrementHalfMove(String move) {
        if (wasTransformPawnMove(move)) {  // превращение пешки
            halfMovesCount = 0;
        } else if (wasPawnMove(move)) {  // ход пешкой
            halfMovesCount = 0;
        } else if (wasTakingByFigure(move)) {  // взятие
            halfMovesCount = 0;
        } else {
            halfMovesCount++;
        }
    }

    private boolean wasTransformPawnMove(String move) {
        return move.length() == 5;
    }

    private boolean wasPawnMove(String move) {
        // с поля
        String fromSquareStr = move.substring(move.length() - 4, move.length() - 2);
        Square fromSquare = new Square(fromSquareStr);

        char fromElement = boardElement(fromSquare);

        return fromElement == 'P' || fromElement == 'p';
    }

    private boolean wasTakingByFigure(String move) {
        // на поле
        String toSquareStr = move.substring(move.length() - 2);
        Square toSquare = new Square(toSquareStr);
        // если на этом поле что-то было, то это взятие
        return boardElement(toSquare) != '.';
    }

    public Position copy() {
        return new Position(Arrays.copyOf(this.board, this.board.length),
                this.isWhiteMove, this.whiteShortCastlingPossible, this.blackShortCastlingPossible,
                this.whiteLongCastlingPossible, this.blackLongCastlingPossible,
                this.aisleTakingSquare, this.halfMovesCount, this.moveNumber);
    }

    public char boardElement(Square square) {
        int horizontalIndex = square.getHorizontalIndex();
        int verticalIndex = square.getVerticalIndex();
        return board[horizontalIndex][verticalIndex];
    }

    private void setBoardElement(Square square, char element) {
        int horizontalIndex = square.getHorizontalIndex();
        int verticalIndex = square.getVerticalIndex();
        board[horizontalIndex][verticalIndex] = element;
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

    public Square getAisleTakingSquare() {
        return aisleTakingSquare;
    }

    public int getHalfMovesCount() {
        return halfMovesCount;
    }

    public int getMoveNumber() {
        return moveNumber;
    }
}
