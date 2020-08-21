package com.ly.tetris.game.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;

public class OPieceTest {
    OPiece piece = null;
    ArrayList<LocationPosn> expectedOccupied;

    public OPieceTest() {
        expectedOccupied = new ArrayList<LocationPosn>();
        expectedOccupied.add(new LocationPosn(18 + 0, 3 + 1));
        expectedOccupied.add(new LocationPosn(18 + 0, 3 + 2));
        expectedOccupied.add(new LocationPosn(18 + 1, 3 + 1));
        expectedOccupied.add(new LocationPosn(18 + 1, 3 + 2));
    }

    @BeforeEach
    public void initialize() {
        piece = new OPiece();
    }

    @Test
    public void nameIsO() {
        assertEquals(PieceName.O, piece.name());
    }

    @Test
    public void spawnLocationIsCorrect() {
        LocationPosn expected = new LocationPosn(18, 3);
        LocationPosn actual = piece.getAbsolutePosition();
        assertEquals(expected, actual, "Spawn location is incorrect.");
    }

    @Test
    public void spawnSquaresOccupiedAreCorrect() {
        ArrayList<LocationPosn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void spawnOrientationIsCorrect() {
        PieceOrientation expected = PieceOrientation.UPRIGHT;
        PieceOrientation actual = piece.orientationNow();
        assertEquals(expected, actual);
    }

    @Test
    public void clockwiseRotationDoesNotChangePositionOfPiece() {
        LocationPosn before = piece.getAbsolutePosition();

        piece.performRotationClockwise();
        LocationPosn after = piece.getAbsolutePosition();
        assertEquals(before, after, "First rotation changes the position.");

        piece.performRotationClockwise();
        after = piece.getAbsolutePosition();
        assertEquals(before, after, "Second rotation changes the position.");

        piece.performRotationClockwise();
        after = piece.getAbsolutePosition();
        assertEquals(before, after, "Third rotation changes the position.");

        piece.performRotationClockwise();
        after = piece.getAbsolutePosition();
        assertEquals(before, after, "Fourth rotation changes the position.S");
    }

    @Test
    public void clockwiseRotationResultsInCorrectSquaresOccupied() {
        piece.performRotationClockwise();
        ArrayList<LocationPosn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void counterclockwiseRotationDoesNotChangePositionOfPiece() {
        LocationPosn before = piece.getAbsolutePosition();

        piece.performRotationCounterClockwise();
        LocationPosn after = piece.getAbsolutePosition();
        assertEquals(before, after,
                     "First rotation changes the position.");

        piece.performRotationCounterClockwise();
        after = piece.getAbsolutePosition();
        assertEquals(before, after,
                     "Second rotation changes the position.");

        piece.performRotationCounterClockwise();
        after = piece.getAbsolutePosition();
        assertEquals(before, after,
                     "Third rotation changes the position.");

        piece.performRotationCounterClockwise();
        after = piece.getAbsolutePosition();
        assertEquals(before, after,
                     "Fourth rotation changes the position.");
    }

    @Test
    public void counterclockwiseRotatResultsInCorrectSquaresOccupied() {
        piece.performRotationCounterClockwise();
        ArrayList<LocationPosn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void
    orientationIfRotatedClockwiseShouldReturnIdenticalResult() {
        PieceOrientation actual = piece.orientationIfRotatedClockwise();
        piece.performRotationClockwise();
        PieceOrientation expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon rotation.");
    }

    @Test
    public void
    orientationIfRotatedCounterclockShouldReturnIdenticalResult() {
        PieceOrientation actual = 
            piece.orientationIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        PieceOrientation expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon rotation.");
    }

    @Test
    public void 
    occupiedIfRotatedClockwiseShouldReturnIdenticalResult() {
        ArrayList<LocationPosn> actual = piece.squaresOccupiedIfRotatedClockwise();
        piece.performRotationClockwise();
        ArrayList<LocationPosn> expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);
    }

    @Test
    public void 
    occupiedIfRotatedCounterclockShouldReturnIdenticalResult() {
        ArrayList<LocationPosn> actual = 
            piece.squaresOccupiedIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        ArrayList<LocationPosn> expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);
    }

    // =====================
    // Private helper methods
    // =====================

    // Asserts that expected is a permutation of actual.
    private void 
    isPermutation(ArrayList<LocationPosn> expected, ArrayList<LocationPosn> actual) {
        assertEquals(expected.size(), actual.size(),
                     "Not of right length: ");

        ListIterator<LocationPosn> it = expected.listIterator();
        while (it.hasNext()) {
            LocationPosn shouldContain = it.next();
            int searched = actual.indexOf(shouldContain);
            assertTrue(searched >= 0, 
                "Doesn't contain expected posn (xxx, yyy). "
                .replaceFirst("xxx", Integer.toString(shouldContain.row))
                .replaceFirst("yyy", Integer.toString(shouldContain.col)));
        }
    }
}