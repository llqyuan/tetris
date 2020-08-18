package com.ly.tetris.game.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ListIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.PieceOrientation;
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
    public void spawnSquaresOccupiedAreCorrect() {
        ArrayList<Posn> expected = upright;
        ArrayList<Posn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);
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
        ArrayList<Posn> expectedFirstRotate = right;
        ArrayList<Posn> expectedSecondRotate = upsidedown;
        ArrayList<Posn> expectedThirdRotate = left;
        ArrayList<Posn> expectedFourthRotate = upright;

        piece.performRotationClockwise();
        ArrayList<Posn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedFirstRotate, actual);

        piece.performRotationClockwise();
        actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedSecondRotate, actual);

        piece.performRotationClockwise();
        actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedThirdRotate, actual);

        piece.performRotationClockwise();
        actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedFourthRotate, actual);
    }

    @Test
    public void clockwiseRotationResultsInCorrectOrientation() {
        piece.performRotationClockwise();
        PieceOrientation actual = piece.getOrientation();
        assertEquals(PieceOrientation.RIGHT, actual);

        piece.performRotationClockwise();
        actual = piece.getOrientation();
        assertEquals(PieceOrientation.UPSIDEDOWN, actual);

        piece.performRotationClockwise();
        actual = piece.getOrientation();
        assertEquals(PieceOrientation.LEFT, actual);

        piece.performRotationClockwise();
        actual = piece.getOrientation();
        assertEquals(PieceOrientation.UPRIGHT, actual);
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
        ArrayList<Posn> expectedFirstRotate = left;
        ArrayList<Posn> expectedSecondRotate = upsidedown;
        ArrayList<Posn> expectedThirdRotate = right;
        ArrayList<Posn> expectedFourthRotate = upright;

        piece.performRotationCounterClockwise();
        ArrayList<Posn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedFirstRotate, actual);

        piece.performRotationCounterClockwise();
        actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedSecondRotate, actual);

        piece.performRotationCounterClockwise();
        actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedThirdRotate, actual);

        piece.performRotationCounterClockwise();
        actual = piece.squaresOccupiedNow();
        this.isPermutation(expectedFourthRotate, actual);
    }

    @Test
    public void counterclockwiseRotatResultsInCorrectOrientation() {
        piece.performRotationCounterClockwise();
        PieceOrientation actual = piece.getOrientation();
        assertEquals(PieceOrientation.LEFT, actual);

        piece.performRotationCounterClockwise();
        actual = piece.getOrientation();
        assertEquals(PieceOrientation.UPSIDEDOWN, actual);

        piece.performRotationCounterClockwise();
        actual = piece.getOrientation();
        assertEquals(PieceOrientation.RIGHT, actual);

        piece.performRotationCounterClockwise();
        actual = piece.getOrientation();
        assertEquals(PieceOrientation.UPRIGHT, actual);
    }

    @Test
    public void
    occupiedIfRotatedClockwiseShouldReturnIdenticalResult() {
        ArrayList<Posn> actual = piece.squaresOccupiedIfRotatedClockwise();
        piece.performRotationClockwise();
        ArrayList<Posn> expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.squaresOccupiedIfRotatedClockwise();
        piece.performRotationClockwise();
        expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.squaresOccupiedIfRotatedClockwise();
        piece.performRotationClockwise();
        expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.squaresOccupiedIfRotatedClockwise();
        piece.performRotationClockwise();
        expected = piece.squaresOccupiedNow();
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

        actual = piece.squaresOccupiedIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.squaresOccupiedIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        expected = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);

        actual = piece.squaresOccupiedIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        expected = piece.squaresOccupiedNow();
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