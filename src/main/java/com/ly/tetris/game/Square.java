package com.ly.tetris.game;

import com.ly.tetris.infostructs.PieceName;

public class Square {
    // Contains the name of the piece that's occupying this square.
    public PieceName occupiedBy;

    // Row and column of the square on the board.
    public final int row;
    public final int col;

    public Square(int r, int c) {
        occupiedBy = PieceName.NOTHING;
        row = r;
        col = c;
    }

    public Square(PieceName p, int r, int c) {
        occupiedBy = p;
        row = r;
        col = c;
    }

    public Square(Square other) {
        this.occupiedBy = other.occupiedBy;
        this.row = other.row;
        this.col = other.col;
    }

    // Returns true if this square is occupied and false otherwise.
    public boolean isOccupied() {
        return (occupiedBy != PieceName.NOTHING);
    }
}