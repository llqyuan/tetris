package com.ly.tetris.game;


/*
ExtendedPlacementLockDown determines whether rotating or moving a piece
on the ground should reset the lock timer. The timer may only be reset 15 times
per piece. 
*/

public class ExtendedPlacementLockDown {
    // The number of times the lock timer has been set for 
    // the current piece. Starts at -1 so that when the 
    // lock timer is first set, this is increased to 0.
    private int timesExtended;

    // The maximum number of times that the lock timer can be 
    // extended for one piece.
    private final int maxAllowedExtensions;

    // Constructor. Sets the class to use Extended Placement Lock 
    // Down with 15 possible move resets.
    public ExtendedPlacementLockDown() {
        this.maxAllowedExtensions = 15;
        this.timesExtended = -1;
    }

    // Constructor. Sets the class to use Extended Placement Lock 
    // Down with maxAllowedExtensions possible move resets.
    public ExtendedPlacementLockDown(int maxAllowedExtensions) {
        this.maxAllowedExtensions = maxAllowedExtensions;
        this.timesExtended = -1;
    }

    // Returns true if the lock timer can be extended 
    // and false otherwise.
    // Effects:
    // * Increments this.timesExtended by 1
    public boolean requestLockTimerExtension() {
        if (this.timesExtended < this.maxAllowedExtensions) 
        {
            this.timesExtended += 1;
            return true;

        } else {
            return false;
        }
    }

    // Resets this.timesExtended to 0. Call this whenever a 
    // new piece is spawned.
    public void resetTimesExtended() {
        this.timesExtended = -1;
    }
}
