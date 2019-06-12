package ru.nchernetsov;

import java.util.Arrays;

import static ru.nchernetsov.Utils.HORIZONTAL_DELIMITER;
import static ru.nchernetsov.Utils.horizontalToFENString;

class Position {

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
    private boolean whiteShortCastlingPossible;
    private boolean blackShortCastlingPossible;
    private boolean whiteLongCastlingPossible;
    private boolean blackLongCastlingPossible;

    /**
     * Поле для возможного взятия на проходе
     */
    private Square aisleTakingSquare;

    /**
     * Число полуходов, прошедших с последнего хода пешки или взятия фигуры
     */
    private int halfMovesCount;

    /**
     * Номер хода (счётчик увеличивается на 1 после каждого хода чёрных)
     */
    private int moveNumber;

    Position(char[][] board, boolean isWhiteMove, boolean whiteShortCastlingPossible,
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
                sb.append(HORIZONTAL_DELIMITER);
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

    public Position move(String move) {
        Position positionCopy = copy();
        if (wasTransformPawnMove(move)) {
            return positionCopy.doPawnTransform(move);
        } else if (wasDoublePawnMoveOrAisleTaking(move)) {
            return positionCopy.doTakingOnAisle(move);
        } else {
            return positionCopy.doMoveOrCastling(move);
        }
    }

    /**
     * Выполнить ход (включая рокировку)
     */
    public Position doMoveOrCastling(String move) {
        Square fromSquare = fromSquare(move);
        Square toSquare = toSquare(move);

        Position positionCopy = copy();

        positionCopy.incrementCounters(move);
        positionCopy.moveRookWhileCastling(move);
        positionCopy.setCastlingFlags(move);
        positionCopy.moveFigure(fromSquare, toSquare);
        positionCopy.aisleTakingSquare = null;

        return positionCopy;
    }

    /**
     * Выполнить превращение пешки
     *
     * @param move ход вида b2a1q
     */
    public Position doPawnTransform(String move) {
        String transformToStr = move.substring(4, 5);

        Square fromSquare = fromSquare(move);
        Square toSquare = toSquare(move);
        char transformTo = transformToStr.toCharArray()[0];

        Position positionCopy = copy();

        positionCopy.incrementCounters(move);
        positionCopy.moveFigure(fromSquare, toSquare);
        positionCopy.setBoardElement(toSquare, transformTo);
        positionCopy.aisleTakingSquare = null;

        return positionCopy;
    }

    /**
     * Выполнить взятие на проходе
     */
    public Position doTakingOnAisle(String move) {
        Square fromSquare = fromSquare(move);
        Square toSquare = toSquare(move);

        int fromSquareHorizontalIndex = fromSquare.getHorizontalIndex();
        int toSquareHorizontalIndex = toSquare.getHorizontalIndex();
        int toSquareVerticalIndex = toSquare.getVerticalIndex();

        int horizontalDelta = toSquareHorizontalIndex - fromSquareHorizontalIndex;

        Position positionCopy = copy();

        // если ход - это двойной прыжок пешки, то нужно установить признак битого поля,
        // если рядом есть пешка противника
        if (wasDoublePawnMove(move)) {
            positionCopy.setAisleTakingFlag(toSquareHorizontalIndex, toSquareVerticalIndex, horizontalDelta);
        }

        // если ход - это взятие на проходе, то перемещаем пешку, убираем пешку противника,
        // снимаем флаг взятия на проходе
        if (wasAisleTakingMove(move)) {
            // убираем пешку противника
            Square removeOppositePawnSquare = new Square(fromSquareHorizontalIndex, toSquareVerticalIndex);
            positionCopy.setBoardElement(removeOppositePawnSquare, '.');
            // снимаем флаг взятия на проходе
            positionCopy.aisleTakingSquare = null;
        }

        positionCopy.incrementCounters(move);
        positionCopy.moveFigure(fromSquare, toSquare);

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

    // PRIVATE section

    private boolean wasDoublePawnMoveOrAisleTaking(String move) {
        return wasDoublePawnMove(move) || wasAisleTakingMove(move);
    }

    // Установить флаг взятия на проходе
    private void setAisleTakingFlag(int toSquareHorizontalIndex, int toSquareVerticalIndex, int horizontalDelta) {
        char oppositePawn = isWhiteMove ? 'p' : 'P';
        if (toSquareVerticalIndex == 0) {
            // проверяем, нет ли справа пешки противника
            Square right = new Square(toSquareHorizontalIndex, 1);
            char rightElement = boardElement(right);
            if (rightElement == oppositePawn) {
                setAisleTakingSquare(horizontalDelta, toSquareHorizontalIndex, toSquareVerticalIndex);
            }
        } else if (toSquareVerticalIndex == 7) {
            // проверяем, нет ли слева пешки противника
            Square left = new Square(toSquareHorizontalIndex, 6);
            char leftElement = boardElement(left);
            if (leftElement == oppositePawn) {
                setAisleTakingSquare(horizontalDelta, toSquareHorizontalIndex, toSquareVerticalIndex);
            }
        } else {
            // проверяем, нет ли слева или справа пешки противника
            Square left = new Square(toSquareHorizontalIndex, toSquareVerticalIndex - 1);
            Square right = new Square(toSquareHorizontalIndex, toSquareVerticalIndex + 1);
            char leftElement = boardElement(left);
            char rightElement = boardElement(right);
            if (leftElement == oppositePawn || rightElement == oppositePawn) {
                setAisleTakingSquare(horizontalDelta, toSquareHorizontalIndex, toSquareVerticalIndex);
            }
        }
    }

    private void setAisleTakingSquare(int horizontalDelta, int toSquareHorizontalIndex, int toSquareVerticalIndex) {
        if (horizontalDelta == 2) {  // ход вверх
            aisleTakingSquare = new Square(toSquareHorizontalIndex - 1, toSquareVerticalIndex);
        } else {  // ход вниз
            aisleTakingSquare = new Square(toSquareHorizontalIndex + 1, toSquareVerticalIndex);
        }
    }

    // Ход == взятие на проходе
    private boolean wasAisleTakingMove(String move) {
        Square toSquare = toSquare(move);
        return toSquare.equals(aisleTakingSquare);
    }

    // ход == ход пешки на 2 поля
    private boolean wasDoublePawnMove(String move) {
        Square fromSquare = fromSquare(move);
        Square toSquare = toSquare(move);

        int fromSquareHorizontalIndex = fromSquare.getHorizontalIndex();
        int fromSquareVerticalIndex = fromSquare.getVerticalIndex();
        int toSquareHorizontalIndex = toSquare.getHorizontalIndex();
        int toSquareVerticalIndex = toSquare.getVerticalIndex();

        int horizontalDelta = toSquareHorizontalIndex - fromSquareHorizontalIndex;

        return fromSquareVerticalIndex == toSquareVerticalIndex &&
                (horizontalDelta == -2 || horizontalDelta == 2);
    }

    private void incrementCounters(String move) {
        incrementMove();
        incrementHalfMove(move);
    }

    // переместить фигуру с одного поля на другое
    private void moveFigure(Square fromSquare, Square toSquare) {
        char fromSquareElement = boardElement(fromSquare);
        setBoardElement(toSquare, fromSquareElement);
        setBoardElement(fromSquare, '.');
    }

    // Установить флаги рокировок
    private void setCastlingFlags(String move) {
        // Если это ход ладьёй - необходимо убрать флаг рокировки для этого игрока в сторону этой ладьи.
        // Если это ход королём - необходимо убрать флаг рокировки для этого игрока
        Square fromSquare = fromSquare(move);
        int fromSquareVerticalIndex = fromSquare.getVerticalIndex();
        if (wasWhiteRookMove(move)) {
            if (fromSquareVerticalIndex <= 3) {  // ферзевая ладья
                whiteLongCastlingPossible = false;
            } else {  // королевская ладья
                whiteShortCastlingPossible = false;
            }
        } else if (wasBlackRookMove(move)) {
            if (fromSquareVerticalIndex <= 3) {  // ферзевая ладья
                blackLongCastlingPossible = false;
            } else {  // королевская ладья
                blackShortCastlingPossible = false;
            }
        } else if (wasWhiteKingMove(move)) {
            whiteShortCastlingPossible = false;
            whiteLongCastlingPossible = false;
        } else if (wasBlackKingMove(move)) {
            blackShortCastlingPossible = false;
            blackLongCastlingPossible = false;
        }
    }

    // выполнить перемещение ладьи во время рокировки
    private void moveRookWhileCastling(String move) {
        // если это рокировка - ход королём на две клетки влево или вправо,
        // необходимо переставить короля и ладью
        if (wasWhiteShortCastling(move)) {
            setBoardElement(new Square(0, 5), 'R');
            setBoardElement(new Square(0, 7), '.');
        } else if (wasWhiteLongCastling(move)) {
            setBoardElement(new Square(0, 3), 'R');
            setBoardElement(new Square(0, 0), '.');
        } else if (wasBlackShortCastling(move)) {
            setBoardElement(new Square(7, 5), 'r');
            setBoardElement(new Square(7, 7), '.');
        } else if (wasBlackLongCastling(move)) {
            setBoardElement(new Square(7, 3), 'r');
            setBoardElement(new Square(7, 0), '.');
        }
    }

    private boolean wasTransformPawnMove(String move) {
        return move.length() == 5;
    }

    private boolean wasPawnMove(String move) {
        char movedFigure = movedFigure(move);
        return movedFigure == 'P' || movedFigure == 'p';
    }

    private boolean wasTakingByFigure(String move) {
        // на поле
        Square toSquare = toSquare(move);
        // если на этом поле что-то было, то это взятие
        return boardElement(toSquare) != '.';
    }

    private boolean wasWhiteRookMove(String move) {
        char movedFigure = movedFigure(move);
        return movedFigure == 'R';
    }

    private boolean wasBlackRookMove(String move) {
        char movedFigure = movedFigure(move);
        return movedFigure == 'r';
    }

    private boolean wasWhiteKingMove(String move) {
        char movedFigure = movedFigure(move);
        return movedFigure == 'K';
    }

    private boolean wasBlackKingMove(String move) {
        char movedFigure = movedFigure(move);
        return movedFigure == 'k';
    }

    private boolean wasWhiteShortCastling(String move) {
        if (!whiteShortCastlingPossible) {
            return false;
        }
        return wasCastlingMove(move, true, true);
    }

    private boolean wasWhiteLongCastling(String move) {
        if (!whiteLongCastlingPossible) {
            return false;
        }
        return wasCastlingMove(move, true, false);
    }

    private boolean wasBlackShortCastling(String move) {
        if (!blackShortCastlingPossible) {
            return false;
        }
        return wasCastlingMove(move, false, true);
    }

    private boolean wasBlackLongCastling(String move) {
        if (!blackLongCastlingPossible) {
            return false;
        }
        return wasCastlingMove(move, false, false);
    }

    // Проверяем, был ли ход рокировкой белых или чёрных, в короткую или длинную сторону
    private boolean wasCastlingMove(String move, boolean isWhite, boolean isShort) {
        char movedFigure = movedFigure(move);
        char king = isWhite ? 'K' : 'k';
        String kingField = isWhite ? "e1" : "e8";
        if (movedFigure == king) {
            Square fromSquare = fromSquare(move);
            Square toSquare = toSquare(move);
            if (!fromSquare.getNotation().equals(kingField)) {
                return false;
            }
            int fromSquareHorizontalIndex = fromSquare.getHorizontalIndex();
            int fromSquareVerticalIndex = fromSquare.getVerticalIndex();
            int toSquareHorizontalIndex = toSquare.getHorizontalIndex();
            int toSquareVerticalIndex = toSquare.getVerticalIndex();
            int horizontalDelta = toSquareHorizontalIndex - fromSquareHorizontalIndex;
            int verticalDelta = toSquareVerticalIndex - fromSquareVerticalIndex;
            return isShort ?
                    horizontalDelta == 0 && verticalDelta == 2 :
                    horizontalDelta == 0 && verticalDelta == -2;
        }
        return false;
    }

    private char movedFigure(String move) {
        Square fromSquare = fromSquare(move);
        return boardElement(fromSquare);
    }

    private Square fromSquare(String move) {
        String fromSquareStr = move.substring(0, 2);
        return new Square(fromSquareStr);
    }

    private Square toSquare(String move) {
        String toSquareStr = move.substring(2, 4);
        return new Square(toSquareStr);
    }

    private Position copy() {
        return new Position(Arrays.copyOf(this.board, this.board.length),
                this.isWhiteMove, this.whiteShortCastlingPossible, this.blackShortCastlingPossible,
                this.whiteLongCastlingPossible, this.blackLongCastlingPossible,
                this.aisleTakingSquare, this.halfMovesCount, this.moveNumber);
    }

    private char boardElement(Square square) {
        int horizontalIndex = square.getHorizontalIndex();
        int verticalIndex = square.getVerticalIndex();
        return board[horizontalIndex][verticalIndex];
    }

    private void setBoardElement(Square square, char element) {
        int horizontalIndex = square.getHorizontalIndex();
        int verticalIndex = square.getVerticalIndex();
        board[horizontalIndex][verticalIndex] = element;
    }
}
