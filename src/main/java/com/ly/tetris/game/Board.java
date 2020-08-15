package com.ly.tetris.game;

import com.ly.tetris.game.pieces.IPiece;
import com.ly.tetris.game.pieces.JPiece;
import com.ly.tetris.game.pieces.LPiece;
import com.ly.tetris.game.pieces.OPiece;
import com.ly.tetris.game.pieces.Piece;
import com.ly.tetris.game.pieces.SPiece;
import com.ly.tetris.game.pieces.TPiece;
import com.ly.tetris.game.pieces.ZPiece;
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
    // Returns true if piece has room to spawn and false otherwise
    public boolean spawn(PieceName piece) {
        return true;
    }

    // Attempts to rotate the piece that's currently in play and 
    // update hard drop location. Returns true if the piece 
    // has room to rotate and false otherwise. Follows SRS rules.
    public boolean rotate() {
        return true;
    }


    // Moves the current piece to the bottom of the board
    private void movePieceToBottom() {
    }

    // Locks the current piece and removes it from play. 
    // Requires: the piece is at the bottom of the board
    private void lockPiece() {
    }
}