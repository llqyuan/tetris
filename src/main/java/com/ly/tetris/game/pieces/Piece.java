package com.ly.tetris.game.pieces;

import java.util.ArrayList;
import java.util.ListIterator;

import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;

public abstract class Piece {
    // The absolute position of the piece. It represents the upper left 
    // corner of the piece's local field of occupied squares (shape of 
    // field depends on the piece -- eg. 4x4 for I, 3x3 for L)
    private LocationPosn absolutePosition = null;

    // The orientation of the piece.
    private PieceOrientation orientation = PieceOrientation.UPRIGHT;

    // Constructor. Sets absolute position to (r, c)
    public Piece(int r, int c, PieceOrientation o) {
        absolutePosition = new LocationPosn(r, c);
        orientation = o;
    }

    // Returns the name of the piece.
    public final PieceName name() {
        return this.nameOfPiece();
    }

    // Getter for the piece's absolute position. 
    // (Returns a copy, so the client can't modify it)
    public final LocationPosn getAbsolutePosition() {
        return new LocationPosn(absolutePosition);
    }

    // Sets the piece's absolute position to posn.
    // Requires: posn is not null
    public final void setAbsolutePosition(LocationPosn posn) 
    throws NullPointerException 
    {
        if (posn == null) {
            throw new NullPointerException("Passed posn is null.");
        }
        absolutePosition.row = posn.row;
        absolutePosition.col = posn.col;
    }

    // Rotate the piece counterclockwise by 90 degrees.
    // Effects: 
    // * Modifies the piece
    public final void performRotationClockwise() {

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
    // Effects:
    // * Modifies the piece
    public final void performRotationCounterClockwise() {
        
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

    // Returns an arraylist of posns that are currently occupied 
    // on the board.
    public final ArrayList<LocationPosn> squaresOccupiedNow() {
        ArrayList<LocationPosn> absoluteOccupied = 
            new ArrayList<LocationPosn>();
        ArrayList<OffsetPosn> relativeOccupied = this.occupiedNow();
        ListIterator<OffsetPosn> it = relativeOccupied.listIterator();
        while (it.hasNext()) {
            absoluteOccupied.add(absolutePosition.add(it.next()));
        }
        return absoluteOccupied;
    }

    // Returns the piece's orientation.
    public final PieceOrientation orientationNow() {
        return orientation;
    }

    // Returns what the piece's orientation would be if it were 
    // rotated clockwise.
    public final PieceOrientation orientationIfRotatedClockwise() {
        if (this.orientation == PieceOrientation.UPRIGHT) {
            return PieceOrientation.RIGHT;
        } else if (this.orientation == PieceOrientation.RIGHT) {
            return PieceOrientation.UPSIDEDOWN;
        } else if (this.orientation == PieceOrientation.UPSIDEDOWN) {
            return PieceOrientation.LEFT;
        } else {
            return PieceOrientation.UPRIGHT;
        }
    }

    // Returns what the piece's orientation would be if it were 
    // rotated counterclockwise.
    public final PieceOrientation orientationIfRotatedCounterClockwise() {
        if (this.orientation == PieceOrientation.UPRIGHT) {
            return PieceOrientation.LEFT;
        } else if (this.orientation == PieceOrientation.LEFT) {
            return PieceOrientation.UPSIDEDOWN;
        } else if (this.orientation == PieceOrientation.UPSIDEDOWN) {
            return PieceOrientation.RIGHT;
        } else {
            return PieceOrientation.UPRIGHT;
        }
    }

    // Returns an arraylist of posns that will be occupied on the board 
    // if the piece were to be rotated clockwise.
    public final ArrayList<LocationPosn> squaresOccupiedIfRotatedClockwise() {
        ArrayList<LocationPosn> absoluteOccupied = new ArrayList<LocationPosn>();
        ArrayList<OffsetPosn> relativeOccupied = this.occupiedIfRotatedClockwise();
        ListIterator<OffsetPosn> it = relativeOccupied.listIterator();
        while (it.hasNext()) {
            absoluteOccupied.add(absolutePosition.add(it.next()));
        }
        return absoluteOccupied;
    }

    // Returns an arraylist of posns that will be occupied on the board 
    // if the piece were to be rotated counterclockwise.
    public final ArrayList<LocationPosn> squaresOccupiedIfRotatedCounterClockwise() {
        ArrayList<LocationPosn> absoluteOccupied = new ArrayList<LocationPosn>();
        ArrayList<OffsetPosn> relativeOccupied = 
            this.occupiedIfRotatedCounterClockwise();
        ListIterator<OffsetPosn> it = relativeOccupied.listIterator();
        while (it.hasNext()) {
            absoluteOccupied.add(absolutePosition.add(it.next()));
        }
        return absoluteOccupied;
    }

    // ========================================
    // Helper methods
    // ========================================

    // Returns the name of the piece. (Protected virtual helper)
    protected abstract PieceName nameOfPiece();

    // Rotate the piece counterclockwise by 90 degrees. (Protected 
    // virtual helper)
    protected abstract void rotateCounterClockwise();

    // Rotate the piece clockwise by 90 degrees. (Protected virtual 
    // helper)
    protected abstract void rotateClockwise();

    // Returns an arraylist of posns, representing squares relative to
    // the piece's absolute position, that are currently occupied.
    // (Protected virtual helper)
    protected abstract ArrayList<OffsetPosn> occupiedNow();

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated clockwise.
    // (Protected virtual helper)
    protected abstract ArrayList<OffsetPosn> occupiedIfRotatedClockwise();

    // Returns an arraylist of posns, representing squares relative to 
    // the piece's absolute position, that will be occupied if the 
    // piece were to be rotated counterclockwise.
    // (Protected virtual helper)
    protected abstract ArrayList<OffsetPosn> occupiedIfRotatedCounterClockwise();
}