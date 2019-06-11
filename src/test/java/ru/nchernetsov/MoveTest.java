package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MoveTest {

    @Test
    void moveTest1() {
        FEN initFEN = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Position position = initFEN.toPosition();
        position.incrementMove();
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");
    }

    @Test
    void moveTest2() {
        FEN initFEN = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");
        Position position = initFEN.toPosition();
        position.incrementMove();
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 2");
    }

    @Test
    void moveTest3() {
        FEN initFEN = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 2");
        Position position = initFEN.toPosition();
        position.incrementMove();
        FEN fenAfterMove = position.toFEN();
        assertThat(fenAfterMove.getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 2");
    }

    @Test
    void moveTest4() {
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
}


