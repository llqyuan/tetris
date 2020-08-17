package com.ly.tetris.game.pieces;

import java.util.ArrayList;
import com.ly.tetris.infostructs.Posn;

public class TPiece extends Piece {
    // A 3x3 grid, representing the field around the piece. 
    // The upper left corner is (0,0). 
    // An entry is true when the piece occupies that square, and 
    // false otherwise.
    private boolean[][] localFieldOccupied = new boolean[3][3];

    // Constructor. Spawns piece in the upright position above the 
    // visible field.
    public TPiece() {
        super(18, 4);
        localFieldOccupied[0][0] = false;
        localFieldOccupied[0][1] = true;
        localFieldOccupied[0][2] = false;
        for (int i = 0; i < 3; i++) {
            localFieldOccupied[1][i] = true;
            localFieldOccupied[2][i] = false;
        }
    }
    
    @Override
    public void rotateClockwise() {
        boolean[][] rotated = this.rotatedClockwise();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                localFieldOccupied[r][c] = rotated[r][c];
            }
        }
    }

    @Override
    public void rotateCounterClockwise() {
        boolean[][] rotated = this.rotatedCounterClockwise();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                localFieldOccupied[r][c] = rotated[r][c];
            }
        }
    }

    @Override
    public ArrayList<Posn> occupiedNow() {
        ArrayList<Posn> occupied = new ArrayList<Posn>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (localFieldOccupied[r][c]) {
                    occupied.add(new Posn(r, c));
                }
             }
        }
        return occupied;
    }

    @Override
    public ArrayList<Posn> occupiedIfRotatedClockwise() {
        ArrayList<Posn> occupied = new ArrayList<Posn>();
        boolean[][] rotatedField = this.rotatedClockwise();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (rotatedField[r][c]) {
                    occupied.add(new Posn(r, c));
                }
            }
        }
        return occupied;
    }

    @Override
    public ArrayList<Posn> occupiedIfRotatedCounterClockwise() {
        ArrayList<Posn> occupied = new ArrayList<Posn>();
        boolean[][] rotatedField = this.rotatedCounterClockwise();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (rotatedField[r][c]) {
                    occupied.add(new Posn(r, c));
                }
            }
        }
        return occupied;
    }

    // ======================
    // Private helpers
    // ======================

    // Returns the 3x3 array that localFieldOccupied would have 
    // been set to if the piece were rotated clockwise. 
    private boolean[][] rotatedClockwise() {
        boolean[][] occupiedWhenRotated = new boolean[4][4];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                occupiedWhenRotated[c][2 - r] = localFieldOccupied[r][c];
            }
        }
        return occupiedWhenRotated;
    }

    // Returns the 3x3 array that localFieldOccupied would have 
    // been set to if the piece were rotated counterclockwise.
    private boolean[][] rotatedCounterClockwise() {
        boolean[][] occupiedWhenRotated = new boolean[4][4];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                occupiedWhenRotated[2 - c][r] = localFieldOccupied[r][c];
            }
        }
        return occupiedWhenRotated;
    }
}