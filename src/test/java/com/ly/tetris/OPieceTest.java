package com.ly.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void spawnOrientationIsCorrect() {
        ArrayList<Posn> actual = piece.occupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void rotateClockwiseCorrectly() {
        piece.rotateClockwise();
        ArrayList<Posn> actual = piece.occupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void rotateCounterClockwiseCorrectly() {
        piece.rotateCounterClockwise();
        ArrayList<Posn> actual = piece.occupiedNow();
        this.isPermutation(expectedOccupied, actual);
    }

    @Test
    public void 
    occupiedIfRotatedClockwiseShouldReturnIdenticalResult() {
        ArrayList<Posn> actual = piece.occupiedIfRotatedClockwise();
        piece.rotateClockwise();
        ArrayList<Posn> expected = piece.occupiedNow();
        this.isPermutation(expected, actual);
    }

    @Test
    public void 
    occupiedIfRotatedCounterclockShouldReturnIdenticalResult() {
        ArrayList<Posn> actual = piece.occupiedIfRotatedCounterClockwise();
        piece.rotateCounterClockwise();
        ArrayList<Posn> expected = piece.occupiedNow();
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
                       "Doesn't contain expected positions. ");
        }
    }
}