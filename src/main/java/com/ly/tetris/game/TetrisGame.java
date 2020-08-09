package com.ly.tetris.game;

import java.util.ArrayList;
import com.ly.tetris.infostructs.PieceName;

public class TetrisGame {
    // Stores the score.
    private int score;

    // Stores the level (1-20)
    private int difficulty;

    // Hold piece
    private PieceName hold;

    // Next queue
    private ArrayList<PieceName> next;

    // At the end of each user move, determine whether to use 
    // fallCounter or lockCounter. If the move is a rotation,
    // left-movement, or right-movement, and the maximum 
    // number of move-delays hasn't been reached, reset the counters.

    // When the piece is falling, counts up every 50 ms and soft-drops 
    // the piece by 1 square when it reaches a certain maximum count 
    // (depends on difficulty. (21 - difficulty) * 50 ms?).
    private int fallCounter;

    // When the piece is on top of the stack, counts up every 50 ms and 
    // locks when it reaches a maximum count (20?). Resets upon certain 
    // actions: rotating, moving left or right.
    private int lockCounter;

    private boolean useLockCounter = false;

    public TetrisGame() {
        this.score = 0;
        this.difficulty = 1;
        this.hold = PieceName.NOTHING;
        this.fallCounter = 0;
        // Here, generate starting pieces (random)
    }

    public TetrisGame(boolean debug) {
        this.score = 0;
        // Here, read info from debug file?
        this.difficulty = 1;
        this.hold = PieceName.NOTHING;
        this.fallCounter = 0;
    }
}