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
    private Square[] theBoard = new Square[10][22];
    private Piece inPlay;

    // (x,y) is the position of the piece on the board, where (0,0) is 
    // the upper left corner. The first two lines are off the board, 
    // and are where pieces first spawn
    private int x;
    private int y;

    // (hardDropX, hardDropY) is where the piece would be 
    // on the board if dropped. (0,0) is the upper left corner.
    // The first two lines are off the board, and are where 
    // pieces first spawn
    private int hardDropX;
    private int hardDropY;

    public Board() {
        this.inPlay = null;
    }

    // Drops the current piece and removes it from play.
    public void drop() {
    } 

    // Attempts to spawn the new piece and update hard drop location.
    // Returns true if piece has room to spawn and false otherwise
    public boolean spawn(PieceName piece) {
        
    }

    // Attempts to rotate the piece that's currently in play and 
    // update hard drop location. Returns true if the piece 
    // has room to rotate and false otherwise. Follows SRS rules.
    public boolean rotate() {
    }
}