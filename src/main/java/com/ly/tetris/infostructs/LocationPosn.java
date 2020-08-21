package com.ly.tetris.infostructs;

// Position used to describe the location of a piece.
public class LocationPosn extends Posn {
    // Constructor
    public LocationPosn(int r, int c) {
        super(r, c);
    }

    // Copy constructor
    public LocationPosn(LocationPosn other) {
        super(other.row, other.col);
    }

    // Returns a new LocationPosn where the row is the sum of this.row and 
    // other.row, and the col is the sum of this.col and other.col.
    // Requires:
    // * other is not null
    public LocationPosn add(OffsetPosn other) {
        return new LocationPosn(
            this.row + other.row, 
            this.col + other.col
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocationPosn)) {
            return false;
        }
        LocationPosn other = (LocationPosn)o;
        return ( (this.row == other.row) && (this.col == other.col) );
    }
}