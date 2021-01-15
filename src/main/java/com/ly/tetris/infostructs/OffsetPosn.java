package com.ly.tetris.infostructs;


/** 
OffsetPosn implements Posn for a 2-dimensional position used to 
describe an offset to a LocationPosn.
*/

public class OffsetPosn extends Posn {

    /**
     * Constructor.
     * @param r row of posn
     * @param c column of posn
     */
    public OffsetPosn(int r, int c) {
        super(r, c);
    }

    /**
     * Copy constructor.
     * @param other OffsetPosn to copy
     */
    public OffsetPosn(OffsetPosn other) {
        super(other.row, other.col);
    }

    /**
     * 
     * @param other a non-null OffsetPosn
     * @return a new OffsetPosn where the row is the sum of this.row and 
     other.row, and the col is the sum of this.col and other.col
     */
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