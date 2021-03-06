package com.ly.tetris.infostructs;


/**
Posn defines the implementation for a 2-dimensional position.
*/

public abstract class Posn {
    public int row;
    public int col;

    /**
     * Constructor
     * @param r row of posn
     * @param c column of posn
     */
    public Posn(int r, int c) {
        this.row = r;
        this.col = c;
    }
}