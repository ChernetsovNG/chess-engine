package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CastlingFlagsTest {

    @Test
    void castlingFlagsTest1() {
        // Ход ферзевой ладьёй черных
        String fenString = "r3k2r/pppppppp/8/N7/8/8/PPPPPPPP/R3K2R b KQkq - 0 16";
        String move = "a8b8";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.move(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("1r2k2r/pppppppp/8/N7/8/8/PPPPPPPP/R3K2R w KQk - 1 17");
    }

    @Test
    void castlingFlagsTest2() {
        // Ход королевской ладьёй белых
        String fenString = "rnbqk2r/pppp1ppp/5n2/2b1p3/2B1P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 5 4";
        String move = "h8g8";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.move(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("rnbqk1r1/pppp1ppp/5n2/2b1p3/2B1P3/5N2/PPPP1PPP/RNBQ1RK1 w q - 6 5");
    }

    @Test
    void castlingFlagsTest3() {
        // Ход королём белых
        String fenString = "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3";
        String move = "e1e2";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.move(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPPKPPP/RNBQ1B1R b kq - 3 3");
    }

    @Test
    void castlingFlagsTest4() {
        // Ход королём чёрных
        String fenString = "r1bqk2r/pppp1ppp/2n2n2/2b1p3/2B1P3/2PP1N2/PP3PPP/RNBQK2R b KQkq - 0 5";
        String move = "e8f8";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.move(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("r1bq1k1r/pppp1ppp/2n2n2/2b1p3/2B1P3/2PP1N2/PP3PPP/RNBQK2R w KQ - 1 6");
    }
}
