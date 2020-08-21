package com.ly.tetris.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;

public class SuperRotationSystemBasicTest {

    ArrayList<SimpleEntry<PieceOrientation, PieceOrientation>> 
    legalRotations;
    ArrayList<SimpleEntry<PieceOrientation, PieceOrientation>> 
    illegalRotations;

    public SuperRotationSystemBasicTest() {
        legalRotations = new ArrayList<SimpleEntry<PieceOrientation, PieceOrientation>>();
        illegalRotations = new ArrayList<SimpleEntry<PieceOrientation, PieceOrientation>>();

        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPRIGHT, PieceOrientation.RIGHT));
        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.RIGHT, PieceOrientation.UPRIGHT));
        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.RIGHT, PieceOrientation.UPSIDEDOWN));
        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPSIDEDOWN, PieceOrientation.RIGHT));
        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPSIDEDOWN, PieceOrientation.LEFT));
        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.LEFT, PieceOrientation.UPSIDEDOWN));
        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.LEFT, PieceOrientation.UPRIGHT));
        legalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPRIGHT, PieceOrientation.LEFT));

        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPRIGHT, PieceOrientation.UPSIDEDOWN));
        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPSIDEDOWN, PieceOrientation.UPRIGHT));
        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.RIGHT, PieceOrientation.LEFT));
        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.LEFT, PieceOrientation.RIGHT));
        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPRIGHT, PieceOrientation.UPRIGHT));
        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.RIGHT, PieceOrientation.RIGHT));
        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.UPSIDEDOWN, PieceOrientation.UPSIDEDOWN));
        illegalRotations.add(
            new SimpleEntry<PieceOrientation, PieceOrientation>(
                PieceOrientation.LEFT, PieceOrientation.LEFT));
    }

    @Test
    public void correctNumberOfWallKickSuggestionsForO() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = legalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation 
                = it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.O, start, end);
            assertEquals(
                1, suggested.size(),
                "Incorrect number of suggested kicks for O: " + 
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void correctNumberOfWallKickSuggestionsForI() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = legalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation 
                = it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.I, start, end);
            assertEquals(
                5, suggested.size(),
                "Incorrect number of suggested kicks for I: " + 
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void correctNumberOfWallKickSuggestionsForJ() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = legalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation 
                = it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.J, start, end);
            assertEquals(
                5, suggested.size(),
                "Incorrect number of suggested kicks for J: " + 
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void correctNumberOfWallKickSuggestionsForL() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = legalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation 
                = it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.L, start, end);
            assertEquals(
                5, suggested.size(),
                "Incorrect number of suggested kicks for L: " + 
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void correctNumberOfWallKickSuggestionsForS() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = legalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation 
                = it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.S, start, end);
            assertEquals(
                5, suggested.size(),
                "Incorrect number of suggested kicks for S: " + 
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void correctNumberOfWallKickSuggestionsForT() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = legalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation 
                = it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.T, start, end);
            assertEquals(
                5, suggested.size(),
                "Incorrect number of suggested kicks for T: " + 
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void correctNumberOfWallKickSuggestionsForZ() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = legalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation 
                = it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.Z, start, end);
            assertEquals(
                5, suggested.size(),
                "Incorrect number of suggested kicks for Z: " + 
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void noWallKickSuggestionsForIllegalRotation() {
        ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
        it = illegalRotations.listIterator();

        while (it.hasNext()) {

            SimpleEntry<PieceOrientation, PieceOrientation> rotation =
                it.next();
            PieceOrientation start = rotation.getKey();
            PieceOrientation end = rotation.getValue();
            ArrayList<OffsetPosn> suggested = 
                SuperRotationSystem.kicksToAttempt(PieceName.J, start, end);
            assertEquals(
                0, suggested.size(),
                "There are wall kicks suggested for illegal rotation O: " +
                "{0} -> {1}."
                .replaceFirst("\\{0\\}", start.toString())
                .replaceFirst("\\{1\\}", end.toString()));

        }
    }

    @Test
    public void firstSuggestedWallKickIsZeroPosn() {
        ArrayList<PieceName> allPieces = new ArrayList<PieceName>();
        allPieces.add(PieceName.I);
        allPieces.add(PieceName.J);
        allPieces.add(PieceName.L);
        allPieces.add(PieceName.O);
        allPieces.add(PieceName.S);
        allPieces.add(PieceName.T);
        allPieces.add(PieceName.Z);
        OffsetPosn expected = new OffsetPosn(0, 0);

        ListIterator<PieceName> pieceIter = allPieces.listIterator();

        while (pieceIter.hasNext()) {

            PieceName piece = pieceIter.next();
            ListIterator<SimpleEntry<PieceOrientation, PieceOrientation>>
                rotationIter = legalRotations.listIterator();

            while (rotationIter.hasNext()) {

                SimpleEntry<PieceOrientation, PieceOrientation>
                    rotation = rotationIter.next();
                PieceOrientation start = rotation.getKey();
                PieceOrientation end = rotation.getValue();
                ArrayList<OffsetPosn> suggested = 
                    SuperRotationSystem.kicksToAttempt(piece, start, end);
                assertTrue(suggested.size() > 0, 
                           "List of suggested kicks is empty.");
                assertEquals(expected, suggested.get(0),
                            "First suggested posn for 0: 1 -> 2 is not zero."
                            .replaceFirst("0", piece.toString())
                            .replaceFirst("1", start.toString())
                            .replaceFirst("2", end.toString()));

            }
        }
    }
}