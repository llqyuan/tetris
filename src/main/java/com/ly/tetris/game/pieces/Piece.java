package com.ly.tetris.game.pieces;

import java.util.ArrayList;
import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.Posn;

public abstract class Piece {
    // The absolute position of the piece. It represents the upper left 
    // corner of the piece's local field of occupied squares (shape of 
    // field depends on the piece -- eg. 4x4 for I, 3x3 for L)
    private Posn absolutePosition = null;

    // The orientation of the piece.
    private PieceOrientation orientation = PieceOrientation.UPRIGHT;

    // Constructor. Sets absolute position to (r, c)
    public Piece(int r, int c, PieceOrientation o) {
        absolutePosition = new Posn(r, c);
        orientation = o;
    }

    // Getter for the piece's absolute position. 
    // (Returns a copy, so the client can't modify it)
    public Posn getAbsolutePosition() {
        Posn toReturn = new Posn(absolutePosition);
        return toReturn;
    }

    // Sets the piece's absolute position to posn.
    // Requires: posn is not null
    public void setAbsolutePosition(Posn posn) throws NullPointerException {
        if (posn == null) {
            throw new NullPointerException("Passed Posn is null.");
        }
        absolutePosition.row = posn.row;
        absolutePosition.col = posn.col;
    }

    // Getter for the piece's orientation.
    public PieceOrientation getOrientation() {
        return orientation;
    }

    // Rotate the piece counterclockwise by 90 degrees.
    public void performRotationClockwise() {

        this.rotateClockwise();

        if (orientation == PieceOrientation.UPRIGHT) {
            orientation = PieceOrientation.RIGHT;

        } else if (orientation == PieceOrientation.RIGHT) {
            orientation = PieceOrientation.UPSIDEDOWN;

        } else if (orientation == PieceOrientation.UPSIDEDOWN) {
            orientation = PieceOrientation.LEFT;

        } else {
            orientation = PieceOrientation.UPRIGHT;
        }
    }

    // Rotate the piece clockwise by 90 degrees.
    public void performRotationCounterClockwise() {
        
        this.rotateCounterClockwise();

        if (orientation == PieceOrientation.UPRIGHT) {
            orientation = PieceOrientation.LEFT;

        } else if (orientation == PieceOrientation.LEFT) {
            orientation = PieceOrientation.UPSIDEDOWN;

        } else if (orientation == PieceOrientation.UPSIDEDOWN) {
            orientation = PieceOrientation.RIGHT;

        } else {
            orientation = PieceOrientation.UPRIGHT;
        }
    }

    // Returns an arraylist of posns, representing squares relative to
    // the piece's absolute position, that are currently occupied.
    public ArrayList<Posn> squaresOccupiedNow() {
        return this.occupiedNow();
    }

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated clockwise.
    public ArrayList<Posn> squaresOccupiedIfRotatedClockwise() {
        return this.occupiedIfRotatedClockwise();
    }

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated counterclockwise.
    public ArrayList<Posn> squaresOccupiedIfRotatedCounterClockwise() {
        return this.occupiedIfRotatedCounterClockwise();
    }

    // ========================================
    // Helper methods
    // ========================================

    // Rotate the piece counterclockwise by 90 degrees. (Protected 
    // virtual helper)
    protected abstract void rotateCounterClockwise();

    // Rotate the piece clockwise by 90 degrees. (Protected virtual 
    // helper)
    protected abstract void rotateClockwise();

    // Returns an arraylist of posns, representing squares relative to
    // the piece's absolute position, that are currently occupied.
    // (Protected virtual helper)
    protected abstract ArrayList<Posn> occupiedNow();

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated clockwise.
    // (Protected virtual helper)
    protected abstract ArrayList<Posn> occupiedIfRotatedClockwise();

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated counterclockwise.
    // (Protected virtual helper)
    protected abstract ArrayList<Posn> occupiedIfRotatedCounterClockwise();
}