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
    private ArrayList<PieceName> nextFivePieces;
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
        this.nextFivePieces = new ArrayList<PieceName>();
        this.spawnUnsuccessful = false;
        this.timerUpdate = new TimerUpdateMessage();
        this.score = 0;
        this.acknowledge = KeyCommand.NOTHING;
    }

    // temp, remove/modify
    public BoardUpdateMessage(KeyCommand key) {
        this.pieceInPlay = PieceName.NOTHING;
        this.squaresOfPieceInPlay = new ArrayList<LocationPosn>();
        squaresOfPieceInPlay.add(new LocationPosn(0, 0));
        this.squaresOfHardDropGhost = new ArrayList<LocationPosn>();
        squaresOfHardDropGhost.add(new LocationPosn(0, 0));
        this.changesToStack = new ArrayList<Square>();
        changesToStack.add(new Square(0, 0));
        this.hold = PieceName.NOTHING;
        this.nextFivePieces = new ArrayList<PieceName>();
        nextFivePieces.add(PieceName.I);
        this.spawnUnsuccessful = false;
        this.timerUpdate = new TimerUpdateMessage();
        this.score = 0;
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

    public ArrayList<PieceName> getNextFivePieces() {
        return nextFivePieces;
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