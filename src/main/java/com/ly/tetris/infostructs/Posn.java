package com.ly.tetris.infostructs;

// 2-dimensional position.
public class Posn {
    public int row;
    public int col;

    public Posn(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public Posn(Posn other) {
        this.row = other.row;
        this.col = other.col;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Posn)) {
            return false;
        }
        Posn other = (Posn)o;
        return ( (this.row == other.row) && (this.col == other.col) );
    }
}