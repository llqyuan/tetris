package com.ly.tetris.game;

import java.util.ArrayList;
import com.ly.tetris.infostructs.OffsetPosn;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.PieceOrientation;

/** 

SuperRotationSystem determines the list of wall kicks to use 
for an attempted rotation, following the Super Rotation System.

Interpret language from Tetris wiki:

Rotation state:
 - "spawn state": UPRIGHT
 - "right": RIGHT
 - "state resulting from two successive rotations": UPSIDEDOWN
 - "left": LEFT

Wall kick direction:
 - (x, y) in coordinates is equivalent to (-y, x) for a OffsetPosn.

*/

public class SuperRotationSystem {

    /**
     * 
     * @param piece piece type for which the wall kicks will be attempted. 
     * Must not be NOTHING.
     * @param start starting orientation
     * @param end ending orientation
     * @return the wall kicks to be attempted, in order
     * @throws IllegalArgumentException
     */
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