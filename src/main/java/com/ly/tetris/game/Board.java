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
import com.ly.tetris.game.SuperRotationSystem;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.Posn;
import com.ly.tetris.infostructs.RotationDirection;

/*
Todo:

Need to figure out where to clear lines

lockPiece, movePieceToBottom
spawn
rotate (testing)
squares occupied by piece in play, hard drop ghost (testing)
*/

public class Board {
    // The board. Rows 20 through 39 are the visible playing field.
    // Rows 0 through 19 are hidden, above the visible playing field.
    // The upper left corner is (0,0).
    private Square[][] theBoard = new Square[40][10];

    // The piece currently falling. Should be null before spawning 
    // the first piece, or after locking a piece but before spawning a
    // new one.
    private Piece inPlay;

    // (drowToRow, [current column]) is where the piece currently 
    // in play would be on the board if dropped.
    // Should be correct at the beginning and end of every 
    // public function if a piece is in play.
    private int dropToRow;

    public Board() {
        this.inPlay = null;
    }

    // Returns the name of the piece currently in play.
    public PieceName pieceInPlay() {
        return inPlay.name();
    }

    // Returns the (row, col) coordinates of the squares currently 
    // occupied by the piece in play.
    public ArrayList<LocationPosn> squaresOccupiedByPieceInPlay() {
        return inPlay.squaresOccupiedNow();
    }
    
    // Returns the (row, col) coordinates of the squares that 
    // the piece in play would occupy if it were hard-dropped now.
    public ArrayList<LocationPosn> squaresOccupiedByHardDropGhost() {
        LocationPosn posnOfPieceInPlay = inPlay.getAbsolutePosition();
        OffsetPosn offset = 
            new OffsetPosn(this.dropToRow - posnOfPieceInPlay.row, 0);
        ArrayList<LocationPosn> squaresOccupiedByGhost = 
            this.offsetList(inPlay.squaresOccupiedNow(), offset);
        return squaresOccupiedByGhost;
    }

    // Drops the current piece and removes it from play.
    // Effects: 
    // * Modifies the board.
    public void drop() {
        if (inPlay != null) {
            movePieceToBottom();
            lockPiece();
        }
    } 

    // Attempts to spawn the new piece and update hard drop location.
    // Returns true if piece has room to spawn and false otherwise.
    // Requires: 
    // * piece is not NOTHING
    // Effects:
    // * May modify the board
    public boolean spawn(PieceName piece) throws IllegalArgumentException {
        if (piece == PieceName.NOTHING) {
            throw new IllegalArgumentException("Piece name is NOTHING.");
        }
        // todo
        return true;
    }

    // Attempts to rotate the piece that's currently in play and 
    // update hard drop location. Returns true if the piece 
    // has room to rotate and false otherwise. 
    // Follows the Super Rotation System (SRS) rules.
    // Requires: 
    // * the piece in play is not null.
    // Effects: 
    // * May modify the board.
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
                    "Wall-kicked piece occupies xxx squares, expected 4."
                    .replaceFirst(
                        "xxx", 
                        Integer.toString(occupiedAfterKick.size())));
            }

            if (this.squaresAreFree(occupiedAfterKick)) {
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

    // Updates the hard drop location of the piece in play.
    // Effects: 
    // * Modifies the board.
    private void updateDropToRow() {
        OffsetPosn oneRowDown = new OffsetPosn(1, 0);
        ArrayList<LocationPosn> occupiedBeforeMove = inPlay.squaresOccupiedNow();

        int updatedDropToRow = inPlay.getAbsolutePosition().row;
        ArrayList<LocationPosn> occupiedOneRowDown = 
            this.offsetList(occupiedBeforeMove, oneRowDown);

        while (updatedDropToRow + 1 < 40 
                && 
               this.squaresAreFree(occupiedOneRowDown))
        {
            updatedDropToRow += 1;
            occupiedOneRowDown = 
                this.offsetList(occupiedOneRowDown, oneRowDown);
        }
        this.dropToRow = updatedDropToRow;
    }

    // Moves the current piece to the bottom of the board
    // Effects:
    // * Modifies the board.
    private void movePieceToBottom() {
    }

    // Locks the current piece and sets inPlay to null. 
    // Requires: 
    // * the piece is at the bottom of the board
    // * all squares occupied are free
    // Effects:
    // * Modifies the board.
    private void lockPiece() {
    }

    // Returns true if the piece does not overlap with any part of 
    // the current board and false otherwise.
    // (Probably useful only for spawning and nothing else)
    private boolean pieceCanOccupyCurrentLocation(Piece piece) {
        return this.squaresAreFree(piece.squaresOccupiedNow());
    }

    // Returns true if the squares given in squares are unoccupied 
    // and false otherwise.
    // Requires: 
    // * each posn in the list is in range (0 <= row < 40, 0 <= col < 10)
    private boolean squaresAreFree(ArrayList<LocationPosn> squares) 
    throws IndexOutOfBoundsException 
    {
        ListIterator<LocationPosn> it = squares.listIterator();
        while (it.hasNext()) {
            LocationPosn square = it.next();

            if (!(0 <= square.row && square.row < 40)) {
                throw new IndexOutOfBoundsException(
                    "Illegal row: xxx"
                    .replaceFirst("xxx", Integer.toString(square.row)));
            } if (!(0 <= square.col && square.col < 10)) {
                throw new IndexOutOfBoundsException(
                    "Illegal col: xxx"
                    .replaceFirst("xxx", Integer.toString(square.col)));
            }

            if (theBoard[square.row][square.col].isOccupied()) {
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
        ListIterator<LocationPosn> iter = original.listIterator();
        while (iter.hasNext()) {
            offset.add(iter.next().add(offsetBy));
        }
        return offset;
    }
}