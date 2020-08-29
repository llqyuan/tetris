package com.ly.tetris.game;

import java.util.ArrayList;
import java.util.ListIterator;
import com.ly.tetris.game.pieces.IPiece;
import com.ly.tetris.game.pieces.JPiece;
import com.ly.tetris.game.pieces.LPiece;
import com.ly.tetris.game.pieces.OPiece;
import com.ly.tetris.game.pieces.Piece;
import com.ly.tetris.game.pieces.SPiece;
import com.ly.tetris.game.pieces.TPiece;
import com.ly.tetris.game.pieces.ZPiece;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.RotationDirection;

public class Board {
    // ==============
    // Fields
    // ==============

    // The board. Rows 20 through 39 are the visible playing field.
    // Rows 0 through 19 are hidden, above the visible playing field.
    // The upper left corner is (0,0).
    private final Square[][] theBoard = new Square[40][10];

    // The piece currently falling. Should be null before spawning 
    // the first piece, or after locking a piece but before spawning a
    // new one.
    private Piece inPlay;

    // (drowToRow, [current column]) is where the piece currently 
    // in play would be on the board if dropped.
    // Should be correct at the beginning and end of every 
    // public function if a piece is in play.
    private int dropToRow;

    // ================
    // Constructors
    // ================

    // Initializes the board to be empty.
    public Board() {
        this.inPlay = null;
        for (int r = 0; r < 40; r++) {
            for (int c = 0; c < 10; c++) {
                theBoard[r][c] = new Square(r, c);
            }
        }
    }

    // Initializes the board with some squares already occupied.
    public Board(ArrayList<LocationPosn> alreadyOccupied) {
        this.inPlay = null;
        for (int r = 0; r < 40; r++) {
            for (int c = 0; c < 10; c++) {
                theBoard[r][c] = new Square(r, c);
            }
        }
        ListIterator<LocationPosn> iter = alreadyOccupied.listIterator();
        while (iter.hasNext()) {
            LocationPosn occupy = iter.next();
            if (isInRange(occupy)) {
                theBoard[occupy.row][occupy.col].occupiedBy = PieceName.I;
            }
        }
    }

    // =================
    // Public interface: Status of board
    // =================

    // Returns a copy of the board. The copy does not contain 
    // information on the piece in play.
    public Square[][] copyOfBoard() {
        Square[][] copy = new Square[40][10];
        for (int r = 0; r < 40; r++) {
            for (int c = 0; c < 10; c++) {
                copy[r][c] = new Square(theBoard[r][c]);
            }
        }
        return copy;
    }

    // Returns the name of the piece currently in play, or 
    // PieceName.NOTHING if none are in play.
    public PieceName pieceInPlay() {
        if (inPlay != null) {
            return inPlay.name();
        } else {
            return PieceName.NOTHING;
        }
    }

    // Returns the orientation of the piece currently in play.
    // Requires: there is a piece in play
    public PieceOrientation orientationOfPieceInPlay() 
    throws NullPointerException 
    {
        if (inPlay == null) {
            throw new NullPointerException(
                "Can't get orientation of a nonexistent piece in play.");
        }
        return inPlay.orientationNow();
    }

    // Returns the (row, col) coordinates of the squares currently 
    // occupied by the piece in play. List may be empty if no piece 
    // is in play.
    public ArrayList<LocationPosn> squaresOccupiedByPieceInPlay() {
        if (inPlay != null) {
            return inPlay.squaresOccupiedNow();
        } else {
            return new ArrayList<LocationPosn>();
        }
    }
    
    // Returns the (row, col) coordinates of the squares that 
    // the piece in play would occupy if it were hard-dropped now.
    // List may be empy if no piece is in play.
    public ArrayList<LocationPosn> squaresOccupiedByHardDropGhost() {
        if (inPlay == null) {
            return new ArrayList<LocationPosn>();
        }
        LocationPosn posnOfPieceInPlay = inPlay.getAbsolutePosition();
        OffsetPosn offset = 
            new OffsetPosn(this.dropToRow - posnOfPieceInPlay.row, 0);
        ArrayList<LocationPosn> squaresOccupiedByGhost = 
            this.offsetList(inPlay.squaresOccupiedNow(), offset);
        return squaresOccupiedByGhost;
    }

    // Returns the (row, col) coordinates of the squares on the board 
    // currently occupied by the Tetris stack -- ie. parts of 
    // previously dropped pieces, but not the piece that's currently 
    // falling.
    public ArrayList<LocationPosn> squaresOccupiedByStack() {
        ArrayList<LocationPosn> occupied = new ArrayList<LocationPosn>();
        for (int row = 0; row < 40; row++) {
            for (int col = 0; col < 10; col++) {
                if (theBoard[row][col].occupiedBy != PieceName.NOTHING) {
                    occupied.add(new LocationPosn(row, col));
                }
            }
        }
        return occupied;
    }

    // Returns true if there is a piece in play and it is in the 
    // air, and false otherwise.
    // Requires:
    // * dropToRow is up to date
    public boolean pieceIsInAir() {
        if (inPlay != null) {
            return dropToRow > inPlay.getAbsolutePosition().row;
        } else {
            return false;
        }
    }

    /* 
    Returns true if the piece in play is a T and at least three squares 
    adjacent to the center are occupied on the board, and false otherwise.
    */
    public boolean pieceIsTAndThreeAdjacentToCenterAreOccupied() {
        if (inPlay == null || inPlay.name() != PieceName.T) {
            return false;
        }
        TPiece tPieceInPlay = (TPiece)inPlay;
        ArrayList<LocationPosn> listOfAdjacentSquares = 
            tPieceInPlay.adjacentToCenter();
        
        int squaresOccupied = 0;
        ListIterator<LocationPosn> iterAdjacent = 
            listOfAdjacentSquares.listIterator();
        while (iterAdjacent.hasNext()) {
            LocationPosn adj = iterAdjacent.next();
            if (adj.row < 0 || adj.row >= 40 || adj.col < 0 || adj.col >= 10) {
                continue;
            }
            if (theBoard[adj.row][adj.col].occupiedBy != PieceName.NOTHING) {
                squaresOccupied += 1;
            }
        }
        return squaresOccupied >= 3;
    }

    /* 
    Returns true if the board is empty and false otherwise.
    */
    public boolean isEmpty() {
        for (int r = 0; r < 40; r++) {
            for (int c = 0; c < 10; c++) {
                if (theBoard[r][c].occupiedBy != PieceName.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }


    // =====================================
    // Public interface: Modifying the board
    // =====================================

    // Removes the piece from play. Returns the name of the piece 
    // in play, or NOTHING if no piece was in play.
    // Effects:
    // * Removes the current piece from play, if there was one.
    public PieceName removePieceFromPlay() {
        PieceName piece = PieceName.NOTHING;
        if (inPlay != null) {
            piece = inPlay.name();
        }
        inPlay = null;
        return piece;
    }

    // Attempts to move the piece in play left by one square.
    // Returns true if successful and false otherwise.
    // Requires:
    // * there is a piece in play
    // Effects:
    // * May modify board
    // * Updates dropToRow if the piece was successfully moved
    public boolean moveLeft() throws IllegalStateException {
        return this.moveDirection(0, -1);
    }

    // Attempts to move the piece in play right by one square.
    // Returns true if successful and false otherwise.
    // Requires:
    // * there is a piece in play
    // Effects:
    // * May modify the board
    // * Updates dropToRow if the piece was successfully moved
    public boolean moveRight() throws IllegalStateException {
        return this.moveDirection(0, 1);
    }

    // Attempts to move the piece in play down by one square.
    // Returns true if successful and false otherwise.
    // Requires:
    // * there is a piece in play
    // Effects:
    // * May modify the board
    // * Updates dropToRow if the piece was successfully moved
    public boolean moveDown() throws IllegalStateException {
        return this.moveDirection(1, 0);
    }

    // Hard drops the current piece and removes it from play.
    // Returns the number of lines cleared.
    // Requires:
    // * there is a piece in play
    // Effects: 
    // * Modifies the board.
    // * Removes the current piece in play from play, after which 
    //   no piece will be falling.
    public int hardDrop() throws IllegalStateException {
        if (inPlay != null) {
            movePieceToBottom();
            lockPiece();
            inPlay = null;
            return this.clearLines();
        } else {
            throw new IllegalStateException(
                "Tried to hard-drop a nonexistent piece.");
        }
    } 


    // Sonic-drops the current piece, if one is in play.
    // Effects:
    // * Modifies the board
    public void sonicDrop() {
        if (inPlay != null) {
            movePieceToBottom();
        }
    }

    // Attempts to spawn the new piece and update hard drop location.
    // Returns true if piece has room to spawn and false otherwise.
    // Requires: 
    // * there is no piece in play before calling the function
    // * piece is not NOTHING
    // Effects:
    // * May modify the board by spawning a new piece
    // * Updates dropToRow if the piece is successfully spawned
    public boolean spawn(PieceName piece) 
    throws IllegalArgumentException, IllegalStateException {
        if (inPlay != null) {
            throw new IllegalStateException(
                "Tried to spawn new piece while a piece was falling.");
        }
        Piece newlySpawned = null;
        switch (piece) {
            case I:
                newlySpawned = new IPiece();
                break;
            case J:
                newlySpawned = new JPiece();
                break;
            case L:
                newlySpawned = new LPiece();
                break;
            case O:
                newlySpawned = new OPiece();
                break;
            case S:
                newlySpawned = new SPiece();
                break;
            case T:
                newlySpawned = new TPiece();
                break;
            case Z:
                newlySpawned = new ZPiece();
                break;
            default:
                throw new IllegalArgumentException(
                    "Piece name is NOTHING.");
        }
        if (this.pieceCanOccupyCurrentLocation(newlySpawned)) {
            inPlay = newlySpawned;
            this.updateDropToRow();
            return true;
        } else {
            return false;
        }
    }

    // Attempts to spawn the new piece at (r, c) and update hard drop 
    // location. Returns true if the piece has room to spawn and 
    // false otherwise.
    // (Intended for debugging purposes only)
    // Requires:
    // * there is no piece in play before calling the function
    // * piece is not NOTHING
    // Effects:
    // * May modify the board by spawning a new piece
    // * Updates dropToRow if the piece is successfully spawned
    public boolean spawn(PieceName piece, int r, int c)
    throws IllegalArgumentException, IllegalStateException {
        if (inPlay != null) {
            throw new IllegalStateException(
                "Tried to spawn new piece while a piece was falling.");
        }
        Piece newlySpawned = null;
        switch (piece) {
            case I:
                newlySpawned = new IPiece(r, c);
                break;
            case J:
                newlySpawned = new JPiece(r, c);
                break;
            case L:
                newlySpawned = new LPiece(r, c);
                break;
            case O:
                newlySpawned = new OPiece(r, c);
                break;
            case S:
                newlySpawned = new SPiece(r, c);
                break;
            case T:
                newlySpawned = new TPiece(r, c);
                break;
            case Z:
                newlySpawned = new ZPiece(r, c);
                break;
            default:
                throw new IllegalArgumentException(
                    "Piece name is NOTHING.");
        }
        if (this.pieceCanOccupyCurrentLocation(newlySpawned)) {
            inPlay = newlySpawned;
            this.updateDropToRow();
            return true;
        } else {
            return false;
        }
    }

    // Attempts to rotate the piece that's currently in play and 
    // update hard drop location. Returns true if the piece 
    // has room to rotate and false otherwise. 
    // Follows Super Rotation System (SRS) rules.
    // Requires: 
    // * the piece in play is not null.
    // Effects: 
    // * May modify the board.
    // * Updates dropToRow if the piece is successfully rotated
    public boolean rotate(RotationDirection direction) 
    throws NullPointerException, IllegalStateException
    {

        boolean validRotation = false;

        if (inPlay == null) {
            throw new NullPointerException(
                "Tried to rotate a nonexistent falling piece.");
        }

        ArrayList<LocationPosn> occupiedBeforeKick;
        ArrayList<OffsetPosn> tryOffsets;
        if (direction == RotationDirection.CLOCKWISE) {
            occupiedBeforeKick = 
                inPlay.squaresOccupiedIfRotatedClockwise();
            tryOffsets = SuperRotationSystem.kicksToAttempt(
                inPlay.name(), 
                inPlay.orientationNow(), 
                inPlay.orientationIfRotatedClockwise()
            );
        } else {
            occupiedBeforeKick = 
                inPlay.squaresOccupiedIfRotatedCounterClockwise();
            tryOffsets = SuperRotationSystem.kicksToAttempt(
                inPlay.name(), 
                inPlay.orientationNow(), 
                inPlay.orientationIfRotatedCounterClockwise()
            );
        }

        OffsetPosn useOffset = null;
        ListIterator<OffsetPosn> iterTryOffset = tryOffsets.listIterator();

        while (iterTryOffset.hasNext()) {

            OffsetPosn offset = iterTryOffset.next();
            ArrayList<LocationPosn> occupiedAfterKick = 
                this.offsetList(occupiedBeforeKick, offset);

            if (occupiedAfterKick.size() != 4) {
                throw new IllegalStateException(
                    ("Piece occupies xxx squares after rotating and " + 
                    "wall-kicking, expected 4.")
                    .replaceFirst(
                        "xxx", 
                        Integer.toString(occupiedAfterKick.size())));
            }

            if (this.posnsInRange(occupiedAfterKick) && 
                this.squaresAreFree(occupiedAfterKick)) 
            {
                validRotation = true;
                useOffset = offset;
                break;
            }

        }

        if (validRotation) {
            if (direction == RotationDirection.CLOCKWISE) {
                inPlay.performRotationClockwise();
            } else {
                inPlay.performRotationCounterClockwise();
            }
            LocationPosn locationAfterKick = 
                inPlay.getAbsolutePosition().add(useOffset);
            inPlay.setAbsolutePosition(locationAfterKick);
            this.updateDropToRow();
            return true;

        } else {
            return false;
        }

    }


    // =================================
    // Private helper methods
    // =================================

    // Returns true if the posn is a legal coordinate on the board 
    // and false otherwise.
    private boolean isInRange(LocationPosn p) {
        return (0 <= p.row && p.row < 40 && 0 <= p.col && p.col < 10);
    }

    // Updates the hard drop location of the piece in play.
    // Requires:
    // * There is a piece in play
    // Effects: 
    // * Modifies the board
    // * Updates dropToRow
    private void updateDropToRow() throws NullPointerException {
        if (inPlay == null) {
            throw new NullPointerException("Piece in play is null.");
        }
        OffsetPosn oneRowDown = new OffsetPosn(1, 0);
        ArrayList<LocationPosn> occupiedBeforeMove = inPlay.squaresOccupiedNow();

        int updatedDropToRow = inPlay.getAbsolutePosition().row;
        ArrayList<LocationPosn> occupiedOneRowDown = 
            this.offsetList(occupiedBeforeMove, oneRowDown);

        while (this.posnsInRange(occupiedOneRowDown)
                && 
               this.squaresAreFree(occupiedOneRowDown))
        {
            updatedDropToRow += 1;
            occupiedOneRowDown = 
                this.offsetList(occupiedOneRowDown, oneRowDown);
        }
        this.dropToRow = updatedDropToRow;
    }

    // Attempts to move the piece by diffr rows down and diffc cols right.
    // Returns true if the piece was able to move and false otherwise.
    // Requires: 
    // * there is a piece in play
    // Effects:
    // * May modify the board by moving the piece in play
    // * Updates dropToRow if the piece was successfully moved
    private boolean moveDirection(int diffr, int diffc) 
    throws IllegalStateException {
        if (inPlay == null) {
            throw new IllegalStateException(
                "Tried to move a nonexistent piece.");
        }
        OffsetPosn offset = new OffsetPosn(diffr, diffc);
        ArrayList<LocationPosn> occupiedAfterMove = 
            this.offsetList(inPlay.squaresOccupiedNow(), offset);
        
        if (this.posnsInRange(occupiedAfterMove) && 
            this.squaresAreFree(occupiedAfterMove))
        {
            LocationPosn locationAfterMove = 
                inPlay.getAbsolutePosition().add(offset);
            inPlay.setAbsolutePosition(locationAfterMove);
            this.updateDropToRow();
            return true;

        } else {
            return false;
        }
    }

    // Moves the current piece to the bottom of the board
    // Requires:
    // * dropToRow is up to date
    // Effects:
    // * Modifies the board.
    private void movePieceToBottom() {
        LocationPosn locationOfPiece = inPlay.getAbsolutePosition();
        LocationPosn bottom = new LocationPosn(
            this.dropToRow, locationOfPiece.col);
        inPlay.setAbsolutePosition(bottom);
    }

    // Locks the current piece and sets inPlay to null. 
    // Does not clear lines.
    // Requires: 
    // * the piece is in range of the board (that is, it is not occupying 
    ///  squares off of the board)
    // * the piece is at the bottom of the board
    // * all squares occupied are free
    // Effects:
    // * Modifies the board.
    // * inPlay becomes null, and dropToRow becomes invalid until 
    //   a new piece is spawned
    private void lockPiece() {
        ArrayList<LocationPosn> occupied = inPlay.squaresOccupiedNow();
        ListIterator<LocationPosn> iterOccupied = occupied.listIterator();
        while (iterOccupied.hasNext()) {
            LocationPosn square = iterOccupied.next();
            theBoard[square.row][square.col].occupiedBy = inPlay.name();
        }
        inPlay = null;
    }

    // Clears the rows that are currently "full", ie. can be cleared.
    // Returns the number of lines cleared.
    // Intended for use immediately after this.lockPiece().
    // Effects:
    // * May modify the board by clearing lines.
    private int clearLines() {
        int cleared = 0;
        for (int r = 0; r < 40; r++) {
            boolean rowIsFull = true;
            for (int c = 0; c < 10; c++) {
                if (theBoard[r][c].occupiedBy == PieceName.NOTHING) {
                    rowIsFull = false;
                    break;
                }
            }
            if (rowIsFull) {
                cleared += 1;
                copyRowsAboveOneRowDown(r);
            }
        }
        return cleared;
    }

    // Copies the rows above copyDownTo one row down.
    // Intended for use in this.clearLines() only.
    // Requires:
    // * 16 <= copyDownTo < 40
    // Effects:
    // * Modifies the board
    private void copyRowsAboveOneRowDown(int copyDownTo) 
    throws IllegalArgumentException {
        if (!(16 <= copyDownTo && copyDownTo < 40)) {
            throw new IllegalArgumentException(
                "Expected copyDownTo to be between 16 and 39 " +
                "inclusive, got xxx."
                .replaceFirst("xxx", Integer.toString(copyDownTo)));
        }
        for (int r = copyDownTo ; r > 0; r--) {
            for (int c = 0; c < 10; c++) {
                theBoard[r][c].occupiedBy = theBoard[r - 1][c].occupiedBy;
            }
        }
    }

    // Returns true if the piece does not overlap with any part of 
    // the current board and false otherwise.
    // (Probably useful only for spawning and nothing else)
    private boolean pieceCanOccupyCurrentLocation(Piece piece) {
        return this.squaresAreFree(piece.squaresOccupiedNow());
    }

    // Returns true if all coordinates in posns are legal coordinates 
    // on the board, and false otherwise.
    private boolean posnsInRange(ArrayList<LocationPosn> posns) {
        ListIterator<LocationPosn> it = posns.listIterator();
        while (it.hasNext()) {
            LocationPosn posn = it.next();
            if (!(isInRange(posn))) {
                return false;
            }
        }
        return true;
    }

    // Returns true if the squares given in squares are unoccupied 
    // on the board, and false otherwise.
    // Requires: 
    // * each posn in the list is in range (0 <= row < 40, 0 <= col < 10)
    private boolean squaresAreFree(ArrayList<LocationPosn> squares) 
    throws IndexOutOfBoundsException 
    {
        ListIterator<LocationPosn> it = squares.listIterator();
        while (it.hasNext()) {
            LocationPosn posn = it.next();

            if (!(isInRange(posn))) {
                throw new IndexOutOfBoundsException(
                    "Illegal board coordinate: (xxx, yyy)"
                    .replaceFirst("xxx", Integer.toString(posn.row))
                    .replaceFirst("yyy", Integer.toString(posn.col)));
            }

            if (theBoard[posn.row][posn.col].occupiedBy != PieceName.NOTHING) {
                return false;
            }

        }
        return true;
    }

    // Returns a list obtained from original by applying an offset 
    // of offsetBy to each entry.
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