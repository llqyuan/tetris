package com.ly.tetris.infostructs;

// Position used to describe an offset to a LocationPosn.
public class OffsetPosn extends Posn {
    // Constructor
    public OffsetPosn(int r, int c) {
        super(r, c);
    }

    // Copy constructor
    public OffsetPosn(OffsetPosn other) {
        super(other.row, other.col);
    }

    // Returns a new OffsetPosn where the row is the sum of this.row and 
    // other.row, and the col is the sum of this.col and other.col.
    // Requires:
    // * other is not null
    public OffsetPosn add(OffsetPosn other) {
        return new OffsetPosn(this.row + other.row, this.col + other.col);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OffsetPosn)) {
            return false;
        }
        OffsetPosn other = (OffsetPosn)o;
        return ( (this.row == other.row) && (this.col == other.col) );
    }
}