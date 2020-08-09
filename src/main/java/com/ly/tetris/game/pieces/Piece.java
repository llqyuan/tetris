package com.ly.tetris.game.pieces;

public abstract class Piece {

    // Rotate the piece counterclockwise by 90 degrees.
    public abstract void rotateCounterClockwise();

    // Rotate the piece clockwise by 90 degrees.
    public abstract void rotateClockwise();

    // Others: return occupied squares if the piece were to be rotated 
    // clockwise, counterclockwise. This allows the board to determine 
    // whether the piece can be rotated.

}