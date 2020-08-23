package com.ly.tetris.game;

import java.util.ArrayList;
import com.ly.tetris.infostructs.BoardUpdateMessage;
import com.ly.tetris.infostructs.EventMessage;
import com.ly.tetris.infostructs.TimerUpdateMessage;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.PieceName;

/* 
Ideas for piece falling

Idea 2:

After certain actions, send a message to the browser that says to 
send a new request after some delay. 

When the block is in the air, tell the browser to send a 
message to drop the block later (time determined by difficulty). 

When the piece reaches the top of the stack, tell the browser to send a
message to lock the block later. Upon certain actions (rotating, moving 
left or right), tell the browser to reset the timer and restart it.

*/

public class TetrisGame {

    // Game board.
    private Board board;

    // Score.
    private int score;

    // Level (1-15)
    private int difficulty;

    // Hold piece
    private PieceName hold;
    
    // Next queue
    private NextPiecesQueue next;

    // Height of board.
    private final int height;

    // Width of board.
    private final int width;

    // Time to piece lock in milliseconds, if the piece is 
    // on the ground and is not moved or rotated.
    // (Tetris guideline: 500 milliseconds)
    private final int lockTime;

    // Time spent on each row when the player soft-drops the 
    // piece.
    private final int softDropTime;

    // Copy of board as of previous update.
    private Square[][] previousBoardCopy;

    public TetrisGame() {
        this.board = new Board();
        this.score = 0;
        this.difficulty = 1;
        this.hold = PieceName.NOTHING;
        this.next = new NextPiecesQueue();
        this.previousBoardCopy = this.board.copyOfBoard();
        this.height = this.previousBoardCopy.length;
        this.width = this.previousBoardCopy[0].length;
        this.lockTime = 500;
        this.softDropTime = 100;
    }

    public TetrisGame(boolean debug) {
        this.board = new Board();
        this.score = 0;
        this.difficulty = 1;
        this.hold = PieceName.NOTHING;
        // Read info from debug file to generate starting lineup?
        this.next = new NextPiecesQueue();
        this.previousBoardCopy = this.board.copyOfBoard();
        this.height = this.previousBoardCopy.length;
        this.width = this.previousBoardCopy[0].length;
        this.lockTime = 500;
        this.softDropTime = 100;
    }

    // ==================================================
    // Public interface
    // ==================================================

    // Spawns the first piece at the start of the game.
    // Only the first needs to be explicitly spawned; the rest will 
    // spawn automatically after a piece is dropped.
    public BoardUpdateMessage startingPieceSpawn(EventMessage event) 
    throws Exception {
        if (!board.spawn(next.produceAndRemoveNextPieceInQueue())) {
            throw new IllegalStateException(
                "Unable to spawn a new piece at the start of the game.");
        }
        return this.produceBoardUpdate(
            event, 
            false, 
            true, 
            false, 
            this.fallInterval());
    }

    // Moves the piece and returns information on the state of the 
    // resulting board.
    // If the piece is on the ground, resets the lock timer.
    public BoardUpdateMessage moveLeft(EventMessage event) throws Exception {
        board.moveLeft();
        return this.produceBoardUpdate(
            event, 
            false, 
            false,
            false, 
            -1);
    }

    // Moves the piece and return information on the state of the 
    // resulting board.
    // If the piece is on the ground, resets the lock timer.
    public BoardUpdateMessage moveRight(EventMessage event) throws Exception {
        board.moveRight();
        return this.produceBoardUpdate(
            event, 
            false, 
            false,
            false,
            -1);
    }

    // Soft drops the piece by one row (manual). Ignore for 
    // certain levels (where a piece's fall from softdropping is 
    // slower than the fall from gravity alone).
    // If the piece is in the air afterwards, resets the fall timer.
    // If the piece is in the air before and on the ground afterwards, 
    // stops the fall timer and starts the lock timer.
    // If the piece is on the ground both before and afterwards, 
    // updates neither timer.
    public BoardUpdateMessage softDrop(EventMessage event)
    throws Exception {
        boolean inAirBefore = board.pieceIsInAir();
        if (this.softDropTime < this.fallInterval()) {
            board.moveDown();
        }
        boolean inAirAfter = board.pieceIsInAir();

        boolean updateFallTimer = false;
        boolean updateLockTimer = false;
        int requestNewUpdateIn = -1;
        if (inAirAfter) {
            updateFallTimer = true;
            requestNewUpdateIn = this.fallInterval();
        } else if (inAirBefore) {
            updateLockTimer = true;
            requestNewUpdateIn = this.lockTime;
        }
        
        return this.produceBoardUpdate(
            event, 
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn);
    }

    // Soft drops the piece by one row (automatic, due to gravity).
    // If the piece is in the air afterwards, start a new fall timer.
    // If the piece is on the ground afterwards, start a lock timer.
    // Only appropriate to call when the piece is in the air.
    // Requires:
    // * There is a piece in play
    // * The piece in play is in the air
    public BoardUpdateMessage gravityDrop(EventMessage event) 
    throws Exception {
        if (!board.moveDown()) {
            throw new IllegalStateException(
                "Called gravityDrop() at an inappropriate time. " +
                "The piece was on the ground and unable to move down. " +
                "[lock function] should be called instead.");
        }
        boolean updateFallTimer;
        boolean updateLockTimer;
        int requestNewUpdateIn;
        if (board.pieceIsInAir()) {
            updateFallTimer = true;
            updateLockTimer = false;
            requestNewUpdateIn = this.fallInterval();
        } else {
            updateFallTimer = false;
            updateLockTimer = true;
            requestNewUpdateIn = this.lockTime;
        }
        return this.produceBoardUpdate(
            event, 
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn);
    }


    // =========================================================
    // Private helper methods
    // =========================================================

    // Returns a message detailing the state of the board.
    // This should be called once between each command from the 
    // player. 
    // * event should be the message from the controller. 
    // * spawnWasUnsuccessful should be true if the game attempted 
    //   to spawn a new piece but failed due 
    //   to not having room.
    // * updateFallTimer, updateLockTimer, and requestNewUpdateIn 
    //   are as described in TimerUpdateMessage and are meant to 
    //   be passed to a TimerUpdateMessage constructor.
    // Effects:
    // * Updates previousBoardCopy
    private BoardUpdateMessage produceBoardUpdate(
        EventMessage event, 
        boolean spawnWasUnsuccessful,
        boolean updateFallTimer,
        boolean updateLockTimer, 
        int requestNewUpdateIn) 
    {
        PieceName inPlay = board.pieceInPlay();
        ArrayList<LocationPosn> squaresOfPieceInPlay = 
            board.squaresOccupiedByPieceInPlay();
        ArrayList<LocationPosn> squaresOfHardDropGhost = 
            board.squaresOccupiedByHardDropGhost();

        ArrayList<Square> changesToStack = new ArrayList<Square>();
        Square[][] boardCopy = board.copyOfBoard();
        for (int r = 0; r < this.height; r++) {
            for (int c = 0; c < this.width; c++) {
                if (!(boardCopy[r][c].equals(previousBoardCopy[r][c]))) {
                    changesToStack.add(boardCopy[r][c]);
                }
            }
        }
        previousBoardCopy = boardCopy;

        PieceName hold = this.hold;
        ArrayList<PieceName> nextFivePieces = next.peekNextFivePieces();
        TimerUpdateMessage timerUpdate = 
            new TimerUpdateMessage(
                updateFallTimer, 
                updateLockTimer, 
                requestNewUpdateIn);

        BoardUpdateMessage message = 
            new BoardUpdateMessage(
                inPlay,
                squaresOfPieceInPlay,
                squaresOfHardDropGhost,
                changesToStack,
                hold,
                nextFivePieces,
                spawnWasUnsuccessful,
                timerUpdate,
                this.score,
                event.getKeyCommand()
            );
        return message;
    }

    // todo
    // Returns the interval between successive commands to 
    // automatically drop the piece by one block, depending on
    // the current difficulty.
    private int fallInterval() {
        /*
        Formula from Tetris Worlds for time spent per row in 
        milliseconds:

        1000 * (0.8 - 0.007 (Level - 1)) ^ (Level - 1) 

        1: 1000
        2: 793
        3: 618 (rounded up from 617.796)
        4: 473 (rounded up from ~472.73)
        5: 355 (rounded down from ~355.196)
        6: 262 (rounded down from ~262.004)
        7: 190 (rounded up from ~189.68)
        8: 135 (rounded up from ~134.735)
        9: 94 (rounded up from ~93.882)
        10: 64 (rounded down from ~64.158)
        11: 43 (rounded up from ~42.976)
        12: 28 (rounded down from ~28.218)
        13: 18 (rounded down from ~18.153)
        14: 11 (rounded down from ~11.439)
        15: 7 (rounded down from ~7.059)

        Problem: latency will inflate the time for higher levels. Maybe 
        omit higher levels?
        */
        return 1000;
    }
}