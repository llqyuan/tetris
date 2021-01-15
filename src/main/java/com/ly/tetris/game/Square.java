package com.ly.tetris.game;

import com.ly.tetris.infostructs.PieceName;


/**
Square stores the information of a single square on the board.
*/

public class Square {
    /**
    // Contains the name of the piece that's occupying this square.
    */
    public PieceName occupiedBy;

    /** Row of the square on the board. 0 is up.*/
    public final int row;

    /** Column of the square on the board. 0 is left.*/
    public final int col;

    /**
     * Constructor. Initializes with no piece.
     * @param r row of square on the board
     * @param c column of square on the board
     */
    public Square(int r, int c) {
        occupiedBy = PieceName.NOTHING;
        row = r;
        col = c;
    }

    /**
     * Constructor.
     * @param p piece that the square will belong to
     * @param r row of square on the board
     * @param c column of square on the board
     */
    public Square(PieceName p, int r, int c) {
        occupiedBy = p;
        row = r;
        col = c;
    }

    /**
     * Copy constructor.
     * @param other Square to copy
     */
    public Square(Square other) {
        this.occupiedBy = other.occupiedBy;
        this.row = other.row;
        this.col = other.col;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Square)) {
            return false;
        }
        Square other = (Square)o;
        return (this.occupiedBy == other.occupiedBy);
    }
}