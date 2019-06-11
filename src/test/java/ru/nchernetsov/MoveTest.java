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
}
