package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MoveTest {

    @Test
    void incrementMoveTest1() {
        FEN initFEN = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Position position = initFEN.toPosition();
        position.incrementMove();
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");
    }

    @Test
    void incrementMoveTest2() {
        FEN initFEN = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");
        Position position = initFEN.toPosition();
        position.incrementMove();
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 2");
    }

    @Test
    void incrementMoveTest3() {
        FEN initFEN = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 2");
        Position position = initFEN.toPosition();
        position.incrementMove();
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 2");
    }

    @Test
    void incrementMoveTest4() {
        FEN initFEN = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 2");
        Position position = initFEN.toPosition();
        position.incrementMove();
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 3");
    }

    @Test
    void incrementHalfMoveTest1() {
        String fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String move = "b1f3";
        FEN fen = new FEN(fenString);
        Position position = fen.toPosition();
        position.incrementMove();
        position.incrementHalfMove(move);
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 1 1");
    }

    @Test
    void incrementHalfMoveTest2() {
        String fenString = "r1bqk2r/1pppbppp/p1n2n2/4p3/B3P3/5N2/PPPP1PPP/RNBQ1RK1 w kq - 4 6";
        String move = "d2d4";  // ход пешкой
        FEN fen = new FEN(fenString);
        Position position = fen.toPosition();
        position.incrementMove();
        position.incrementHalfMove(move);
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("r1bqk2r/1pppbppp/p1n2n2/4p3/B3P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 0 6");
    }

    @Test
    void moveTest1() {
        String fenString = "k7/r7/b7/q7/N7/B7/R7/K7 w - - 15 48";
        String move = "a2h2";
        FEN fen = new FEN(fenString);
        Position position = fen.toPosition();
        Position afterMove = position.move(move);
        FEN fenAfterMove = afterMove.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("k7/r7/b7/q7/N7/B7/7R/K7 b - - 16 48");
    }

    @Test
    void moveTest2() {
        String fenString = "r1bq1rk1/1ppnppbp/p2p2p1/3Pn3/2P1P3/2N2N1P/PP2BPP1/R1BQK2R w KQ - 1 10";
        String move = "f3e5";
        FEN fen = new FEN(fenString);
        Position position = fen.toPosition();
        Position afterMove = position.move(move);
        FEN fenAfterMove = afterMove.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("r1bq1rk1/1ppnppbp/p2p2p1/3PN3/2P1P3/2N4P/PP2BPP1/R1BQK2R b KQ - 0 10");
    }
}
