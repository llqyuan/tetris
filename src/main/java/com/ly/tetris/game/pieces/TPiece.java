package com.ly.tetris.game.pieces;

import java.util.ArrayList;

import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;

/** 
TPiece implements Piece as appropriate for a T tetromino.
*/

public class TPiece extends Piece {
    /** 
    A 3x3 grid, representing the field around the piece. 
    The upper left corner is (0,0). 
    An entry is true when the piece occupies that square, and 
    false otherwise.
    */
    private boolean[][] localFieldOccupied = new boolean[3][3];

    /** 
    Constructor. Spawns piece in the upright position above the 
    visible field.
    */
    public TPiece() {
        super(18, 3, PieceOrientation.UPRIGHT);
        localFieldOccupied[0][0] = false;
        localFieldOccupied[0][1] = true;
        localFieldOccupied[0][2] = false;
        for (int i = 0; i < 3; i++) {
            localFieldOccupied[1][i] = true;
            localFieldOccupied[2][i] = false;
        }
    }

    /**
     * Constructor
     * @param r row to spawn at
     * @param c column to spawn at
     */
    public TPiece(int r, int c) {
        super(r, c, PieceOrientation.UPRIGHT);
        localFieldOccupied[0][0] = false;
        localFieldOccupied[0][1] = true;
        localFieldOccupied[0][2] = false;
        for (int i = 0; i < 3; i++) {
            localFieldOccupied[1][i] = true;
            localFieldOccupied[2][i] = false;
        }
    }

    @Override
    protected PieceName nameOfPiece() {
        return PieceName.T;
    }
    
    @Override
    protected void rotateClockwise() {
        boolean[][] rotated = this.rotatedClockwise();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                localFieldOccupied[r][c] = rotated[r][c];
            }
        }
    }

    @Override
    protected void rotateCounterClockwise() {
        boolean[][] rotated = this.rotatedCounterClockwise();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                localFieldOccupied[r][c] = rotated[r][c];
            }
        }
    }

    @Override
    protected ArrayList<OffsetPosn> occupiedNow() {
        ArrayList<OffsetPosn> occupied = new ArrayList<OffsetPosn>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
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
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
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
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (rotatedField[r][c]) {
                    occupied.add(new OffsetPosn(r, c));
                }
            }
        }
        return occupied;
    }

    /*
    Returns the positions of squares on the board that 
    are adjacent to the center of the piece. May include coordinates that 
    would be out of bounds; it is the responsibility of 
    Board to check that each posn is a valid position on the board.
    */
    public ArrayList<LocationPosn> adjacentToCenter() {
        ArrayList<LocationPosn> adjacent = new ArrayList<LocationPosn>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if ( (!(r == 1 && c == 1)) && (!localFieldOccupied[r][c]) ) {
                    adjacent.add(
                        this.getAbsolutePosition().add(new OffsetPosn(r, c)));
                }
            }
        }
        return adjacent;
    }

    /*
    ======================
    Private helpers
    ======================
    */

    /**
     * 
     * @return the 3x3 array that localFieldOccupied would have 
     * been set to if the piece were rotated clockwise. 
     */
    private boolean[][] rotatedClockwise() {
        boolean[][] occupiedWhenRotated = new boolean[4][4];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                occupiedWhenRotated[c][2 - r] = localFieldOccupied[r][c];
            }
        }
        return occupiedWhenRotated;
    }

    /**
     * 
     * @return the 3x3 array that localFieldOccupied would have 
     * been set to if the piece were rotated counterclockwise.
     */
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