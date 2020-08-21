package com.ly.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.game.pieces.IPiece;

public class MiscTests {
    @Test
    public void absolutePositionOfPieceNotModifiableThroughGetter() {
        IPiece piece = new IPiece();
        LocationPosn before = piece.getAbsolutePosition();
        before.row += 1;
        before.col -= 1;
        LocationPosn after = piece.getAbsolutePosition();
        assertNotEquals(
            before, after, 
            "Absolute position was modified " +
            "through getAbsolutePosition().");
    }

    @Test
    public void twoEqualPosnsAreEqual() {
        LocationPosn a = new LocationPosn(0, 1);
        LocationPosn b = new LocationPosn(0, 1);
        OffsetPosn c = new OffsetPosn(0, 1);
        OffsetPosn d = new OffsetPosn(0, 1);
        assertEquals(a, b);
        assertEquals(c, d);
    }

    @Test
    public void twoUnequalPosnsAreNotEqual() {
        LocationPosn a = new LocationPosn(0, 1);
        LocationPosn b = new LocationPosn(1, 1);
        LocationPosn c = new LocationPosn(0, -1);
        OffsetPosn d = new OffsetPosn(0, 1);
        OffsetPosn e = new OffsetPosn(1, 1);
        OffsetPosn f = new OffsetPosn(0, -1);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, c);
        assertNotEquals(d, e);
        assertNotEquals(d, f);
        assertNotEquals(e, f);
    }

    @Test
    public void posnNotEqualToObjectOfDifferentType() {
        LocationPosn a = new LocationPosn(0, 0);
        OffsetPosn b = new OffsetPosn(0, 0);
        assertNotEquals(a, 4);
        assertNotEquals(b, a, "LocationPosn equal to OffsetPosn.");
        assertNotEquals(a, b, "LocationPosn equal to OffsetPosn.");
    }

    @Test
    public void posnAdditionAddsRowsToRowsAndColsToCols() {
        LocationPosn a = new LocationPosn(1, 2);
        OffsetPosn b = new OffsetPosn(4, -3);
        OffsetPosn c = new OffsetPosn(5, 1);
        LocationPosn expectedA = new LocationPosn(5, -1);
        OffsetPosn expectedB = new OffsetPosn(9, -2);
        assertEquals(expectedA, a.add(b));
        assertEquals(expectedB, b.add(c));
    }

    @Test
    public void addingZeroPosnResultsInSamePosn() {
        OffsetPosn zero = new OffsetPosn(0, 0);
        LocationPosn a = new LocationPosn(10, 14);
        OffsetPosn expectedZero = new OffsetPosn(0, 0);
        LocationPosn expectedA = new LocationPosn(10, 14);
        assertEquals(expectedZero, zero.add(zero));
        assertEquals(expectedA, a.add(zero));
    }
}