package com.ly.tetris.infostructs;


/**
LocationPosn implements Posn for 2-dimensional positions describing the 
position of a piece.
*/

public class LocationPosn extends Posn {

    /**
     * Constructor
     * @param r row of posn
     * @param c column of posn
     */
    public LocationPosn(int r, int c) {
        super(r, c);
    }

    /**
     * Copy constructor
     * @param other LocationPosn to copy
     */
    public LocationPosn(LocationPosn other) {
        super(other.row, other.col);
    }

    /**
     * 
     * @param other non-null LocationPosn
     * @return a new LocationPosn where the row is the sum of
     * this.row and other.row, and the col is the sum of this.col and
     * other.col
     */
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