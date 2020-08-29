package com.ly.tetris.infostructs;

import java.util.ArrayList;
import com.ly.tetris.game.Square;

public class BoardUpdateMessage {

    private PieceName pieceInPlay;
    private ArrayList<LocationPosn> squaresOfPieceInPlay;
    private ArrayList<LocationPosn> squaresOfHardDropGhost;
    private ArrayList<Square> drawOnStack;

    private PieceName hold;
    private boolean sealHoldPiece;
    private ArrayList<PieceName> nextFivePieces;
    private boolean spawnedPiece;
    private boolean spawnUnsuccessful;
    private TimerUpdateMessage timerUpdate;
    private int score;
    private LineClearMessage lineClearInfo;

    private KeyCommand acknowledge;

    public BoardUpdateMessage() {
        this.pieceInPlay = PieceName.NOTHING;
        this.squaresOfPieceInPlay = new ArrayList<LocationPosn>();
        this.squaresOfHardDropGhost = new ArrayList<LocationPosn>();
        this.drawOnStack = new ArrayList<Square>();
        this.hold = PieceName.NOTHING;
        this.sealHoldPiece = false;
        this.nextFivePieces = new ArrayList<PieceName>();
        this.spawnedPiece = false;
        this.spawnUnsuccessful = false;
        this.timerUpdate = new TimerUpdateMessage();
        this.score = 0;
        this.lineClearInfo = new LineClearMessage();
        this.acknowledge = KeyCommand.NOTHING;
    }

    public BoardUpdateMessage(
        PieceName pieceInPlay,
        ArrayList<LocationPosn> squaresOfPieceInPlay,
        ArrayList<LocationPosn> squaresOfHardDropGhost,
        ArrayList<Square> drawOnStack,
        PieceName hold,
        boolean sealHoldPiece,
        ArrayList<PieceName> nextFivePieces,
        boolean spawnedPiece,
        boolean spawnUnsuccessful,
        TimerUpdateMessage timerUpdate,
        int score,
        LineClearMessage lineClearInfo,
        KeyCommand key
    ) {
        this.pieceInPlay = pieceInPlay;
        this.squaresOfPieceInPlay = squaresOfPieceInPlay;
        this.squaresOfHardDropGhost = squaresOfHardDropGhost;
        this.drawOnStack = drawOnStack;
        this.hold = hold;
        this.sealHoldPiece = sealHoldPiece;
        this.nextFivePieces = nextFivePieces;
        this.spawnedPiece = spawnedPiece;
        this.spawnUnsuccessful = spawnUnsuccessful;
        this.timerUpdate = timerUpdate;
        this.score = score;
        this.lineClearInfo = lineClearInfo;
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

    public ArrayList<Square> getDrawOnStack() {
        return drawOnStack;
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

    public LineClearMessage getLineClearInfo() {
        return lineClearInfo;
    }

    public KeyCommand getAcknowledge() {
        return acknowledge;
    }
}