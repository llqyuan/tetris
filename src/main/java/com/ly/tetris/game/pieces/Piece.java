package com.ly.tetris.game.pieces;

import java.util.ArrayList;
import java.util.ListIterator;

import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.RotationDirection;

/**
Piece defines the implementation for the 7 Tetris pieces. The following 
responsibilities are delegated to the piece classes:

 * Storing the shape of the piece
 * Storing the location and orientation of the piece
 * Determining the new shape of the piece after rotation
*/

public abstract class Piece {
    /** 
    The absolute position of the piece. It represents the upper left 
    corner of the piece's local field of occupied squares (shape of 
    field depends on the piece -- eg. 4x4 for I, 3x3 for L)
    */
    private LocationPosn absolutePosition = null;

    /** 
    The orientation of the piece.
    */
    private PieceOrientation orientation = PieceOrientation.UPRIGHT;

    /*
    ===================================================
    Public interface
    ===================================================
    */

    /**
     * Constructor. Sets absolute position to (r, c)
     * @param r row of absolute position
     * @param c column of absolute position
     * @param o orientation of piece
     */
    public Piece(int r, int c, PieceOrientation o) {
        absolutePosition = new LocationPosn(r, c);
        orientation = o;
    }

    /**
     * Returns the name of this piece
     * @return
     */
    public final PieceName name() {
        return this.nameOfPiece();
    }

    /**
    Move the piece down by diffr and right by diffc.
    Effects:
     * Modifies the piece
     * @param diffr move the piece down by this amount
     * @param diffc move the piece right by this amount
     */
    public final void movePieceBy(int diffr, int diffc) {
        absolutePosition = absolutePosition.add(new OffsetPosn(diffr, diffc));
    }

    /** 
    Rotate the piece counterclockwise by 90 degrees.
    Effects: 
    * Modifies the piece
    */
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

    /** 
    Rotate the piece clockwise by 90 degrees.
    Effects:
    * Modifies the piece
    */
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

    /** 
    Returns an arraylist of posns that are currently occupied 
    on the board.
    */
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

    /** 
    Returns the piece's orientation.
    */
    public final PieceOrientation orientationNow() {
        return orientation;
    }

    /** 
    Returns what the piece's orientation would be if it were 
    rotated clockwise.
    */
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

    /** 
    Returns what the piece's orientation would be if it were 
    rotated counterclockwise.
    */
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

    /** 
    Returns an arraylist of posns that would be occupied on the board 
    if the piece were to be rotated clockwise.
    */
    public final ArrayList<LocationPosn> squaresOccupiedIfRotatedClockwise() {
        ArrayList<LocationPosn> absoluteOccupied = new ArrayList<LocationPosn>();
        ArrayList<OffsetPosn> relativeOccupied = this.occupiedIfRotatedClockwise();
        ListIterator<OffsetPosn> it = relativeOccupied.listIterator();
        while (it.hasNext()) {
            absoluteOccupied.add(absolutePosition.add(it.next()));
        }
        return absoluteOccupied;
    }

    /**
     * Returns an arraylist of posns that would be occupied on the board 
    if the piece were to be rotated counterclockwise.
     */

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

    /**
     * Returns an arraylist of posns that would be occupied on the board 
     * if the piece were to be moved down by diffr and right by diffc.
     * @param diffr move the piece down by this amount
     * @param diffc move the piece right by this amount
     * @return positions that would be occupied on the board if moved
     */
    public final ArrayList<LocationPosn> 
    squaresOccupiedIfMoved(int diffr, int diffc) {
        ArrayList<LocationPosn> occupiedBeforeMove = 
            this.squaresOccupiedNow();
        return this.offsetList(occupiedBeforeMove, new OffsetPosn(diffr, diffc));
    }

    /**
     * Returns an arraylist of posns that would be occupied on the board 
     * if the piece were to be moved down by diffr, right by diffc, 
     * and rotated in the direction rotateInDirection.
     * @param diffr move the piece down by this amount
     * @param diffc move the piece right by this amount
     * @param rotateInDirection rotate in this direction
     * @return positions that would be occupied on the board if moved
     */
    public final ArrayList<LocationPosn>
    squaresOccupiedIfMovedAndRotated(
        int diffr, 
        int diffc, 
        RotationDirection rotateInDirection)
    {
        ArrayList<LocationPosn> occupiedIfRotated;
        if (rotateInDirection == RotationDirection.CLOCKWISE) {
            occupiedIfRotated = this.squaresOccupiedIfRotatedClockwise();
        } else {
            occupiedIfRotated = this.squaresOccupiedIfRotatedCounterClockwise();
        }
        ArrayList<LocationPosn> occupiedIfMovedAndRotated = 
            this.offsetList(occupiedIfRotated, new OffsetPosn(diffr, diffc));
        return occupiedIfMovedAndRotated;
    }

    /*
    ========================================
    Interface provided only for child classes
    ========================================
    */

    /**
     * 
     * @return a copy of the position of the piece.
     */
    protected final LocationPosn getAbsolutePosition() {
        return new LocationPosn(absolutePosition);
    }

    /*
    ========================================
    Private/protected helper methods
    ========================================
    */

    /**
     * 
     * @return name of the piece
     */
    protected abstract PieceName nameOfPiece();

    /** 
    Rotate the piece counterclockwise by 90 degrees. (Protected 
    virtual helper)
    */
    protected abstract void rotateCounterClockwise();

    /** 
    Rotate the piece clockwise by 90 degrees. (Protected virtual 
    helper)
    */
    protected abstract void rotateClockwise();

    /**
     * 
     * @return an arraylist of posns, representing square relative to
     * the piece's absolute position, that are currently occupied.
     */
    protected abstract ArrayList<OffsetPosn> occupiedNow();

    /**
     * 
     * @return an arraylist of posns, representing squares relative to 
     * the piece's absolute position, that would be occupied if the 
     * piece were to be rotated clockwise.
     */
    protected abstract ArrayList<OffsetPosn> occupiedIfRotatedClockwise();

    /**
     * 
     * @return an arraylist of posns, representing squares relative to 
     * the piece's absolute position, that would be occupied if the 
     * piece were to be rotated counterclockwise.
     */
    protected abstract ArrayList<OffsetPosn> occupiedIfRotatedCounterClockwise();

    /**
     * 
     * @param original list of original positions
     * @param offsetBy offset amount for each position
     * @return a list obtained from original by applying an offset 
     * of offsetBy to each entry.
     */
    private ArrayList<LocationPosn> 
    offsetList(ArrayList<LocationPosn> original, OffsetPosn offsetBy) {
        ArrayList<LocationPosn> offset  = new ArrayList<LocationPosn>();
        ListIterator<LocationPosn> iterOriginal = original.listIterator();
        while (iterOriginal.hasNext()) {
            offset.add(iterOriginal.next().add(offsetBy));
        }
        return offset;
    }
}