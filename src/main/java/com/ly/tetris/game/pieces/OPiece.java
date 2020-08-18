package com.ly.tetris.game.pieces;

import java.util.ArrayList;

import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.Posn;

public class OPiece extends Piece {
    // A grid 4 units wide and 3 units tall, representing the field 
    // around the piece. The upper left corner is (0,0). 
    // An entry is true when the piece occupies that square, and 
    // false otherwise.
    private boolean[][] localFieldOccupied = new boolean[3][4];

    public OPiece() {
        super(18, 3, PieceOrientation.UPRIGHT);
        for (int i = 0; i < 2; i++) {
            localFieldOccupied[i][0] = false;
            localFieldOccupied[i][1] = true;
            localFieldOccupied[i][2] = true;
            localFieldOccupied[i][3] = false;
        }
        for (int i = 0; i < 4; i++) {
            localFieldOccupied[2][i] = false;
        }
    }
    
    @Override
    protected void rotateClockwise() {
    }

    @Override
    protected void rotateCounterClockwise() {
    }

    @Override
    protected ArrayList<Posn> occupiedNow() {
        ArrayList<Posn> occupied = new ArrayList<Posn>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (localFieldOccupied[r][c]) {
                    occupied.add(new Posn(r, c));
                }
             }
        }
        return occupied;
    }

    @Override
    protected ArrayList<Posn> occupiedIfRotatedClockwise() {
        ArrayList<Posn> occupied = new ArrayList<Posn>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (localFieldOccupied[r][c]) {
                    occupied.add(new Posn(r, c));
                }
             }
        }
        return occupied;
    }

    @Override
    protected ArrayList<Posn> occupiedIfRotatedCounterClockwise() {
        ArrayList<Posn> occupied = new ArrayList<Posn>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (localFieldOccupied[r][c]) {
                    occupied.add(new Posn(r, c));
                }
             }
        }
        return occupied;
    }
}