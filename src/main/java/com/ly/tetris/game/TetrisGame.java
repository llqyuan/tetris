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

    // Counts up every 50 ms and soft-drops the piece by 1 square 
    // when it reaches a certain maximum count (depends on difficulty).
    // (If the piece is already at the bottom, determine whether it 
    // should be locked)
    private int fallCounter;

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