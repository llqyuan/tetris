package com.ly.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import com.ly.tetris.infostructs.Posn;
import com.ly.tetris.game.pieces.IPiece;

public class MiscTests {
    @Test
    public void absolutePositionOfPieceNotModifiableThroughGetter() {
        IPiece piece = new IPiece();
        Posn before = piece.getAbsolutePosition();
        before.row += 1;
        before.col -= 1;
        Posn after = piece.getAbsolutePosition();
        assertNotEquals(
            before, after, 
            "Absolute position was modified " +
            "through getAbsolutePosition().");
    }

    @Test
    public void twoEqualPosnsAreEqual() {
        Posn a = new Posn(0, 1);
        Posn b = new Posn(0, 1);
        assertEquals(a, b);
    }

    @Test
    public void twoUnequalPosnsAreNotEqual() {
        Posn a = new Posn(0, 1);
        Posn b = new Posn(1, 1);
        Posn c = new Posn(0, -1);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, c);
    }

    @Test
    public void posnNotEqualToObjectOfDifferentType() {
        Posn a = new Posn(0, 0);
        assertNotEquals(a, 4);
        assertNotEquals(4, a);
    }
}