package com.ly.tetris.game;

import com.ly.tetris.game.pieces.IPiece;
import com.ly.tetris.game.pieces.JPiece;
import com.ly.tetris.game.pieces.LPiece;
import com.ly.tetris.game.pieces.OPiece;
import com.ly.tetris.game.pieces.Piece;
import com.ly.tetris.game.pieces.SPiece;
import com.ly.tetris.game.pieces.TPiece;
import com.ly.tetris.game.pieces.ZPiece;
import com.ly.tetris.game.SuperRotationSystem;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.RotationDirection;

public class Board {
    // The board. Rows 20 through 39 are the visible playing field.
    // Rows 0 through 19 are hidden, above the visible playing field.
    // The upper left corner is (0,0).
    private Square[][] theBoard = new Square[40][10];

    // The piece currently falling.
    private Piece inPlay;

    // (drowToRow, x) is where the piece currently in play would be 
    // on the board if dropped.
    private int dropToRow;

    public Board() {
        this.inPlay = null;
    }

    // Drops the current piece and removes it from play.
    public void drop() {
        movePieceToBottom();
        lockPiece();
    } 

    // Attempts to spawn the new piece and update hard drop location.
    // Returns true if piece has room to spawn and false otherwise.
    // Requires: piece is not NOTHING
    public boolean spawn(PieceName piece) throws IllegalArgumentException {
        if (piece == PieceName.NOTHING) {
            throw new IllegalArgumentException("Piece name is NOTHING.");
        }
        return true;
    }

    // Attempts to rotate the piece that's currently in play and 
    // update hard drop location. Returns true if the piece 
    // has room to rotate and false otherwise. 
    // Follows the Super Rotation System (SRS) rules.
    // Requires: the piece in play is not null.
    public boolean rotate(RotationDirection direction) {
        // Get the squares that the piece would occupy if it were rotated.
        // Try applying SRS offsets.
        // If a rotation works (doesn't overlap anything): 
        // * rotate the stored piece
        // * apply the wall kick
        // * update the hard drop location
        // and return true.
        // If none of the offsets work, modify nothing and return false.

        return true;
    }


    // =================================
    // Private helper methods
    // =================================

    // Moves the current piece to the bottom of the board
    private void movePieceToBottom() {
    }

    // Locks the current piece and sets inPlay to null. 
    // Requires: the piece is at the bottom of the board
    private void lockPiece() {
    }

    // Returns true if the piece does not overlap with any part of 
    // the current board and false otherwise.
    private boolean pieceCanOccupyCurrentLocation(Piece piece) {
        return true;
    }
}