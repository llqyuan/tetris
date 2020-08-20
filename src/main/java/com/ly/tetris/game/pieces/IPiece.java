package com.ly.tetris.game.pieces;

import java.util.ArrayList;

import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;
import com.ly.tetris.infostructs.Posn;

public class IPiece extends Piece {
    // A 4x4 grid, representing the field around the piece. 
    // The upper left corner is (0,0). 
    // An entry is true when the piece occupies that square, and 
    // false otherwise.
    private boolean[][] localFieldOccupied = new boolean[4][4];

    // Constructor. Spawns the piece in the upright position above 
    // the visible field
    public IPiece() {
        super(18, 3, PieceOrientation.UPRIGHT);
        for (int i = 0; i < 4; i++) {
            localFieldOccupied[0][i] = false;
            localFieldOccupied[1][i] = true;
            localFieldOccupied[2][i] = false;
            localFieldOccupied[3][i] = false;
        }
    }

    public IPiece(int r, int c) {
        super(r, c, PieceOrientation.UPRIGHT);
        for (int i = 0; i < 4; i++) {
            localFieldOccupied[0][i] = false;
            localFieldOccupied[1][i] = true;
            localFieldOccupied[2][i] = false;
            localFieldOccupied[3][i] = false;
        }
    }

    @Override
    protected PieceName nameOfPiece() {
        return PieceName.I;
    }

    @Override
    protected void rotateClockwise(){
        boolean[][] rotated = this.rotatedClockwise();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                localFieldOccupied[r][c] = rotated[r][c];
            }
        }
    }

    @Override
    protected void rotateCounterClockwise() {
        boolean[][] rotated = this.rotatedCounterClockwise();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                localFieldOccupied[r][c] = rotated[r][c];
            }
        }
    }

    @Override
    protected ArrayList<OffsetPosn> occupiedNow() {
        ArrayList<OffsetPosn> occupied = new ArrayList<OffsetPosn>();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (localFieldOccupied[r][c]) {
                    occupied.add(new OffsetPosn(r, c));
                }
            }
        }
        return occupied;
    }

    @Override
    protected ArrayList<OffsetPosn> occupiedIfRotatedClockwise() {
        ArrayList<OffsetPosn> occupied = new ArrayList<OffsetPosn>();
        boolean[][] rotatedField = this.rotatedClockwise();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {

                if (rotatedField[r][c]) {
                    occupied.add(new OffsetPosn(r, c));
                }

            }
        }
        return occupied;
    }

    @Override
    protected ArrayList<OffsetPosn> occupiedIfRotatedCounterClockwise() {
        ArrayList<OffsetPosn> occupied = new ArrayList<OffsetPosn>();
        boolean[][] rotatedField = this.rotatedCounterClockwise();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {

                if (rotatedField[r][c]) {
                    occupied.add(new OffsetPosn(r, c));
                }

            }
        }
        return occupied;
    }

    // ======================
    // Private helpers
    // ======================

    // Returns the 4x4 array that localFieldOccupied would have 
    // been set to if the piece were rotated clockwise. 
    private boolean[][] rotatedClockwise() {
        boolean[][] occupiedWhenRotated = new boolean[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                occupiedWhenRotated[c][3 - r] = localFieldOccupied[r][c];
            }
        }
        return occupiedWhenRotated;
    }

    // Returns the 4x4 array that localFieldOccupied would have 
    // been set to if the piece were rotated counterclockwise.
    private boolean[][] rotatedCounterClockwise() {
        boolean[][] occupiedWhenRotated = new boolean[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                occupiedWhenRotated[3 - c][r] = localFieldOccupied[r][c];
            }
        }
        return occupiedWhenRotated;
    }
}