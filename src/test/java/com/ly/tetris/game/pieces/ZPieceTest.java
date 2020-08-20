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
import com.ly.tetris.infostructs.Posn;
import com.ly.tetris.game.pieces.ZPiece;

public class ZPieceTest {
    ZPiece piece = null;
    ArrayList<LocationPosn> uprightSpawn;
    ArrayList<LocationPosn> leftSpawn;
    ArrayList<LocationPosn> rightSpawn;
    ArrayList<LocationPosn> upsidedownSpawn;

    public ZPieceTest() {
        uprightSpawn = new ArrayList<LocationPosn>();
        leftSpawn = new ArrayList<LocationPosn>();
        rightSpawn = new ArrayList<LocationPosn>();
        upsidedownSpawn = new ArrayList<LocationPosn>();

        uprightSpawn.add(new LocationPosn(18 + 0, 4 + 0));
        uprightSpawn.add(new LocationPosn(18 + 0, 4 + 1));
        uprightSpawn.add(new LocationPosn(18 + 1, 4 + 1));
        uprightSpawn.add(new LocationPosn(18 + 1, 4 + 2));

        rightSpawn.add(new LocationPosn(18 + 0, 4 + 2));
        rightSpawn.add(new LocationPosn(18 + 1, 4 + 2));
        rightSpawn.add(new LocationPosn(18 + 1, 4 + 1));
        rightSpawn.add(new LocationPosn(18 + 2, 4 + 1));

        upsidedownSpawn.add(new LocationPosn(18 + 1, 4 + 0));
        upsidedownSpawn.add(new LocationPosn(18 + 1, 4 + 1));
        upsidedownSpawn.add(new LocationPosn(18 + 2, 4 + 1));
        upsidedownSpawn.add(new LocationPosn(18 + 2, 4 + 2));

        leftSpawn.add(new LocationPosn(18 + 0, 4 + 1));
        leftSpawn.add(new LocationPosn(18 + 1, 4 + 1));
        leftSpawn.add(new LocationPosn(18 + 1, 4 + 0));
        leftSpawn.add(new LocationPosn(18 + 2, 4 + 0));
    }

    @BeforeEach
    public void initialize() {
        piece = new ZPiece();
    }

    @Test
    public void nameIsZ() {
        assertEquals(PieceName.Z, piece.name());
    }

    @Test
    public void spawnLocationIsCorrect() {
        LocationPosn expected = new LocationPosn(18, 4);
        LocationPosn actual = piece.getAbsolutePosition();
        assertEquals(expected, actual, "Spawn location incorrect. ");
    }

    @Test
    public void spawnSquaresOccupiedAreCorrect() {
        ArrayList<LocationPosn> expected = uprightSpawn;
        ArrayList<LocationPosn> actual = piece.squaresOccupiedNow();
        this.isPermutation(expected, actual);
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
        ArrayList<LocationPosn> expectedFirstRotate = rightSpawn;
        ArrayList<LocationPosn> expectedSecondRotate = upsidedownSpawn;
        ArrayList<LocationPosn> expectedThirdRotate = leftSpawn;
        ArrayList<LocationPosn> expectedFourthRotate = uprightSpawn;

        piece.performRotationClockwise();
        ArrayList<LocationPosn> actual = piece.squaresOccupiedNow();
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
        PieceOrientation actual = piece.orientationNow();
        assertEquals(PieceOrientation.RIGHT, actual);

        piece.performRotationClockwise();
        actual = piece.orientationNow();
        assertEquals(PieceOrientation.UPSIDEDOWN, actual);

        piece.performRotationClockwise();
        actual = piece.orientationNow();
        assertEquals(PieceOrientation.LEFT, actual);

        piece.performRotationClockwise();
        actual = piece.orientationNow();
        assertEquals(PieceOrientation.UPRIGHT, actual);
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
        ArrayList<LocationPosn> expectedFirstRotate = leftSpawn;
        ArrayList<LocationPosn> expectedSecondRotate = upsidedownSpawn;
        ArrayList<LocationPosn> expectedThirdRotate = rightSpawn;
        ArrayList<LocationPosn> expectedFourthRotate = uprightSpawn;

        piece.performRotationCounterClockwise();
        ArrayList<LocationPosn> actual = piece.squaresOccupiedNow();
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
        PieceOrientation actual = piece.orientationNow();
        assertEquals(PieceOrientation.LEFT, actual);

        piece.performRotationCounterClockwise();
        actual = piece.orientationNow();
        assertEquals(PieceOrientation.UPSIDEDOWN, actual);

        piece.performRotationCounterClockwise();
        actual = piece.orientationNow();
        assertEquals(PieceOrientation.RIGHT, actual);

        piece.performRotationCounterClockwise();
        actual = piece.orientationNow();
        assertEquals(PieceOrientation.UPRIGHT, actual);
    }

    @Test
    public void
    orientationIfRotatedClockwiseShouldReturnIdenticalResult() {
        PieceOrientation actual = piece.orientationIfRotatedClockwise();
        piece.performRotationClockwise();
        PieceOrientation expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon first rotation.");
        
        actual = piece.orientationIfRotatedClockwise();
        piece.performRotationClockwise();
        expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon second rotation.");

        actual = piece.orientationIfRotatedClockwise();
        piece.performRotationClockwise();
        expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon third rotation.");
        
        actual = piece.orientationIfRotatedClockwise();
        piece.performRotationClockwise();
        expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon fourth rotation.");
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
            "Orientation doesn't match upon first rotation.");
        
        actual = piece.orientationIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon second rotation.");

        actual = piece.orientationIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon third rotation.");
        
        actual = piece.orientationIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        expected = piece.orientationNow();
        assertEquals(
            expected, actual, 
            "Orientation doesn't match upon fourth rotation.");
    }

    @Test
    public void
    occupiedIfRotatedClockwiseShouldReturnIdenticalResult() {
        ArrayList<LocationPosn> actual = piece.squaresOccupiedIfRotatedClockwise();
        piece.performRotationClockwise();
        ArrayList<LocationPosn> expected = piece.squaresOccupiedNow();
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
        ArrayList<LocationPosn> actual = 
            piece.squaresOccupiedIfRotatedCounterClockwise();
        piece.performRotationCounterClockwise();
        ArrayList<LocationPosn> expected = piece.squaresOccupiedNow();
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
    isPermutation(ArrayList<LocationPosn> expected, ArrayList<LocationPosn> actual) {
        assertEquals(expected.size(), actual.size(),
                     "Not of right length: ");

        ListIterator<LocationPosn> it = expected.listIterator();
        while (it.hasNext()) {
            LocationPosn shouldContain = it.next();
            int searched = actual.indexOf(shouldContain);
            assertTrue(searched >= 0, 
                       "Doesn't contain expected posns. ");
        }
    }
}