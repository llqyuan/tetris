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

public class Board {
    // The board. Rows 20 through 39 are the visible playing field.
    // Rows 0 through 19 are above the visible playing field.
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
    public boolean rotate() {
        // Rotate the piece, then determine whether it's overlapping 
        // anything on the stack.
        // If so, try applying SRS offsets.
        // If a rotation works (doens't overlap anything), update 
        // the stored piece and return true.
        // If none of the offsets work, return false.

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
}