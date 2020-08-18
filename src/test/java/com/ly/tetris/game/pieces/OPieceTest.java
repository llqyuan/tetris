package com.ly.tetris.game.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.Posn;
import com.ly.tetris.game.pieces.OPiece;

public class OPieceTest {
    OPiece piece = null;
    ArrayList<Posn> expectedOccupied;

    public OPieceTest() {
        expectedOccupied = new ArrayList<Posn>();
        expectedOccupied.add(new Posn(0, 1));
        expectedOccupied.add(new Posn(0, 2));
        expectedOccupied.add(new Posn(1, 1));
        expectedOccupied.add(new Posn(1, 2));
    }

    @BeforeEach
    public void initialize() {
        piece = new OPiece();
    }

    @Test
    public void spawnLocationIsCorrect() {
        Posn expected = new Posn(18, 3);
        Posn actual = piece.getAbsolutePosition();
        assertEquals(expected, actual, "Spawn location is incorrect.");
    }

    @Test
    public void spawnSquaresOccupiedAreCorrect() {
        ArrayList<Posn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void spawnOrientationIsCorrect() {
        PieceOrientation expected = PieceOrientation.UPRIGHT;
        PieceOrientation actual = piece.getOrientation();
        assertEquals(expected, actual);
    }

    @Test
    public void clockwiseRotationDoesNotChangePositionOfPiece() {
        Posn before = piece.getAbsolutePosition();

        piece.performRotationClockwise();
        Posn after = piece.getAbsolutePosition();
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
        ArrayList<Posn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void counterclockwiseRotationDoesNotChangePositionOfPiece() {
        Posn before = piece.getAbsolutePosition();

        piece.performRotationCounterClockwise();
        Posn after = piece.getAbsolutePosition();
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
        ArrayList<Posn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void 
    occupiedIfRotatedClockwiseShouldReturnIdenticalResult() {
        ArrayList<Posn> actual = piece.squaresOccupiedIfRotatedClockwise();
        piece.performRotationClockwise();
        ArrayList<Posn> expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);
    }

    @Test
    public void 
    occupiedIfRotatedCounterclockShouldReturnIdenticalResult() {
        ArrayList<Posn> actual = 
            piece.squaresOccupiedIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        ArrayList<Posn> expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);
    }

    // =====================
    // Private helper methods
    // =====================

    // Asserts that expected is a permutation of actual.
    private void 
    isPermutation(ArrayList<Posn> expected, ArrayList<Posn> actual) {
        assertEquals(expected.size(), actual.size(),
                     "Not of right length: ");

        ListIterator<Posn> it = expected.listIterator();
        while (it.hasNext()) {
            Posn shouldContain = it.next();
            int searched = actual.indexOf(shouldContain);
            assertTrue(searched >= 0, 
                       "Doesn't contain expected posns. ");
        }
    }
}