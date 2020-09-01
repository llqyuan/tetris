package com.ly.tetris.game;

import java.util.ArrayList;
import com.ly.tetris.infostructs.BoardUpdateMessage;
import com.ly.tetris.infostructs.EventMessage;
import com.ly.tetris.infostructs.TimerUpdateMessage;
import com.ly.tetris.infostructs.LineClearMessage;
import com.ly.tetris.infostructs.LocationPosn;
import com.ly.tetris.infostructs.Movement;
import com.ly.tetris.infostructs.PieceName;
import com.ly.tetris.infostructs.RotationDirection;

public class TetrisGame {

    // Game board.
    private Board board;

    // Score.
    private int score;

    // Level (1-15 valid)
    private int level;

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

    // True if a piece was previously held but no piece has 
    // been locked between then and now (in which case the player 
    // cannot spawn the currently held piece until the current 
    // piece locks)
    private boolean heldButNotLocked;

    // Number of consecutive line clears that were tspins or 
    // tetrises. Starts at -1, so that the first Tetris or T-spin 
    // increases this to 0.
    private int consecTetrisOrTSpin;

    // Number of consecutive piece-locks that cleared lines.
    // Starts at -1, so that dropping the first line-clearing 
    // piece increases this to 0.
    private int combo;

    // Last successful movement. Should be NOTHING immediately 
    // after a piece is spawned, and before its first movement.
    private Movement lastSuccessfulMovement;

    public TetrisGame() {
        this.board = new Board();
        this.score = 0;
        this.level = 1;
        this.hold = PieceName.NOTHING;
        this.next = new NextPiecesQueue();
        this.previousBoardCopy = this.board.copyOfBoard();
        this.height = this.previousBoardCopy.length;
        this.width = this.previousBoardCopy[0].length;
        this.lockTime = 500;
        this.softDropTime = 100;
        this.heldButNotLocked = false;
        this.consecTetrisOrTSpin = -1;
        this.combo = -1;
        this.lastSuccessfulMovement = Movement.NOTHING;
    }

    // ==================================================
    // Public interface
    // ==================================================

    // Spawns the first piece at the start of the game.
    // Only the first needs to be explicitly spawned; the rest will 
    // spawn automatically after a piece is dropped.
    // Requires:
    // * No game is currently running
    // Effects:
    // * Spawns a new piece on this.board
    // * Sets the last successful movement to NOTHING
    public BoardUpdateMessage startingPieceSpawn(EventMessage event) 
    throws Exception {
        if (!board.spawn(next.produceAndRemoveNextPieceInQueue())) {
            throw new IllegalStateException(
                "Unable to spawn a new piece at the start of the game.");
        }
        this.lastSuccessfulMovement = Movement.NOTHING;
        return this.produceBoardUpdate(
            event, 
            true,
            false, 
            true, 
            false, 
            this.fallInterval(),
            null);
    }

    // Moves the piece and returns information on the state of the 
    // resulting board.
    // If the piece is on the ground afterward, resets the lock timer.
    // If the piece is in the air afterward but was on the ground before,
    // sets a fall timer.
    // Effects:
    // * May move the piece in play left on this.board
    // * If the piece moved, sets the last successful movement to LEFT
    public BoardUpdateMessage moveLeft(EventMessage event) throws Exception {
        boolean inAirBefore = board.pieceIsInAir();
        boolean succeeded = board.moveLeft();
        if (succeeded) {
            this.lastSuccessfulMovement = Movement.LEFT;
        }
        boolean inAirAfter = board.pieceIsInAir();

        boolean updateFallTimer = false;
        boolean updateLockTimer = false;
        int requestNewUpdateIn = -1;
        if (!inAirAfter) {
            if (succeeded) {
                updateLockTimer = true;
                requestNewUpdateIn = this.lockTime;
            }
        } else if (!inAirBefore) {
            updateFallTimer = true;
            requestNewUpdateIn = this.fallInterval();
        }
        return this.produceBoardUpdate(
            event, 
            false,
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn,
            null);
    }

    // Moves the piece and return information on the state of the 
    // resulting board.
    // If the piece is on the ground afterward, resets the lock timer.
    // If the piece is in the air afterward but was on the ground before,
    // sets a fall timer.
    // Effects:
    // * May move the piece in play right on this.board
    // * If the piece moved, sets the last successful movement to RIGHT
    public BoardUpdateMessage moveRight(EventMessage event) throws Exception {
        boolean inAirBefore = board.pieceIsInAir();
        boolean succeeded = board.moveRight();
        if (succeeded) {
            this.lastSuccessfulMovement = Movement.RIGHT;
        }
        boolean inAirAfter = board.pieceIsInAir();

        boolean updateFallTimer = false;
        boolean updateLockTimer = false;
        int requestNewUpdateIn = -1;
        if (!inAirAfter) {
            if (succeeded) {
                updateLockTimer = true;
                requestNewUpdateIn = this.lockTime;
            }
        } else if (!inAirBefore) {
            updateFallTimer = true;
            requestNewUpdateIn = this.fallInterval();
        }
        return this.produceBoardUpdate(
            event, 
            false,
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn,
            null);
    }

    // Rotates the piece clockwise and returns information on the state
    // of the resulting board.
    // If the piece is on the ground afterward, resets the lock timer.
    // If the piece is not on the ground afterward but was on the 
    // ground before, cancels the lock timer and sets a new fall timer.
    // Effects:
    // * May rotate the piece in play on this.board
    // * If the piece rotated, sets the last successful movement to ROTATE
    public BoardUpdateMessage rotateClockwise(EventMessage event)
    throws Exception {
        boolean inAirBefore = board.pieceIsInAir();
        boolean succeeded = board.rotate(RotationDirection.CLOCKWISE);
        if (succeeded) {
            this.lastSuccessfulMovement = Movement.ROTATE;
        }
        boolean inAirAfter = board.pieceIsInAir();

        boolean updateFallTimer = false;
        boolean updateLockTimer = false;
        int requestNewUpdateIn = -1;
        if (!inAirAfter) {
            if (succeeded) {
                updateLockTimer = true;
                requestNewUpdateIn = this.lockTime;
            }
        } else if (!inAirBefore) {
            updateFallTimer = true;
            requestNewUpdateIn = this.fallInterval();
        }
        return this.produceBoardUpdate(
            event, 
            false,
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn,
            null);
    }

    // Rotates the piece clockwise and returns information on the state 
    // of the resulting board.
    // If the piece is on the ground afterwards, resets the lock timer.
    // If the piece is not on the ground afterward but was on the 
    // ground before, cancels the lock timer and sets a new fall timer.
    // Effects:
    // * May rotate the piece on this.board
    // * If the piece rotated, sets the last successful movement to ROTATE
    public BoardUpdateMessage rotateCounterClockwise(EventMessage event)
    throws Exception {
        boolean inAirBefore = board.pieceIsInAir();
        boolean succeeded = board.rotate(RotationDirection.COUNTERCLOCKWISE);
        if (succeeded) {
            this.lastSuccessfulMovement = Movement.ROTATE;
        }
        boolean inAirAfter = board.pieceIsInAir();

        boolean updateFallTimer = false;
        boolean updateLockTimer = false;
        int requestNewUpdateIn = -1;
        if (!inAirAfter) {
            if (succeeded) {
                updateLockTimer = true;
                requestNewUpdateIn = this.lockTime;
            }
        } else if (!inAirBefore) {
            updateFallTimer = true;
            requestNewUpdateIn = this.fallInterval();
        }
        return this.produceBoardUpdate(
            event, 
            false,
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn,
            null);
    }

    // Holds the piece. 
    // Effects:
    // * Holds the current piece on the board
    // * Sets this.heldButNotLocked to true
    // * Sets the fall timer for a newly spawned piece
    public BoardUpdateMessage hold(EventMessage event)
    throws Exception {
        if (this.heldButNotLocked) {
            return this.produceBoardUpdate(
                event, 
                false, 
                false, 
                false, 
                false, 
                -1,
                null);
        }
        PieceName putInHold = board.removePieceFromPlay();
        PieceName spawnPiece;
        if (hold == PieceName.NOTHING) {
            spawnPiece = next.produceAndRemoveNextPieceInQueue();
        } else {
            spawnPiece = hold;
        }
        if (board.spawn(spawnPiece)) {
            this.hold = putInHold;
            this.heldButNotLocked = true;
            return this.produceBoardUpdate(
                event, 
                true, 
                false, 
                true, 
                false, 
                this.fallInterval(),
                null);
        } else {
            return this.produceBoardUpdate(
                event, 
                true, 
                true, 
                false, 
                false, 
                -1,
                null);
        }
    }

    // Hard drops the piece (manual). Attempts to spawn a new piece.
    // If the spawn was successful, sets the fall timer.
    // Otherwise tells the browser to end the game.
    // Effects:
    // * Hard drops the current piece on the board
    // * Updates the score
    // * Resets the flag this.heldButNotLocked
    // * May spawn a new piece on the board
    public BoardUpdateMessage hardDrop(EventMessage event)
    throws Exception {
        // board.hardDrop();
        int distanceDropped = board.distanceOfPieceToBottom();
        LineClearMessage lineClearInfo = this.hardDropAndCalculateBonuses();
        boolean spawnUnsuccessful = 
            !board.spawn(next.produceAndRemoveNextPieceInQueue());
        this.heldButNotLocked = false;
        this.updateScore(lineClearInfo, false, true, 0, distanceDropped);
        if (spawnUnsuccessful) {
            return this.produceBoardUpdate(
                event, 
                true, 
                spawnUnsuccessful, 
                false, 
                false, 
                -1,
                lineClearInfo);
        } else if (board.pieceIsInAir()) {
            return this.produceBoardUpdate(
                event, 
                true, 
                spawnUnsuccessful, 
                true, 
                false, 
                this.fallInterval(),
                lineClearInfo);
        } else {
            return this.produceBoardUpdate(
                event, 
                true, 
                spawnUnsuccessful, 
                false, 
                true, 
                this.lockTime,
                lineClearInfo);
        }
    }

    // Sonic-drops the piece.
    // If the piece was in the air before, stop the fall timer 
    // and set the lock timer.
    // Otherwise does not update the timer.
    // Effects:
    // * Updates the score
    // * May move the piece down on this.board
    // * If the piece moved down, sets the last successful movement to DOWN
    public BoardUpdateMessage sonicDrop(EventMessage event)
    throws Exception {
        int distanceDropped = board.distanceOfPieceToBottom();
        boolean inAirBefore = board.pieceIsInAir();
        board.sonicDrop();
        if (inAirBefore) {
            this.lastSuccessfulMovement = Movement.DOWN;
        }

        boolean updateFallTimer = false;
        boolean updateLockTimer = false;
        int requestNewUpdateIn = -1;
        if (inAirBefore) {
            updateLockTimer = true;
            requestNewUpdateIn = this.lockTime;
        }
        this.updateScore(
            new LineClearMessage(), true, false, distanceDropped, 0);
        return this.produceBoardUpdate(
            event, 
            false, 
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn,
            null);
    }

    // [unused] Soft drops the piece by one row (manual). Ignore for 
    // certain levels (where a piece's fall from softdropping is 
    // slower than the fall from gravity alone).
    // If the piece is in the air afterwards, resets the fall timer.
    // If the piece is in the air before and on the ground afterwards, 
    // stops the fall timer and starts the lock timer.
    // If the piece is on the ground both before and afterwards, 
    // updates neither timer.
    // Effects:
    // * May move the piece down on this.board
    // * If the piece moved down, sets the last successful movement to DOWN
    public BoardUpdateMessage softDrop(EventMessage event)
    throws Exception {
        boolean inAirBefore = board.pieceIsInAir();
        if (this.softDropTime < this.fallInterval()) {
            boolean successful = board.moveDown();
            if (successful) {
                this.lastSuccessfulMovement = Movement.DOWN;
            }
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
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn,
            null);
    }

    // Locks the piece if it is on the ground, or soft-drops it 
    // by one row if it is in the air (automatic).
    // Effects:
    // * Locks the piece or soft-drops it by one row
    // * If the piece moved down by one row, sets the last successful movement 
    //   to DOWN
    public BoardUpdateMessage automaticFallOrLock(EventMessage event)
    throws Exception {
        if (board.pieceIsInAir()) {
            this.lastSuccessfulMovement = Movement.DOWN;
            return this.gravityDrop(event);
        } else {
            return this.automaticLock(event);
        }
    }


    // =========================================================
    // Private helper methods
    // =========================================================

    // Locks the piece on the ground (automatic, delayed lock).
    // Spawns a new piece and sets the fall timer.
    // Only appropriate to call if the piece is on the ground.
    // Requires:
    // * The piece in play is on the ground
    // Effects:
    // * Updates the score
    // * Locks the current piece on the board
    // * Resets the flag this.heldButNotLocked
    // * Spawns a new piece on the board
    private BoardUpdateMessage automaticLock(EventMessage event)
    throws Exception {
        if (board.pieceIsInAir()) {
            throw new IllegalStateException(
                "Called automaticLock() at an inappropriate time. " +
                "The piece was in the air. gravityDrop() should have " +
                "been called instead.");
        }
        // board.hardDrop();
        LineClearMessage lineClearInfo = this.hardDropAndCalculateBonuses();
        boolean spawnUnsuccessful = 
            !board.spawn(next.produceAndRemoveNextPieceInQueue());
        this.heldButNotLocked = false;
        this.updateScore(lineClearInfo, false, false, 0, 0);
        if (spawnUnsuccessful) {
            return this.produceBoardUpdate(
                event, 
                true, 
                spawnUnsuccessful, 
                false, 
                false, 
                -1,
                lineClearInfo);
        } else if (board.pieceIsInAir()) {
            this.lastSuccessfulMovement = Movement.NOTHING;
            return this.produceBoardUpdate(
                event, 
                true, 
                spawnUnsuccessful, 
                true, 
                false, 
                this.fallInterval(),
                lineClearInfo);
        } else {
            this.lastSuccessfulMovement = Movement.NOTHING;
            return this.produceBoardUpdate(
                event, 
                true, 
                spawnUnsuccessful, 
                false, 
                true, 
                this.lockTime,
                lineClearInfo);
        }
    }

    // Soft drops the piece by one row (automatic, due to gravity).
    // If the piece is in the air afterwards, start a new fall timer.
    // If the piece is on the ground afterwards, start a lock timer.
    // Only appropriate to call when the piece is in the air.
    // Requires:
    // * The piece in play is in the air
    // Effects:
    // * Moves the piece down on this.board
    private BoardUpdateMessage gravityDrop(EventMessage event) 
    throws Exception {
        if (!board.moveDown()) {
            throw new IllegalStateException(
                "Called gravityDrop() at an inappropriate time. " +
                "The piece was on the ground and unable to move down. " +
                "automaticLock() should be called instead.");
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
            false, 
            updateFallTimer, 
            updateLockTimer, 
            requestNewUpdateIn,
            null);
    }


    /* 
    Returns true if all of the below are true, or false otherwise:
    1. The piece in play is T
    2. The last successful movement was a rotation
    3. At least three squares adjacent to the center of the T
       are occupied on the board
    */
    private boolean validTSpinIfLockedNow() {
        boolean lastWasRotation = 
            this.lastSuccessfulMovement == Movement.ROTATE;
        boolean threeAreOccupied = 
            board.pieceIsTAndThreeAdjacentToCenterAreOccupied();
        return lastWasRotation && threeAreOccupied;
    }

    /* 
    Drops the piece and returns the calculated LineClearMessage.
    Effects:
    * Hard-drops the piece
    * Modifies the flag this.consecTetrisOrTSpin
    * Modifies the flag this.consecLineClears
    */
    private LineClearMessage hardDropAndCalculateBonuses()
    throws Exception {
        boolean isTSpin = validTSpinIfLockedNow();
        int linesCleared = board.hardDrop();
        boolean isPerfectClear = board.isEmpty();
        if (linesCleared == 4 || (isTSpin && linesCleared > 0)) {
            this.consecTetrisOrTSpin += 1;
            this.combo += 1;
        } else if (linesCleared > 0) {
            this.consecTetrisOrTSpin = -1;
            this.combo += 1;
        } else {
            this.combo = -1;
        }
        return new LineClearMessage(
            isTSpin, 
            linesCleared, 
            this.consecTetrisOrTSpin, 
            this.combo,
            isPerfectClear);
    }


    /*
    Updates the score. Scoring mimics that of recent Tetris Guideline-adherent
    games as closely as possible.
    The value of distanceSonicDropped does not matter if sonicDropped is false.
    The value of distanceHardDropped does not matter if hardDropped is false.
    
    Requires:
    * lineClearInfo is not null
    Effects:
    * Modifies this.score
    */
    private void updateScore(
        LineClearMessage lineClearInfo, 
        boolean sonicDropped,
        boolean hardDropped,
        int distanceSonicDropped,
        int distanceHardDropped)
    throws NullPointerException
    {
        if (lineClearInfo == null) {
            throw new NullPointerException("lineClearInfo is null.");
        }

        int addToScore = 0;

        final int lines = lineClearInfo.getLinesCleared();
        final int backToBacks = lineClearInfo.getConsecTetrisOrTSpin();
        final int combo = lineClearInfo.getCombo();
        final boolean isTSpin = lineClearInfo.getTSpin();
        
        if (!isTSpin) {
            switch (lines) {
                case 1:
                    addToScore += 100 * this.level;
                    break;
                case 2:
                    addToScore += 300 * this.level;
                    break;
                case 3:
                    addToScore += 500 * this.level;
                    break;
                case 4:
                    if (backToBacks >= 1) {
                        addToScore += 1200 * this.level;
                    } else {
                        addToScore += 800 * this.level;
                    }
                    break;
            }

        } else if (backToBacks >= 1) {
            switch (lines) {
                case 0:
                    addToScore += 600 * this.level;
                case 1:
                    addToScore += 1200 * this.level;
                    break;
                case 2:
                    addToScore += 1800 * this.level;
                    break;
                case 3:
                    addToScore += 2400 * this.level;
                    break;
            }

        } else {
            switch (lines) {
                case 0:
                    addToScore += 400 * this.level;
                case 1:
                    addToScore += 800 * this.level;
                    break;
                case 2:
                    addToScore += 1200 * this.level;
                    break;
                case 3:
                    addToScore += 1600 * this.level;
                    break;
            }
        }
    
        if (combo >= 1) {
            addToScore += 50 * combo;
        }

        if (sonicDropped) {
            addToScore += distanceSonicDropped;

        } else if (hardDropped) {
            addToScore += 2 * distanceHardDropped;
        }

        this.score += addToScore;
    }

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
        boolean spawnedNewPiece,
        boolean spawnWasUnsuccessful,
        boolean updateFallTimer,
        boolean updateLockTimer, 
        int requestNewUpdateIn,
        LineClearMessage lineClearInfo) 
    {
        PieceName inPlay = board.pieceInPlay();
        ArrayList<LocationPosn> squaresOfPieceInPlay = 
            board.squaresOccupiedByPieceInPlay();
        ArrayList<LocationPosn> squaresOfHardDropGhost = 
            board.squaresOccupiedByHardDropGhost();

        ArrayList<Square> drawOnStack = new ArrayList<Square>();
        Square[][] boardCopy = board.copyOfBoard();
        for (int r = 0; r < this.height; r++) {
            for (int c = 0; c < this.width; c++) {
                if (!(boardCopy[r][c].equals(previousBoardCopy[r][c]))) {
                    drawOnStack.add(boardCopy[r][c]);
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
                drawOnStack,
                hold,
                this.heldButNotLocked,
                nextFivePieces,
                spawnedNewPiece,
                spawnWasUnsuccessful,
                timerUpdate,
                this.score,
                lineClearInfo,
                event.getKeyCommand()
            );
        return message;
    }

    /*
    Returns the interval between successive commands to 
    automatically drop the piece by one block, depending on
    the current level.

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

    Problem: higher levels would be less stable because of the connection
    being swamped by automatic-drop requests
    */
    private int fallInterval() {
        return 1000;
    }
}