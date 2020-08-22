package com.ly.tetris.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import com.ly.tetris.infostructs.PieceName;

public class NextPiecesQueue {
    private ArrayDeque<PieceName> next;

    // Constructor. Generates a starting queue for a new game 
    // (contains one instance of each of the 7 piece types, 
    // in a random order)
    public NextPiecesQueue() {
        this.next = new ArrayDeque<PieceName>();
        ArrayList<PieceName> temp = new ArrayList<PieceName>();
        temp.add(PieceName.I);
        temp.add(PieceName.J);
        temp.add(PieceName.L);
        temp.add(PieceName.O);
        temp.add(PieceName.S);
        temp.add(PieceName.T);
        temp.add(PieceName.Z);
        Collections.shuffle(temp);
        ListIterator<PieceName> iter = temp.listIterator();
        while (iter.hasNext()) {
            this.next.addLast(iter.next());
        }
    }

    // Returns the next piece in the queue and removes it from the 
    // queue.
    // Effects:
    // * Removes the first piece from next.
    // * If there are too few pieces in next, adds 14 more to 
    //   the end of next.
    public PieceName firstPieceInQueue() {
        // todo
        return PieceName.NOTHING;
    }
}