package ru.nchernetsov;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.FEN.horizontalRepresentation;

class FENTest {

    @Test
    void horizontalRepresentationTest1() {
        String representation = horizontalRepresentation(8, "rnbqkbnr");
        assertThat(representation).isEqualTo("8 | r n b q k b n r |");
    }

    @Test
    void horizontalRepresentationTest2() {
        String representation = horizontalRepresentation(6, "8");
        assertThat(representation).isEqualTo("6 | . . . . . . . . |");
    }

    @Test
    void horizontalRepresentationTest3() {
        String representation = horizontalRepresentation(4, "4P3");
        assertThat(representation).isEqualTo("4 | . . . . P . . . |");
    }

    @Test
    void horizontalRepresentationTest4() {
        String representation = horizontalRepresentation(3, "5N2");
        assertThat(representation).isEqualTo("3 | . . . . . N . . |");
    }

    @Test
    void horizontalRepresentationTest5() {
        String representation = horizontalRepresentation(2, "PPPP1PPP");
        assertThat(representation).isEqualTo("2 | P P P P . P P P |");
    }

    @Test
    void horizontalRepresentationTest6() {
        String representation = horizontalRepresentation(1, "RNBQKB1R");
        assertThat(representation).isEqualTo("1 | R N B Q K B . R |");
    }

    @Test
    void printFenInitialPosition() {
        String fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String[] correctRepresentation =
            {"  +-----------------+",
             "8 | r n b q k b n r |",
             "7 | p p p p p p p p |",
             "6 | . . . . . . . . |",
             "5 | . . . . . . . . |",
             "4 | . . . . . . . . |",
             "3 | . . . . . . . . |",
             "2 | P P P P P P P P |",
             "1 | R N B Q K B N R |",
             "  +-----------------+",
             "    a b c d e f g h  "};

        FEN fen = new FEN(fenString);
        String[] positionRepresentation = fen.toRepresentation();

        checkPositionRepresentationSize(positionRepresentation);
        assertThat(positionRepresentation).isEqualTo(correctRepresentation);
    }

    @Test
    void printFenSicilianDefenceStartPosition() {
        String fenString = "r1bqkbnr/pp1ppppp/2n5/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3";
        String[] correctRepresentation =
            {"  +-----------------+",
             "8 | r . b q k b n r |",
             "7 | p p . p p p p p |",
             "6 | . . n . . . . . |",
             "5 | . . p . . . . . |",
             "4 | . . . . P . . . |",
             "3 | . . . . . N . . |",
             "2 | P P P P . P P P |",
             "1 | R N B Q K B . R |",
             "  +-----------------+",
             "    a b c d e f g h  "};

        FEN fen = new FEN(fenString);
        String[] positionRepresentation = fen.toRepresentation();

        checkPositionRepresentationSize(positionRepresentation);
        assertThat(positionRepresentation).isEqualTo(correctRepresentation);
    }

    @Test
    void printFenTestWithTakeOnTheAisle() {
        // тест с возможностью взятия пешки чёрных на проходе после хода f7-f5 (на поле f6)

        String fenString = "r1bqkbnr/p2pp1pp/1pn5/2p1Pp2/3P4/5N2/PPP2PPP/RNBQKB1R w KQkq f6 0 5";
        String[] correctRepresentation =
            {"  +-----------------+",
             "8 | r . b q k b n r |",
             "7 | p . . p p . p p |",
             "6 | . p n . . . . . |",
             "5 | . . p . P p . . |",
             "4 | . . . P . . . . |",
             "3 | . . . . . N . . |",
             "2 | P P P . . P P P |",
             "1 | R N B Q K B . R |",
             "  +-----------------+",
             "    a b c d e f g h  "};

        FEN fen = new FEN(fenString);
        String[] positionRepresentation = fen.toRepresentation();

        checkPositionRepresentationSize(positionRepresentation);
        assertThat(positionRepresentation).isEqualTo(correctRepresentation);
    }

    private void checkPositionRepresentationSize(String[] positionRepresentation) {
        assertThat(positionRepresentation).hasSize(11);
        assertThat(positionRepresentation[0]).hasSize(21);
        assertThat(positionRepresentation[1]).hasSize(21);
        assertThat(positionRepresentation[2]).hasSize(21);
        assertThat(positionRepresentation[3]).hasSize(21);
        assertThat(positionRepresentation[4]).hasSize(21);
        assertThat(positionRepresentation[5]).hasSize(21);
        assertThat(positionRepresentation[6]).hasSize(21);
        assertThat(positionRepresentation[7]).hasSize(21);
        assertThat(positionRepresentation[8]).hasSize(21);
        assertThat(positionRepresentation[9]).hasSize(21);
        assertThat(positionRepresentation[10]).hasSize(21);
    }
}
