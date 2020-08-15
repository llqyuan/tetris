package com.ly.tetris.game.pieces;

import java.util.ArrayList;
import com.ly.tetris.infostructs.Posn;

public abstract class Piece {
    // The absolute position of the piece. It represents the upper left 
    // corner of the piece's local field of occupied squares (shape of 
    // field depends on the piece -- eg. 4x4 for I, 3x3 for L)
    private Posn absolutePosition;

    // Constructor. Sets absolute position to (r, c)
    public Piece(int r, int c) {
        Posn p = new Posn(r, c);
        absolutePosition = p;
    }

    public Posn getAbsolutePosition() {
        return absolutePosn;
    }

    public void setAbsolutePosition(Posn posn) {
        absolutePosition.row = posn.row;
        absolutePosition.col = posn.col;
    }

    // Rotate the piece counterclockwise by 90 degrees.
    public abstract void rotateCounterClockwise();

    // Rotate the piece clockwise by 90 degrees.
    public abstract void rotateClockwise();

    // Returns an arraylist of posns, representing squares relative to
    // the piece's absolute position, that are currently occupied.
    public abstract ArrayList<Posn> occupiedNow();

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated clockwise.
    public abstract ArrayList<Posn> occupiedIfRotatedClockwise();

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated counterclockwise.
    public abstract ArrayList<Posn> occupiedIfRotatedCounterClockwise();
}