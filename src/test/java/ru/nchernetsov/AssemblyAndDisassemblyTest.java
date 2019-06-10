package ru.nchernetsov;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.nchernetsov.FEN.horizontalRepresentation;
import static ru.nchernetsov.FEN.horizontalRepresentationToBoardRow;
import static ru.nchernetsov.Position.horizontalToFENString;

class AssemblyAndDisassemblyTest {

    @Test
    void assemblyDisassemblyTest1() {
        FEN fen = new FEN("rnbqkbnr/pppppppp/8/8/8/17/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        Position position = fen.toPosition();

        int[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly(13, 15, 14, 12, 11, 14, 15, 13);
        assertThat(board[1]).containsExactly(16, 16, 16, 16, 16, 16, 16, 16);
        assertThat(board[2]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[3]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[4]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[5]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[6]).containsExactly(26, 26, 26, 26, 26, 26, 26, 26);
        assertThat(board[7]).containsExactly(23, 25, 24, 22, 21, 24, 25, 23);

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare()).containsExactly(-1, -1);

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

        int[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly(13, 15, 14, 12, 11, 14, 15, 13);
        assertThat(board[1]).containsExactly(16, 16, 16, 16, 16, 16, 16, 16);
        assertThat(board[2]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[3]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[4]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[5]).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
        assertThat(board[6]).containsExactly(26, 26, 26, 26, 26, 26, 26, 26);
        assertThat(board[7]).containsExactly(23, 25, 24, 22, 21, 24, 25, 23);

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare()).containsExactly(-1, -1);

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

        int[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly(13, 15, 14, 12, 11, 14, 0, 13);
        assertThat(board[1]).containsExactly(16, 16, 16, 16, 0, 16, 16, 16);
        assertThat(board[2]).containsExactly(0, 0, 0, 0, 0, 15, 0, 0);
        assertThat(board[3]).containsExactly(0, 0, 0, 0, 16, 0, 0, 0);
        assertThat(board[4]).containsExactly(0, 0, 26, 0, 0, 0, 0, 0);
        assertThat(board[5]).containsExactly(0, 0, 25, 0, 0, 0, 0, 0);
        assertThat(board[6]).containsExactly(26, 26, 0, 26, 26, 26, 26, 26);
        assertThat(board[7]).containsExactly(23, 0, 24, 22, 21, 24, 25, 23);

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare()).containsExactly(-1, -1);

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

        int[][] board = position.getBoard();
        assertThat(board).hasSize(8);
        assertThat(board[0]).containsExactly(13, 15, 14, 12, 11, 14, 0, 13);
        assertThat(board[1]).containsExactly(16, 16, 16, 0, 0, 16, 16, 16);
        assertThat(board[2]).containsExactly(0, 0, 0, 0, 0, 15, 0, 0);
        assertThat(board[3]).containsExactly(0, 0, 0, 16, 0, 0, 0, 0);
        assertThat(board[4]).containsExactly(0, 0, 26, 0, 16, 26, 0, 0);
        assertThat(board[5]).containsExactly(0, 26, 25, 0, 0, 0, 0, 0);
        assertThat(board[6]).containsExactly(26, 0, 0, 26, 26, 0, 26, 26);
        assertThat(board[7]).containsExactly(23, 0, 24, 22, 21, 24, 25, 23);

        assertThat(position.isWhiteMove()).isTrue();

        assertThat(position.isWhiteShortCastlingPossible()).isTrue();
        assertThat(position.isWhiteLongCastlingPossible()).isTrue();
        assertThat(position.isBlackShortCastlingPossible()).isTrue();
        assertThat(position.isBlackLongCastlingPossible()).isTrue();

        assertThat(position.getAisleTakingSquare()).containsExactly(5, 5);

        assertThat(position.getHalfMovesCount()).isEqualTo(0);

        assertThat(position.getMoveNumber()).isEqualTo(5);

        FEN newFen = position.toFEN();
        assertThat(newFen.getFen()).isEqualTo(fenString);
    }

    @Test
    void horizontalRepresentationTest1() {
        String representation = horizontalRepresentation(8, "rnbqkbnr");
        Assertions.assertThat(representation).isEqualTo("8 | r n b q k b n r |");
        int[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly(23, 25, 24, 22, 21, 24, 25, 23);
    }

    @Test
    void horizontalRepresentationTest2() {
        String representation = horizontalRepresentation(6, "8");
        Assertions.assertThat(representation).isEqualTo("6 | . . . . . . . . |");
        int[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
    }

    @Test
    void horizontalRepresentationTest3() {
        String representation = horizontalRepresentation(4, "4P3");
        Assertions.assertThat(representation).isEqualTo("4 | . . . . P . . . |");
        int[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly(0, 0, 0, 0, 16, 0, 0, 0);
    }

    @Test
    void horizontalRepresentationTest4() {
        String representation = horizontalRepresentation(3, "5N2");
        Assertions.assertThat(representation).isEqualTo("3 | . . . . . N . . |");
        int[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly(0, 0, 0, 0, 0, 15, 0, 0);
    }

    @Test
    void horizontalRepresentationTest5() {
        String representation = horizontalRepresentation(2, "PPPP1PPP");
        Assertions.assertThat(representation).isEqualTo("2 | P P P P . P P P |");
        int[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly(16, 16, 16, 16, 0, 16, 16, 16);
    }

    @Test
    void horizontalRepresentationTest6() {
        String representation = horizontalRepresentation(1, "RNBQKB1R");
        Assertions.assertThat(representation).isEqualTo("1 | R N B Q K B . R |");
        int[] boardRow = horizontalRepresentationToBoardRow(representation);
        assertThat(boardRow).hasSize(8);
        assertThat(boardRow).containsExactly(13, 15, 14, 12, 11, 14, 0, 13);
    }

    @Test
    void horizontalToFENStringTest1() {
        int[] horizontal = new int[]{23, 25, 24, 22, 21, 24, 25, 23};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("rnbqkbnr");
    }

    @Test
    void horizontalToFENStringTest2() {
        int[] horizontal = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("8");
    }

    @Test
    void horizontalToFENStringTest3() {
        int[] horizontal = new int[]{0, 0, 0, 0, 16, 0, 0, 0};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("4P3");
    }

    @Test
    void horizontalToFENStringTest4() {
        int[] horizontal = new int[]{0, 0, 0, 0, 0, 15, 0, 0};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("5N2");
    }

    @Test
    void horizontalToFENStringTest5() {
        int[] horizontal = new int[]{16, 16, 16, 16, 0, 16, 16, 16};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("PPPP1PPP");
    }

    @Test
    void horizontalToFENStringTest6() {
        int[] horizontal = new int[]{13, 15, 14, 12, 11, 14, 0, 13};
        String fenString = horizontalToFENString(horizontal);
        assertThat(fenString).isEqualTo("RNBQKB1R");
    }
}
