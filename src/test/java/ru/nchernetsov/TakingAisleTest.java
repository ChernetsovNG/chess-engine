package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TakingAisleTest {

    @Test
    void takingAisleTest1() {
        String fenString = "rnbqkbnr/p1pp1ppp/8/4p3/1p1PP3/5N2/PPP2PPP/RNBQKB1R w KQkq - 0 4";
        String move = "c2c4";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doTakingOnAisle(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("rnbqkbnr/p1pp1ppp/8/4p3/1pPPP3/5N2/PP3PPP/RNBQKB1R b KQkq c3 0 4");
    }

    @Test
    void takingAisleTest2() {
        String fenString = "rnbqkbnr/p1pp1p1p/6p1/6P1/1pPpP3/5N2/PP3P1P/RNBQKB1R b KQkq - 0 6";
        String move = "h7h5";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doTakingOnAisle(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("rnbqkbnr/p1pp1p2/6p1/6Pp/1pPpP3/5N2/PP3P1P/RNBQKB1R w KQkq h6 0 7");
    }

    @Test
    void takingAisleTest3() {
        String fenString = "rnbqkbnr/p1pp1p2/6p1/6Pp/1pPpP3/5N2/PP3P1P/RNBQKB1R w KQkq h6 0 7";
        String move = "a2a4";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doTakingOnAisle(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("rnbqkbnr/p1pp1p2/6p1/6Pp/PpPpP3/5N2/1P3P1P/RNBQKB1R b KQkq a3 0 7");
    }

    @Test
    void takingAisleTest4() {
        String fenString = "rnbqkbnr/ppp1pppp/8/4P3/2Pp4/8/PP1P1PPP/RNBQKBNR b KQkq c3 0 3";
        String move = "d4c3";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doTakingOnAisle(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("rnbqkbnr/ppp1pppp/8/4P3/8/2p5/PP1P1PPP/RNBQKBNR w KQkq - 0 4");
    }

    @Test
    void takingAisleTest5() {
        String fenString = "rnbqkbnr/ppp2ppp/4p3/4P3/2Pp4/3P4/PP3PPP/RNBQKBNR b KQkq c3 0 4";
        String move = "d4c3";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doTakingOnAisle(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("rnbqkbnr/ppp2ppp/4p3/4P3/8/2pP4/PP3PPP/RNBQKBNR w KQkq - 0 5");
    }
}
