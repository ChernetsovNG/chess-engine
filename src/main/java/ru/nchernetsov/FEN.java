package ru.nchernetsov;

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
        Square aisleTakingSquare = getAisleTakingSquare(aisleTaking);

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
        String[] horizontalsArray = horizontals.split(Utils.HORIZONTAL_DELIMITER);
        if (horizontalsArray.length != 8) {
            throw new IllegalStateException("Horizontals array length not equal 8");
        }

        // Первая, последняя и предпоследняя строки всегда одинаковые
        String[] result = new String[11];

        result[0] = FIRST_ROW;

        for (int i = 0; i < 8; i++) {
            String horizontal = horizontalsArray[i];
            result[i + 1] = Utils.horizontalRepresentation(8 - i, horizontal);
        }

        result[9] = PENULTIMATE_ROW;
        result[10] = LAST_ROW;

        return result;
    }

    public String getFen() {
        return fen;
    }

    // PRIVATE section

    private char[][] createBoard(String horizontals) {
        // Горизонтали разбиваем по разделителю
        String[] horizontalsArray = horizontals.split(Utils.HORIZONTAL_DELIMITER);
        if (horizontalsArray.length != 8) {
            throw new IllegalStateException("Horizontals array length not equal 8");
        }

        char[][] board = new char[8][];

        for (int i = 0; i < 8; i++) {
            String horizontal = horizontalsArray[i];
            String horizontalRepresentation = Utils.horizontalRepresentation(8 - i, horizontal);
            char[] boardRow = Utils.horizontalRepresentationToBoardRow(horizontalRepresentation);
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

    private Square getAisleTakingSquare(String aisleTaking) {
        if (aisleTaking.equals("-")) {
            return null;
        }
        return new Square(aisleTaking);
    }

    // const

    private static final String FIRST_ROW = "  +-----------------+";

    private static final String PENULTIMATE_ROW = "  +-----------------+";

    private static final String LAST_ROW = "    a b c d e f g h  ";

    static final String EMPTY = ".";
}
