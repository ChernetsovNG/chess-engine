package ru.nchernetsov;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.nchernetsov.Utils.horizontalRepresentation;
import static ru.nchernetsov.Utils.horizontalRepresentationToBoardRow;
import static ru.nchernetsov.Utils.horizontalToFENString;

class AssemblyAndDisassemblyTest {

    @Test
    void assemblyDisassemblyTest1() {
        FEN fen = new FEN("rnbqkbnr/pppppppp/8/8/8/17/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        Position position = fen.toPosition();

        char[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly('R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R');
        assertThat(board[1]).containsExactly('P', 'P', 'P', 'P', 'P', 'P', 'P', 'P');
        assertThat(board[2]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[3]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[4]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[5]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[6]).containsExactly('p', 'p', 'p', 'p', 'p', 'p', 'p', 'p');
        assertThat(board[7]).containsExactly('r', 'n', 'b', 'q', 'k', 'b', 'n', 'r');

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare()).isNull();

        assertThat(position.getHalfMovesCount()).isEqualTo(0);

        assertThat(position.getMoveNumber()).isEqualTo(1);

        FEN newFen = position.toFEN();
        assertThat(newFen.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    @Test
    void assemblyDisassemblyTest2() {
        String fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        FEN fen = new FEN(fenString);
        Position position = fen.toPosition();

        char[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly('R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R');
        assertThat(board[1]).containsExactly('P', 'P', 'P', 'P', 'P', 'P', 'P', 'P');
        assertThat(board[2]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[3]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[4]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[5]).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
        assertThat(board[6]).containsExactly('p', 'p', 'p', 'p', 'p', 'p', 'p', 'p');
        assertThat(board[7]).containsExactly('r', 'n', 'b', 'q', 'k', 'b', 'n', 'r');

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare()).isNull();

        assertThat(position.getHalfMovesCount()).isEqualTo(0);

        assertThat(position.getMoveNumber()).isEqualTo(1);

        FEN newFen = position.toFEN();
        assertThat(newFen.getFen()).isEqualTo(fenString);
    }

    @Test
    void assemblyDisassemblyTest3() {
        String fenString = "r1bqkbnr/pp1ppppp/2n5/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3";
        FEN fen = new FEN(fenString);
        Position position = fen.toPosition();

        char[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly('R', 'N', 'B', 'Q', 'K', 'B', '.', 'R');
        assertThat(board[1]).containsExactly('P', 'P', 'P', 'P', '.', 'P', 'P', 'P');
        assertThat(board[2]).containsExactly('.', '.', '.', '.', '.', 'N', '.', '.');
        assertThat(board[3]).containsExactly('.', '.', '.', '.', 'P', '.', '.', '.');
        assertThat(board[4]).containsExactly('.', '.', 'p', '.', '.', '.', '.', '.');
        assertThat(board[5]).containsExactly('.', '.', 'n', '.', '.', '.', '.', '.');
        assertThat(board[6]).containsExactly('p', 'p', '.', 'p', 'p', 'p', 'p', 'p');
        assertThat(board[7]).containsExactly('r', '.', 'b', 'q', 'k', 'b', 'n', 'r');

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare()).isNull();

        assertThat(position.getHalfMovesCount()).isEqualTo(2);

        assertThat(position.getMoveNumber()).isEqualTo(3);

        FEN newFen = position.toFEN();
        assertThat(newFen.getFen()).isEqualTo(fenString);
    }

    @Test
    void assemblyDisassemblyTest4() {
        String fenString = "r1bqkbnr/p2pp1pp/1pn5/2p1Pp2/3P4/5N2/PPP2PPP/RNBQKB1R w KQkq f6 0 5";
        FEN fen = new FEN(fenString);
        Position position = fen.toPosition();

        char[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly('R', 'N', 'B', 'Q', 'K', 'B', '.', 'R');
        assertThat(board[1]).containsExactly('P', 'P', 'P', '.', '.', 'P', 'P', 'P');
        assertThat(board[2]).containsExactly('.', '.', '.', '.', '.', 'N', '.', '.');
        assertThat(board[3]).containsExactly('.', '.', '.', 'P', '.', '.', '.', '.');
        assertThat(board[4]).containsExactly('.', '.', 'p', '.', 'P', 'p', '.', '.');
        assertThat(board[5]).containsExactly('.', 'p', 'n', '.', '.', '.', '.', '.');
        assertThat(board[6]).containsExactly('p', '.', '.', 'p', 'p', '.', 'p', 'p');
        assertThat(board[7]).containsExactly('r', '.', 'b', 'q', 'k', 'b', 'n', 'r');

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare().getNotation()).isEqualTo("f6");

        assertThat(position.getHalfMovesCount()).isEqualTo(0);

        assertThat(position.getMoveNumber()).isEqualTo(5);

        FEN newFen = position.toFEN();
        assertThat(newFen.getFen()).isEqualTo(fenString);
    }

    @Test
    void horizontalRepresentationTest1() {
        String representation = horizontalRepresentation(8, "rnbqkbnr");
        Assertions.assertThat(representation).isEqualTo("8 | r n b q k b n r |");
        char[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly('r', 'n', 'b', 'q', 'k', 'b', 'n', 'r');
    }

    @Test
    void horizontalRepresentationTest2() {
        String representation = horizontalRepresentation(6, "8");
        Assertions.assertThat(representation).isEqualTo("6 | . . . . . . . . |");
        char[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly('.', '.', '.', '.', '.', '.', '.', '.');
    }

    @Test
    void horizontalRepresentationTest3() {
        String representation = horizontalRepresentation(4, "4P3");
        Assertions.assertThat(representation).isEqualTo("4 | . . . . P . . . |");
        char[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly('.', '.', '.', '.', 'P', '.', '.', '.');
    }

    @Test
    void horizontalRepresentationTest4() {
        String representation = horizontalRepresentation(3, "5N2");
        Assertions.assertThat(representation).isEqualTo("3 | . . . . . N . . |");
        char[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly('.', '.', '.', '.', '.', 'N', '.', '.');
    }

    @Test
    void horizontalRepresentationTest5() {
        String representation = horizontalRepresentation(2, "PPPP1PPP");
        Assertions.assertThat(representation).isEqualTo("2 | P P P P . P P P |");
        char[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly('P', 'P', 'P', 'P', '.', 'P', 'P', 'P');
    }

    @Test
    void horizontalRepresentationTest6() {
        String representation = horizontalRepresentation(1, "RNBQKB1R");
        Assertions.assertThat(representation).isEqualTo("1 | R N B Q K B . R |");
        char[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly('R', 'N', 'B', 'Q', 'K', 'B', '.', 'R');
    }

    @Test
    void horizontalToFENStringTest1() {
        char[] horizontal = new char[]{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("rnbqkbnr");
    }

    @Test
    void horizontalToFENStringTest2() {
        char[] horizontal = new char[]{'.', '.', '.', '.', '.', '.', '.', '.'};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("8");
    }

    @Test
    void horizontalToFENStringTest3() {
        char[] horizontal = new char[]{'.', '.', '.', '.', 'P', '.', '.', '.'};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("4P3");
    }

    @Test
    void horizontalToFENStringTest4() {
        char[] horizontal = new char[]{'.', '.', '.', '.', '.', 'N', '.', '.'};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("5N2");
    }

    @Test
    void horizontalToFENStringTest5() {
        char[] horizontal = new char[]{'P', 'P', 'P', 'P', '.', 'P', 'P', 'P'};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("PPPP1PPP");
    }

    @Test
    void horizontalToFENStringTest6() {
        char[] horizontal = new char[]{'R', 'N', 'B', 'Q', 'K', 'B', '.', 'R'};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("RNBQKB1R");
    }
}
