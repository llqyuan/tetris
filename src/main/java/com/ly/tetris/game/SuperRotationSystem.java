package com.ly.tetris.game;

import java.util.ArrayList;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;

/*
    
Interpret language from Tetris wiki:

Rotation state:
* "spawn state": upright
* "right": right
* "state resulting from two successive rotations": upside down
* "left": left

Wall kick direction:
* (x, y) in coordinates is equivalent to (-y, x) for a OffsetPosn.

*/

public class SuperRotationSystem {

    // Returns the wall kicks to be attempted (in order) for the
    // piece given by piece, that starts in the orientation start, 
    // and rotates to the orientation end.
    // Requires: piece is not NOTHING
    public static ArrayList<OffsetPosn> 
    kicksToAttempt(PieceName piece, 
                   PieceOrientation start, 
                   PieceOrientation end)
    throws IllegalArgumentException
    {
        if (piece == PieceName.NOTHING) {
            throw new IllegalArgumentException("Piece name is NOTHING.");
        }

        ArrayList<OffsetPosn> kicks = new ArrayList<OffsetPosn>();

        if (start == PieceOrientation.UPRIGHT &&
            end == PieceOrientation.RIGHT) 
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, -2));
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(1, -2));
                kicks.add(new OffsetPosn(-2, 1));
            } else if (!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(-1, -1));
                kicks.add(new OffsetPosn(2, 0));
                kicks.add(new OffsetPosn(2, -1));
            }

        } 
        else if (start == PieceOrientation.RIGHT && 
            end == PieceOrientation.UPRIGHT)
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, 2));
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(-1, 2));
                kicks.add(new OffsetPosn(2, -1));
            } else if (!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(1, 1));
                kicks.add(new OffsetPosn(-2, 0));
                kicks.add(new OffsetPosn(-2, 1));
            }

        } 
        else if (start == PieceOrientation.RIGHT && 
            end == PieceOrientation.UPSIDEDOWN)
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(0, 2));
                kicks.add(new OffsetPosn(-2, -1));
                kicks.add(new OffsetPosn(1, 2));
            } else if (!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(1, 1));
                kicks.add(new OffsetPosn(-2, 0));
                kicks.add(new OffsetPosn(-2, 1));
            }

        } 
        else if (start == PieceOrientation.UPSIDEDOWN && 
            end == PieceOrientation.RIGHT)
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(0, -2));
                kicks.add(new OffsetPosn(2, 1));
                kicks.add(new OffsetPosn(-1, -2));
            } else if (!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(-1, -1));
                kicks.add(new OffsetPosn(2, 0));
                kicks.add(new OffsetPosn(2, -1));
            }

        } 
        else if (start == PieceOrientation.UPSIDEDOWN &&
            end == PieceOrientation.LEFT)
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, 2));
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(-1, 2));
                kicks.add(new OffsetPosn(2, -1));
            } else if (!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(-1, 1));
                kicks.add(new OffsetPosn(2, 0));
                kicks.add(new OffsetPosn(2, 1));
            }

        } 
        else if (start == PieceOrientation.LEFT &&
            end == PieceOrientation.UPSIDEDOWN)
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, -2));
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(1, -2));
                kicks.add(new OffsetPosn(-2, 1));
            } else if (!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(1, -1));
                kicks.add(new OffsetPosn(-2, 0));
                kicks.add(new OffsetPosn(-2, -1));
            }

        } 
        else if (start == PieceOrientation.LEFT && 
            end == PieceOrientation.UPRIGHT)
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(0, -2));
                kicks.add(new OffsetPosn(2, 1));
                kicks.add(new OffsetPosn(-1, -2));
            } else if (!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(1, -1));
                kicks.add(new OffsetPosn(-2, 0));
                kicks.add(new OffsetPosn(-2, -1));
            }

        } 
        else if (start == PieceOrientation.UPRIGHT && 
            end == PieceOrientation.LEFT)
        {
            kicks.add(new OffsetPosn(0, 0));

            if (piece == PieceName.I) {
                kicks.add(new OffsetPosn(0, -1));
                kicks.add(new OffsetPosn(0, 2));
                kicks.add(new OffsetPosn(-2, -1));
                kicks.add(new OffsetPosn(1, 2));
            } else if(!(piece == PieceName.O)) {
                kicks.add(new OffsetPosn(0, 1));
                kicks.add(new OffsetPosn(-1, 1));
                kicks.add(new OffsetPosn(2, 0));
                kicks.add(new OffsetPosn(2, 1));
            }

        }

        return kicks;
    }
}