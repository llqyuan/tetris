package com.ly.tetris.infostructs;

// 2-dimensional position.
public abstract class Posn {
    public int row;
    public int col;

    // Constructor
    public Posn(int r, int c) {
        this.row = r;
        this.col = c;
    }
}