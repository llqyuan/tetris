package com.ly.tetris.game;

import com.ly.tetris.infostructs.PieceName;

public class Square {
    // Contains the name of the piece that's occupying this square.
    public PieceName occupiedBy;

    public Square() {
        occupiedBy = PieceName.NOTHING;
    }

    public Square(PieceName p) {
        occupiedBy = p;
    }

    public Square(Square other) {
        this.occupiedBy = other.occupiedBy;
    }

    // Returns true if this square is occupied and false otherwise.
    public boolean isOccupied() {
        return (occupiedBy != PieceName.NOTHING);
    }
}