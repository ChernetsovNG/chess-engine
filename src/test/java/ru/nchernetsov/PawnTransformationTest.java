package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PawnTransformationTest {

    @Test
    void pawnTransformationTest1() {
        String fenString = "rnbqkbnr/pppp1ppp/4P3/8/8/8/Pp3PPP/RNBQKBNR b KQkq - 0 5";
        String move = "b2a1q";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doPawnTransform(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("rnbqkbnr/pppp1ppp/4P3/8/8/8/P4PPP/qNBQKBNR w KQkq - 0 6");
    }

    @Test
    void pawnTransformationTest2() {
        String fenString = "r1bq1b1r/p1pppkPp/1pn2n2/8/3P4/8/PPP2PPP/RNBQKBNR w KQ - 1 6";
        String move = "g7f8N";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doPawnTransform(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("r1bq1N1r/p1pppk1p/1pn2n2/8/3P4/8/PPP2PPP/RNBQKBNR b KQ - 0 6");
    }
}
