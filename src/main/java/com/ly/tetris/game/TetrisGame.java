package com.ly.tetris.game;

import java.util.ArrayList;
import com.ly.tetris.infostructs.PieceName;

public class TetrisGame {
    // Stores the board.
    private Board board = null;

    // Stores the score.
    private int score;

    // Stores the level (1-20)
    private int difficulty;

    // Hold piece
    private PieceName hold;

    // Next queue
    private ArrayList<PieceName> next = null;

    /* 
    Ideas for piece falling

    --------
    Idea 1:

    At the end of each user move, determine whether to use 
    fallCounter or lockCounter. If the move is a rotation,
    left-movement, or right-movement, and the maximum 
    number of move-delays hasn't been reached, reset the counters.

    When the piece is falling, counts up every 50 ms and soft-drops 
    the piece by 1 square when it reaches a certain maximum count 
    (depends on difficulty. (21 - difficulty) * 50 ms?).

    When the piece is on top of the stack, counts up every 50 ms and 
    locks when it reaches a maximum count (20?). Resets upon certain 
    actions: rotating, moving left or right.

    private int fallCounter;

    private int lockCounter;

    private boolean useLockCounter = false;

    -------
    Idea 2 (tentatively preferred):

    After certain actions, send a message to the browser that says to 
    send a new request after some delay. 
    
    When the block is in the air, tell the browser to send a 
    message to drop the block later (drop time determined by difficulty). 
    
    When the piece reaches the top of the stack, tell the browser to send a
    message to lock the block later. Upon certain actions (rotating, moving 
    left or right), tell the browser to reset the timer and restart it.
    */

    public TetrisGame() {
        this.board = new Board();
        this.score = 0;
        this.difficulty = 1;
        this.hold = PieceName.NOTHING;
        this.next = new ArrayList<PieceName>();
        // Here, generate starting pieces (random)
    }

    public TetrisGame(boolean debug) {
        this.board = new Board();
        this.score = 0;
        // Here, read info from debug file?
        this.difficulty = 1;
        this.hold = PieceName.NOTHING;
        this.next = new ArrayList<PieceName>();
    }
}