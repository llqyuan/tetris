package com.ly.tetris.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import com.ly.tetris.infostructs.PieceName;

public class NextPiecesQueue {
    private ArrayList<PieceName> next;

    // =========================================================
    // Public interface
    // =========================================================

    // Constructor. Generates a starting queue for a new game 
    // (contains one instance of each of the 7 piece types, 
    // in a random order)
    public NextPiecesQueue() {
        this.next = new ArrayList<PieceName>();
        ArrayList<PieceName> temp = new ArrayList<PieceName>();
        temp.add(PieceName.I);
        temp.add(PieceName.J);
        temp.add(PieceName.L);
        temp.add(PieceName.O);
        temp.add(PieceName.S);
        temp.add(PieceName.T);
        temp.add(PieceName.Z);
        this.addShuffledQueueToNext(temp);
    }

    // Returns the next piece in the queue and removes it from the 
    // queue.
    // Effects:
    // * Removes the first piece from next.
    // * If there are too few pieces in next, adds 7 more to 
    //   the end: one of each of the 7 piece types, in a 
    //   random order.
    public PieceName produceAndRemoveNextPieceInQueue() {
        PieceName firstPieceInQueue = next.remove(0);
        if (next.size() < 5) {
            ArrayList<PieceName> temp = new ArrayList<PieceName>();
            temp.add(PieceName.I);
            temp.add(PieceName.J);
            temp.add(PieceName.L);
            temp.add(PieceName.O);
            temp.add(PieceName.S);
            temp.add(PieceName.T);
            temp.add(PieceName.Z);
            this.addShuffledQueueToNext(temp);
        }
        return firstPieceInQueue;
    }

    // Returns the next five pieces in the queue.
    public ArrayList<PieceName> peekNextFivePieces() 
    throws IllegalStateException {
        ArrayList<PieceName> nextFive = new ArrayList<PieceName>();
        int count = 0;
        ListIterator<PieceName> iter = next.listIterator();
        while (iter.hasNext() && count < 5) {
            nextFive.add(iter.next());
            count += 1;
        }
        if (count < 5) {
            throw new IllegalStateException(
                "There are fewer than 5 pieces in the next-pieces queue.");
        }
        return nextFive;
    }

    // ===========================================================
    // Private helper methods
    // ===========================================================

    // Shuffles toAdd, then adds each piece in toAdd to next.
    // Effects:
    // * Adds the pieces from toAdd to the end of next, in a random order
    private void addShuffledQueueToNext(ArrayList<PieceName> toAdd) {
        Collections.shuffle(toAdd);
        ListIterator<PieceName> iter = toAdd.listIterator();
        while (iter.hasNext()) {
            this.next.add(iter.next());
        }
    }
}