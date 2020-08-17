package com.ly.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.Posn;
import com.ly.tetris.game.pieces.SPiece;

public class SPieceTest {
    SPiece piece = null;
    ArrayList<Posn> upright;
    ArrayList<Posn> left;
    ArrayList<Posn> right;
    ArrayList<Posn> upsidedown;

    public SPieceTest() {
        upright = new ArrayList<Posn>();
        left = new ArrayList<Posn>();
        right = new ArrayList<Posn>();
        upsidedown = new ArrayList<Posn>();

        upright.add(new Posn(0, 1));
        upright.add(new Posn(0, 2));
        upright.add(new Posn(1, 0));
        upright.add(new Posn(1, 1));

        right.add(new Posn(0, 1));
        right.add(new Posn(1, 1));
        right.add(new Posn(1, 2));
        right.add(new Posn(2, 2));

        upsidedown.add(new Posn(1, 1));
        upsidedown.add(new Posn(1, 2));
        upsidedown.add(new Posn(2, 0));
        upsidedown.add(new Posn(2, 1));

        left.add(new Posn(0, 0));
        left.add(new Posn(1, 0));
        left.add(new Posn(1, 1));
        left.add(new Posn(2, 1));
    }

    @BeforeEach
    public void initialize() {
        piece = new SPiece();
    }

    @Test
    public void spawnLocationIsCorrect() {
        Posn expected = new Posn(18, 4);
        Posn actual = piece.getAbsolutePosition();
        assertEquals(expected, actual, "Spawn location incorrect.");
    }

    @Test
    public void spawnOrientationIsCorrect() {
        ArrayList<Posn> expected = upright;
        ArrayList<Posn> actual = piece.occupiedNow();
        this.isPermutation(expected, actual);
    }

    @Test
    public void rotateClockwiseCorrectly() {
        ArrayList<Posn> expectedFirstRotate = right;
        ArrayList<Posn> expectedSecondRotate = upsidedown;
        ArrayList<Posn> expectedThirdRotate = left;
        ArrayList<Posn> expectedFourthRotate = upright;

        piece.rotateClockwise();
        ArrayList<Posn> actual = piece.occupiedNow();
        this.isPermutation(expectedFirstRotate, actual);

        piece.rotateClockwise();
        actual = piece.occupiedNow();
        this.isPermutation(expectedSecondRotate, actual);

        piece.rotateClockwise();
        actual = piece.occupiedNow();
        this.isPermutation(expectedThirdRotate, actual);

        piece.rotateClockwise();
        actual = piece.occupiedNow();
        this.isPermutation(expectedFourthRotate, actual);
    }

    @Test
    public void rotateCounterClockwiseCorrectly() {
        ArrayList<Posn> expectedFirstRotate = left;
        ArrayList<Posn> expectedSecondRotate = upsidedown;
        ArrayList<Posn> expectedThirdRotate = right;
        ArrayList<Posn> expectedFourthRotate = upright;

        piece.rotateCounterClockwise();
        ArrayList<Posn> actual = piece.occupiedNow();
        this.isPermutation(expectedFirstRotate, actual);

        piece.rotateCounterClockwise();
        actual = piece.occupiedNow();
        this.isPermutation(expectedSecondRotate, actual);

        piece.rotateCounterClockwise();
        actual = piece.occupiedNow();
        this.isPermutation(expectedThirdRotate, actual);

        piece.rotateCounterClockwise();
        actual = piece.occupiedNow();
        this.isPermutation(expectedFourthRotate, actual);
    }

    @Test
    public void
    occupiedIfRotatedClockwiseShouldReturnIdenticalResult() {
        ArrayList<Posn> actual = piece.occupiedIfRotatedClockwise();
        piece.rotateClockwise();
        ArrayList<Posn> expected = piece.occupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.occupiedIfRotatedClockwise();
        piece.rotateClockwise();
        expected = piece.occupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.occupiedIfRotatedClockwise();
        piece.rotateClockwise();
        expected = piece.occupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.occupiedIfRotatedClockwise();
        piece.rotateClockwise();
        expected = piece.occupiedNow();
        this.isPermutation(expected, actual);
    }

    @Test
    public void 
    occupiedIfRotatedCounterclockShouldReturnIdenticalResult() {
        ArrayList<Posn> actual = piece.occupiedIfRotatedCounterClockwise();
        piece.rotateCounterClockwise();
        ArrayList<Posn> expected = piece.occupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.occupiedIfRotatedCounterClockwise();
        piece.rotateCounterClockwise();
        expected = piece.occupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.occupiedIfRotatedCounterClockwise();
        piece.rotateCounterClockwise();
        expected = piece.occupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.occupiedIfRotatedCounterClockwise();
        piece.rotateCounterClockwise();
        expected = piece.occupiedNow();
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