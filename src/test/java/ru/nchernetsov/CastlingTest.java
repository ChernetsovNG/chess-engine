package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CastlingTest {

    @Test
    void castlingTest1() {
        String fenString = "r4rk1/pppppppp/8/N7/8/8/PPPPPPPP/R3K2R w KQ - 1 17";
        String move = "e1g1";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doMoveOrCastling(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("r4rk1/pppppppp/8/N7/8/8/PPPPPPPP/R4RK1 b - - 2 17");
    }

    @Test
    void castlingTest2() {
        // короткая рокировка белых
        String fenString = "r1bqk1nr/pppp1ppp/2n5/2b1p3/2B1P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4";
        String move = "e1g1";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doMoveOrCastling(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("r1bqk1nr/pppp1ppp/2n5/2b1p3/2B1P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 5 4");
    }

    @Test
    void castlingTest3() {
        // длинная рокировка белых
        String fenString = "r2qk1nr/ppp1bppp/2npb3/4p3/4P3/2NPB3/PPPQ1PPP/R3KBNR w KQkq - 6 6";
        String move = "e1c1";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doMoveOrCastling(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("r2qk1nr/ppp1bppp/2npb3/4p3/4P3/2NPB3/PPPQ1PPP/2KR1BNR b kq - 7 6");
    }

    @Test
    void castlingTest4() {
        // короткая рокировка чёрных
        String fenString = "r2qk2r/ppp1bppp/2npbn2/4p3/4P3/2NPB3/PPPQNPPP/2KR1B1R b kq - 9 7";
        String move = "e8g8";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doMoveOrCastling(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("r2q1rk1/ppp1bppp/2npbn2/4p3/4P3/2NPB3/PPPQNPPP/2KR1B1R w - - 10 8");
    }

    @Test
    void castlingTest5() {
        // длинная рокировка чёрных
        String fenString = "r3kbnr/pbpp1ppp/1pn1pq2/8/2BPP3/2P2N2/PP1B1PPP/RN1QK2R b KQkq - 0 6";
        String move = "e8c8";

        FEN initFen = new FEN(fenString);
        Position initPosition = initFen.toPosition();
        Position position = initPosition.doMoveOrCastling(move);
        FEN fen = position.toFEN();

        assertThat(fen.getFen()).isEqualTo("2kr1bnr/pbpp1ppp/1pn1pq2/8/2BPP3/2P2N2/PP1B1PPP/RN1QK2R w KQ - 1 7");
    }
}



