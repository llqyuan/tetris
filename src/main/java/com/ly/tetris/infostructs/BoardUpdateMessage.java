package com.ly.tetris.infostructs;

import java.util.ArrayList;
import com.ly.tetris.game.Square;

/*

* location and type of piece in play, hard drop ghost
* current tetris stack
* piece in hold
* next 5 pieces in the next queue
* whether new spawn was unsuccessful
* start or update a timer (fall timer, lock timer)

*/

public class BoardUpdateMessage {

    private PieceName pieceInPlay;
    private ArrayList<LocationPosn> squaresOfPieceInPlay;
    private ArrayList<LocationPosn> squaresOfHardDropGhost;
    private ArrayList<Square> changesToStack;

    private PieceName hold;
    private boolean sealHoldPiece;
    private ArrayList<PieceName> nextFivePieces;
    private boolean spawnedPiece;
    private boolean spawnUnsuccessful;
    private TimerUpdateMessage timerUpdate;
    private int score;

    private KeyCommand acknowledge;

    public BoardUpdateMessage() {
        this.pieceInPlay = PieceName.NOTHING;
        this.squaresOfPieceInPlay = new ArrayList<LocationPosn>();
        this.squaresOfHardDropGhost = new ArrayList<LocationPosn>();
        this.changesToStack = new ArrayList<Square>();
        this.hold = PieceName.NOTHING;
        this.sealHoldPiece = false;
        this.nextFivePieces = new ArrayList<PieceName>();
        this.spawnedPiece = false;
        this.spawnUnsuccessful = false;
        this.timerUpdate = new TimerUpdateMessage();
        this.score = 0;
        this.acknowledge = KeyCommand.NOTHING;
    }

    public BoardUpdateMessage(
        PieceName pieceInPlay,
        ArrayList<LocationPosn> squaresOfPieceInPlay,
        ArrayList<LocationPosn> squaresOfHardDropGhost,
        ArrayList<Square> changesToStack,
        PieceName hold,
        boolean sealHoldPiece,
        ArrayList<PieceName> nextFivePieces,
        boolean spawnedPiece,
        boolean spawnUnsuccessful,
        TimerUpdateMessage timerUpdate,
        int score,
        KeyCommand key
    ) {
        this.pieceInPlay = pieceInPlay;
        this.squaresOfPieceInPlay = squaresOfPieceInPlay;
        this.squaresOfHardDropGhost = squaresOfHardDropGhost;
        this.changesToStack = changesToStack;
        this.hold = hold;
        this.sealHoldPiece = sealHoldPiece;
        this.nextFivePieces = nextFivePieces;
        this.spawnedPiece = spawnedPiece;
        this.spawnUnsuccessful = spawnUnsuccessful;
        this.timerUpdate = timerUpdate;
        this.score = score;
        this.acknowledge = key;
    }

    public PieceName getPieceInPlay() {
        return pieceInPlay;
    }

    public ArrayList<LocationPosn> getSquaresOfPieceInPlay() {
        return squaresOfPieceInPlay;
    }

    public ArrayList<LocationPosn> getSquaresOfHardDropGhost() {
        return squaresOfHardDropGhost;
    }

    public ArrayList<Square> getChangesToStack() {
        return changesToStack;
    }

    public PieceName getHold() {
        return hold;
    }

    public boolean getSealHoldPiece() {
        return sealHoldPiece;
    }

    public ArrayList<PieceName> getNextFivePieces() {
        return nextFivePieces;
    }

    public boolean getSpawnedPiece() {
        return spawnedPiece;
    }

    public boolean getSpawnUnsuccessful() {
        return spawnUnsuccessful;
    }

    public TimerUpdateMessage getTimerUpdate() {
        return timerUpdate;
    }

    public int getScore() {
        return score;
    }

    public KeyCommand getAcknowledge() {
        return acknowledge;
    }
}