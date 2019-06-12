package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ComplexMovesTest {

    /**
     * "Бессмертная партия" - первые 17 ходов
     * Бессмертная партия (нем. Unsterbliche Partie) — шахматная партия, сыгранная 21 июня 1851 года в Лондоне
     * между Адольфом Андерсеном (белые) и Лионелем Кизерицким (чёрные)
     * Примечательна большим количеством жертв, которые принесли белые для достижения победы
     * Одна из самых знаменитых партий за всю историю шахмат, была единодушно признана высшим
     * образцом романтических шахмат — течения, господствовавшего в середине XIX века
     */
    @Test
    void immortalGameTest() {
        FEN fen = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Position position = fen.toPosition();

        // 1. e2-e4 e7-e5
        Position pos1 = position.move("e2e4");
        assertThat(pos1.toFEN().getFen()).isEqualTo("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1");

        Position pos2 = pos1.move("e7e5");
        assertThat(pos2.toFEN().getFen()).isEqualTo("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2");

        // 2. f2-f4 e5:f4
        Position pos3 = pos2.move("f2f4");
        assertThat(pos3.toFEN().getFen()).isEqualTo("rnbqkbnr/pppp1ppp/8/4p3/4PP2/8/PPPP2PP/RNBQKBNR b KQkq - 0 2");

        Position pos4 = pos3.move("e5f4");
        assertThat(pos4.toFEN().getFen()).isEqualTo("rnbqkbnr/pppp1ppp/8/8/4Pp2/8/PPPP2PP/RNBQKBNR w KQkq - 0 3");

        // 3. Cf1-c4 Фd8-h4+
        Position pos5 = pos4.move("f1c4");
        assertThat(pos5.toFEN().getFen()).isEqualTo("rnbqkbnr/pppp1ppp/8/8/2B1Pp2/8/PPPP2PP/RNBQK1NR b KQkq - 1 3");

        Position pos6 = pos5.move("d8h4");
        assertThat(pos6.toFEN().getFen()).isEqualTo("rnb1kbnr/pppp1ppp/8/8/2B1Pp1q/8/PPPP2PP/RNBQK1NR w KQkq - 2 4");

        // 4. Kpe1-f1 b7-b5?
        Position pos7 = pos6.move("e1f1");
        assertThat(pos7.toFEN().getFen()).isEqualTo("rnb1kbnr/pppp1ppp/8/8/2B1Pp1q/8/PPPP2PP/RNBQ1KNR b kq - 3 4");

        Position pos8 = pos7.move("b7b5");
        assertThat(pos8.toFEN().getFen()).isEqualTo("rnb1kbnr/p1pp1ppp/8/1p6/2B1Pp1q/8/PPPP2PP/RNBQ1KNR w kq - 0 5");

        // 5. Cc4:b5 Kg8-f6
        Position pos9 = pos8.move("c4b5");
        assertThat(pos9.toFEN().getFen()).isEqualTo("rnb1kbnr/p1pp1ppp/8/1B6/4Pp1q/8/PPPP2PP/RNBQ1KNR b kq - 0 5");

        Position pos10 = pos9.move("g8f6");
        assertThat(pos10.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/5n2/1B6/4Pp1q/8/PPPP2PP/RNBQ1KNR w kq - 1 6");

        // 6. Kg1-f3 Фh4-h6
        Position pos11 = pos10.move("g1f3");
        assertThat(pos11.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/5n2/1B6/4Pp1q/5N2/PPPP2PP/RNBQ1K1R b kq - 2 6");

        Position pos12 = pos11.move("h4h6");
        assertThat(pos12.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/5n1q/1B6/4Pp2/5N2/PPPP2PP/RNBQ1K1R w kq - 3 7");

        // 7. d2-d3 Kf6-h5
        Position pos13 = pos12.move("d2d3");
        assertThat(pos13.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/5n1q/1B6/4Pp2/3P1N2/PPP3PP/RNBQ1K1R b kq - 0 7");

        Position pos14 = pos13.move("f6h5");
        assertThat(pos14.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/7q/1B5n/4Pp2/3P1N2/PPP3PP/RNBQ1K1R w kq - 1 8");

        // 8. Kf3-h4 Фh6-g5?
        Position pos15 = pos14.move("f3h4");
        assertThat(pos15.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/7q/1B5n/4Pp1N/3P4/PPP3PP/RNBQ1K1R b kq - 2 8");

        Position pos16 = pos15.move("h6g5");
        assertThat(pos16.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/8/1B4qn/4Pp1N/3P4/PPP3PP/RNBQ1K1R w kq - 3 9");

        // 9. Kh4-f5 c7-c6
        Position pos17 = pos16.move("h4f5");
        assertThat(pos17.toFEN().getFen()).isEqualTo("rnb1kb1r/p1pp1ppp/8/1B3Nqn/4Pp2/3P4/PPP3PP/RNBQ1K1R b kq - 4 9");

        Position pos18 = pos17.move("c7c6");
        assertThat(pos18.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/2p5/1B3Nqn/4Pp2/3P4/PPP3PP/RNBQ1K1R w kq - 0 10");

        // 10. g2-g4 Kh5-f6
        Position pos19 = pos18.move("g2g4");
        assertThat(pos19.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/2p5/1B3Nqn/4PpP1/3P4/PPP4P/RNBQ1K1R b kq g3 0 10");

        Position pos20 = pos19.move("h5f6");
        assertThat(pos20.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/2p2n2/1B3Nq1/4PpP1/3P4/PPP4P/RNBQ1K1R w kq - 1 11");

        // 11. Лh1-g1! c6:b5
        Position pos21 = pos20.move("h1g1");
        assertThat(pos21.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/2p2n2/1B3Nq1/4PpP1/3P4/PPP4P/RNBQ1KR1 b kq - 2 11");

        Position pos22 = pos21.move("c6b5");
        assertThat(pos22.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/5n2/1p3Nq1/4PpP1/3P4/PPP4P/RNBQ1KR1 w kq - 0 12");

        // 12. h2-h4 Фg5-g6
        Position pos23 = pos22.move("h2h4");
        assertThat(pos23.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/5n2/1p3Nq1/4PpPP/3P4/PPP5/RNBQ1KR1 b kq - 0 12");

        Position pos24 = pos23.move("g5g6");
        assertThat(pos24.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/5nq1/1p3N2/4PpPP/3P4/PPP5/RNBQ1KR1 w kq - 1 13");

        // 13. h4-h5 Фg6-g5
        Position pos25 = pos24.move("h4h5");
        assertThat(pos25.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/5nq1/1p3N1P/4PpP1/3P4/PPP5/RNBQ1KR1 b kq - 0 13");

        Position pos26 = pos25.move("g6g5");
        assertThat(pos26.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/5n2/1p3NqP/4PpP1/3P4/PPP5/RNBQ1KR1 w kq - 1 14");

        // 14. Фd1-f3 Kf6-g8
        Position pos27 = pos26.move("d1f3");
        assertThat(pos27.toFEN().getFen()).isEqualTo("rnb1kb1r/p2p1ppp/5n2/1p3NqP/4PpP1/3P1Q2/PPP5/RNB2KR1 b kq - 2 14");

        Position pos28 = pos27.move("f6g8");
        assertThat(pos28.toFEN().getFen()).isEqualTo("rnb1kbnr/p2p1ppp/8/1p3NqP/4PpP1/3P1Q2/PPP5/RNB2KR1 w kq - 3 15");

        // 15. Cc1:f4 Фg5-f6
        Position pos29 = pos28.move("c1f4");
        assertThat(pos29.toFEN().getFen()).isEqualTo("rnb1kbnr/p2p1ppp/8/1p3NqP/4PBP1/3P1Q2/PPP5/RN3KR1 b kq - 0 15");

        Position pos30 = pos29.move("g5f6");
        assertThat(pos30.toFEN().getFen()).isEqualTo("rnb1kbnr/p2p1ppp/5q2/1p3N1P/4PBP1/3P1Q2/PPP5/RN3KR1 w kq - 1 16");

        // 16. Kb1-c3 Cf8-c5
        Position pos31 = pos30.move("b1c3");
        assertThat(pos31.toFEN().getFen()).isEqualTo("rnb1kbnr/p2p1ppp/5q2/1p3N1P/4PBP1/2NP1Q2/PPP5/R4KR1 b kq - 2 16");

        Position pos32 = pos31.move("f8c5");
        assertThat(pos32.toFEN().getFen()).isEqualTo("rnb1k1nr/p2p1ppp/5q2/1pb2N1P/4PBP1/2NP1Q2/PPP5/R4KR1 w kq - 3 17");

        // 17. Kc3-d5 Фf6:b2
        Position pos33 = pos32.move("c3d5");
        assertThat(pos33.toFEN().getFen()).isEqualTo("rnb1k1nr/p2p1ppp/5q2/1pbN1N1P/4PBP1/3P1Q2/PPP5/R4KR1 b kq - 4 17");

        Position pos34 = pos33.move("f6b2");
        assertThat(pos34.toFEN().getFen()).isEqualTo("rnb1k1nr/p2p1ppp/8/1pbN1N1P/4PBP1/3P1Q2/PqP5/R4KR1 w kq - 0 18");
    }
}
